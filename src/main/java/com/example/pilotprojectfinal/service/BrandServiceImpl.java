package com.example.pilotprojectfinal.service;

import com.example.pilotprojectfinal.dao.IBrandDao;
import com.example.pilotprojectfinal.entity.BrandEntity;
import com.example.pilotprojectfinal.model.ResponseDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Primary
public class BrandServiceImpl implements com.example.pilotprojectfinal.service.IBrandService {

    @Autowired
    private IBrandDao brandDao;

    @Override
    public BrandEntity add(BrandEntity brandEntity) {
        return null;
    }

    @Override
    public BrandEntity update(BrandEntity brandEntity) {
        return null;
    }

    @Override
    public ResponseDataModel delete(Long brandId) {
        return null;
    }

    @Override
    public List<BrandEntity> getAll() {
        return brandDao.findAll();
    }

    @Override
    public BrandEntity findByBrandId(Long brandId) {
        return null;
    }

    @Override
    public BrandEntity findByBrandName(String brandName) {
        return null;
    }

    @Override
    public Map<String, Object> findAllWithPager(int pageNumber) {
        return null;
    }

    @Override
    public ResponseDataModel findAllWithPagerApi(int pageNumber) {
        return null;
    }

    @Override
    public ResponseDataModel findBrandByIdApi(Long brandId) {
        return null;
    }

    @Override
    public ResponseDataModel addApi(BrandEntity brandEntity) {
        return null;
    }

    @Override
    public ResponseDataModel updateApi(BrandEntity brandEntity) {
        return null;
    }

    @Override
    public ResponseDataModel deleteApi(Long brandId) {
        return null;
    }

    @Override
    public ResponseDataModel search(int pageNumber, String keyword) {
        return null;
    }
}
