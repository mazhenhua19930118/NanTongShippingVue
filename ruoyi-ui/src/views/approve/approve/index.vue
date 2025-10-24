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
      <el-form-item label="申请人" prop="applicantUser">
        <el-input
          v-model="queryParams.applicantUser"
          placeholder="请输入申请人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请时间" prop="applicantTime">
        <el-date-picker clearable
          v-model="queryParams.applicantTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择申请时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="申请原因" prop="applicantReason">
        <el-input
          v-model="queryParams.applicantReason"
          placeholder="请输入申请原因"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批原因" prop="approveReason">
        <el-input
          v-model="queryParams.approveReason"
          placeholder="请输入审批原因"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批人" prop="approveUser">
        <el-input
          v-model="queryParams.approveUser"
          placeholder="请输入审批人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批时间" prop="approveTime">
        <el-date-picker clearable
          v-model="queryParams.approveTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择审批时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="审批状态" prop="approveStatus">
        <el-select v-model="queryParams.approveStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.ship_approve_status"
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

<!--    <el-row :gutter="10" class="mb8">-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['approve:approve:add']"-->
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
<!--          v-hasPermi="['approve:approve:edit']"-->
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
<!--          v-hasPermi="['approve:approve:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['approve:approve:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
<!--    </el-row>-->

    <el-table v-loading="loading" :data="approveList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="id" align="center" prop="id" />-->
      <el-table-column label="订单编号" align="center" prop="orderId" />
      <el-table-column label="申请人" align="center" prop="applicantUser" />
      <el-table-column label="申请时间" align="center" prop="applicantTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applicantTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请原因" align="center" prop="applicantReason" />
      <el-table-column label="审批状态" align="center" prop="approveStatus" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ship_approve_status" :value="scope.row.approveStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审批原因" align="center" prop="approveReason" />
      <el-table-column label="审批人" align="center" prop="approveUser" />
      <el-table-column label="审批时间" align="center" prop="approveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.approveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['approve:approve:edit']"
            :disabled="scope.row.approveStatus === '1'"
          >审批</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['approve:approve:remove']"-->
<!--          >删除</el-button>-->
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

    <!-- 添加或修改审批对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号" disabled/>
        </el-form-item>
        <el-form-item label="申请人" prop="applicantUser">
          <el-input v-model="form.applicantUser" placeholder="请输入申请人" disabled/>
        </el-form-item>
        <el-form-item label="申请时间" prop="applicantTime">
          <el-date-picker clearable
            v-model="form.applicantTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择申请时间" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请原因" prop="applicantReason">
          <el-input v-model="form.applicantReason" placeholder="请输入申请原因" disabled/>
        </el-form-item>
        <el-form-item label="审批原因" prop="approveReason">
          <el-input v-model="form.approveReason" placeholder="请输入审批原因" />
        </el-form-item>
<!--        <el-form-item label="审批人" prop="approveUser">-->
<!--          <el-input v-model="form.approveUser" placeholder="请输入审批人" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="审批时间" prop="approveTime">-->
<!--          <el-date-picker clearable-->
<!--            v-model="form.approveTime"-->
<!--            type="date"-->
<!--            value-format="yyyy-MM-dd"-->
<!--            placeholder="请选择审批时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
        <el-form-item label="审批状态" prop="approveStatus">
        <el-select v-model="form.approveStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.ship_approve_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
import { listApprove, getApprove, delApprove, addApprove, updateApprove } from "@/api/approve/approve"

export default {
  name: "Approve",
  dicts: ['ship_approve_status'],
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
      // 审批表格数据
      approveList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        applicantUser: null,
        applicantTime: null,
        applicantReason: null,
        approveReason: null,
        approveUser: null,
        approveTime: null,
        approveStatus: null
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
    /** 查询审批列表 */
    getList() {
      this.loading = true
      listApprove(this.queryParams).then(response => {
        this.approveList = response.rows
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
        applicantUser: null,
        applicantTime: null,
        applicantReason: null,
        approveReason: null,
        approveUser: null,
        approveTime: null,
        approveStatus: null
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
      this.title = "添加审批"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getApprove(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改审批"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateApprove(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addApprove(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除审批编号为"' + ids + '"的数据项？').then(function() {
        return delApprove(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('approve/approve/export', {
        ...this.queryParams
      }, `approve_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
