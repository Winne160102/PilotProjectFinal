package com.example.pilotprojectfinal.controller;

import com.example.pilotprojectfinal.entity.BrandEntity;
import com.example.pilotprojectfinal.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("brand", new BrandEntity());
        return "BrandForm";
    }

    @PostMapping("/add")
    public String addBrand(@ModelAttribute("brand") BrandEntity brandEntity) {
        brandService.add(brandEntity);
        return "redirect:/brands";
    }

    @GetMapping("/edit/{brandId}")
    public String showEditForm(@PathVariable Long brandId, Model model) {
        BrandEntity brand = brandService.findByBrandId(brandId);
        if (brand != null) {
            model.addAttribute("brand", brand);
            return "BrandForm";
        } else {
            return "redirect:/brands";
        }
    }

    @PostMapping("/edit/{brandId}")
    public String editBrand(@PathVariable Long brandId, @ModelAttribute("brand") BrandEntity brandEntity) {
        brandEntity.setBrandId(brandId);
        brandService.update(brandEntity);
        return "redirect:/brands";
    }

    @GetMapping("/delete/{brandId}")
    public String deleteBrand(@PathVariable Long brandId) {
        brandService.delete(brandId);
        return "redirect:/brands";
    }
}
