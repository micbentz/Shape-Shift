package com.game.bentz.ShapeShift.GameLogic.Shape;

/**
 * Created by Michael on 6/28/16.
 */
public class Classification {

    private CategoryEnum categoryEnum;
    private PatternEnum patternEnum;

    public Classification(){}

    public Classification(CategoryEnum categoryEnum, PatternEnum patternEnum){
        this.categoryEnum = categoryEnum;
        this.patternEnum = patternEnum;
    }

    public CategoryEnum getCategory(){
        return categoryEnum;
    }

    public PatternEnum getPattern(){
        return patternEnum;
    }

    public void setCategory(CategoryEnum category){
        this.categoryEnum = category;
    }

    public void setPattern(PatternEnum pattern){
        this.patternEnum = pattern;
    }
}
