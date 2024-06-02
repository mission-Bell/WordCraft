package com.hyomoto.wordcraft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.hyomoto.wordcraft.model.WordCraftModel;
import com.hyomoto.wordcraft.service.WordCraftService;


@RestController
public class WordCraftReceptionController {

    private final WordCraftService wordCraftService;

    @Autowired
    public WordCraftReceptionController(WordCraftService wordCraftService){
        this.wordCraftService = wordCraftService;

}

    @PostMapping("replacement")
    public WordCraftModel replace(@RequestBody @Validated WordCraftModel wordCraftModel) {
        wordCraftService.replace(wordCraftModel);
        return wordCraftModel;
    }
    


}
