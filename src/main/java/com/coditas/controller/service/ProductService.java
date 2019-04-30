package com.coditas.controller.service;

import com.coditas.controller.entity.Product;
import com.coditas.controller.exception.ControllerException;
import com.coditas.controller.repository.ActivityRepository;
import com.coditas.controller.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * @author Harshal Patil
 */
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Transactional
    public Product addProduct(Product product){
        if(null == product) {
            throw new ControllerException("Invalid Product");
        }
        product = productRepository.saveAndFlush(product);
        return product;
    }


    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Transactional
    public Product getProductByUpc(String upc) {

        if(null == upc) {
            throw new ControllerException("Invalid UPC");
        }
        Product product = productRepository.findByUpc(upc);
        return product;
    }

    public Product getProductByUpcAndFacingId(String upc, String facing) {
        Product product = null;
        return product;
    }
}
