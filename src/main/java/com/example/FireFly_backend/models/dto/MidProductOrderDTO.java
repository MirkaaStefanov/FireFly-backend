package com.example.FireFly_backend.models.dto;

import com.example.FireFly_backend.models.entity.MidProduct;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MidProductOrderDTO {

    private Long id;
    private MidProductDTO midProduct;
    private int quantity;
    private boolean done;
    private boolean deleted;
}
