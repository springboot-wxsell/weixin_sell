package com.sell.service;

import com.github.pagehelper.PageInfo;
import com.sell.dto.CartDTO;
import com.sell.pojo.ProductInfo;


import java.util.List;

/**
 * @author WangWei
 * @Title: ProductInfoService
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/15 18:30
 */
public interface ProductInfoService {

    /**
     * 根据商品状态查询商品
     *
     * @param productStatus
     * @return
     */
    List<ProductInfo> selectByProductStatus(Byte productStatus);

    ProductInfo selectOne(String productId);

    PageInfo<ProductInfo> selectAll(int pageNum, int pageSize);

    int insert(ProductInfo productInfo);

    int update(ProductInfo productInfo);

    // 加库存
    void incrementStock(List<CartDTO> cartDTOList);

    // 减库存
    void decrementStock(List<CartDTO> cartDTOList);

    // 上架
    int onSale(String productId);

    // 下架
    int offSale(String productId);
}
