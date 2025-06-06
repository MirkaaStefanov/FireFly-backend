package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.dto.MidProductDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.repositories.FirstProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FirstProductService {
    private final FirstProductRepository firstProductRepository;
    private final ModelMapper modelMapper;
    private final ExchangeService exchangeService;


    public FirstProductDTO save(FirstProductDTO productDTO) throws IOException {
        FirstProduct product = modelMapper.map(productDTO, FirstProduct.class);
        byte[] decodedImage = Base64.getDecoder().decode(productDTO.getImage());
        product.setImage(decodedImage);
        FirstProduct savedProduct = firstProductRepository.save(product);
        return modelMapper.map(savedProduct, FirstProductDTO.class);
    }

    public List<FirstProductDTO> findAll() {
        List<FirstProduct> products = firstProductRepository.findAllByDeletedFalse();
        return products.stream()
                .map(product -> modelMapper.map(product, FirstProductDTO.class))
                .collect(Collectors.toList());
    }

    public FirstProductDTO findById(Long id) throws ChangeSetPersister.NotFoundException {
        FirstProduct firstProduct = firstProductRepository.findByIdAndDeletedFalse(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return modelMapper.map(firstProduct, FirstProductDTO.class);
    }

    public FirstProductDTO update(Long id, FirstProductDTO firstProductDTO) {
        FirstProduct firstProduct = modelMapper.map(firstProductDTO, FirstProduct.class);
        byte[] decodedImage = Base64.getDecoder().decode(firstProductDTO.getImage());
        firstProduct.setImage(decodedImage);
        firstProduct.setId(id);
        return modelMapper.map(firstProductRepository.save(firstProduct), FirstProductDTO.class);
    }

    public void delete(Long id) throws ChangeSetPersister.NotFoundException {
        FirstProduct firstProduct = firstProductRepository.findByIdAndDeletedFalse(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        firstProduct.setDeleted(true);
        firstProductRepository.save(firstProduct);
    }

}