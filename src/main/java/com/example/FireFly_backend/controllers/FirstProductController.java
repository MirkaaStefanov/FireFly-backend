package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.dto.MidProductDTO;
import com.example.FireFly_backend.services.impl.FirstProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/api/v1/firstProduct")
@AllArgsConstructor
public class FirstProductController {
    private final FirstProductService firstProductService;

    @PostMapping("/save")
    public ResponseEntity<FirstProductDTO> save(@RequestBody FirstProductDTO productDTO,@RequestHeader("Authorization") String auth) throws IOException {
        return ResponseEntity.ok(firstProductService.save(productDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FirstProductDTO>> findAll(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(firstProductService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<FirstProductDTO> findById(@PathVariable Long id,@RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(firstProductService.findById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FirstProductDTO> update(@PathVariable Long id, @RequestBody FirstProductDTO firstProductDTO, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(firstProductService.update(id, firstProductDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        firstProductService.delete(id);
        return ResponseEntity.ok("Product have been deleted successfully");
    }

}
