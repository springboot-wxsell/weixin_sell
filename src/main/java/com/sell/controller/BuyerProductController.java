package com.sell.controller;

import com.sell.enums.ProductStatusEnum;
import com.sell.pojo.ProductCategory;
import com.sell.pojo.ProductInfo;
import com.sell.service.ProductCategoryService;
import com.sell.service.ProductInfoService;
import com.sell.utils.ResultVOUtils;
import com.sell.vo.ProductInfoVO;
import com.sell.vo.ProductVO;
import com.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangWei
 * @Title: BuyerProductController
 * @ProjectName weixin_sell
 * @date 2018/10/16 15:51
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductInfoService productInfoService;

    @GetMapping("/list")
    //@Cacheable(cacheNames = "product", key = "#sellerId", condition = "#sellerId.length() > 3", unless = "#result.getCode() != 0")
    public ResultVO list() {
        // 1. 查询所有上架的商品
        List<ProductInfo> productInfoList = productInfoService.selectByProductStatus((byte) ProductStatusEnum.UP.getCode().intValue());

        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType()).collect(Collectors.toList());
        // 2. 查询类目("一次性查询所有")
        List<ProductCategory> productCategoryList = productCategoryService.selectByCategoryTypeIn(categoryTypeList);
        // 3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtils.success(productVOList);
    }
}
