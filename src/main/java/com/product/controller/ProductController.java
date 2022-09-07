package com.product.controller;

import com.product.dto.ProductDTO;
import com.product.entity.ProductEntity;
import com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/product", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity(productService.create(productDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/product", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> productsByQuery(@RequestParam(value = "nameFilter") String nameFilter,
                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductEntity> content = productService.findAll(nameFilter, pageable).getContent();
        if(content.isEmpty()){
            return new ResponseEntity<>(content, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @GetMapping(path = "/product/test", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> productsByJava(@RequestParam(value = "nameFilter") String nameFilter) {
        List<ProductEntity> products = productService.findAll(nameFilter);
        if(products.isEmpty()){
            return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
