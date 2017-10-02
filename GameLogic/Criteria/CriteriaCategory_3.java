package com.game.bentz.ShapeShift.GameLogic.Criteria;

import com.game.bentz.ShapeShift.GameLogic.Shape.CategoryEnum;
import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 5/6/16.
 * A <code>CriteriaCategory_3</code> will check if the List contains "Triangle" shapes and
 * return the corresponding list
 */
public class CriteriaCategory_3 implements Criteria{

    @Override
    public List<Shape> meetCriteria(List<Shape> shapes) {
        List<Shape> categoryShapes = new ArrayList<>();

        for (Shape shape: shapes){
//            if (shape.getColor().equalsIgnoreCase("BLUE")) {
            if(shape.getClassification().getCategory() == CategoryEnum.CATEGORY_3){
                categoryShapes.add(shape);
            }
        }
        return categoryShapes;
    }

    @Override
    public List<Shape> doesNotMeetCriteria(List<Shape> shapes) {
        List<Shape> nonCategoryShapes = new ArrayList<>();

        for (Shape shape: shapes){
//            if (!shape.getColor().equalsIgnoreCase("BLUE")){
            if(shape.getClassification().getCategory() != CategoryEnum.CATEGORY_3){
                nonCategoryShapes.add(shape);
            }
        }
        return nonCategoryShapes;
    }
}
