package com.example.pilotprojectfinal.service;

import com.example.pilotprojectfinal.common.constant.Constant;
import com.example.pilotprojectfinal.dao.IProductDao;
import com.example.pilotprojectfinal.entity.ProductEntity;
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
public class ProductServiceImpl implements IProductService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${parent.folder.images.product}")
    private String productImageFolderPath;

    @Autowired
    IProductDao productDao;

    @Override
    public ProductEntity add(ProductEntity productEntity) {
        try {
            MultipartFile[] imageFiles = productEntity.getImageFiles();
            if (imageFiles != null && imageFiles.length > 0 && !imageFiles[0].isEmpty()) {
                String fileName = imageFiles[0].getOriginalFilename();
                Path uploadPath = Paths.get(productImageFolderPath);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (var inputStream = imageFiles[0].getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    productEntity.setImage(filePath.toString());
                } catch (IOException e) {
                    throw new IOException("Could not save image file: " + fileName, e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productDao.saveAndFlush(productEntity);
    }

    @Override
    public ProductEntity update(ProductEntity productEntity) {
        try {
            MultipartFile[] imageFiles = productEntity.getImageFiles();
            if (imageFiles != null && imageFiles.length > 0 && !imageFiles[0].isEmpty()) {
                String fileName = imageFiles[0].getOriginalFilename();
                Path uploadPath = Paths.get(productImageFolderPath);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (var inputStream = imageFiles[0].getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    productEntity.setImage(filePath.toString());
                } catch (IOException e) {
                    throw new IOException("Could not save image file: " + fileName, e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productDao.saveAndFlush(productEntity);
    }

    @Override
    public ResponseDataModel delete(Long productId) {
        ProductEntity productEntity = productDao.findByProductId(productId);
        if (productEntity != null) {
            productDao.deleteById(productId);
            productDao.flush();
            try {
                Path filePath = Paths.get(productEntity.getImage());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<ProductEntity> getAll() {
        return productDao.findAll(Sort.by(Sort.Direction.DESC, "productId"));
    }

    @Override
    public ProductEntity findByProductId(Long productId) {
        return productDao.findByProductId(productId);
    }

    @Override
    public ResponseDataModel findProductByIdApi(Long productId) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        ProductEntity productEntity = null;
        try {
            productEntity = productDao.findByProductId(productId);
            if (productEntity != null) {
                responseCode = Constant.RESULT_CD_SUCCESS;
            }
        } catch (Exception e) {
            responseMsg = "Error when finding product by ID";
            LOGGER.error("Error when finding product by ID: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg, productEntity);
    }

    @Override
    public ProductEntity findByProductName(String productName) {
        return productDao.findByProductName(productName);
    }

    @Override
    public Map<String, Object> findAllWithPager(int pageNumber) {
        Map<String, Object> responseMap = new HashMap<>();
        Sort sortInfo = Sort.by(Sort.Direction.DESC, "productId");
        Pageable pageable = PageRequest.of(pageNumber - 1, Constant.PAGE_SIZE, sortInfo);
        Page<ProductEntity> productEntitiesPage = productDao.findAll(pageable);
        responseMap.put("productsList", productEntitiesPage.getContent());
        responseMap.put("paginationInfo", new PagerModel(pageNumber, productEntitiesPage.getTotalPages()));
        return responseMap;
    }

    @Override
    public ResponseDataModel findAllWithPagerApi(int pageNumber) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Sort sortInfo = Sort.by(Sort.Direction.ASC, "productId");
            Pageable pageable = PageRequest.of(pageNumber - 1, Constant.PAGE_SIZE);
            Page<ProductEntity> productEntitiesPage = productDao.findAll(pageable);
            responseMap.put("productsList", productEntitiesPage.getContent());
            responseMap.put("paginationInfo", new PagerModel(pageNumber, productEntitiesPage.getTotalPages()));
            responseMap.put("totalItem", productEntitiesPage.getTotalElements());
            responseCode = Constant.RESULT_CD_SUCCESS;
        } catch (Exception e) {
            responseMsg = e.getMessage();
            LOGGER.error("Error when get all products: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg, responseMap);
    }

    @Override
    public ResponseDataModel addApi(ProductEntity productEntity) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;

        try {
            if (findByProductName(productEntity.getProductName()) != null) {
                responseMsg = "Product Name is duplicated";
                responseCode = Constant.RESULT_CD_DUPL;
            } else {
                MultipartFile[] imageFiles = productEntity.getImageFiles();
                if (imageFiles != null && imageFiles.length > 0 && !imageFiles[0].isEmpty()) {
                    String fileName = imageFiles[0].getOriginalFilename();
                    Path uploadPath = Paths.get(productImageFolderPath);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    try (var inputStream = imageFiles[0].getInputStream()) {
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        productEntity.setImage(filePath.toString());
                    } catch (IOException e) {
                        throw new IOException("Could not save image file: " + fileName, e);
                    }
                }
                productDao.saveAndFlush(productEntity);
                responseMsg = "Product is added successfully";
                responseCode = Constant.RESULT_CD_SUCCESS;
            }
        } catch (IOException e) {
            responseMsg = "Error when adding product";
            LOGGER.error("Error when adding product: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg);
    }

    @Override
    public ResponseDataModel updateApi(ProductEntity productEntity) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        try {
            ProductEntity duplicatedProduct = productDao.findByProductNameAndProductIdNot(productEntity.getProductName(), productEntity.getProductId());
            if (duplicatedProduct != null) {
                responseMsg = "Product Name is duplicated";
                responseCode = Constant.RESULT_CD_DUPL;
            } else {
                MultipartFile[] imageFiles = productEntity.getImageFiles();
                if (imageFiles != null && imageFiles.length > 0 && !imageFiles[0].isEmpty()) {
                    String fileName = imageFiles[0].getOriginalFilename();
                    Path uploadPath = Paths.get(productImageFolderPath);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    try (var inputStream = imageFiles[0].getInputStream()) {
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        productEntity.setImage(filePath.toString());
                    } catch (IOException e) {
                        throw new IOException("Could not save image file: " + fileName, e);
                    }
                }
                productDao.saveAndFlush(productEntity);
                responseMsg = "Product is updated successfully";
                responseCode = Constant.RESULT_CD_SUCCESS;
            }
        } catch (Exception e) {
            responseMsg = "Error when updating product";
            LOGGER.error("Error when updating product: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg);
    }

    @Override
    public ResponseDataModel deleteApi(Long productId) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        ProductEntity productEntity = productDao.findByProductId(productId);
        try {
            if (productEntity != null) {
                productDao.deleteById(productId);
                productDao.flush();
                Path filePath = Paths.get(productEntity.getImage());
                Files.deleteIfExists(filePath);
                responseMsg = "Product is deleted successfully";
                responseCode = Constant.RESULT_CD_SUCCESS;
            }
        } catch (Exception e) {
            responseMsg = "Error when deleting product";
            LOGGER.error("Error when delete product: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg);
    }

    @Override
    public ResponseDataModel search(int pageNumber, String keyword) {
        int responseCode = Constant.RESULT_CD_FAIL;
        String responseMsg = StringUtils.EMPTY;
        Map<String, Object> rpMap = new HashMap<>();
        try {
            Sort sort = Sort.by(Sort.Direction.ASC, "productName");
            Pageable pageable = PageRequest.of(pageNumber - 1, Constant.PAGE_SIZE, sort);
            Page<ProductEntity> productEntitiesPage = productDao.findByProductNameLike("%" + keyword + "%", pageable);
            rpMap.put("productsList", productEntitiesPage.getContent());
            rpMap.put("paginationInfo", new PagerModel(pageNumber, productEntitiesPage.getTotalPages()));
            rpMap.put("totalItem", productEntitiesPage.getTotalElements());
            responseCode = Constant.RESULT_CD_SUCCESS;
            responseMsg = "ResultSet has " + productEntitiesPage.getTotalElements() + " products";
        } catch (Exception e) {
            responseMsg = e.getMessage();
            LOGGER.error("Error when searching products: ", e);
        }
        return new ResponseDataModel(responseCode, responseMsg, rpMap);
    }
}

