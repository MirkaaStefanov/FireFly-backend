package com.example.FireFly_backend.models.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.mail.Multipart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalProductDTO {
    private Long id;
    private String image;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private double finalCost;
    private double tryPrice;
    private double tryFinalCost;

    @JsonIgnore
    private MultipartFile multipartFile;


}
