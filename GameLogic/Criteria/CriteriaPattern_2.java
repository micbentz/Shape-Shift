package com.game.bentz.ShapeShift.GameLogic.Criteria;

import com.game.bentz.ShapeShift.GameLogic.Shape.PatternEnum;
import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 5/6/16.
 * A <code>CriteriaPattern_2</code> will check if the List contains "Green" shapes and
 * return the corresponding list
 */
public class CriteriaPattern_2 implements Criteria {

    @Override
    public List<Shape> meetCriteria(List<Shape> shapes) {
        List<Shape> patternShapes = new ArrayList<>();

        for (Shape shape: shapes){
//            if (shape.getColor().equalsIgnoreCase("BLUE")) {
            if(shape.getClassification().getPattern() == PatternEnum.PATTERN_2){
                patternShapes.add(shape);
            }
        }
        return patternShapes;
    }

    @Override
    public List<Shape> doesNotMeetCriteria(List<Shape> shapes) {
        List<Shape> nonPatternShapes = new ArrayList<>();

        for (Shape shape: shapes){
//            if (!shape.getColor().equalsIgnoreCase("BLUE")){
            if(shape.getClassification().getPattern() != PatternEnum.PATTERN_2){
                nonPatternShapes.add(shape);
            }
        }
        return nonPatternShapes;
    }
}
