package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.repositories.FirstProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class FirstProductService {
    private final FirstProductRepository firstProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FirstProductService(FirstProductRepository firstProductRepository,
                               @Qualifier("productModelMapper") ModelMapper modelMapper) {
        this.firstProductRepository = firstProductRepository;
        this.modelMapper = modelMapper;
    }

    public FirstProductDTO save(FirstProductDTO productDTO) throws IOException {
        FirstProduct product = modelMapper.map(productDTO, FirstProduct.class);
        if (productDTO.getMultipartFile() != null && !productDTO.getMultipartFile().isEmpty()) {
            product.setImage(productDTO.getMultipartFile().getBytes());
        }
        FirstProduct savedProduct = firstProductRepository.save(product);
        return modelMapper.map(savedProduct, FirstProductDTO.class);
    }
}