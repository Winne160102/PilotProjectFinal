package com.example.pilotprojectfinal.controller;

import com.example.pilotprojectfinal.entity.BrandEntity;
import com.example.pilotprojectfinal.model.ResponseDataModel;
import com.example.pilotprojectfinal.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    @Qualifier("brandServiceImpl")
    private IBrandService brandService;

    @GetMapping
    public String initPage(Model model) {
        return "brand-index";
    }

    @GetMapping("/api/findAll/{pageNumber}")
    @ResponseBody
    public ResponseDataModel findAllWithPagerApi(@PathVariable("pageNumber") int pageNumber) {
        return brandService.findAllWithPagerApi(pageNumber);
    }

    @GetMapping("/api/findAll")
    @ResponseBody
    public ResponseDataModel findBrandByIdApi(@RequestParam("id") Long brandId) {
        return brandService.findBrandByIdApi(brandId);
    }

    @PostMapping(value="/api/add")
    @ResponseBody
    public ResponseDataModel addApi(@ModelAttribute BrandEntity brandEntity) {
        return brandService.addApi(brandEntity);
    }

    @PostMapping(value ="/api/update")
    @ResponseBody
    public ResponseDataModel updateApi(@ModelAttribute BrandEntity brandEntity) {
        return brandService.updateApi(brandEntity);
    }

    @DeleteMapping(value ="/api/delete/{brandId}")
    @ResponseBody
    public ResponseDataModel deleteApi(@PathVariable("brandId") Long brandId) {
        return brandService.deleteApi(brandId);
    }
}
