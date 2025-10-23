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
      <el-form-item label="装载重量" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入重量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重量单位" prop="weightUnit">
        <el-select v-model="form.weightUnit" placeholder="请输入重量单位">
          <el-option
            v-for="dict in dict.type.ship_unit"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="子订单价格" prop="subOrderPrice">
        <el-input
          v-model="queryParams.subOrderPrice"
          placeholder="请输入子订单价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主订单价格" prop="mainOrderPrice">
        <el-input
          v-model="queryParams.mainOrderPrice"
          placeholder="请输入主订单价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际重量" prop="aboveLimitWeight">
        <el-input
          v-model="queryParams.aboveLimitWeight"
          placeholder="请输入实际重量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际重量单位" prop="aboveLimitWeightUnit">
        <el-select v-model="form.aboveLimitWeightUnit" placeholder="请输入重量单位">
          <el-option
            v-for="dict in dict.type.ship_unit"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="货物价格" prop="goodsPrice">
        <el-input
          v-model="queryParams.goodsPrice"
          placeholder="请输入货物价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付金额" prop="paymentAmount">
        <el-input
          v-model="queryParams.paymentAmount"
          placeholder="请输入支付金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结算金额" prop="settlementAmount">
        <el-input
          v-model="queryParams.settlementAmount"
          placeholder="请输入结算金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="延期费支付金额" prop="deferredPayment">
        <el-input
          v-model="queryParams.deferredPayment"
          placeholder="请输入延期费支付金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="延期费结算金额" prop="deferredSettlement">
        <el-input
          v-model="queryParams.deferredSettlement"
          placeholder="请输入延期费结算金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="是否上传卸货管理明细" prop="isUploadDetailsManagement">-->
<!--        <el-input-->
<!--          v-model="queryParams.isUploadDetailsManagement"-->
<!--          placeholder="请输入是否上传卸货管理明细"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

<!--    <el-row :gutter="10" class="mb8">-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['finance:finance:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['finance:finance:edit']"-->
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
<!--          v-hasPermi="['finance:finance:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['finance:finance:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
<!--    </el-row>-->

    <el-table v-loading="loading" :data="financeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderId" />
<!--      <el-table-column label="是否预付款 0否 1是" align="center" prop="isAdvancePayment" />-->
      <el-table-column label="装载重量" align="center" prop="weight" />
      <el-table-column label="装载重量单位" align="center" prop="weightUnit" >
      <template slot-scope="scope">
        <dict-tag :options="dict.type.ship_unit" :value="scope.row.weightUnit"/>
      </template>
      </el-table-column>
      <el-table-column label="实际重量" align="center" prop="aboveLimitWeight" />
      <el-table-column label="实际重量单位" align="center" prop="aboveLimitWeightUnit" >
      <template slot-scope="scope">
        <dict-tag :options="dict.type.ship_unit" :value="scope.row.aboveLimitWeightUnit"/>
      </template>
      </el-table-column>
      <el-table-column label="损耗" align="center" prop="float" />
      <el-table-column label="主订单价格" align="center" prop="mainOrderPrice" />
      <el-table-column label="子订单价格" align="center" prop="subOrderPrice" />
      <el-table-column label="货物价格" align="center" prop="goodsPrice" />
      <el-table-column label="支付金额" align="center" prop="paymentAmount" />
      <el-table-column label="结算金额" align="center" prop="settlementAmount" />
      <el-table-column label="延期费支付金额" align="center" prop="deferredPayment" />
      <el-table-column label="延期费结算金额" align="center" prop="deferredSettlement" />
<!--      <el-table-column label="是否上传卸货管理明细" align="center" prop="isUploadDetailsManagement" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:finance:edit']"
          >填报卸货资料</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:finance:edit']"
          >支付</el-button>
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

    <!-- 添加或修改财务管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="装载重量" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量" />
        </el-form-item>
        <el-form-item label="装载重量单位" prop="weightUnit">
          <el-select v-model="form.weightUnit" placeholder="请输入重量单位">
            <el-option
              v-for="dict in dict.type.ship_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="子订单价格" prop="subOrderPrice">-->
<!--          <el-input v-model="form.subOrderPrice" placeholder="请输入子订单价格" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="主订单价格" prop="mainOrderPrice">-->
<!--          <el-input v-model="form.mainOrderPrice" placeholder="请输入主订单价格" />-->
<!--        </el-form-item>-->
        <el-form-item label="实际重量" prop="aboveLimitWeight">
          <el-input v-model="form.aboveLimitWeight" placeholder="请输入超耗重量单位" />

        </el-form-item>
        <el-form-item label="实际重量单位" prop="aboveLimitWeightUnit">
          <el-select v-model="form.aboveLimitWeightUnit" placeholder="请输入实际重量单位">
            <el-option
              v-for="dict in dict.type.ship_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="货物价格" prop="goodsPrice">-->
<!--          <el-input v-model="form.goodsPrice" placeholder="请输入货物价格" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="支付金额" prop="paymentAmount">-->
<!--          <el-input v-model="form.paymentAmount" placeholder="请输入支付金额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="结算金额" prop="settlementAmount">-->
<!--          <el-input v-model="form.settlementAmount" placeholder="请输入结算金额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="延期费支付金额" prop="deferredPayment">-->
<!--          <el-input v-model="form.deferredPayment" placeholder="请输入延期费支付金额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="延期费结算金额" prop="deferredSettlement">-->
<!--          <el-input v-model="form.deferredSettlement" placeholder="请输入延期费结算金额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="是否上传卸货管理明细" prop="isUploadDetailsManagement">-->
<!--          <el-input v-model="form.isUploadDetailsManagement" placeholder="请输入是否上传卸货管理明细" />-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="financeTitle" :visible.sync="openFinance" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="子订单价格" prop="subOrderPrice">
          <el-input v-model="form.subOrderPrice" placeholder="请输入子订单价格" />
        </el-form-item>
        <el-form-item label="主订单价格" prop="mainOrderPrice">
          <el-input v-model="form.mainOrderPrice" placeholder="请输入主订单价格" />
        </el-form-item>
        <el-form-item label="货物价格" prop="goodsPrice">
          <el-input v-model="form.goodsPrice" placeholder="请输入货物价格" />
        </el-form-item>
        <el-form-item label="支付金额" prop="paymentAmount">
          <el-input v-model="form.paymentAmount" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="结算金额" prop="settlementAmount">
          <el-input v-model="form.settlementAmount" placeholder="请输入结算金额" />
        </el-form-item>
        <el-form-item label="延期费支付金额" prop="deferredPayment">
          <el-input v-model="form.deferredPayment" placeholder="请输入延期费支付金额" />
        </el-form-item>
        <el-form-item label="延期费结算金额" prop="deferredSettlement">
          <el-input v-model="form.deferredSettlement" placeholder="请输入延期费结算金额" />
        </el-form-item>
        <!--        <el-form-item label="是否上传卸货管理明细" prop="isUploadDetailsManagement">-->
        <!--          <el-input v-model="form.isUploadDetailsManagement" placeholder="请输入是否上传卸货管理明细" />-->
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
import { listFinance, getFinance, delFinance, addFinance, updateFinance } from "@/api/finance/finance"
import finance from "@/views/finance/finance/index.vue";

export default {
  name: "Finance",
  dicts: ['ship_task_status','ship_unit'],
  computed: {
    finance() {
      return finance
    }
  },
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
      // 财务管理表格数据
      financeList: [],
      // 弹出层标题
      title: "",
      financeTitle: "",
      // 是否显示弹出层
      open: false,
      openFinance: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        isAdvancePayment: null,
        weight: null,
        weightUnit: null,
        subOrderPrice: null,
        mainOrderPrice: null,
        aboveLimitWeight: null,
        aboveLimitWeightUnit: null,
        goodsPrice: null,
        paymentAmount: null,
        settlementAmount: null,
        deferredPayment: null,
        deferredSettlement: null,
        isUploadDetailsManagement: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询财务管理列表 */
    getList() {
      this.loading = true
      listFinance(this.queryParams).then(response => {
        this.financeList = response.rows
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
        isAdvancePayment: null,
        weight: null,
        weightUnit: null,
        subOrderPrice: null,
        mainOrderPrice: null,
        aboveLimitWeight: null,
        aboveLimitWeightUnit: null,
        goodsPrice: null,
        paymentAmount: null,
        settlementAmount: null,
        deferredPayment: null,
        deferredSettlement: null,
        isUploadDetailsManagement: null,
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
      this.title = "添加财务管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getFinance(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改财务管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFinance(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addFinance(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除财务管理编号为"' + ids + '"的数据项？').then(function() {
        return delFinance(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/finance/export', {
        ...this.queryParams
      }, `finance_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
