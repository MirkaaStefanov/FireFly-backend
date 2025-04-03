package com.example.FireFly_backend.models.dto;

import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalProductNeedDTO {

    public Long id;
    private FinalProductDTO finalProduct;
    private MidProductDTO midProduct;
    private int quantity;
    private boolean deleted;

}
