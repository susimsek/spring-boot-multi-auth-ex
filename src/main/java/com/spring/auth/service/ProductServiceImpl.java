package com.spring.auth.service;

import com.spring.auth.exception.production.ProductAlreadyExistsException;
import com.spring.auth.exception.role.RoleNotFoundException;
import com.spring.auth.exception.user.UsernameAlreadyExistsException;
import com.spring.auth.model.Product;
import com.spring.auth.model.Role;
import com.spring.auth.model.User;
import com.spring.auth.model.enumerated.RoleName;
import com.spring.auth.payload.request.ProductCreateRequest;
import com.spring.auth.payload.request.UserCreateRequest;
import com.spring.auth.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ModelMapper modelMapper;


    @Override
    public Product createProduct(ProductCreateRequest productCreateRequest) {

        if (productRepository.existsByName(productCreateRequest.getName())) {
            throw new ProductAlreadyExistsException();
        }
        Product product=modelMapper.map(productCreateRequest, Product.class);

        product=productRepository.save(product);
        return product;
    }
}
