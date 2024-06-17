package com.example.pilotprojectfinal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "PRODUCT_NAME", length = 45, nullable = true)
    private String productName;

    @Column(name = "QUANTITY", nullable = true)
    private int quantity;

    @Column(name = "PRICE", nullable = true)
    private double price;

    @Column(name = "BRAND_ID", nullable = true)
    private int brandId;

    @Column(name = "SALE_DATE", nullable = true)
    private String saleDate;

    @Column(name = "IMAGE", nullable = true)
    private String image;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Transient
    private MultipartFile[] imageFiles;

    public MultipartFile[] getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(MultipartFile[] imageFiles) {
        this.imageFiles = imageFiles;
    }
}
