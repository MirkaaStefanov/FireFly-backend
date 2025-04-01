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
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinalProductService {

    private final FinalProductRepository finalProductRepository;
    private final ModelMapper modelMapper;



    public FinalProductDTO save(FinalProductDTO productDTO) throws IOException {
        FinalProduct product = modelMapper.map(productDTO, FinalProduct.class);

        byte[] decodedImage = Base64.getDecoder().decode(productDTO.getImage());
        product.setImage(decodedImage);
        FinalProduct savedProduct = finalProductRepository.save(product);
        return modelMapper.map(savedProduct, FinalProductDTO.class);
    }

    public List<FinalProductDTO> findAll() {
        List<FinalProduct> products = finalProductRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, FinalProductDTO.class))
                .collect(Collectors.toList());
    }

}
