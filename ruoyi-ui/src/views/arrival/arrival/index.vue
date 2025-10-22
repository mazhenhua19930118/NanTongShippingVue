<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="托运单位" prop="shippingUnit">
        <el-input
          v-model="queryParams.shippingUnit"
          placeholder="请输入托运单位"
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
      <el-form-item label="海伦号" prop="helenNumber">
        <el-input
          v-model="queryParams.helenNumber"
          placeholder="请输入海伦号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="船号" prop="shipNumber">
        <el-input
          v-model="queryParams.shipNumber"
          placeholder="请输入船号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中转港" prop="transitPort">
        <el-input
          v-model="queryParams.transitPort"
          placeholder="请输入中转港"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目的港" prop="destinationPort">
        <el-input
          v-model="queryParams.destinationPort"
          placeholder="请输入目的港"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重量" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入重量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重量单位" prop="weightUnit">
        <el-select v-model="queryParams.weightUnit" placeholder="请输入重量单位">
          <el-option
            v-for="dict in dict.type.ship_unit"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="货物" prop="goods">
        <el-input
          v-model="queryParams.goods"
          placeholder="请输入货物"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="到港时间" prop="arrivalTime">
        <el-date-picker clearable
          v-model="queryParams.arrivalTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择到港时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="托运单位" align="center" prop="shippingUnit" />
      <el-table-column label="订单编号" align="center" prop="orderId" />
      <el-table-column label="海伦号" align="center" prop="helenNumber" />
      <el-table-column label="船号" align="center" prop="shipNumber" />
      <el-table-column label="目的港" align="center" prop="destinationPort" />
      <el-table-column label="状态" align="center" prop="status" >
      <template slot-scope="scope">
        <dict-tag :options="dict.type.ship_task_status" :value="scope.row.status"/>
      </template>
      </el-table-column>
      <el-table-column label="货物" align="center" prop="goods" />
      <el-table-column label="到港时间" align="center" prop="arrivalTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arrivalTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="特殊场景类型" prop="specialCases">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.special_cases" :value="scope.row.specialCases"/>
        </template>
      </el-table-column>
<!--      <el-form-item label="特殊原因" prop="specialCaseReason">-->
<!--        <el-input v-model="form.specialCaseReason" placeholder="请输入特殊原因" />-->
<!--      </el-form-item>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:goods:edit']"
          >到港确认</el-button>
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

    <!-- 添加或修改货源信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="托运单位" prop="shippingUnit">
          <el-input v-model="form.shippingUnit" placeholder="请输入托运单位" disabled/>
        </el-form-item>
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号" disabled/>
        </el-form-item>
        <el-form-item label="海伦号" prop="helenNumber">
          <el-input v-model="form.helenNumber" placeholder="请输入海伦号" disabled/>
        </el-form-item>
        <el-form-item label="船号" prop="shipNumber">
          <el-input v-model="form.shipNumber" placeholder="请输入船号" disabled/>
        </el-form-item>
        <el-form-item label="目的港" prop="destinationPort">
          <el-input v-model="form.destinationPort" placeholder="请输入目的港" disabled />
        </el-form-item>
        <el-form-item label="货物" prop="goods">
          <el-input v-model="form.goods" placeholder="请输入货物" disabled />
        </el-form-item>
        <el-form-item label="到港时间" prop="arrivalTime">
          <el-date-picker clearable
                          v-model="form.arrivalTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择到港时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="特殊场景类型" prop="specialCases">
          <el-select v-model="form.specialCases" placeholder="请选择特殊场景类型" clearable>
            <el-option
              v-for="dict in dict.type.special_cases"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
<!--        <el-form-item label="特殊原因" prop="weight">-->
<!--          <el-input v-model="form.specialCaseReason" placeholder="请输入特殊原因" />-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {arrvialEdit, getGoods, delGoods, addGoods, updateGoods, publishGoods, listArrivalGoods} from "@/api/goods/goods"

export default {
  name: "Goods",
  dicts: ['ship_task_status','ship_unit','special_cases'],
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
      // 货源信息表格数据
      goodsList: [],
      // 弹出层标题
      title: "",
      publishTitle: "",
      // 是否显示弹出层
      open: false,
      publishOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shippingUnit: null,
        orderId: null,
        helenNumber: null,
        shipNumber: null,
        transitPort: null,
        destinationPort: null,
        weight: null,
        weightUnit: null,
        status: null,
        goods: null,
        publishBy: null,
        arrivalTime: null,
        specialCases: null,
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
  },
  methods: {
    /** 查询货源信息列表 */
    getList() {
      this.loading = true
      listArrivalGoods(this.queryParams).then(response => {
        this.goodsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.publishOpen = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        shippingUnit: null,
        orderId: null,
        helenNumber: null,
        shipNumber: null,
        transitPort: null,
        destinationPort: null,
        weight: null,
        weightUnit: null,
        status: null,
        goods: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        publishBy: null,
        publishTime: null,
        arrivalTime: null,
        specialCases: null,
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
      this.title = "添加货源信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getGoods(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改货源信息"
      })
    },
    handlePublish(row){
      this.reset()
      const id = row.id || this.ids
      getGoods(id).then(response => {
        this.form = response.data
        this.publishOpen = true
        this.publishTitle = "发布货源信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            arrvialEdit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    submitPublishForm(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            publishGoods(this.form).then(response => {
              this.$modal.msgSuccess("发布成功")
              this.publishOpen = false;
              this.getList()
            })
          } else {
            this.$modal.msgSuccess("发布失败！未查询到ID")
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除货源信息编号为"' + ids + '"的数据项？').then(function() {
        return delGoods(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('goods/goods/export', {
        ...this.queryParams
      }, `goods_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
