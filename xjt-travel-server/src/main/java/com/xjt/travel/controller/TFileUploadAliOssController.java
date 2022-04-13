package com.xjt.travel.controller;

import com.xjt.travel.service.FileUploadAliyunService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("aliyun")
public class TFileUploadAliOssController {
    @Autowired
    private FileUploadAliyunService fileUploadAliyunService;

    /*上传文件*/
    @ResponseBody
    @PostMapping("/oss/upload")
    private RespBean ossFileUpload(MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return fileUploadAliyunService.uploadFile(file);
    }

}
