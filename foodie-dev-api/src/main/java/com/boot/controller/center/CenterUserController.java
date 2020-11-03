package com.boot.controller.center;

import com.boot.controller.BaseController;
import com.boot.pojo.Users;
import com.boot.pojo.bo.center.CenterUserBo;
import com.boot.resource.FileUpload;
import com.boot.service.center.CenterUserService;
import com.boot.utils.CookieUtils;
import com.boot.utils.DateUtil;
import com.boot.utils.JSONResult;
import com.boot.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true) MultipartFile file
    ){
        FileOutputStream fileOutputStream = null;
        //保存地址
        String fileSpace = fileUpload.getImageUserFaceLocation();
        //userid
        String uploadPathPrefix = File.separator + userId;
        //开始文件上传
        if(file != null){
            try {
                // 获取文件名
                String filename= file.getOriginalFilename();

                if(StringUtils.isNotBlank(filename)){
                    String filenameArr[] = filename.split("\\.");
                    // 后缀名
                    String suffix = filenameArr[filenameArr.length - 1];

                    //校验后缀名
                    if(suffix.equalsIgnoreCase("png") && suffix.equalsIgnoreCase("jpg") && suffix.equalsIgnoreCase("jpeg")){
                        return new JSONResult("图片格式不正确", 514);
                    }

                    // 文件名重组
                    String newFilename = "face-" + userId + "." + suffix;

                    // 保存路径
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFilename;

                    uploadPathPrefix += ("/" + newFilename);

                    File outFile = new File(finalFacePath);
                    if(outFile.getParentFile() != null){
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    //文件输出到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);

                    return new JSONResult("ok");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    if(fileOutputStream != null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            return new JSONResult("文件不能为空", 513);
        }

        // 更新头像到数据库
        String imageServerUrl = fileUpload.getImageServerUrl();
        String finalUrl = imageServerUrl + uploadPathPrefix + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        Users userResult = centerUserService.updateUserFace(userId, finalUrl);
        return new JSONResult(userResult);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "GET")
    @PostMapping("/update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "centerUserBo", value = "修改的数据集", required = true) @RequestBody @Valid CenterUserBo centerUserBo,
            BindingResult result,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ){
        //判断validate是否正确
        if(result.hasErrors()){
            Map<String, String> map = this.getErrors(result);
            return new JSONResult(map);
        }

        Users users = centerUserService.updateUserInfo(userId, centerUserBo);

        // TODO 后续修改为redis
        // 临时处理
        users = setNullProperty(users);
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, "user", JsonUtils.objectToJson(users), true);
        return new JSONResult();
    }

    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setSex(null);
        userResult.setMobile(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    private Map<String, String> getErrors(BindingResult result){
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for(FieldError error : errorList){
            //发生验证错误所对应的每个属性
            String errorField = error.getField();
            //验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }
}
