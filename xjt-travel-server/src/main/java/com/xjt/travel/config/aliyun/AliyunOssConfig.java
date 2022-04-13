package com.xjt.travel.config.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 声明配置类，放入Spring容器
@Configuration
// 指定配置文件位置
//@PropertySource(value = {"classpath:application-aliyun-oss.properties"})
// 指定配置文件中自定义属性前缀
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
@Accessors(chain = true)
public class AliyunOssConfig {
    private String endPoint;        // 地域节点
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;      // OSS的Bucket名称
    private String urlPrefix;       // Bucket 域名
    private String fileHost;        // 目标文件夹

    // 将OSS 客户端交给Spring容器托管
    @Bean
    public OSS getOssClient() {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        return ossClient;
    }
}

