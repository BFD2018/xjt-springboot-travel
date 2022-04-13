package com.xjt.travel.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.xjt.travel.config.aliyun.AliyunOssConfig;
import com.xjt.travel.domain.TTravelNote;
import com.xjt.travel.mapper.TTravelNoteMapper;
import com.xjt.travel.service.FileUploadAliyunService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadAliyunServiceImpl implements FileUploadAliyunService {
    @Autowired
    private OSS ossClient;          // 注入阿里云oss文件服务器客户端

    @Autowired
    private AliyunOssConfig aliyunOssConfig;// 注入阿里云OSS基本配置类

    @Autowired
    private TTravelNoteMapper travelNoteMapper;

    @Override
    public RespBean uploadFile(MultipartFile file) {
        // 获取oss的Bucket名称
        String bucketName = aliyunOssConfig.getBucketName();
        // 获取oss的地域节点
        String endpoint = aliyunOssConfig.getEndPoint();
        // 获取oss目标文件夹
        String filehost = aliyunOssConfig.getFileHost();

        // 获取文件原名称
        String originalFilename = file.getOriginalFilename();
        // 新文件名称
        String newFileName = UUID.randomUUID().toString().replace("-","") + "-" + originalFilename;

        // 构建日期路径, 例如：OSS目标文件夹  /2022/10/文件名
        Date date = new Date();
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(date);
        // 文件上传的路径地址
        String uploadUrl = filehost + "/" + filePath + "/" + newFileName;

        // 获取文件输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(!ossClient.doesBucketExist(bucketName)){
                ossClient.createBucket(bucketName);
                CreateBucketRequest bucketRequest = new CreateBucketRequest(bucketName);
                bucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(bucketRequest);
            }
            //1、文件上传至阿里云OSS
            ossClient.putObject(bucketName, uploadUrl, inputStream);

            //2、存入数据库
            // 获取文件上传后的返回地址
            String returnUrl = "http://" + bucketName + "." + endpoint + "/" + uploadUrl;

            return RespBean.success("ok",returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("error",e.getMessage());
        }
    }
}
