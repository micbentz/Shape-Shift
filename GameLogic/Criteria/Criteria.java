package com.game.bentz.ShapeShift.GameLogic.Criteria;

import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;

import java.util.List;

/**
 * Created by Michael on 5/6/16.
 * A <code>Criteria</code> is an interface that when implemented will check if the list meets the specified criteria
 */
public interface Criteria {
    /**
     * Checks if the list of shapes meets the criteria
     * @param shapes the List of Shapes to check
     * @return the list of shapes that meet the criteria
     */
    public List<Shape> meetCriteria(List<Shape> shapes);

    /**
     * Checks if the list of shapes does not meet the criteria
     * @param shapes the List of Shapes to check
     * @return the list of shapes that didn't meet the criteria
     */
    public List<Shape> doesNotMeetCriteria(List<Shape> shapes);
}
