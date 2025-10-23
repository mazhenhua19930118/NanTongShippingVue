package com.ruoyi.task.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.FileType;
import com.ruoyi.common.enums.TaskStatus;
import com.ruoyi.common.utils.CopyUtils;
import com.ruoyi.common.utils.StringUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.vo.GoodsDetailVo;
import com.ruoyi.common.vo.TaskNodeVo;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.task.domain.FinanceDto;
import com.ruoyi.task.domain.ShippingTask;
import com.ruoyi.task.domain.ShippingTaskDetail;
import com.ruoyi.task.service.IShippingTaskDetailService;
import com.ruoyi.task.service.IShippingTaskService;
import com.ruoyi.upload.domain.ShippingUpload;
import com.ruoyi.upload.service.IShippingUploadService;
import net.sf.jsqlparser.expression.JsonAggregateFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller
 * 
 * @author ruoyi
 * @date 2025-04-08
 */
@RestController
@RequestMapping("/documentation/task")
public class ShippingTaskController extends BaseController
{
    @Autowired
    private IShippingTaskService shippingTaskService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IShippingUploadService iShippingUploadService;
    @Autowired
    private IShippingTaskDetailService shippingTaskDetailService;

    /**
     * 查询
     */
    @GetMapping("/list")
    public TableDataInfo list(ShippingTask shippingTask)
    {
        startPage();
        shippingTask.setTaskStatus(TaskStatus.pre_insurance.getCode());
        List<ShippingTask> list = shippingTaskService.selectShippingTaskList(shippingTask);
        for(ShippingTask task :list){
            if(task.getAssignUserId()!=null){
                SysUser sysUser = userService.selectUserById(task.getAssignUserId());
                task.setAssignUserName(sysUser.getUserName());
                task.setAssignUserNickName(sysUser.getNickName());
            }
        }
        return getDataTable(list);
    }

    @GetMapping("/unload/list")
    public TableDataInfo unloadList(ShippingTask shippingTask)
    {
        startPage();
        shippingTask.setTaskStatus(TaskStatus.wait_unloaded.getCode());
        List<ShippingTask> list = shippingTaskService.selectShippingTaskList(shippingTask);
        for(ShippingTask task :list){
            if(task.getAssignUserId()!=null){
                SysUser sysUser = userService.selectUserById(task.getAssignUserId());
                task.setAssignUserName(sysUser.getUserName());
                task.setAssignUserNickName(sysUser.getNickName());
            }
        }
        return getDataTable(list);
    }

    @GetMapping("/camera/list")
    public TableDataInfo cameraList(ShippingTask shippingTask)
    {
        startPage();
        ShippingTaskDetail shippingTaskDetail = new ShippingTaskDetail();
        shippingTaskDetail.setTaskStatus(TaskStatus.unloaded.getCode());
        List<ShippingTaskDetail> selectShippingTaskDetailList = shippingTaskDetailService.selectShippingTaskDetailListWithTask(shippingTaskDetail);
        return getDataTable(selectShippingTaskDetailList);
    }


    /**
     * 导出
     */
    @Log(title = "【导出】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShippingTask shippingTask)
    {
        List<ShippingTask> list = shippingTaskService.selectShippingTaskList(shippingTask);
        ExcelUtil<ShippingTask> util = new ExcelUtil<ShippingTask>(ShippingTask.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ShippingTask task = shippingTaskService.selectShippingTaskById(id);
        ShippingUpload shippingUpload = new ShippingUpload();
        shippingUpload.setRelatedId(task.getOrderId());
        List<ShippingUpload> shippingUploads = iShippingUploadService.selectShippingUploadList(shippingUpload);
        task.setShippingUploadList(shippingUploads);
        return success(task);
    }

//    /**
//     * 新增
//     */
//    @Log(title = "【新增】", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody ShippingTask shippingTask)
//    {
//        ShippingTask task = new ShippingTask();
//        task.setGoodsId(shippingTask.getGoodsId());
//        List<ShippingTask> shippingTasks = shippingTaskService.selectShippingTaskList(task);
//        if(!CollectionUtils.isEmpty(shippingTasks)){
//            return AjaxResult.error("当前货源已有在途任务！");
//        }
//        return toAjax(shippingTaskService.insertShippingTask(shippingTask));
//    }

    /**
     * 修改【请填写功能名称】
     */
    @Log(title = "【修改】", businessType = BusinessType.UPDATE)
    @PostMapping(consumes = "multipart/form-data")
    public AjaxResult edit(@RequestPart("data") String dataJson, @RequestParam(value = "file", required = false) MultipartFile[] files) {
        try {
            ShippingTask shippingTask = JSONObject.parseObject(dataJson, ShippingTask.class);
//            AjaxResult ajaxResult = shippingTaskService.checkPreviousTaskNode(shippingTask);
//            if(!ajaxResult.isSuccess()){
//                return ajaxResult;
//            }
            List<ShippingUpload> fileList = new ArrayList<>();
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        // 保存文件到指定目录
                        ShippingUpload shippingUpload = new ShippingUpload();
                        shippingUpload.setOriginFileName(file.getOriginalFilename());
                        shippingUpload.setRelatedId(shippingTask.getOrderId());
                        shippingUpload.setFileType(FileType.IMG.getCode());
                        shippingUpload.setCreateBy(getUsername());
                        shippingUpload.setUploadType(shippingTask.getTaskStatus());
                        String filePath = FileUploadUtils.uploadNew(Constants.SHIPPING_RESOURCE_PREFIX, file, MimeTypeUtils.IMAGE_EXTENSION);
                        int lastSlashIndex = filePath.lastIndexOf('/');
                        // 提取文件名（包括后缀）
                        if (lastSlashIndex != -1) {
                            String fileName = filePath.substring(lastSlashIndex + 1);
                            shippingUpload.setFileName(fileName);
                        }
                        shippingUpload.setFilePath(filePath);
                        fileList.add(shippingUpload);
                    }
                }
            }
            for (ShippingUpload shippingUpload : fileList) {
                List<ShippingUpload> shippingUploads = iShippingUploadService.selectShippingUploadList(shippingUpload);
                if(CollectionUtils.isEmpty(shippingUploads)){
                    iShippingUploadService.insertShippingUpload(shippingUpload);
                }
            }
            shippingTask.setCurrentUserId(getUserId());
            shippingTaskService.generateTaskAndDetails(shippingTask);
            return toAjax(1);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @Log(title = "【修改】", businessType = BusinessType.UPDATE)
    @PostMapping(path = "/unload",consumes = "multipart/form-data")
    public AjaxResult unload(@RequestPart("data") String dataJson, @RequestParam(value = "file", required = false) MultipartFile[] files) {
        try {
            ShippingTask shippingTask = JSONObject.parseObject(dataJson, ShippingTask.class);
            shippingTask.setTaskStatus(TaskStatus.unloaded.getCode());
            List<ShippingUpload> fileList = new ArrayList<>();
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        // 保存文件到指定目录
                        ShippingUpload shippingUpload = new ShippingUpload();
                        shippingUpload.setOriginFileName(file.getOriginalFilename());
                        shippingUpload.setRelatedId(shippingTask.getOrderId());
                        shippingUpload.setFileType(FileType.IMG.getCode());
                        shippingUpload.setCreateBy(getUsername());
                        shippingUpload.setUploadType(shippingTask.getTaskStatus());
                        String filePath = FileUploadUtils.uploadNew(Constants.SHIPPING_RESOURCE_PREFIX, file, MimeTypeUtils.IMAGE_EXTENSION);
                        int lastSlashIndex = filePath.lastIndexOf('/');
                        // 提取文件名（包括后缀）
                        if (lastSlashIndex != -1) {
                            String fileName = filePath.substring(lastSlashIndex + 1);
                            shippingUpload.setFileName(fileName);
                        }
                        shippingUpload.setFilePath(filePath);
                        fileList.add(shippingUpload);
                    }
                }
            }
            for (ShippingUpload shippingUpload : fileList) {
                List<ShippingUpload> shippingUploads = iShippingUploadService.selectShippingUploadList(shippingUpload);
                if(CollectionUtils.isEmpty(shippingUploads)){
                    iShippingUploadService.insertShippingUpload(shippingUpload);
                }
            }
            shippingTask.setCurrentUserId(getUserId());
            shippingTaskService.generateTaskAndDetails(shippingTask);
            return toAjax(1);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @Log(title = "【修改】", businessType = BusinessType.UPDATE)
    @PostMapping(path = "/camera",consumes = "multipart/form-data")
    public AjaxResult camera(@RequestPart("data") String dataJson, @RequestParam(value = "file", required = false) MultipartFile[] files) {
        try {
            ShippingTask shippingTask = JSONObject.parseObject(dataJson, ShippingTask.class);
            shippingTask.setTaskStatus(TaskStatus.camera.getCode());
            List<ShippingUpload> fileList = new ArrayList<>();
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        // 保存文件到指定目录
                        ShippingUpload shippingUpload = new ShippingUpload();
                        shippingUpload.setOriginFileName(file.getOriginalFilename());
                        shippingUpload.setRelatedId(shippingTask.getOrderId());
                        shippingUpload.setFileType(FileType.IMG.getCode());
                        shippingUpload.setCreateBy(getUsername());
                        shippingUpload.setUploadType(shippingTask.getTaskStatus());
                        String filePath = FileUploadUtils.uploadNew(Constants.SHIPPING_RESOURCE_PREFIX, file, MimeTypeUtils.IMAGE_EXTENSION);
                        int lastSlashIndex = filePath.lastIndexOf('/');
                        // 提取文件名（包括后缀）
                        if (lastSlashIndex != -1) {
                            String fileName = filePath.substring(lastSlashIndex + 1);
                            shippingUpload.setFileName(fileName);
                        }
                        shippingUpload.setFilePath(filePath);
                        fileList.add(shippingUpload);
                    }
                }
            }
            for (ShippingUpload shippingUpload : fileList) {
                List<ShippingUpload> shippingUploads = iShippingUploadService.selectShippingUploadList(shippingUpload);
                if(CollectionUtils.isEmpty(shippingUploads)){
                    iShippingUploadService.insertShippingUpload(shippingUpload);
                }
            }
            shippingTask.setCurrentUserId(getUserId());
            FinanceDto financeDto = new FinanceDto();
            financeDto.setOrderId(shippingTask.getOrderId());
            shippingTaskService.insertFiance(financeDto);
            shippingTaskService.generateTaskAndDetails(shippingTask);
            return toAjax(1);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除
     */
    @Log(title = "【删除】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shippingTaskService.deleteShippingTaskByIds(ids));
    }

    /**
     * 内勤跟单确认查询
     * @param shippingTask
     * @return
     */
    @GetMapping("/internal/list")
    public TableDataInfo internalList(ShippingTask shippingTask)
    {
        startPage();
        shippingTask.setTaskStatus(TaskStatus.advance_payment.getCode());
        List<ShippingTask> list = shippingTaskService.selectShippingTaskList(shippingTask);
        for(ShippingTask task :list){
            if(task.getAssignUserId()!=null){
                SysUser sysUser = userService.selectUserById(task.getAssignUserId());
                task.setAssignUserName(sysUser.getUserName());
                task.setAssignUserNickName(sysUser.getNickName());
            }
        }
        return getDataTable(list);
    }

    /**
     * 综合查询 任务节点查询
     * @param shippingTask
     * @return
     */
    @GetMapping("/taskNode")
    public AjaxResult taskNode(ShippingTask shippingTask) {
        ShippingTask task = shippingTaskService.selectShippingTaskById(shippingTask.getId());
        if (StringUtil.isEquals(task.getTaskStatus(), TaskStatus.termination.getCode())) {
            task.setTermination(Boolean.TRUE);
        }
        return AjaxResult.success(task);
    }

    /**
     * 综合查询 查询所有任务分页
     * @param shippingTask
     * @return
     */
    @GetMapping("/comprehensive")
    public TableDataInfo comprehensive(ShippingTask shippingTask) {
        startPage();
        List<ShippingTask> list = shippingTaskService.selectShippingTaskList(shippingTask);
        return getDataTable(list);
    }

    /**
     * 生成任务和任务详情
     *
     * @param shippingTask
     * @return
     */
    @GetMapping("/generateTaskAndDetails")
    public AjaxResult generateTaskAndDetails(ShippingTask shippingTask) {
        shippingTask.setCurrentUserId(getUserId());
        shippingTaskService.generateTaskAndDetails(shippingTask);
        return AjaxResult.success("");
    }

    /**
     * 综合查询 查询任务状态list
     * @param shippingTask
     * @return
     */
    @GetMapping("/getTaskNodeList")
    public AjaxResult getTaskNodeList(ShippingTask shippingTask) {
        List<ShippingTaskDetail> shippingTaskDetails = shippingTaskService.selectShippingTaskNodeList(shippingTask);
        if(CollectionUtils.isEmpty(shippingTaskDetails)){
            return AjaxResult.success();
        }
        //对shippingTaskDetails取出taskStatus集合转成Long并排序
        List<TaskNodeVo> taskNodeVos = new ArrayList<>();
        for(ShippingTaskDetail taskDetail :shippingTaskDetails){
            TaskNodeVo taskNodeVo = new TaskNodeVo();
            taskNodeVo.setTaskStatus(StringUtil.convertStringToLong(taskDetail.getTaskStatus()));
            taskNodeVo.setNodeName(TaskStatus.getDescByCode(taskDetail.getTaskStatus()));
            taskNodeVos.add(taskNodeVo);
        }
        List<TaskNodeVo> taskNodeList = taskNodeVos.stream().sorted(Comparator.comparing(TaskNodeVo::getTaskStatus)).collect(Collectors.toList());
        return AjaxResult.success(taskNodeList);
    }


    /**
     * 获取详细信息
     */
    @PostMapping(value = "/getTaskWithImage")
    public AjaxResult getTaskWithImage(ShippingUpload shippingUpload)
    {
        ShippingTask task = shippingTaskService.selectShippingTaskByRelatedId(shippingUpload.getRelatedId());
        List<ShippingUpload> shippingUploads = iShippingUploadService.selectShippingUploadList(shippingUpload);
        task.setShippingUploadList(shippingUploads);
        return success(task);
    }

    /**
     * 综合查询明细查询
     */
    @PostMapping("/getGoodsDetails")
    public AjaxResult getGoodsDetails(ShippingTask shippingTask)
    {
        GoodsDetailVo goodsDetailVo = shippingTaskService.selectGoodsDetailByGoodsId(shippingTask.getOrderId());
        return success(goodsDetailVo);
    }
}
