package com.xjt.travel.service;

import com.xjt.travel.utils.RespBean;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadAliyunService {
    RespBean uploadFile(MultipartFile file);
}
