<template>
  <div class="app-container">
    <el-form :model="queryParams"
             ref="queryForm"
             size="small"
             :inline="true"
             v-show="showSearch"
             label-width="100px">
      <el-form-item label="订单编号"
                    prop="orderId">
        <el-input v-model="queryParams.orderId"
                  placeholder="请输入订单编号"
                  clearable
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="任务状态"
                    prop="taskStatus">
        <el-select v-model="queryParams.taskStatus"
                   placeholder="请选择任务状态"
                   clearable>
          <el-option v-for="dict in dict.type.ship_task_status"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="当前负责人员"
                    prop="assignUserId">
        <el-input v-model="queryParams.assignUserId"
                  placeholder="请输入当前负责人员"
                  clearable
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   icon="el-icon-search"
                   size="mini"
                   @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh"
                   size="mini"
                   @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10"
            class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['documentation:task:add']"-->
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
      <!--          v-hasPermi="['documentation:task:edit']"-->
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
      <!--          v-hasPermi="['documentation:task:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['documentation:task:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch"
                     @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading"
              :data="taskList"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55"
                       align="center" />
      <el-table-column label="订单编号"
                       align="center"
                       prop="orderId" />
      <el-table-column label="任务状态"
                       align="center"
                       prop="taskStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ship_task_status"
                    :value="scope.row.taskStatus" />
        </template>
      </el-table-column>
      <el-table-column label="当前负责人员编号"
                       align="center"
                       prop="assignUserId" />
      <el-table-column label="当前负责人员姓名"
                       align="center"
                       prop="assignUserName" />
      <el-table-column label="摄像头说明"
                       align="center"
                       prop="cameraInstructions" />
      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
                     v-hasPermi="['documentation:task:edit']">装货确认</el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['documentation:task:remove']"-->
          <!--          >删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList" />

    <!-- 添加或修改订单任务对话框 -->
    <el-dialog :title="title"
               :visible.sync="open"
               width="600px"
               append-to-body>
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item label="订单编号"
                      prop="orderId">
          <el-input v-model="form.orderId"
                    placeholder="请输入订单编号" />
        </el-form-item>
<!--        <el-form-item label="任务状态"-->
<!--                      prop="taskStatus">-->
<!--          <el-select v-model="form.taskStatus"-->
<!--                     placeholder="请选择任务状态"-->
<!--                     disabled>-->
<!--            <el-option label="已装货"-->
<!--                       :value='StatusEnum.loading.value'></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="摄像头说明"
                      prop="cameraInstructions">
          <el-input v-model="form.cameraInstructions"
                    placeholder="请输入摄像头说明" />
        </el-form-item>
        <!--        <el-form-item label="分配人员id" prop="assignUserId">-->
        <!--          <el-input v-model="form.assignUserId" placeholder="请输入分配人员id" />-->
        <!--        </el-form-item>-->
        <el-upload action="#"
                   list-type="picture-card"
                   :auto-upload="false"
                   :limit="6"
                   :on-exceed="handleExceed"
                   :file-list="fileList"
                   :on-change="handleChange">
          <i slot="default"
             class="el-icon-plus"></i>
          <div slot="file"
               slot-scope="{file}">
            <img class="el-upload-list__item-thumbnail"
                 :src="file.url"
                 alt="">
            <span class="el-upload-list__item-actions">
              <span class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)">
                <i class="el-icon-zoom-in"></i>
              </span>
              <!--        <span-->
              <!--          v-if="!disabled"-->
              <!--          class="el-upload-list__item-delete"-->
              <!--          @click="handleDownload(file)"-->
              <!--        >-->
              <!--          <i class="el-icon-download"></i>-->
              <!--        </span>-->
              <span v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)">
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%"
               :src="dialogImageUrl"
               alt="">
        </el-dialog>

      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="primary"
                   @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTask, getTask, delTask, addTask, updateTask, getTaskWithImage } from "@/api/documentation/task/task";
import { arrLength } from "../../../common/common";
import { StatusEnum } from "../../../common/taskStatus.enum";
export default {
  name: "Task",
  dicts: ['ship_task_status'],
  data () {
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
      // 订单任务表格数据
      taskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        taskStatus: null,
        assignUserId: null,
      },
      // 表单参数
      form: { 'taskStatus': StatusEnum.loading.value },
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
      },
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      fileList: [],
      StatusEnum: StatusEnum // 挂载到 data
    };
  },
  created () {
    this.getList();
  },
  methods: {
    handleChange (file, fileList) {
      const isJPGorPNG = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png';
      const isLt20M = file.size / 1024 / 1024 < 20; // 限制文件大小小于2MB，可选
      if (!isJPGorPNG) {
        this.$message.error(`图片只能是 JPG/PNG 格式！`);
        fileList.pop()
        return; // 阻止上传
      }
      if (!isLt20M) {
        this.$message.error(`上传图片大小不能超过 20MB！`);
        fileList.pop()
        return; // 阻止上传，如果需要限制大小
      }
      this.fileList = fileList;
    },
    handleExceed (files, fileList) {
      this.$message.error(`最多只能上传6张！`);
    },
    handleRemove (file, fileList) {
      const index = this.fileList.findIndex(f => f.name === file.name);
      if (index !== -1) {
        this.fileList.splice(index, 1); // 移除匹配的文件
      }
    },
    handleDownload (file) {
      console.log(file);
    },
    // 附件上传图片预览事件，这个就是将路径直接放进一个弹窗显示出来就可以了
    handlePictureCardPreview (file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
      console.log("dialogImageUrl", this.dialogImageUrl)
    },
    /** 查询订单任务列表 */
    getList () {
      this.loading = true;
      listTask(this.queryParams).then(response => {
        this.taskList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {
        id: null,
        orderId: null,
        taskStatus: null,
        assignUserId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset();
      this.open = true;
      this.title = "添加订单任务";
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.reset();
      this.fileList = [];
      const id = row.id || this.ids
      let data = {};
      data.relatedId = row.orderId;
      data.uploadType = StatusEnum.loading.value;
      getTaskWithImage(data).then(response => {
        this.form = response.data;
        this.form.taskStatus = StatusEnum.loading.value
        this.open = true;
        this.title = "修改装货确认";
        console.log("process.env.VUE_APP_BASE_API", process.env.VUE_APP_BASE_API)
        if (arrLength(response.data.shippingUploadList)) {
          for (let v of response.data.shippingUploadList) {
            const image = {};
            image.name = v.fileName;
            image.url = process.env.VUE_APP_BASE_API + v.filePath
            image.status = 'finished'
            console.log("image", image)
            this.fileList.push(image)
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm () {
      console.log("fileList", this.fileList)
      console.log("form", this.form)
      const formData = new FormData();
      this.fileList.forEach(file => {
        formData.append('file', file.raw); // 使用 file.raw 获取原始 File 对象，并以 'file' 作为键名
      });
      formData.append('data', new Blob([JSON.stringify(this.form)], { type: 'application/json' }));
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTask(formData).then(response => {
              this.$modal.msgSuccess("操作成功");
              this.open = false;
              this.getList();
            });
          } else {
            // addTask(this.form).then(response => {
            //   this.$modal.msgSuccess("新增成功");
            //   this.open = false;
            //   this.getList();
            // });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除订单任务编号为"' + ids + '"的数据项？').then(function () {
        return delTask(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport () {
      this.download('system/task/export', {
        ...this.queryParams
      }, `task_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
