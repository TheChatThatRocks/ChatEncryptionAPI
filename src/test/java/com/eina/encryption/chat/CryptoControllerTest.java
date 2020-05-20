package com.eina.encryption.chat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CryptoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void symmetricCryptoTest() throws Exception{
        try {
            String data = "dataTestForSymmetricEncrypt";
            MvcResult symEncrypted = mockMvc.perform(post("/symmetricEncrypt").content(data)
                .contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andReturn();
            String encrypted = symEncrypted.getResponse().getContentAsString();
            System.out.println("Data to encrypt: " + data);
            System.out.println("Symmetic encrypted data: " + encrypted);
            MvcResult symDecrypted = mockMvc.perform(post("/symmetricDecrypt").content(encrypted)
                    .contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andReturn();
            String decrypted = symDecrypted.getResponse().getContentAsString();
            System.out.println("Symmetic decrypted data: " + decrypted);
            assert data.equals(decrypted);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void asymmetricCryptoTest() throws Exception{
        try {
            String data = "dataTestForAAASymmetricEncrypt";
            MvcResult symEncrypted = mockMvc.perform(post("/asymmetricEncrypt").content(data)
                    .contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andReturn();
            String encrypted = symEncrypted.getResponse().getContentAsString();
            System.out.println("Data to encrypt: " + data);
            System.out.println("ASymmetic encrypted data: " + encrypted);
            MvcResult symDecrypted = mockMvc.perform(post("/asymmetricDecrypt").content(encrypted)
                    .contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andReturn();
            String decrypted = symDecrypted.getResponse().getContentAsString();
            System.out.println("ASymmetic decrypted data: " + decrypted);
            assert data.equals(decrypted);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
