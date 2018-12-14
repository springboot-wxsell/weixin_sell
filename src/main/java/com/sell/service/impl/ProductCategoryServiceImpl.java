package com.sell.service.impl;

import com.sell.mapper.ProductCategoryMapper;
import com.sell.pojo.ProductCategory;
import com.sell.pojo.ProductCategoryExample;
import com.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangWei
 * @Title: ProductCategoryServiceImpl
 * @ProjectName sell
 * @Description: TODO
 * @date 2018/10/12 11:16
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategory selectOne(Integer categoryId) {
        return productCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public List<ProductCategory> selectAll() {
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria();
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(example);
        if (null != productCategoryList && productCategoryList.size() > 0) {
            return productCategoryList;
        }
        return null;
    }

    @Override
    public int insert(ProductCategory productCategory) {
        return productCategoryMapper.insert(productCategory);
    }

    @Override
    public int update(ProductCategory productCategory) {
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    @Override
    public List<ProductCategory> selectByCategoryTypeIn(List<Integer> categroyTypeList) {
        ProductCategoryExample example =  new ProductCategoryExample();
        example.createCriteria().andCategoryTypeIn(categroyTypeList);
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(example);
        if (null != productCategoryList && productCategoryList.size() > 0) {
            return productCategoryList;
        }
        return null;
    }
}
