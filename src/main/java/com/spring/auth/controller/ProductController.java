package com.spring.auth.controller;


import com.spring.auth.config.Profiles;
import com.spring.auth.model.Product;
import com.spring.auth.payload.request.ProductCreateRequest;
import com.spring.auth.payload.request.UserCreateRequest;
import com.spring.auth.service.ProductService;
import com.spring.auth.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
@RequiredArgsConstructor
@RequestMapping("/versions/1")
public class ProductController {

    ProductService productService;

    @ApiOperation(value = "Create a Product",response = Product.class)
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        return productService.createProduct(productCreateRequest);
    }
}
