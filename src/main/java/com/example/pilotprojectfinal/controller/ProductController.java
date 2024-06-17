package com.example.pilotprojectfinal.controller;

import com.example.pilotprojectfinal.entity.ProductEntity;
import com.example.pilotprojectfinal.model.ResponseDataModel;
import com.example.pilotprojectfinal.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    @Qualifier("productServiceImpl")
    private IProductService productService;

    @GetMapping
    public String initPage(Model model) {
        return "product-index";
    }

    @GetMapping("/api/findAll/{pageNumber}")
    @ResponseBody
    public ResponseDataModel findAllWithPagerApi(@PathVariable("pageNumber") int pageNumber) {
        return productService.findAllWithPagerApi(pageNumber);
    }

    @GetMapping("/api/findAll")
    @ResponseBody
    public ResponseDataModel findProductByIdApi(@RequestParam("id") Long productId) {
        return productService.findProductByIdApi(productId);
    }

    @PostMapping(value="/api/add")
    @ResponseBody
    public ResponseDataModel addApi(@ModelAttribute ProductEntity productEntity) {
        return productService.addApi(productEntity);
    }

    @PostMapping(value ="/api/update")
    @ResponseBody
    public ResponseDataModel updateApi(@ModelAttribute ProductEntity productEntity) {
        return productService.updateApi(productEntity);
    }

    @DeleteMapping(value ="/api/delete/{productId}")
    @ResponseBody
    public ResponseDataModel deleteApi(@PathVariable("productId") Long productId) {
        return productService.deleteApi(productId);
    }

    @GetMapping(value = { "/api/search/{keyword}/{pageNumber}" })
    @ResponseBody
    public ResponseDataModel searchApi(@PathVariable("keyword") String keyword,
                                       @PathVariable("pageNumber") int pageNumber) {
        return productService.search(pageNumber, keyword);
    }
}
