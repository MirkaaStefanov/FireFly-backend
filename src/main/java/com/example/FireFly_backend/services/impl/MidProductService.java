package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.dto.MidProductDTO;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.repositories.FirstProductRepository;
import com.example.FireFly_backend.repositories.MidProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MidProductService {

    private final MidProductRepository midProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MidProductService(MidProductRepository midProductRepository,
                               @Qualifier("productModelMapper") ModelMapper modelMapper) {
        this.midProductRepository = midProductRepository;
        this.modelMapper = modelMapper;
    }

    public MidProductDTO save(MidProductDTO productDTO) throws IOException {
        MidProduct product = modelMapper.map(productDTO, MidProduct.class);
        if (productDTO.getMultipartFile() != null && !productDTO.getMultipartFile().isEmpty()) {
            product.setImage(productDTO.getMultipartFile().getBytes());
        }
        MidProduct savedProduct = midProductRepository.save(product);
        return modelMapper.map(savedProduct, MidProductDTO.class);
    }


}
