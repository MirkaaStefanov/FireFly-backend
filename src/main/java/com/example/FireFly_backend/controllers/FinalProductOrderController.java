package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.FinalProductOrderDTO;
import com.example.FireFly_backend.models.entity.FinalProductOrder;
import com.example.FireFly_backend.services.impl.FinalProductOrderService;
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
@RequestMapping("/api/v1/finalProductOrder")
@AllArgsConstructor
public class FinalProductOrderController {

    private final FinalProductOrderService finalProductOrderService;

    @PostMapping("/create")
    public ResponseEntity<String> createFinalProductOrder(@RequestParam Long finalProductId, @RequestParam int requiredQuantity, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        finalProductOrderService.createFromFinalProductOrder(finalProductId, requiredQuantity);
        return ResponseEntity.ok("Orders have been made successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<FinalProductOrderDTO>> findAllFinalProductOrders(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(finalProductOrderService.allFinalProductOrders());
    }
}
