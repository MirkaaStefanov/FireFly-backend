package com.example.FireFly_backend.models.entity;

import com.example.FireFly_backend.config.ApplicationContextProvider;
import com.example.FireFly_backend.enums.MaterialType;
import com.example.FireFly_backend.services.impl.ExchangeService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Base64;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "first_products")
public class FirstProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;
    private String name;
    private String description;
    private double price;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;

    @Transient
    private double tryPrice;

    public String getBase64Image() {
        return Base64.getEncoder().encodeToString(this.image);
    }

}
