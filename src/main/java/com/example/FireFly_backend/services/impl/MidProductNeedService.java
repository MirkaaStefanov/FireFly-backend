package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductNeedDTO;
import com.example.FireFly_backend.models.dto.MidProductNeedDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FinalProductNeed;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.models.entity.MidProductNeed;
import com.example.FireFly_backend.repositories.FinalProductNeedRepository;
import com.example.FireFly_backend.repositories.FinalProductRepository;
import com.example.FireFly_backend.repositories.FirstProductRepository;
import com.example.FireFly_backend.repositories.MidProductNeedRepository;
import com.example.FireFly_backend.repositories.MidProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MidProductNeedService {

    private final MidProductNeedRepository midProductNeedRepository;
    private final MidProductRepository midProductRepository;
    private final FirstProductRepository firstProductRepository;
    private final ModelMapper modelMapper;

    public MidProductNeedDTO save(Long midProductId, Long firstProductId, int quantity) throws ChangeSetPersister.NotFoundException {
        MidProductNeed productNeed = new MidProductNeed();

        MidProduct midProduct = midProductRepository.findByIdAndDeletedFalse(midProductId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        FirstProduct firstProduct = firstProductRepository.findByIdAndDeletedFalse(firstProductId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        productNeed.setMidProduct(midProduct);
        productNeed.setFirstProduct(firstProduct);
        productNeed.setQuantity(quantity);

        return modelMapper.map(midProductNeedRepository.save(productNeed), MidProductNeedDTO.class);
    }

    public List<MidProductNeedDTO> findNeedsForProduct(Long productId) throws ChangeSetPersister.NotFoundException {
        MidProduct midProduct = midProductRepository.findByIdAndDeletedFalse(productId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<MidProductNeed> allForProduct = midProductNeedRepository.findAllByMidProductAndDeletedFalse(midProduct);

        return allForProduct.stream()
                .map(need -> modelMapper.map(need, MidProductNeedDTO.class))
                .collect(Collectors.toList());
    }

    public Double calculateCost(Long productId) throws ChangeSetPersister.NotFoundException {
        MidProduct midProduct = midProductRepository.findByIdAndDeletedFalse(productId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<MidProductNeed> allForProduct = midProductNeedRepository.findAllByMidProductAndDeletedFalse(midProduct);
        Double finalPrice = (double) 0;
        for (MidProductNeed midProductNeed : allForProduct) {
            double priceForOneFirstProduct = midProductNeed.getFirstProduct().getPrice();
            int quantity = midProductNeed.getQuantity();
            Double costOfOneType = priceForOneFirstProduct * quantity;
            finalPrice += costOfOneType;
        }
        return finalPrice;
    }


}
