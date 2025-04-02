package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.repositories.FinalProductRepository;
import com.example.FireFly_backend.services.impl.FinalProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/finalProduct")
@AllArgsConstructor
public class FinalProductController {
    private final FinalProductService finalProductService;

    @PostMapping("/save")
    public ResponseEntity<FinalProductDTO> save(@RequestBody FinalProductDTO productDTO) throws IOException {
        return ResponseEntity.ok(finalProductService.save(productDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FinalProductDTO>> findAll() {
        return ResponseEntity.ok(finalProductService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<FinalProductDTO> findById(@RequestParam Long id) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(finalProductService.findById(id));
    }

}
