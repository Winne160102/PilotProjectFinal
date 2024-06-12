package com.example.pilotprojectfinal.service;

import com.example.pilotprojectfinal.common.constant.Constant;
import com.example.pilotprojectfinal.dao.IBrandDao;
import com.example.pilotprojectfinal.entity.BrandEntity;
import com.example.pilotprojectfinal.model.PagerModel;
import com.example.pilotprojectfinal.model.ResponseDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandServiceImpl implements IBrandService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${parent.folder.images.brand}")
    private String brandLogoFolderPath;

    @Autowired
    IBrandDao brandDao;

    @Override
    public BrandEntity add(BrandEntity brandEntity) {
        try {
            MultipartFile[] logoFiles = brandEntity.getLogoFiles();
            if (logoFiles != null && logoFiles.length > 0 && !logoFiles[0].isEmpty()) {
                String fileName = logoFiles[0].getOriginalFilename();
                Path uploadPath = Paths.get(brandLogoFolderPath);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (var inputStream = logoFiles[0].getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    brandEntity.setLogo(filePath.toString());
                } catch (IOException e) {
                    throw new IOException("Could not save image file: " + fileName, e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return brandDao.saveAndFlush(brandEntity);
    }

    @Override
    public BrandEntity update(BrandEntity brandEntity) {
        try {
            MultipartFile[] logoFiles = brandEntity.getLogoFiles();
            if (logoFiles != null && logoFiles.length > 0 && !logoFiles[0].isEmpty()) {
                String fileName = logoFiles[0].getOriginalFilename();
                Path uploadPath = Paths.get(brandLogoFolderPath);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (var inputStream = logoFiles[0].getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    brandEntity.setLogo(filePath.toString());
                } catch (IOException e) {
                    throw new IOException("Could not save image file: " + fileName, e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return brandDao.saveAndFlush(brandEntity);
    }

    @Override
    public ResponseDataModel delete(Long brandId) {
        BrandEntity brandEntity = brandDao.findByBrandId(brandId);
        if (brandEntity != null) {
            brandDao.deleteById(brandId);
            brandDao.flush();
            try {
                Path filePath = Paths.get(brandEntity.getLogo());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<BrandEntity> getAll() {
        return brandDao.findAll(Sort.by(Sort.Direction.DESC, "brandId"));
    }

    @Override
    public BrandEntity findByBrandId(Long brandId) {
        return null;
    }

    @Override
    public ResponseDataModel findBrandByIdApi(Long brandId) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        BrandEntity brandEntity = null;
        try {
            brandEntity = brandDao.findByBrandId(brandId);
            if (brandEntity != null) {
                responseCode = Constant.RESULT_CD_SUCCESS;
            }
        } catch (Exception e) {
            responseMsg = "Error when finding brand by ID";
            LOGGER.error("Error when finding brand by ID: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg, brandEntity);
    }

    @Override
    public BrandEntity findByBrandName(String brandName) {
        return brandDao.findByBrandName(brandName);
    }

    @Override
    public Map<String, Object> findAllWithPager(int pageNumber) {
        Map<String, Object> responseMap = new HashMap<>();
        Sort sortInfo = Sort.by(Sort.Direction.DESC, "brandId");
        Pageable pageable = PageRequest.of(pageNumber - 1, Constant.PAGE_SIZE, sortInfo);
        Page<BrandEntity> brandEntitiesPage = brandDao.findAll(pageable);
        responseMap.put("brandsList", brandEntitiesPage.getContent());
        responseMap.put("paginationInfo", new PagerModel(pageNumber, brandEntitiesPage.getTotalPages()));
        return responseMap;
    }

    @Override
    public ResponseDataModel findAllWithPagerApi(int pageNumber) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Sort sortInfo = Sort.by(Sort.Direction.ASC, "brandId");
            Pageable pageable = PageRequest.of(pageNumber - 1, 7);
            Page<BrandEntity> brandEntitiesPage = brandDao.findAll(pageable);
            responseMap.put("brandsList", brandEntitiesPage.getContent());
            responseMap.put("paginationInfo", new PagerModel(pageNumber, brandEntitiesPage.getTotalPages()));
            responseMap.put("totalItem", brandEntitiesPage.getTotalElements());
            responseCode = Constant.RESULT_CD_SUCCESS;
        } catch (Exception e) {
            responseMsg = e.getMessage();
            LOGGER.error("Error when get all brand: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg, responseMap);
    }

    @Override
    public ResponseDataModel addApi(BrandEntity brandEntity) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;

        try {
            if (findByBrandName(brandEntity.getBrandName()) != null) {
                responseMsg = "Brand Name is duplicated";
                responseCode = Constant.RESULT_CD_DUPL;
            } else {
                MultipartFile[] logoFiles = brandEntity.getLogoFiles();
                if (logoFiles != null && logoFiles.length > 0 && !logoFiles[0].isEmpty()) {
                    String fileName = logoFiles[0].getOriginalFilename();
                    Path uploadPath = Paths.get(brandLogoFolderPath);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    try (var inputStream = logoFiles[0].getInputStream()) {
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        brandEntity.setLogo(filePath.toString());
                    } catch (IOException e) {
                        throw new IOException("Could not save image file: " + fileName, e);
                    }
                }
                brandDao.saveAndFlush(brandEntity);
                responseMsg = "Brand is added successfully";
                responseCode = Constant.RESULT_CD_SUCCESS;
            }
        } catch (IOException e) {
            responseMsg = "Error when adding brand";
            LOGGER.error("Error when adding brand: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg);
    }

    @Override
    public ResponseDataModel updateApi(BrandEntity brandEntity) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        try {
            BrandEntity duplicatedBrand = brandDao.findByBrandNameAndBrandIdNot(brandEntity.getBrandName(), brandEntity.getBrandId());
            if (duplicatedBrand != null) {
                responseMsg = "Brand Name is duplicated";
                responseCode = Constant.RESULT_CD_DUPL;
            } else {
                MultipartFile[] logoFiles = brandEntity.getLogoFiles();
                if (logoFiles != null && logoFiles.length > 0 && !logoFiles[0].isEmpty()) {
                    String fileName = logoFiles[0].getOriginalFilename();
                    Path uploadPath = Paths.get(brandLogoFolderPath);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    try (var inputStream = logoFiles[0].getInputStream()) {
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        brandEntity.setLogo(filePath.toString());
                    } catch (IOException e) {
                        throw new IOException("Could not save image file: " + fileName, e);
                    }
                }
                brandDao.saveAndFlush(brandEntity);
                responseMsg = "Brand is updated successfully";
                responseCode = Constant.RESULT_CD_SUCCESS;
            }
        } catch (Exception e) {
            responseMsg = "Error when updating brand";
            LOGGER.error("Error when updating brand: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg);
    }

    @Override
    public ResponseDataModel deleteApi(Long brandId) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        BrandEntity brandEntity = brandDao.findByBrandId(brandId);
        try {
            if (brandEntity != null) {
                brandDao.deleteById(brandId);
                brandDao.flush();
                Path filePath = Paths.get(brandEntity.getLogo());
                Files.deleteIfExists(filePath);
                responseMsg = "Brand is deleted successfully";
                responseCode = Constant.RESULT_CD_SUCCESS;
            }
        } catch (Exception e) {
            responseMsg = "Error when deleting brand";
            LOGGER.error("Error when delete brand: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg);
    }

    @Override
    public ResponseDataModel search(int pageNumber, String keyword) {
        return null;
    }
}
