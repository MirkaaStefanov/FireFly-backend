package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductOrderDTO;
import com.example.FireFly_backend.models.dto.FirstProductOrderDTO;
import com.example.FireFly_backend.models.entity.FinalProductOrder;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.FirstProductOrder;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.models.entity.MidProductNeed;
import com.example.FireFly_backend.models.entity.MidProductOrder;
import com.example.FireFly_backend.repositories.FinalProductNeedRepository;
import com.example.FireFly_backend.repositories.FinalProductOrderRepository;
import com.example.FireFly_backend.repositories.FinalProductRepository;
import com.example.FireFly_backend.repositories.FirstProductOrderRepository;
import com.example.FireFly_backend.repositories.FirstProductRepository;
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
public class FirstProductOrderService {

    private final FirstProductOrderRepository firstProductOrderRepository;
    private final MidProductNeedRepository midProductNeedRepository;
    private final FirstProductRepository firstProductRepository;
    private final ModelMapper modelMapper;

    public void returnListWhenFinalProductOrdered(List<MidProductOrder> midProductOrderList, int requiredQuantity) {

        for (MidProductOrder midProductOrder : midProductOrderList) {
            List<MidProductNeed> midProductNeeds = midProductNeedRepository.findAllByMidProductAndDeletedFalse(midProductOrder.getMidProduct());
            for (MidProductNeed midProductNeed : midProductNeeds) {
                Optional<FirstProductOrder> optionalFirstProductOrder = firstProductOrderRepository.findByFirstProductAndDeletedFalse(midProductNeed.getFirstProduct());
                if (optionalFirstProductOrder.isPresent()) {
                    FirstProductOrder firstProductOrder = optionalFirstProductOrder.get();
                    firstProductOrder.setQuantity(firstProductOrder.getQuantity() + midProductOrder.getQuantity() * midProductNeed.getQuantity());
                    firstProductOrderRepository.save(firstProductOrder);
                } else {
                    FirstProductOrder firstProductOrder = new FirstProductOrder();
                    firstProductOrder.setFirstProduct(midProductNeed.getFirstProduct());
                    firstProductOrder.setQuantity(midProductOrder.getQuantity() * midProductNeed.getQuantity());
                    firstProductOrderRepository.save(firstProductOrder);
                }
            }
        }

    }

    public void returnListWhenMidProductOrdered(List<MidProductNeed> midProductNeeds, int requiredQuantity) {
        List<FirstProductOrder> firstProductOrderList = new ArrayList<>();

        for (MidProductNeed midProductNeed : midProductNeeds) {
            Optional<FirstProductOrder> optionalFirstProductOrder = firstProductOrderRepository.findByFirstProductAndDeletedFalse(midProductNeed.getFirstProduct());
            if (optionalFirstProductOrder.isPresent()) {
                FirstProductOrder firstProductOrder = optionalFirstProductOrder.get();
                firstProductOrder.setQuantity(firstProductOrder.getQuantity() + midProductNeed.getQuantity() * requiredQuantity);
                firstProductOrderRepository.save(firstProductOrder);
            }else{
                FirstProductOrder firstProductOrder = new FirstProductOrder();
                firstProductOrder.setFirstProduct(midProductNeed.getFirstProduct());
                firstProductOrder.setQuantity(midProductNeed.getQuantity() * requiredQuantity);
                firstProductOrderList.add(firstProductOrder);
                firstProductOrderRepository.save(firstProductOrder);
            }
        }

    }

    public List<FirstProductOrderDTO> allFirstProductOrders() {
        List<FirstProductOrder> firstProductOrderList = firstProductOrderRepository.findAllByDeletedFalse();

        return firstProductOrderList.stream()
                .map(product -> modelMapper.map(product, FirstProductOrderDTO.class))
                .collect(Collectors.toList());
    }

    public void createFirstProductOrder(Long firstProductId, int requiredQuantity) throws ChangeSetPersister.NotFoundException {
        FirstProduct firstProduct = firstProductRepository.findByIdAndDeletedFalse(firstProductId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        Optional<FirstProductOrder> optionalFirstProductOrder = firstProductOrderRepository.findByFirstProductAndDeletedFalse(firstProduct);
        if (optionalFirstProductOrder.isPresent()) {
            FirstProductOrder firstProductOrder = optionalFirstProductOrder.get();
            firstProductOrder.setQuantity(firstProductOrder.getQuantity() + requiredQuantity);
            firstProductOrderRepository.save(firstProductOrder);
        } else {
            FirstProductOrder firstProductOrder = new FirstProductOrder();
            firstProductOrder.setFirstProduct(firstProduct);
            firstProductOrder.setQuantity(requiredQuantity);
            firstProductOrderRepository.save(firstProductOrder);
        }
    }


}
