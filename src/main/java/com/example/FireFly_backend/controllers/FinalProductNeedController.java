package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FinalProductNeedDTO;
import com.example.FireFly_backend.services.impl.FinalProductNeedService;
import com.example.FireFly_backend.services.impl.FinalProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/finalProductNeed")
@AllArgsConstructor
public class FinalProductNeedController {
    private final FinalProductNeedService finalProductNeedService;

    @PostMapping("/save")
    public ResponseEntity<FinalProductNeedDTO> save(@RequestParam Long finalProductId, @RequestParam Long midProductId, @RequestParam int quantity) throws IOException, ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(finalProductNeedService.save(finalProductId, midProductId, quantity));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<FinalProductNeedDTO>> findAllForFinalProduct(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(finalProductNeedService.findNeedsForProduct(id));
    }
}
