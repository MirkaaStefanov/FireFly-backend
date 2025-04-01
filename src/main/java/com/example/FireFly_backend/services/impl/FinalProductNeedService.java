package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductNeedDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FinalProductNeed;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.repositories.FinalProductNeedRepository;
import com.example.FireFly_backend.repositories.FinalProductRepository;
import com.example.FireFly_backend.repositories.MidProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinalProductNeedService {

    private final FinalProductNeedRepository finalProductNeedRepository;
    private final FinalProductRepository finalProductRepository;
    private final MidProductRepository midProductRepository;
    private final ModelMapper modelMapper;

    public FinalProductNeedDTO save(Long finalProductId, Long midProductId, int quantity) throws ChangeSetPersister.NotFoundException {
        FinalProductNeed productNeed = new FinalProductNeed();

        FinalProduct finalProduct = finalProductRepository.findById(finalProductId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        MidProduct midProduct = midProductRepository.findById(midProductId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        productNeed.setFinalProduct(finalProduct);
        productNeed.setMidProduct(midProduct);
        productNeed.setQuantity(quantity);

        return modelMapper.map(finalProductNeedRepository.save(productNeed), FinalProductNeedDTO.class);
    }

    public List<FinalProductNeedDTO> findNeedsForProduct(Long productId) throws ChangeSetPersister.NotFoundException {
        FinalProduct finalProduct = finalProductRepository.findById(productId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<FinalProductNeed> allForProduct = finalProductNeedRepository.findAllByFinalProduct(finalProduct);

        return allForProduct.stream()
                .map(need -> modelMapper.map(need, FinalProductNeedDTO.class))
                .collect(Collectors.toList());
    }

}
