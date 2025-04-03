package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.MidProductOrderDTO;
import com.example.FireFly_backend.models.entity.MidProductOrder;
import com.example.FireFly_backend.services.impl.MidProductOrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/midProductOrder")
@AllArgsConstructor
public class MidProductOrderController {
    private final MidProductOrderService midProductOrderService;

    @PostMapping("/create")
    public ResponseEntity<String> createFromMidProductOrder(@RequestParam Long midProductId, @RequestParam int requiredQuantity, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        midProductOrderService.createFromMidProductOrder(midProductId, requiredQuantity);
        return ResponseEntity.ok("Orders have been made successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MidProductOrderDTO>> findAllMidProductOrders(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(midProductOrderService.allMidProductOrders());
    }

}
