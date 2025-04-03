package com.example.FireFly_backend.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MidProductDTO {
    private Long id;
    private String image;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private double tryPrice;
    @JsonIgnore
    private MultipartFile multipartFile;

}
