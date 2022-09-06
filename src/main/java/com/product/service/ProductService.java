package com.product.service;

import com.product.dto.ProductDTO;
import com.product.entity.ProductEntity;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    ProductEntity create(ProductDTO item);

    List<ProductEntity> findAll(String nameFilter, Pageable pageable);
}
