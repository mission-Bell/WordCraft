package com.hyomoto.wordcraft.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordCraftModel {

    @NotEmpty    
    private String inputPath;
    @NotEmpty
    private String outPath;
    @NotEmpty
    private String target;
    @NotEmpty
    private String replacement;
    private String messages;

    public String getTargetIncluding(){
        // ターゲットを含むパターンを生成して返却
        return String.format(".*%s.*", target);
    }

    public void addMessages(String message){
        this.messages += message + "\n";

    }
}
