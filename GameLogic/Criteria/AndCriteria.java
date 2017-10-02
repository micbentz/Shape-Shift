package com.game.bentz.ShapeShift.GameLogic.Criteria;

import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;

import java.util.List;

/**
 * Created by Michael on 5/6/16.
 * A <code>AndCriteria</code> will check the given Lists for both criteria and return
 * the corresponding list
 */
public class AndCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    public AndCriteria(Criteria criteria, Criteria otherCriteria){
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Shape> meetCriteria(List<Shape> shapes){
        List<Shape> firstCriteraShapes = criteria.meetCriteria(shapes);
        return otherCriteria.meetCriteria(firstCriteraShapes);
    }


    // Have to use or logic to get criteria that do not meet the current condition
    @Override
    public List<Shape> doesNotMeetCriteria(List<Shape> shapes) {
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
