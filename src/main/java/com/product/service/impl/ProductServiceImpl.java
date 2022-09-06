package com.product.service.impl;

import com.product.dto.ProductDTO;
import com.product.entity.ProductEntity;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductEntity create(ProductDTO item) {
        ProductEntity product = new ProductEntity();
        product.setName(item.getName());
        product.setDescription(item.getDescription());
        return productRepository.save(product);
    }

    @Override
    public List<ProductEntity> findAll(String nameFilter) {
        Pattern compile = Pattern.compile(nameFilter);
        return productRepository.findAll().stream()
                .filter(product -> !compile.matcher(product.getName()).find())
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductEntity> findAll(String nameFilter, Pageable pageable) {
        return productRepository.findAllByNameRegex(nameFilter, pageable);
    }
}
