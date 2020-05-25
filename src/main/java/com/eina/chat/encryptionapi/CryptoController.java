package com.eina.chat.encryptionapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
class CryptoController {

    @Autowired
    private EncryptionService encryptionService;


    private Logger logger = LoggerFactory.getLogger(CryptoController.class);

    @PostMapping("/symmetricEncrypt")
    @ResponseBody
    public String symmetricEncrypt(@RequestBody String data) {
        logger.info("Symmetric Encrypt: " + data);
        return encryptionService.symmetricEncryption(data);
    }

    @PostMapping("/symmetricDecrypt")
    @ResponseBody
    public String symmetricDecrypt(@RequestBody String data) {
        logger.info("Symmetric Decrypt: " + data);
        return encryptionService.symmetricDecryption(data);
    }

    @PostMapping("/asymmetricEncrypt")
    @ResponseBody
    public String oneWayEncrypt(@RequestBody String data) {
        logger.info("One way encryption: " + data);
        return encryptionService.oneWayEncryption(data);
    }
}