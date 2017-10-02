package com.game.bentz.ShapeShift.GameLogic.Shape;


/**
 * Created by Michael on 5/6/16.
 * This factory returns a specified shape.
 */
public class ShapeFactory {

    public Shape getShape(String shapeType){
        if(shapeType == null){
            return null;
        }
        if (shapeType.equalsIgnoreCase("SQUARE")){
//            return new Square();
        }
        if (shapeType.equalsIgnoreCase("TRIANGLE")){
//            return new Triangle();
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")){
//            return new Circle();
        }
        return null;
    }
}
