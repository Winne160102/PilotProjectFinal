package com.example.pilotprojectfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BRAND")
@Getter
@Setter
@NoArgsConstructor
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BRAND_ID", nullable = false)
    private Long brandId;

    @Column(name = "BRAND_NAME", length = 100, nullable = true)
    private String brandName;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "LOGO", nullable = true)
    private String logo;

    @JsonIgnore
    @OneToMany(mappedBy = "brandEntity", fetch = FetchType.LAZY)
    private Set<ProductEntity> productSet;

    public Set<ProductEntity> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<ProductEntity> productSet) {
        this.productSet = productSet;
    }

    @Transient
    private MultipartFile[] logoFiles;

    public MultipartFile[] getLogoFiles() {
        return logoFiles;
    }

    public void setLogoFiles(MultipartFile[] logoFiles) {
        this.logoFiles = logoFiles;
    }
}
