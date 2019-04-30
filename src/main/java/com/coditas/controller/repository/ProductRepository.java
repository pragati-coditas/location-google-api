package com.coditas.controller.repository;

import com.coditas.controller.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Harshal Patil
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Product findByUpc(String upc);

}
