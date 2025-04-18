package com.example.FireFly_backend.controllers;

import com.example.FireFly_backend.models.dto.FinalProductNeedDTO;
import com.example.FireFly_backend.models.dto.MidProductNeedDTO;
import com.example.FireFly_backend.services.impl.MidProductNeedService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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
@RequestMapping("/api/v1/midProductNeed")
@AllArgsConstructor
public class MidProductNeedController {
    private final MidProductNeedService midProductNeedService;

    @PostMapping("/save")
    public ResponseEntity<MidProductNeedDTO> save(@RequestParam Long midProductId, @RequestParam Long firstProductId, @RequestParam int quantity,@RequestHeader("Authorization") String auth) throws IOException, ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(midProductNeedService.save(midProductId, firstProductId, quantity));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<MidProductNeedDTO>> findAllForFinalProduct(@PathVariable Long id,@RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(midProductNeedService.findNeedsForProduct(id));
    }
    @GetMapping("/cost/{id}")
    public ResponseEntity<Double> calculateCost(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(midProductNeedService.calculateCost(id));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<MidProductNeedDTO> update(@PathVariable Long id, @RequestBody MidProductNeedDTO midProductNeedDTO, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(midProductNeedService.update(id, midProductNeedDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        midProductNeedService.delete(id);
        return ResponseEntity.ok("Product have been deleted successfully");
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<MidProductNeedDTO> findById(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(midProductNeedService.findById(id));
    }
}
