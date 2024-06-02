package com.hyomoto.wordcraft.controller;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyomoto.wordcraft.WordcraftApplication;
import com.hyomoto.wordcraft.model.WordCraftModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = WordcraftApplication.class)
public class WordCraftReceptionControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void replace() throws UnsupportedEncodingException, Exception {
        String url = "/replacement";
        WordCraftModel wordCraftModel = new WordCraftModel();
        wordCraftModel.setInputPath("/home/ec2-user/environment/WordCraft/work/input.docx");
        wordCraftModel.setOutPath("/home/ec2-user/environment/WordCraft/work/output.docx");
        wordCraftModel.setTarget("芥川龍之介");
        wordCraftModel.setReplacement("hyomoto");

        String wordCraftParam = objectMapper.writeValueAsString(wordCraftModel);

        String returnString = mockMvc
                .perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(wordCraftParam))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        System.out.println(returnString);


    }

}
