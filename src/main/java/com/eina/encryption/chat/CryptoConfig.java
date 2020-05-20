package com.eina.encryption.chat;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

@Configuration
public class CryptoConfig {

    @Value("${spring.secret}:tmdad_pass")
    private String rawSecret;

    private KeyPair keyPair;

    private Logger logger = LoggerFactory.getLogger(CryptoConfig.class);

    @Bean
    public AesBytesEncryptor symEncryptor(){
        String salt = KeyGenerators.string().generateKey();
        return new AesBytesEncryptor(rawSecret, salt);
    }

    public CryptoConfig()  throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        this.keyPair = keyGen.generateKeyPair();
    }

    @Bean
    public Cipher asymEncrypt(){
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, this.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error creating asymmetric encrypter");
        }
        return cipher;
    }


    @Bean
    public Cipher asymDecrypt(){
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, this.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error creating asymmetric decrypter");
        }
        return cipher;
    }

    public PublicKey getPublicKey() {
        return this.keyPair.getPublic();
    }

    private PrivateKey getPrivateKey() {
        return this.keyPair.getPrivate();
    }
}
