package com.coditas.controller.controller;

import com.coditas.controller.entity.Product;
import com.coditas.controller.service.ProductService;
import com.coditas.controller.service.UserService;
import com.coditas.controller.cloud.CloudStorageService;
import com.coditas.controller.cloud.GCSSignedURLGenerator;
import com.coditas.controller.entity.Activity;
import com.coditas.controller.entity.User;
import com.coditas.controller.exception.ControllerException;
import com.coditas.controller.security.UserPrincipal;
import com.coditas.controller.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author Harshal Patil
 */
@RestController
@RequestMapping("/upc")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CloudStorageService cloudStorageService;

    @Autowired
    GCSSignedURLGenerator gcsSignedURLGenerator;

    @RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/json")
    public ApiResponse addUpc(@RequestBody Map<String, Object> upcDetails) {
        logger.info("Start: addUPC");
        ApiResponse apiResponse = new ApiResponse(true);
        try {
            Product product = new Product();
            if(null == upcDetails.get("upc")) {
                throw new ControllerException("Invalid upc upcDetails: " + upcDetails);
            }
            product.setUpc(String.valueOf(upcDetails.get("upc")));
            product.setPrice(Double.valueOf(String.valueOf(upcDetails.get("price"))));
            Activity activity = new Activity();

            List<String> facings = (ArrayList) upcDetails.get("completedFacings");
            for(String facing: facings){
                if(facing.equalsIgnoreCase("0")){
                    activity.setFrontFacing(true);
                } else if(facing.equalsIgnoreCase("11")){
                    activity.setLeftFacing(true);
                } else if(facing.equalsIgnoreCase("12")){
                    activity.setRightFacing(true);
                } else if(facing.equalsIgnoreCase("2")){
                    activity.setBottomFacing(true);
                } else if(facing.equalsIgnoreCase("3")){
                    activity.setTopFacing(true);
                } else if(facing.equalsIgnoreCase("4")){
                    activity.setRearFacing(true);
                } else if(facing.equalsIgnoreCase("99")){
                    activity.setPriceImage(true);
                }
            }
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = userService.findByEmail(userPrincipal.getEmail());

            activity.setUser(user);
            activity.setTotalPhotosCount(String.valueOf(upcDetails.get("totalPhotosCount")));
            activity.setProduct(product);
            product.setActivity(activity);
            product = productService.addProduct(product);
            logger.info("End: addUPC");
            apiResponse.addData("product", product);
            apiResponse.setMessage("Product added successfully");
        } catch (ControllerException e) {
            e.printStackTrace();
            apiResponse.setError(e.getMessage(), e.getErrorCode());
        }
        return apiResponse;
    }

    @GetMapping("/{upc}")
    public ApiResponse getProductByUPC(@PathVariable String upc) {
        Product product = null;
        ApiResponse apiResponse = new ApiResponse(true);
        try {
            product = productService.getProductByUpc(upc);
            if(null != product){
                String signedUrl = gcsSignedURLGenerator.getSignUrl("upc/" + upc + "/0.jpg");
                product.setImageUrl(signedUrl);
                apiResponse.addData("product", product);
            }else {
                apiResponse.setMessage("Product not found with upc: " + upc);
            }
        } catch (ControllerException e) {
            e.printStackTrace();
            apiResponse.setError(e.getMessage(), e.getErrorCode());
        }
        return apiResponse;
    }

    @GetMapping("/{upc}/{facingId}")
    public Product getProductByUPCAndFacingId(@PathVariable String upc, @PathVariable String facingId) {
        Product product = productService.getProductByUpcAndFacingId(upc, facingId);
        return product;
    }

    @RequestMapping(value = "/{upc}/{facingId}", method = RequestMethod.POST,
        headers = {"content-type=multipart/mixed","content-type=multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse uploadUpcImage(@PathVariable String upc,
                                   @PathVariable String facingId,
                                   @RequestPart("file") MultipartFile file,
                                   HttpServletResponse response) throws Exception {
        logger.info("Start: uploadUpcImage");
        ApiResponse apiResponse = new ApiResponse(true);
        try {

            InputStream inputStream = new ByteArrayInputStream(file.getBytes());

            String filePath = cloudStorageService.storeImage("upc/" + upc + "/" + facingId + ".jpg", inputStream);

            apiResponse.addData("FilePath", filePath);
            logger.info("End: uploadUpcImage");
            apiResponse.setMessage("Image uploaded successfully");
        } catch (ControllerException e) {
            e.printStackTrace();
            apiResponse.setError(e.getMessage(), e.getErrorCode());
        }
        return apiResponse;
    }
}
