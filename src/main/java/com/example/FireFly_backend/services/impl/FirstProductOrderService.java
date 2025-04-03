package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductOrderDTO;
import com.example.FireFly_backend.models.dto.FirstProductOrderDTO;
import com.example.FireFly_backend.models.entity.FinalProductOrder;
import com.example.FireFly_backend.models.entity.FirstProductOrder;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FirstProductOrderService {

    private final FirstProductOrderRepository firstProductOrderRepository;
    private final MidProductNeedRepository midProductNeedRepository;
    private final ModelMapper modelMapper;

    public List<FirstProductOrder> returnListWhenFinalProductOrdered(List<MidProductOrder> midProductOrderList, int requiredQuantity) {

        List<FirstProductOrder> firstProductOrderList = new ArrayList<>();

        for (MidProductOrder midProductOrder : midProductOrderList) {
            List<MidProductNeed> midProductNeeds = midProductNeedRepository.findAllByMidProductAndDeletedFalse(midProductOrder.getMidProduct());
            for (MidProductNeed midProductNeed : midProductNeeds) {
                FirstProductOrder firstProductOrder = new FirstProductOrder();
                firstProductOrder.setFirstProduct(midProductNeed.getFirstProduct());
                firstProductOrder.setQuantity(midProductOrder.getQuantity() * midProductNeed.getQuantity() * requiredQuantity);
                firstProductOrderList.add(firstProductOrder);
            }
        }
        return firstProductOrderList;
    }

    public List<FirstProductOrder> returnListWhenMidProductOrdered(List<MidProductNeed> midProductNeeds, int requiredQuantity) {
        List<FirstProductOrder> firstProductOrderList = new ArrayList<>();

        for (MidProductNeed midProductNeed : midProductNeeds) {
            FirstProductOrder firstProductOrder = new FirstProductOrder();
            firstProductOrder.setFirstProduct(midProductNeed.getFirstProduct());
            firstProductOrder.setQuantity(midProductNeed.getQuantity() * requiredQuantity);
            firstProductOrderList.add(firstProductOrder);
        }

        return firstProductOrderList;
    }

    public List<FirstProductOrderDTO> allFirstProductOrders() {
        List<FirstProductOrder> firstProductOrderList = firstProductOrderRepository.findAllByDeletedFalse();

        return firstProductOrderList.stream()
                .map(product -> modelMapper.map(product, FirstProductOrderDTO.class))
                .collect(Collectors.toList());
    }


}
