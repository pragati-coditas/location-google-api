package com.coditas.controller.cloud;


import com.coditas.controller.exception.ControllerException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author Harshal Patil
 */
@Component
public class GCSSignedURLGenerator {

    @Value("${app.cloudBucket}")
    private String bucketName;

    static JSONObject jsonObject;

    final String BASE_GCS_URL = "https://storage.googleapis.com";

    private String clientAccount;

    private String privateKey;

    // expiry time of the url in Linux epoch form (seconds since january 1970)
    String expiryTime;

    public String getSignUrl(String objectPath) {
        String signedUrl = null;
        try {

            File file = ResourceUtils.getFile("classpath:snap2insight-core-82f625cd83f3.json");
            String content = new String(Files.readAllBytes(file.toPath()));

            this.jsonObject = new JSONObject(content);
            this.clientAccount = this.jsonObject.getString("client_email");
            this.privateKey = this.jsonObject.getString("private_key");

            setExpiryTimeInEpoch();

            String stringToSign = getSignInput(objectPath);
            PrivateKey pk = getPrivateKey();
            String signedString = getSignedString(stringToSign, pk);

            // URL encode the signed string so that we can add this URL
            signedString = URLEncoder.encode(signedString, "UTF-8");

            signedUrl = getSignedUrl(signedString, objectPath);
        } catch (Exception e) {
            throw new ControllerException("Error while generating sign url");
        }
        return signedUrl;
    }

    // Set an expiry date for the signed url. Sets it at two minutes ahead of current time.
    private void setExpiryTimeInEpoch() {
        long now = System.currentTimeMillis();
        long expiredTimeInSeconds = (now + 1200 * 1000L) / 1000;
        expiryTime = expiredTimeInSeconds + "";
    }

    private String getSignedUrl(String signedString, String objectPath) {
        String signedUrl = this.BASE_GCS_URL + "/" + this.bucketName + "/" + objectPath
                + "?GoogleAccessId=" + this.clientAccount
                + "&Expires=" + expiryTime
                + "&Signature=" + signedString;
        return signedUrl;
    }

    private String getSignInput(String objectPath) {
        return "GET" + "\n"
                + "" + "\n"
                + "" + "\n"
                + expiryTime + "\n"
                + "/" + this.bucketName + "/" + objectPath;
    }

    // Use SHA256withRSA to sign the request
    private String getSignedString(String input, PrivateKey pk) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(pk);
        privateSignature.update(input.getBytes("UTF-8"));
        byte[] s = privateSignature.sign();
        return Base64.getEncoder().encodeToString(s);
    }

    // Get private key object from unencrypted PKCS#8 file content
    private PrivateKey getPrivateKey() throws Exception {

        // Remove extra characters in private key.
        String realPK = this.privateKey.replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("-----BEGIN PRIVATE KEY-----", "").replaceAll("\n", "");
        byte[] b1 = Base64.getDecoder().decode(realPK);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

}