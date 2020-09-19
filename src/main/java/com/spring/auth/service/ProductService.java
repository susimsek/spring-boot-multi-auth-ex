package com.spring.auth.service;

import com.spring.auth.model.Product;
import com.spring.auth.model.User;
import com.spring.auth.payload.request.ProductCreateRequest;
import com.spring.auth.payload.request.UserCreateRequest;

public interface ProductService {

    Product createProduct(ProductCreateRequest productCreateRequest);
}
