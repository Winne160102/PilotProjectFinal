package com.example.pilotprojectfinal.service;

import com.example.pilotprojectfinal.entity.ProductEntity;
import com.example.pilotprojectfinal.model.ResponseDataModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface IProductService {
    ProductEntity add(ProductEntity productEntity);

    ProductEntity update(ProductEntity productEntity);

    ResponseDataModel delete(Long productId);

    List<ProductEntity> getAll();

    ProductEntity findByProductId(Long productId);

    ProductEntity findByProductName(String productName);

    Map<String, Object> findAllWithPager(int pageNumber);

    ResponseDataModel findAllWithPagerApi(int pageNumber);

    ResponseDataModel findProductByIdApi(Long productId);

    ResponseDataModel addApi(ProductEntity productEntity);

    ResponseDataModel updateApi(ProductEntity productEntity);

    ResponseDataModel deleteApi(Long productId);

    ResponseDataModel search(int pageNumber, String keyword);
}
