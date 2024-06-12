package com.example.pilotprojectfinal.service;

import com.example.pilotprojectfinal.entity.BrandEntity;
import com.example.pilotprojectfinal.model.ResponseDataModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface IBrandService {
    BrandEntity add(BrandEntity brandEntity);

    BrandEntity update(BrandEntity brandEntity);

    ResponseDataModel delete(Long brandId);

    List<BrandEntity> getAll();

    BrandEntity findByBrandId(Long brandId);

    BrandEntity findByBrandName(String brandName);

    Map<String, Object> findAllWithPager(int pageNumber);

    ResponseDataModel findAllWithPagerApi(int pageNumber);

    ResponseDataModel findBrandByIdApi(Long brandId);

    ResponseDataModel addApi(BrandEntity brandEntity);

    ResponseDataModel updateApi(BrandEntity brandEntity);

    ResponseDataModel deleteApi(Long brandId);

    ResponseDataModel search(int pageNumber , String keyword);
}
