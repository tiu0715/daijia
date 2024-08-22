package com.atguigu.daijia.driver.service.impl;

import com.atguigu.daijia.common.result.Result;
import com.atguigu.daijia.driver.client.CosFeignClient;
import com.atguigu.daijia.driver.service.CosService;
import com.atguigu.daijia.model.vo.driver.CosUploadVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CosServiceImpl implements CosService {

    @Autowired
    private CosFeignClient cosFeignClient;




    /**
     * 文件上传
     * @param file
     * @param path
     * @return
     */
    @Override
    public CosUploadVo uploadFile(MultipartFile file, String path) {
        Result<CosUploadVo> cosUploadVoResult=cosFeignClient.upload(file,path);
        return cosUploadVoResult.getData();
    }
}
