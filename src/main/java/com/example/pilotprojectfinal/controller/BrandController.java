package com.example.pilotprojectfinal.controller;

import com.example.pilotprojectfinal.entity.BrandEntity;
import com.example.pilotprojectfinal.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @GetMapping
    public String getAllBrands(Model model) {
        List<BrandEntity> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        return "BrandIndex";
    }
}
