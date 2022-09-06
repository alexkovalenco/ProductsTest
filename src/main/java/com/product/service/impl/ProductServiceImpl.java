package com.product.service.impl;

import com.product.dto.ProductDTO;
import com.product.entity.ProductEntity;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public List<ProductEntity> findAll(String nameFilter, Pageable pageable) {
        Pattern compile = Pattern.compile(nameFilter);

     /*   Specification<ProductEntity> specification = (root, query, builder) -> builder.equal(builder.selectCase()
                .when(builder.function("regexp_like", Boolean.class, root.get("name"), builder.literal(nameFilter)), 1)
                .otherwise(0), 1);*/

        Page<ProductEntity> all = productRepository.findAllByNameRegex(nameFilter, pageable);
        System.out.println(all);

        return productRepository.findAll().stream()
                .filter(product -> !compile.matcher(product.getName()).find())
                .collect(Collectors.toList());
        //  return productRepository.findAllByNameRegex(nameFilter, pageable);
    }
}
