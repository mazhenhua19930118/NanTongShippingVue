<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="定金" prop="deposit">
        <el-input
          v-model="queryParams.deposit"
          placeholder="请输入定金"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="航费" prop="shippingCost">
        <el-input
          v-model="queryParams.shippingCost"
          placeholder="请输入航费"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否预保险" prop="isPreInsurance">
        <el-select v-model="queryParams.isPreInsurance" placeholder="请选择是否预保险" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['dispatch:dispatch:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['dispatch:dispatch:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dispatch:dispatch:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['dispatch:dispatch:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dispatchList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderId" />
      <el-table-column label="定金" align="center" prop="deposit" />
      <el-table-column label="航费" align="center" prop="shippingCost" />
      <el-table-column label="是否预保险" align="center" prop="isPreInsurance">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isPreInsurance"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dispatch:dispatch:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dispatch:dispatch:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="dispatchOrder(scope.row)"
            v-hasPermi="['dispatch:dispatch:edit']"
          >派单</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改调度管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="定金" prop="deposit">
          <el-input v-model="form.deposit" placeholder="请输入定金" />
        </el-form-item>
        <el-form-item label="航费" prop="shippingCost">
          <el-input v-model="form.shippingCost" placeholder="请输入航费" />
        </el-form-item>
        <el-form-item label="是否预保险" prop="isPreInsurance">
          <el-select v-model="form.isPreInsurance" placeholder="请选择是否预保险">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="派单"
               :visible.sync="isDispatchOrderShow"
               width="500px"
               append-to-body>
      <el-table ref="multipleTable"
                :data="couriers"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                @selection-change="handleSelectionChange">
        <!-- 使用多选框列 -->
        <el-table-column type="selection"
                         width="55">
        </el-table-column>
        <!-- 人员名字列 -->
        <el-table-column prop="userName"
                         label="内勤人员"
                         width="200">
        </el-table-column>
        <!-- 其他信息列（可选） -->
        <el-table-column prop="phonenumber"
                         label="联系电话"
                         width="200">
        </el-table-column>
      </el-table>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="primary"
                   @click="submitSelection">确 定</el-button>
        <el-button @click="dispatchCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDispatch, getDispatch, delDispatch, addDispatch, updateDispatch,selectNqPeople,getUserByDispatchGoods  } from "@/api/dispatch/dispatch"
import { getFieldsFromList, arrLength, formatTaskStatus } from "@/common/common";
export default {
  name: "Dispatch",
  dicts: ['sys_yes_no'],
  data() {
    return {
      isDispatchOrderShow: false,
      tableRowClassName: '',
      couriers: [],
      selectedCouriers: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调度管理表格数据
      dispatchList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        deposit: null,
        shippingCost: null,
        isPreInsurance: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
    this.getNqList();
  },
  methods: {
    dispatchCancel () {
      this.isDispatchOrderShow = false;
    },
    /** 选择派送的人员提交*/
    submitSelection () {
      if (this.selectedCouriers.length === 0) {
        this.$message.warning('请选择至少一个人员');
      } else if (this.selectedCouriers.length > 1) {
        this.$message.warning('只能选择一个人员');
      } else {
        let data = {};
        let userId = getFieldsFromList(this.selectedCouriers, "userId")[0];
        data.goodsId = this.goodsId;
        data.assignUserId = userId;
        console.log("data", data)
        dispatchToUser(data).then(response => {
          if (response.code === 200) {
            this.$modal.msgSuccess("操作成功");
            this.getList();
          } else {
            this.$modal.msgError(response.msg);
          }
        });
        this.isDispatchOrderShow = false;
      }
    },
    /** 查询所有内勤人员*/
    getNqList () {
      selectNqPeople().then(response => {
        this.couriers = response.data;
      });
    },
    /** 查询调度管理列表 */
    getList() {
      this.loading = true
      listDispatch(this.queryParams).then(response => {
        this.dispatchList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderId: null,
        deposit: null,
        shippingCost: null,
        isPreInsurance: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加调度管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getDispatch(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改调度管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDispatch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addDispatch(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除调度管理编号为"' + ids + '"的数据项？').then(function() {
        return delDispatch(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('dispatch/dispatch/export', {
        ...this.queryParams
      }, `dispatch_${new Date().getTime()}.xlsx`)
    },
    /** 派单*/
    dispatchOrder (row) {
      this.isDispatchOrderShow = true;
      this.tableRowClassName = '';
      //清空选中
      if (this.$refs.multipleTable) {
        this.$refs.multipleTable.clearSelection();
      }
      this.orderId = '';
      this.orderId = row.orderId;
      let data = {};
      data.orderId = row.orderId
      //查询本单派送人员
      getUserByDispatchGoods(data).then(response => {
        if (arrLength(response.data) > 0) {
          let userId = response.data[0]
          this.couriers.forEach(v => {
            if (v.userId === userId) {
              this.$refs.multipleTable.toggleRowSelection(v, true);
              this.tableRowClassName = 'row-disabled';
            }
          });
        }
      });
    },
  }
}
</script>
