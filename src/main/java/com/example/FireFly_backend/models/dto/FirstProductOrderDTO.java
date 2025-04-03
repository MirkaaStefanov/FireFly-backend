package com.example.FireFly_backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstProductOrderDTO {

    private Long id;
    private FirstProductDTO firstProduct;
    private int quantity;
    private boolean done;
}
