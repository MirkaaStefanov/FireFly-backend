package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.dto.MidProductDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.repositories.FirstProductRepository;
import com.example.FireFly_backend.repositories.MidProductRepository;
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
public class MidProductService {

    private final MidProductRepository midProductRepository;
    private final ModelMapper modelMapper;
    private final ExchangeService exchangeService;
    private final MidProductNeedService midProductNeedService;


    public MidProductDTO save(MidProductDTO productDTO) throws IOException {
        MidProduct product = modelMapper.map(productDTO, MidProduct.class);
        byte[] decodedImage = Base64.getDecoder().decode(productDTO.getImage());
        product.setImage(decodedImage);
        MidProduct savedProduct = midProductRepository.save(product);
        return modelMapper.map(savedProduct, MidProductDTO.class);
    }

    public List<MidProductDTO> findAll() throws ChangeSetPersister.NotFoundException {
        List<MidProduct> products = midProductRepository.findAll();
        Double tryExchangeRate = exchangeService.getEurToTryRate();

        for(MidProduct midProduct : products){
            Double finalCost = midProductNeedService.calculateCost(midProduct.getId());
            midProduct.setFinalCost(finalCost);
            midProduct.setTryPrice(midProduct.getPrice()*tryExchangeRate);
            midProduct.setTryFinalCost(midProduct.getFinalCost()*tryExchangeRate);
        }
        return products.stream()
                .map(product -> modelMapper.map(product, MidProductDTO.class))
                .collect(Collectors.toList());
    }

    public MidProductDTO findById(Long id) throws ChangeSetPersister.NotFoundException {
        MidProduct midProduct = midProductRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return modelMapper.map(midProduct, MidProductDTO.class);
    }
}
