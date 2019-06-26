package com.hyh.ease.rent.dao.product;

import com.hyh.ease.rent.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductDao {
    int insert(Product product);

    Product selectByPrimaryKey(@Param("id") String id);
    List<Product> query();

}
