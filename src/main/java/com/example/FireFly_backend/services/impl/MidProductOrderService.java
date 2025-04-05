package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductOrderDTO;
import com.example.FireFly_backend.models.dto.MidProductOrderDTO;
import com.example.FireFly_backend.models.entity.FinalProductNeed;
import com.example.FireFly_backend.models.entity.FinalProductOrder;
import com.example.FireFly_backend.models.entity.FirstProductOrder;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.models.entity.MidProductNeed;
import com.example.FireFly_backend.models.entity.MidProductOrder;
import com.example.FireFly_backend.repositories.FinalProductNeedRepository;
import com.example.FireFly_backend.repositories.FinalProductOrderRepository;
import com.example.FireFly_backend.repositories.FinalProductRepository;
import com.example.FireFly_backend.repositories.FirstProductOrderRepository;
import com.example.FireFly_backend.repositories.MidProductNeedRepository;
import com.example.FireFly_backend.repositories.MidProductOrderRepository;
import com.example.FireFly_backend.repositories.MidProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MidProductOrderService {

    private final MidProductOrderRepository midProductOrderRepository;
    private final FirstProductOrderRepository firstProductOrderRepository;
    private final MidProductNeedRepository midProductNeedRepository;
    private final MidProductRepository midProductRepository;
    private final FirstProductOrderService firstProductOrderService;
    private final ModelMapper modelMapper;

    public void returnListWhenFinalProductOrdered(List<FinalProductNeed> finalProductNeeds, int requiredQuantity) {
        List<MidProductOrder> midProductOrderList = new ArrayList<>();

        for (FinalProductNeed finalProductNeed : finalProductNeeds) {
            Optional<MidProductOrder> optionalMidProductOrder = midProductOrderRepository.findByMidProductAndDeletedFalse(finalProductNeed.getMidProduct());
            if (optionalMidProductOrder.isPresent()) {
                MidProductOrder newMidProducts = new MidProductOrder();
                MidProductOrder midProductOrder = optionalMidProductOrder.get();
                midProductOrder.setQuantity(midProductOrder.getQuantity() + finalProductNeed.getQuantity() * requiredQuantity);

                newMidProducts.setQuantity(finalProductNeed.getQuantity() * requiredQuantity);
                newMidProducts.setMidProduct(finalProductNeed.getMidProduct());
                midProductOrderList.add(newMidProducts);

                midProductOrderRepository.save(midProductOrder);
            } else {
                MidProductOrder midProductOrder = new MidProductOrder();
                midProductOrder.setMidProduct(finalProductNeed.getMidProduct());
                midProductOrder.setQuantity(finalProductNeed.getQuantity() * requiredQuantity);
                midProductOrderList.add(midProductOrder);
                midProductOrderRepository.save(midProductOrder);
            }
        }

        firstProductOrderService.returnListWhenFinalProductOrdered(midProductOrderList, requiredQuantity);
    }

    public void createFromMidProductOrder(Long midProductId, int requiredQuantity) throws ChangeSetPersister.NotFoundException {
        MidProduct midProduct = midProductRepository.findByIdAndDeletedFalse(midProductId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        Optional<MidProductOrder> optionalMidProductOrder = midProductOrderRepository.findByMidProductAndDeletedFalse(midProduct);
        if (optionalMidProductOrder.isPresent()) {
            MidProductOrder midProductOrder = optionalMidProductOrder.get();
            midProductOrder.setQuantity(midProductOrder.getQuantity() + requiredQuantity);
            midProductOrderRepository.save(midProductOrder);
        } else {
            MidProductOrder midProductOrder = new MidProductOrder();
            midProductOrder.setMidProduct(midProduct);
            midProductOrder.setQuantity(requiredQuantity);
            midProductOrderRepository.save(midProductOrder);
        }


        List<MidProductNeed> midProductNeeds = midProductNeedRepository.findAllByMidProductAndDeletedFalse(midProduct);
        firstProductOrderService.returnListWhenMidProductOrdered(midProductNeeds, requiredQuantity);

    }

    public List<MidProductOrderDTO> allMidProductOrders() {
        List<MidProductOrder> firstProductOrderList = midProductOrderRepository.findAllByDeletedFalse();

        return firstProductOrderList.stream()
                .map(product -> modelMapper.map(product, MidProductOrderDTO.class))
                .collect(Collectors.toList());
    }


}
