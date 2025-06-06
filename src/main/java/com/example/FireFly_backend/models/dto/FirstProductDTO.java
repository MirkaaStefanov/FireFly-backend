package com.example.FireFly_backend.models.dto;

import com.example.FireFly_backend.enums.MaterialType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstProductDTO {


    private Long id;
    private String image;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private MaterialType materialType;
    @JsonIgnore
    private MultipartFile multipartFile;
    private boolean deleted;

}
