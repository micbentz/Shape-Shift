package com.game.bentz.ShapeShift.Utility;

import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Michael on 5/22/16.
 */
public class ShapeList {
    private static final String TAG = "ShapeList";
    private static ShapeList instance;
    private List<Shape> onScreenShapes;
    private ShapeList(){
        onScreenShapes = new CopyOnWriteArrayList<>();
    }

    public static synchronized ShapeList getInstance(){
        if (instance == null){
            instance = new ShapeList();
        }
        return instance;
    }

    public void addShape(Shape shape){
        onScreenShapes.add(shape);
    }

    public List<Shape> getOnScreenShapes(){
        return this.onScreenShapes;
    }

    public Shape getShape(int location){
        return onScreenShapes.get(location);
    }

    public void removeShape(int location){
        onScreenShapes.remove(location);
    }

    public void removeShape(Shape shape){
        onScreenShapes.remove(shape);
    }
}
