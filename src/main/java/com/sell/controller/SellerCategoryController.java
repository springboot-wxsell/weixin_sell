package com.sell.controller;

import com.sell.enums.ResultEnum;
import com.sell.exception.WeiXinSellException;
import com.sell.form.CategoryForm;
import com.sell.pojo.ProductCategory;
import com.sell.service.ProductCategoryService;
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

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author WangWei
 * @Title: SellerCategoryController
 * @ProjectName weixin_sell
 * @date 2018/11/11 15:13
 * @description
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 列表查询
     * @param result
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(Map<String, Object> result) {
        List<ProductCategory> productCategoryList = productCategoryService.selectAll();
        result.put("productCategoryList", productCategoryList);
        return new ModelAndView("category/list", result);
    }

    /**
     * 回显数据
     * @param categoryId
     * @param result
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index (@RequestParam(value = "categoryId", required = false) Integer categoryId,
                               Map<String, Object> result) {
        if (!StringUtils.isEmpty(categoryId)) {
            ProductCategory productCategory = productCategoryService.selectOne(categoryId);
            result.put("productCategory", productCategory);
        }
        return new ModelAndView("category/index", result);
    }


    /**
     *  保存/更新
     * @param categoryForm
     * @param bindingResult
     * @param result
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> result) {
        if (bindingResult.hasErrors()) {
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
            result.put("url", "/weixin_sell/seller/category/index");
            return new ModelAndView("common/error",  result);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (StringUtils.isEmpty(categoryForm.getCategoryId())){
                BeanUtils.copyProperties(categoryForm, productCategory);
                productCategoryService.insert(productCategory);
            } else {
                productCategory = productCategoryService.selectOne(categoryForm.getCategoryId());
                BeanUtils.copyProperties(categoryForm, productCategory);
                productCategoryService.update(productCategory);
            }
        } catch (WeiXinSellException e) {
            result.put("msg", e.getMessage());
            result.put("url", "/weixin_sell/seller/category/index");
            return new ModelAndView("common/error", result);
        }
        result.put("url", "/weixin_sell/seller/category/list");
        return new ModelAndView("common/success", result);
    }

}
