package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.repositories.FinalProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FinalProductService {

    private final FinalProductRepository finalProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FinalProductService(FinalProductRepository finalProductRepository,  @Qualifier("productModelMapper") ModelMapper modelMapper) {
        this.finalProductRepository = finalProductRepository;
        this.modelMapper = modelMapper;
    }

    public FinalProductDTO save(FinalProductDTO productDTO) throws IOException {
        FinalProduct product = modelMapper.map(productDTO, FinalProduct.class);

        if (productDTO.getMultipartFile() != null && !productDTO.getMultipartFile().isEmpty()) {
            product.setImage(productDTO.getMultipartFile().getBytes());
        }
        FinalProduct savedProduct = finalProductRepository.save(product);
        return modelMapper.map(savedProduct, FinalProductDTO.class);
    }

}
