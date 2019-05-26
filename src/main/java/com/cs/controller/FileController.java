package com.cs.controller;

import com.cs.response.CommonResponse;
import com.cs.util.FileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by smi1e
 * Date 2019/5/26 10:32
 * Description
 */
@RestController
@RequestMapping(value = "/upload")
public class FileController {

    /**
     * 上传文件controller
     * @param file
     * @param type
     * @param request
     * @return 文件网络地址url
     * @throws Exception
     */
    @RequestMapping(value = "/image")
    public CommonResponse uploadFile(@RequestParam("file") MultipartFile file,String type,HttpServletRequest request) throws Exception {
        if (type.equals("")||type==null){
            throw new Exception("输入参数错误");
        }
        return CommonResponse.createResponse(200,FileUtil.uploadFile(file,type));
    }
}
