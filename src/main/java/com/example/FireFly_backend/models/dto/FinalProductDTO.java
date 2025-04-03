package com.example.FireFly_backend.models.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.mail.Multipart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinalProductDTO {
    private Long id;
    private String image;
    private String name;
    private String description;
    private double price;
    private int quantity;
    @JsonIgnore
    private MultipartFile multipartFile;


}
