package com.xjt.travel.controller;

import com.xjt.travel.domain.TSeller;
import com.xjt.travel.service.TSellerService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class TSellerController {
    @Autowired
    private TSellerService sellerService;

    /*根据商家id返回该商家信息*/
    @GetMapping("/id")
    public RespBean getById(@RequestParam(value = "seller_id") String seller_id){
        return sellerService.getById(seller_id);
    }

    /*商家列表*/
    @GetMapping("/all")
    public RespBean getAllSeller(){
        return sellerService.getAllSeller();
    }

    /*商家列表*/
    @GetMapping("/delete")
    public RespBean deleteSellerById(@RequestParam(value = "seller_id") String seller_id){
        return sellerService.deleteSellerById(seller_id);
    }

    /*更新商家信息*/
    @PostMapping("/update")
    public RespBean updateSellerById(@RequestBody TSeller seller){
        return sellerService.updateSellerById(seller);
    }
}

