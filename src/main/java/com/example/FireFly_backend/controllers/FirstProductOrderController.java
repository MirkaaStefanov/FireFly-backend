package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.FirstProductOrderDTO;
import com.example.FireFly_backend.models.entity.FirstProductOrder;
import com.example.FireFly_backend.services.impl.FirstProductOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/firstProductOrder")
@AllArgsConstructor
public class FirstProductOrderController {

    private final FirstProductOrderService firstProductOrderService;

    @GetMapping("/all")
    public ResponseEntity<List<FirstProductOrderDTO>> findAllFirstProductOrders(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(firstProductOrderService.allFirstProductOrders());
    }

}
