<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="主合同编号" prop="mainContractNo">
        <el-input
          v-model="queryParams.mainContractNo"
          placeholder="请输入主合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主合同名称" prop="mainContractName">
        <el-input
          v-model="queryParams.mainContractName"
          placeholder="请输入主合同名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="子合同编号" prop="subContractNo">
        <el-input
          v-model="queryParams.subContractNo"
          placeholder="请输入子合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="子合同名称" prop="subContractName">
        <el-input
          v-model="queryParams.subContractName"
          placeholder="请输入子合同名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单编号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主合同是否签订" prop="mainContractSign">
        <el-select v-model="queryParams.mainContractSign" placeholder="请选择主合同是否签订" clearable>
          <el-option
            v-for="dict in dict.type.ship_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="子合同是否签订" prop="subContractSign">
        <el-select v-model="queryParams.subContractSign" placeholder="请选择子合同是否签订" clearable>
          <el-option
            v-for="dict in dict.type.ship_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户号" prop="customerNo">
        <el-input
          v-model="queryParams.customerNo"
          placeholder="请输入客户号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['contract:contract:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['contract:contract:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['contract:contract:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['contract:contract:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="${comment}" align="center" prop="id" />-->
      <el-table-column label="主合同编号" align="center" prop="mainContractNo" />
      <el-table-column label="主合同名称" align="center" prop="mainContractName" />
      <el-table-column label="子合同编号" align="center" prop="subContractNo" />
      <el-table-column label="子合同名称" align="center" prop="subContractName" />
      <el-table-column label="订单编号" align="center" prop="orderId" />
      <el-table-column label="主合同是否签订1" align="center" prop="mainContractSign">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ship_yes_no" :value="scope.row.mainContractSign"/>
        </template>
      </el-table-column>
      <el-table-column label="子合同是否签订" align="center" prop="subContractSign">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ship_yes_no" :value="scope.row.subContractSign"/>
        </template>
      </el-table-column>
      <el-table-column label="客户号" align="center" prop="customerNo" />
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contract:contract:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contract:contract:remove']"
          >删除</el-button>
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

    <!-- 添加或修改合同管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="主合同编号" prop="mainContractNo">
          <el-input v-model="form.mainContractNo" placeholder="请输入主合同编号" />
        </el-form-item>
        <el-form-item label="主合同名称" prop="mainContractName">
          <el-input v-model="form.mainContractName" placeholder="请输入主合同名称" />
        </el-form-item>
        <el-form-item label="子合同编号" prop="subContractNo">
          <el-input v-model="form.subContractNo" placeholder="请输入子合同编号" />
        </el-form-item>
        <el-form-item label="子合同名称" prop="subContractName">
          <el-input v-model="form.subContractName" placeholder="请输入子合同名称" />
        </el-form-item>
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号" disabled/>
        </el-form-item>
        <el-form-item label="主合同是否签订" prop="mainContractSign">
          <el-select v-model="form.mainContractSign" placeholder="请选择主合同是否签订" :disabled="form.mainContractSign=='1'">
            <el-option
              v-for="dict in dict.type.ship_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="子合同是否签订" prop="subContractSign">
          <el-select v-model="form.subContractSign" placeholder="请选择子合同是否签订" :disabled="form.subContractSign=='1'">
            <el-option
              v-for="dict in dict.type.ship_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户号" prop="customerNo">
          <el-input v-model="form.customerNo" placeholder="请输入客户号" />
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listContract, getContract, delContract, addContract, updateContract } from "@/api/contract/contract"

export default {
  name: "Contract",
  dicts: ['ship_yes_no'],
  data() {
    return {
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
      // 合同管理表格数据
      contractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mainContractNo: null,
        mainContractName: null,
        subContractNo: null,
        subContractName: null,
        orderId: null,
        mainContractSign: null,
        subContractSign: null,
        customerNo: null,
        customerName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mainContractNo: [
          { required: true, message: "主合同编号不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询合同管理列表 */
    getList() {
      this.loading = true
      listContract(this.queryParams).then(response => {
        this.contractList = response.rows
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
        mainContractNo: null,
        mainContractName: null,
        subContractNo: null,
        subContractName: null,
        orderId: null,
        mainContractSign: null,
        subContractSign: null,
        customerNo: null,
        customerName: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.title = "添加合同管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getContract(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改合同管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateContract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addContract(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除合同管理编号为"' + ids + '"的数据项？').then(function() {
        return delContract(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contract/contract/export', {
        ...this.queryParams
      }, `contract_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
