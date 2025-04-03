package com.example.FireFly_backend.models.entity;

import com.example.FireFly_backend.config.ApplicationContextProvider;
import com.example.FireFly_backend.services.impl.ExchangeService;
import com.example.FireFly_backend.services.impl.FinalProductNeedService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Base64;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "final_products")
public class FinalProduct {

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

    @Transient
    private double finalCost;
    @Transient
    private double tryFinalCost;
    @Transient
    private double tryPrice;


    @PostLoad
    public void calculateFinalCost() throws ChangeSetPersister.NotFoundException {
        if (id != null) {
            FinalProductNeedService finalProductNeedService = ApplicationContextProvider.getBean(FinalProductNeedService.class);
            ExchangeService exchangeService = ApplicationContextProvider.getBean(ExchangeService.class);
            this.tryPrice = price * exchangeService.getEurToTryRate();
            this.finalCost = finalProductNeedService.calculateCost(id);
            this.tryFinalCost = finalCost * exchangeService.getEurToTryRate();
        }
    }


    public String getBase64Image() {
        return Base64.getEncoder().encodeToString(this.image);
    }

}
