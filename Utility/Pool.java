package com.game.bentz.ShapeShift.Utility;

import com.game.bentz.ShapeShift.GameLogic.Shape.CategoryEnum;
import com.game.bentz.ShapeShift.GameLogic.Shape.Classification;
import com.game.bentz.ShapeShift.GameLogic.Shape.PatternEnum;
import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Michael on 5/21/16.
 */
public class Pool {
    private static final String TAG = "Pool";
    private static Pool instance;
    private final int SIZE = 50;
    private final int MAX = 3;
    private final int MIN = 1;
    private HashMap<Integer,CategoryEnum> categoryEnumHashMap;
    private HashMap<Integer,PatternEnum> patternEnumHashMap;
    private List<Shape> poolShapes;

    private Pool(){
        categoryEnumHashMap = new HashMap<>();
        patternEnumHashMap = new HashMap<>();
        poolShapes = new CopyOnWriteArrayList<>();
        init();
    }

    public static synchronized Pool getInstance(){
        if (instance == null){
            instance = new Pool();
        }
        return instance;
    }

    public void addShape(Shape shape){
        poolShapes.add(shape);
    }

    public List<Shape> getList(){
        return poolShapes;
    }

    public Shape getRandomShape(){
        int MAX = poolShapes.size() - Constants.ROW_SIZE;
        int MIN = 0;

        // Get a random Criteria
        Random rand = new Random();
        int next = rand.nextInt(MAX - MIN + 1) + MIN;


        Shape shape = poolShapes.get(next);
        poolShapes.remove(next);
        return shape;
    }

    // This method is called by an AsyncTask
    public List<Shape> getRandomRow(){
        List<Shape> temp = new ArrayList<>();
        Random rand = new Random();

        // MAX accounts for the AsyncTask
        // The size may have decreased by the time this task is given cpu time
        int MAX = poolShapes.size() - Constants.ROW_SIZE;
        int MIN = 0;
        int next;

        int rowSize = rand.nextInt(Constants.ROW_SIZE) + 1;

        for(int i = 0; i < rowSize; i++){
            next = rand.nextInt(MAX - MIN) + MIN;
            temp.add(poolShapes.get(next));
            poolShapes.remove(next);
        }

        return temp;
    }

    private void populateMap(){
        categoryEnumHashMap.put(1,CategoryEnum.CATEGORY_1);
        categoryEnumHashMap.put(2,CategoryEnum.CATEGORY_2);
        categoryEnumHashMap.put(3,CategoryEnum.CATEGORY_3);
        patternEnumHashMap.put(1,PatternEnum.PATTERN_1);
        patternEnumHashMap.put(2,PatternEnum.PATTERN_2);
        patternEnumHashMap.put(3,PatternEnum.PATTERN_3);
    }

    private void init(){
        // Populate map for quick lookup
        populateMap();

        // Creates x shapes and adds them to the pool
        for (int i = 0; i < SIZE; i++){
            Random random = new Random();

            // Get a random Category and Pattern
            int category = random.nextInt(MAX - MIN + 1) + MIN;
            int pattern = random.nextInt(MAX - MIN + 1) + MIN;

            // Create a classification with the category and pattern
            Classification classification = new Classification(categoryEnumHashMap.get(category),patternEnumHashMap.get(pattern));

            // Create a shape with the classification
            Shape shape = new Shape(classification);

            // Add the shape to the pool
            poolShapes.add(shape);
        }
    }
}
