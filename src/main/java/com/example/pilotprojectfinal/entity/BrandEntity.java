package com.example.pilotprojectfinal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

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

    @Transient
    private MultipartFile[] logoFiles;

    public MultipartFile[] getLogoFiles() {
        return logoFiles;
    }

    public void setLogoFiles(MultipartFile[] logoFiles) {
        this.logoFiles = logoFiles;
    }
}
