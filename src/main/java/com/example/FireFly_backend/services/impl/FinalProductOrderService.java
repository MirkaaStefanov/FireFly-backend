package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FinalProductOrderDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FinalProductNeed;
import com.example.FireFly_backend.models.entity.FinalProductOrder;
import com.example.FireFly_backend.models.entity.FirstProduct;
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
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinalProductOrderService {

    private final FinalProductOrderRepository finalProductOrderRepository;
    private final MidProductOrderRepository midProductOrderRepository;
    private final FirstProductOrderRepository firstProductOrderRepository;
    private final FinalProductNeedRepository finalProductNeedRepository;
    private final FinalProductRepository finalProductRepository;
    private final MidProductOrderService midProductOrderService;
    private final FirstProductOrderService firstProductOrderService;
    private final ModelMapper modelMapper;

    public void createFromFinalProductOrder(Long finalProductId, int requiredQuantity) throws ChangeSetPersister.NotFoundException {

        FinalProduct finalProduct = finalProductRepository.findByIdAndDeletedFalse(finalProductId).orElseThrow(ChangeSetPersister.NotFoundException::new);



        Optional<FinalProductOrder> optionalFinalProductOrder = finalProductOrderRepository.findByFinalProduct(finalProduct);
        if(optionalFinalProductOrder.isPresent()){
            FinalProductOrder existedFinalProduct = optionalFinalProductOrder.get();
            existedFinalProduct.setQuantity(existedFinalProduct.getQuantity()+requiredQuantity);
            finalProductOrderRepository.save(existedFinalProduct);
        }else {
            FinalProductOrder finalProductOrder = new FinalProductOrder();
            finalProductOrder.setFinalProduct(finalProduct);
            finalProductOrder.setQuantity(requiredQuantity);
            finalProductOrderRepository.save(finalProductOrder);
        }


        List<FinalProductNeed> finalProductNeeds = finalProductNeedRepository.findAllByFinalProductAndDeletedFalse(finalProduct);

        List<MidProductOrder> midProductOrderList = midProductOrderService.returnListWhenFinalProductOrdered(finalProductNeeds, requiredQuantity);
        List<FirstProductOrder> firstProductOrderList = firstProductOrderService.returnListWhenFinalProductOrdered(midProductOrderList, requiredQuantity);

        midProductOrderRepository.saveAll(midProductOrderList);
        firstProductOrderRepository.saveAll(firstProductOrderList);

    }

    public List<FinalProductOrderDTO> allFinalProductOrders() {
        List<FinalProductOrder> firstProductOrderList = finalProductOrderRepository.findAllByDeletedFalse();

        return firstProductOrderList.stream()
                .map(product -> modelMapper.map(product, FinalProductOrderDTO.class))
                .collect(Collectors.toList());
    }



}
