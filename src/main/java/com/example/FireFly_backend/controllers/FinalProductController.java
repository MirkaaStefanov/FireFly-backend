package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.repositories.FinalProductRepository;
import com.example.FireFly_backend.services.impl.FinalProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseEntity<FinalProductDTO> save(@RequestBody FinalProductDTO productDTO, @RequestHeader("Authorization") String auth) throws IOException {
        return ResponseEntity.ok(finalProductService.save(productDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FinalProductDTO>> findAll(@RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(finalProductService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<FinalProductDTO> findById(@RequestParam Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(finalProductService.findById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FinalProductDTO> update(@RequestParam Long id, @RequestBody FinalProductDTO finalProductDTO, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(finalProductService.update(id, finalProductDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@RequestParam Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        finalProductService.delete(id);
        return ResponseEntity.ok("Product have been deleted successfully");
    }
}
