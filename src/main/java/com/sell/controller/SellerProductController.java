package com.sell.controller;

import com.github.pagehelper.PageInfo;
import com.sell.exception.WeiXinSellException;
import com.sell.form.ProductForm;
import com.sell.pojo.ProductCategory;
import com.sell.pojo.ProductInfo;
import com.sell.service.ProductCategoryService;
import com.sell.service.ProductInfoService;
import com.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author WangWei
 * @Title: SellerProductController
 * @ProjectName weixin_sell
 * @date 2018/11/8 11:38
 * @description 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 卖家端查询商品列表
     * @param page
     * @param size
     * @param result
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> result) {
        PageInfo<ProductInfo> productInfoPage = productInfoService.selectAll(page, size);
        result.put("productInfoPage", productInfoPage);
        result.put("currentPage", page);
        result.put("size", size);
        return new ModelAndView("product/list", result);
    }

    /**
     * 商品上架
     * @param productId
     * @param result
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> result) {
        try {
            productInfoService.onSale(productId);
        }catch (WeiXinSellException e) {
            result.put("msg", e.getMessage());
            result.put("url", "/weixin_sell/seller/product/list");
            return new ModelAndView("common/error", result);
        }
        result.put("url", "/weixin_sell/seller/product/list");
        return new ModelAndView("common/success", result);
    }

    /**
     * 商品下架
     * @param productId
     * @param result
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> result) {
        try {
            productInfoService.offSale(productId);
        }catch (WeiXinSellException e) {
            result.put("msg", e.getMessage());
            result.put("url", "/weixin_sell/seller/product/list");
            return new ModelAndView("common/error", result);
        }
        result.put("url", "/weixin_sell/seller/product/list");
        return new ModelAndView("common/success", result);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> result) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.selectOne(productId);
            result.put("productInfo", productInfo);
        }

        // 查询所有类目
        List<ProductCategory> productCategoryList =  productCategoryService.selectAll();
        result.put("productCategoryList", productCategoryList);
        return new ModelAndView("product/index", result);
    }

    /**
     * 保存/更新
     * @param productForm
     * @param bindingResult
     * @param result
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> result) {
        if (bindingResult.hasErrors()) {
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
            result.put("url", "/weixin_sell/seller/product/index");
            return new ModelAndView("common/error", result);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            // 如果productId 为空，说明是新增
            if (StringUtils.isEmpty(productForm.getProductId())) {
                productForm.setProductId(KeyUtils.genUniqueKey());
                BeanUtils.copyProperties(productForm, productInfo);
                productInfoService.insert(productInfo);
            } else {
                productInfo = productInfoService.selectOne(productForm.getProductId());
                BeanUtils.copyProperties(productForm, productInfo);
                productInfoService.update(productInfo);
            }
        } catch (WeiXinSellException e) {
            result.put("msg", e.getMessage());
            result.put("url", "/weixin_sell/seller/product/index");
            return new ModelAndView("common/error", result);
        }
        result.put("url", "/weixin_sell/seller/product/list");
        return new ModelAndView("common/success", result);

    }
}
