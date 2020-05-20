package com.eina.encryption.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.util.Base64;

@RestController
class CryptoController {

    @Autowired
    private AesBytesEncryptor symEncryptor;

    @Autowired
    private Cipher asymEncrypt;

    @Autowired
    private Cipher asymDecrypt;

    private Logger logger = LoggerFactory.getLogger(CryptoController.class);

    @PostMapping("/symmetricEncrypt")
    @ResponseBody
    public String symEncrypt(@RequestBody String data) {
        logger.info("SYMM ENcryption: " + data);
        return Base64.getEncoder().encodeToString(symEncryptor.encrypt(data.getBytes()));
    }

    @PostMapping("/symmetricDecrypt")
    @ResponseBody
    public String symDecrypt(@RequestBody String data) {
        logger.info("SYMM DEcryption: " + data);
        byte [] encodedData = Base64.getDecoder().decode(data);
        return new String(symEncryptor.decrypt(encodedData));
    }

    @PostMapping("/asymmetricEncrypt")
    @ResponseBody
    public String asymEncrypt(@RequestBody String data) {
        try {
            logger.info("ASYMM Encryption: " + data);
            return Base64.getEncoder().encodeToString(asymEncrypt.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error on asymmetric encrypting ");
            return "";
        }
    }

    @PostMapping("/asymmetricDecrypt")
    @ResponseBody
    public String asymDecrypt(@RequestBody String data) {
        try {
            logger.info("ASYMM DEcryption: " + data);
            byte[] encodedData = Base64.getDecoder().decode(data);
            return new String(asymDecrypt.doFinal(encodedData));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error on asymmetric encrypting");
            return "";
        }
    }
}