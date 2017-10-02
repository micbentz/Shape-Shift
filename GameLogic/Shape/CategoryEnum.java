package com.game.bentz.ShapeShift.GameLogic.Shape;

/**
 * Created by Michael on 6/28/16.
 */
public enum CategoryEnum {
    CATEGORY_1(0),
    CATEGORY_2(1),
    CATEGORY_3(2),
    CATEGORY_4(3);

    private final Integer value;
    private CategoryEnum(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
