package com.game.bentz.ShapeShift.GameLogic.Criteria;

import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;

import java.util.List;

/**
 * Created by Michael on 5/6/16.
 * A <code>OrCriteria</code> will check the given Lists for either criteria and return
 * the corresponding list
 */

public class OrCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria){
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Shape> meetCriteria(List<Shape> shapes){
        List<Shape> firstCriteriaShapes = criteria.meetCriteria(shapes);
        List<Shape> otherCriteriaShapes = otherCriteria.meetCriteria(shapes);

        for (Shape shape: otherCriteriaShapes){
            if (!firstCriteriaShapes.contains(shape)){
                firstCriteriaShapes.add(shape);
            }
        }
        return firstCriteriaShapes;
    }

    @Override
    public List<Shape> doesNotMeetCriteria(List<Shape> shapes){
        List<Shape> firstCriteriaShapes = criteria.doesNotMeetCriteria(shapes);
        List<Shape> otherCriteriaShapes = otherCriteria.doesNotMeetCriteria(shapes);

        for (Shape shape: otherCriteriaShapes){
            if (!firstCriteriaShapes.contains(shape)){
                firstCriteriaShapes.add(shape);
            }
        }
        return firstCriteriaShapes;
    }
}
