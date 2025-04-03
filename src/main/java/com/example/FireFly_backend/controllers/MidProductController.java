package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.dto.MidProductDTO;
import com.example.FireFly_backend.services.impl.MidProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/midProduct")
@AllArgsConstructor
public class MidProductController {
    private final MidProductService midProductService;

    @PostMapping("/save")
    public ResponseEntity<MidProductDTO> save(@RequestBody MidProductDTO productDTO,@RequestHeader("Authorization") String auth) throws IOException {
        return ResponseEntity.ok(midProductService.save(productDTO));
    }
    @GetMapping("/all")
    public ResponseEntity<List<MidProductDTO>> findAll(@RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(midProductService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<MidProductDTO> findById(@RequestParam Long id,@RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(midProductService.findById(id));
    }
}
