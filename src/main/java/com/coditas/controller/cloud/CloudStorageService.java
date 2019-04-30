package com.coditas.controller.cloud;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * @author Harshal Patil
 */
@Component
public class CloudStorageService {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Bucket bucket;

    @Value("${app.cloudBucket}")
    private String bucketName;

    @PostConstruct
    public void init() {

        try {

//        Storage storage = StorageOptions.newBuilder()
//                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:json"))))
//                .build()
//                .getService();

            Storage storage = StorageOptions.getDefaultInstance().getService();
            bucket = storage.get(bucketName);
        } catch (Exception e) {
            LOGGER.info("While initializing CloudStorageService");
            e.printStackTrace();
            LOGGER.error("EXCEPTION {}, {}", e.getMessage(), e);
        }
    }

    public String storeImage(String fileName, InputStream imageFilePath) {
        LOGGER.info("CloudStorageService starts storeImage:: fileName={}", fileName);

        try {
            bucket.create(fileName, imageFilePath, "image/jpeg");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION   {}, {}", e.getMessage(), e);
            LOGGER.error("CloudStorageService::storeImage Failed..Retrying.");

            try {
                bucket.create(fileName, imageFilePath);
            } catch (Exception e1) {
                e1.printStackTrace();
                LOGGER.error("CloudStorageService::storeImage Failed in Retry :: {}, {}", e.getMessage(), e);
            }
        }
        LOGGER.info("CloudStorageService Ends BucketPath : {}", fileName);
        return fileName;
    }

    /**
     * This Method returns byte[] of the file. Get filepath from db and get image from that location
     * @param bucketFilePath Absolute path of bucket object
     * @return byte[] of image
     */
    public byte[] getImage(String bucketFilePath) {
        try {
            return bucket.getStorage()
                    .get(bucketName, bucketFilePath)
                    .getContent();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION while getting image from GCS {}, {}", e.getMessage(), e);
        }
        return null;
    }

}
