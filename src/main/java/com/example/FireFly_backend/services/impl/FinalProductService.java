package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.config.ApplicationContextProvider;
import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.repositories.FinalProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    private final FinalProductNeedService finalProductNeedService;
    private final ExchangeService exchangeService;


    public FinalProductDTO save(FinalProductDTO productDTO) throws IOException {
        FinalProduct product = modelMapper.map(productDTO, FinalProduct.class);
        byte[] decodedImage = Base64.getDecoder().decode(productDTO.getImage());
        product.setImage(decodedImage);
        FinalProduct savedProduct = finalProductRepository.save(product);
        return modelMapper.map(savedProduct, FinalProductDTO.class);
    }

    public List<FinalProductDTO> findAll() throws ChangeSetPersister.NotFoundException {
        List<FinalProduct> products = finalProductRepository.findAllByDeletedFalse();
        return products.stream()
                .map(product -> modelMapper.map(product, FinalProductDTO.class))
                .collect(Collectors.toList());
    }

    public FinalProductDTO findById(Long id) throws ChangeSetPersister.NotFoundException {
        FinalProduct finalProduct = finalProductRepository.findByIdAndDeletedFalse(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return modelMapper.map(finalProduct, FinalProductDTO.class);
    }

    public FinalProductDTO update(Long id, FinalProductDTO finalProductDTO) {
        FinalProduct finalProduct = modelMapper.map(finalProductDTO, FinalProduct.class);
        byte[] decodedImage = Base64.getDecoder().decode(finalProductDTO.getImage());
        finalProduct.setImage(decodedImage);
        finalProduct.setId(id);
        return modelMapper.map(finalProductRepository.save(finalProduct), FinalProductDTO.class);
    }

    public void delete(Long id) throws ChangeSetPersister.NotFoundException {
        FinalProduct finalProduct = finalProductRepository.findByIdAndDeletedFalse(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        finalProduct.setDeleted(true);
        finalProductRepository.save(finalProduct);
    }

}
