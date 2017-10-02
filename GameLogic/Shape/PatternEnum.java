package com.game.bentz.ShapeShift.GameLogic.Shape;

/**
 * Created by Michael on 6/28/16.
 */
public enum PatternEnum {
    PATTERN_1(0),
    PATTERN_2(1),
    PATTERN_3(2),
    PATTERN_4(3);

    private final Integer value;
    private PatternEnum(int value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
