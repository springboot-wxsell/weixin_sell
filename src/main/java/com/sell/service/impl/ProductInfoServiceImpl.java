package com.sell.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sell.WeixinSellApplication;
import com.sell.dto.CartDTO;
import com.sell.enums.ProductStatusEnum;
import com.sell.enums.ResultEnum;
import com.sell.exception.WeiXinSellException;
import com.sell.mapper.ProductInfoMapper;
import com.sell.pojo.ProductInfo;
import com.sell.pojo.ProductInfoExample;
import com.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangWei
 * @Title: ProductInfoServiceImpl
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/15 18:33
 */
@Service
//@CacheConfig(cacheNames = "product")
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> selectByProductStatus(Byte productStatus) {
        ProductInfoExample example = new ProductInfoExample();
        example.createCriteria().andProductStatusEqualTo(productStatus);
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(example);
        if (null != productInfos && productInfos.size() > 0) {
            return productInfos;
        }
        return null;
    }

    @Override
    //@Cacheable(key = "123")
    public ProductInfo selectOne(String productId) {
        return productInfoMapper.selectByPrimaryKey(productId);
    }

    @Override
    public PageInfo<ProductInfo> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductInfoExample example = new ProductInfoExample();
        List<ProductInfo> productInfoList = productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo> productInfoPageInfo = new PageInfo<>(productInfoList);
        if (null != productInfoPageInfo.getList() && productInfoPageInfo.getList().size() > 0) {
            return productInfoPageInfo;
        }
        return null;
    }

    @Override
    public int insert(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public int update(ProductInfo productInfo) {
        return productInfoMapper.updateByPrimaryKeySelective(productInfo);
    }

    @Override
    @Transactional
    public void incrementStock(List<CartDTO> cartDTOList) {
        cartDTOList.stream().forEach(e -> {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(e.getProductId());
            if (null == productInfo) {
                throw new WeiXinSellException(ResultEnum.ORDER_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock() + e.getProductQuantity();
            productInfo.setProductStock(stock);
            productInfoMapper.updateByPrimaryKey(productInfo);
        });

    }

    @Override
    @Transactional
    public void decrementStock(List<CartDTO> cartDTOList) {
        cartDTOList.stream().forEach(e -> {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(e.getProductId());
            if (null == productInfo) {
                throw new WeiXinSellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer stock = productInfo.getProductStock() - e.getProductQuantity();
            if (stock < 0) {
                throw new WeiXinSellException(ResultEnum.PRODUCT_STOCK_INSUFFICIENCY);
            }
            productInfo.setProductStock(stock);
            productInfoMapper.updateByPrimaryKey(productInfo);
        });
    }

    @Override
    public int onSale(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if (null == productInfo.getProductId()) {
            throw new WeiXinSellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode().byteValue())) {
            throw new WeiXinSellException(  ResultEnum.PRODUCT_STATUS_ERROR);
        }
        // 更新商品状态
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode().byteValue());
        return productInfoMapper.updateByPrimaryKeySelective(productInfo);
    }

    @Override
    public int offSale(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if (null == productInfo.getProductId()) {
            throw new WeiXinSellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode().byteValue())) {
            throw new WeiXinSellException(  ResultEnum.PRODUCT_STATUS_ERROR);
        }
        // 更新商品状态
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode().byteValue());
        return productInfoMapper.updateByPrimaryKeySelective(productInfo);
    }
}
