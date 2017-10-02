package com.game.bentz.ShapeShift.GameLogic;

import com.game.bentz.ShapeShift.GameLogic.Criteria.AndCriteria;
import com.game.bentz.ShapeShift.GameLogic.Criteria.Criteria;
import com.game.bentz.ShapeShift.GameLogic.Criteria.CriteriaCategory_2;
import com.game.bentz.ShapeShift.GameLogic.Criteria.CriteriaPattern_1;
import com.game.bentz.ShapeShift.GameLogic.Criteria.CriteriaPattern_2;
import com.game.bentz.ShapeShift.GameLogic.Criteria.CriteriaPattern_3;
import com.game.bentz.ShapeShift.GameLogic.Criteria.CriteriaCategory_1;
import com.game.bentz.ShapeShift.GameLogic.Criteria.CriteriaCategory_3;
import com.game.bentz.ShapeShift.GameLogic.Criteria.OrCriteria;
import com.game.bentz.ShapeShift.GameLogic.Shape.CategoryEnum;
import com.game.bentz.ShapeShift.GameLogic.Shape.Classification;
import com.game.bentz.ShapeShift.GameLogic.Shape.PatternEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael on 5/18/16.
 * TODO update initEasy() to match the structure of initHard()
 */
public class CriteriaList {
    private static final String TAG = "CriteriaList";

    private List<Criteria> mCriteriaList;
    private Criteria mCurrentCriteria;
    private Criteria mNextCriteria;
    private HashMap<Criteria,Classification> criteriaClassificationHashMap;

    public CriteriaList(String choice){
        mCriteriaList = new ArrayList<>();
        criteriaClassificationHashMap = new HashMap<>();
        if (choice.equalsIgnoreCase("HARD")){
            initHard();
        }
        if (choice.equalsIgnoreCase("EASY")){
            initEasy();
        }
    }

    // Initializes all of the Criteria and adds them to mCriteriaList
    private void initHard(){
        // Initialize all Criteria
        Criteria criteriaCategory_1 = new CriteriaCategory_1();
        Criteria criteriaCategory_2 = new CriteriaCategory_2();
        Criteria criteriaCategory_3 = new CriteriaCategory_3();
        Criteria criteriaPattern_1 = new CriteriaPattern_1();
        Criteria criteriaPattern_2 = new CriteriaPattern_2();
        Criteria criteriaPattern_3 = new CriteriaPattern_3();

        // Category_1 Requirements
        Criteria andCriteria_1_1 = new AndCriteria(criteriaCategory_1,criteriaPattern_1);
        Criteria andCriteria_1_2 = new AndCriteria(criteriaCategory_1,criteriaPattern_2);
        Criteria andCriteria_1_3 = new AndCriteria(criteriaCategory_1,criteriaPattern_3);

        // Category_2 Requirements
        Criteria andCriteria_2_1 = new AndCriteria(criteriaCategory_2,criteriaPattern_1);
        Criteria andCriteria_2_2 = new AndCriteria(criteriaCategory_2,criteriaPattern_2);
        Criteria andCriteria_2_3 = new AndCriteria(criteriaCategory_2,criteriaPattern_3);

        // Category_3 Requirements
        Criteria andCriteria_3_1 = new AndCriteria(criteriaCategory_3,criteriaPattern_1);
        Criteria andCriteria_3_2 = new AndCriteria(criteriaCategory_3,criteriaPattern_2);
        Criteria andCriteria_3_3 = new AndCriteria(criteriaCategory_3,criteriaPattern_3);

        // Add the Criteria to the List
        mCriteriaList.add(andCriteria_1_1);
        mCriteriaList.add(andCriteria_1_2);
        mCriteriaList.add(andCriteria_1_3);

        mCriteriaList.add(andCriteria_2_1);
        mCriteriaList.add(andCriteria_2_2);
        mCriteriaList.add(andCriteria_2_3);

        mCriteriaList.add(andCriteria_3_1);
        mCriteriaList.add(andCriteria_3_2);
        mCriteriaList.add(andCriteria_3_3);

        // Add the Criteria to Map to make searching easier later
        criteriaClassificationHashMap.put(andCriteria_1_1,new Classification(CategoryEnum.CATEGORY_1,PatternEnum.PATTERN_1));
        criteriaClassificationHashMap.put(andCriteria_1_2,new Classification(CategoryEnum.CATEGORY_1,PatternEnum.PATTERN_2));
        criteriaClassificationHashMap.put(andCriteria_1_3,new Classification(CategoryEnum.CATEGORY_1,PatternEnum.PATTERN_3));

        criteriaClassificationHashMap.put(andCriteria_2_1,new Classification(CategoryEnum.CATEGORY_2,PatternEnum.PATTERN_1));
        criteriaClassificationHashMap.put(andCriteria_2_2,new Classification(CategoryEnum.CATEGORY_2,PatternEnum.PATTERN_2));
        criteriaClassificationHashMap.put(andCriteria_2_3,new Classification(CategoryEnum.CATEGORY_2,PatternEnum.PATTERN_3));

        criteriaClassificationHashMap.put(andCriteria_3_1,new Classification(CategoryEnum.CATEGORY_3,PatternEnum.PATTERN_1));
        criteriaClassificationHashMap.put(andCriteria_3_2,new Classification(CategoryEnum.CATEGORY_3,PatternEnum.PATTERN_2));
        criteriaClassificationHashMap.put(andCriteria_3_3,new Classification(CategoryEnum.CATEGORY_3,PatternEnum.PATTERN_3));
    }

    private void initEasy(){
        // Initialize all Criterias
        Criteria blueCriteria = new CriteriaPattern_1();
        Criteria redCriteria = new CriteriaPattern_3();
        Criteria greenCriteria = new CriteriaPattern_2();
        Criteria triangleCriteria = new CriteriaCategory_3();
        Criteria squareCriteria = new CriteriaCategory_1();
        Criteria circleCriteria = new CriteriaCategory_2();

        // Triangle Requirements
        Criteria blueTriangle = new OrCriteria(blueCriteria,triangleCriteria);
        Criteria redTriangle = new OrCriteria(redCriteria,triangleCriteria);
        Criteria greenTriangle = new OrCriteria(greenCriteria,triangleCriteria);

        // Square Requirements
        Criteria blueSquare = new OrCriteria(blueCriteria,squareCriteria);
        Criteria redSquare = new OrCriteria(redCriteria,squareCriteria);
        Criteria greenSquare = new OrCriteria(greenCriteria,squareCriteria);

        // Circle Requirements
        Criteria blueCircle = new OrCriteria(blueCriteria,circleCriteria);
        Criteria redCircle = new OrCriteria(redCriteria,circleCriteria);
        Criteria greenCircle = new OrCriteria(greenCriteria,circleCriteria);

        mCriteriaList.add(blueTriangle);
        mCriteriaList.add(redTriangle);
        mCriteriaList.add(greenTriangle);

        mCriteriaList.add(blueSquare);
        mCriteriaList.add(redSquare);
        mCriteriaList.add(greenSquare);

        mCriteriaList.add(blueCircle);
        mCriteriaList.add(redCircle);
        mCriteriaList.add(greenCircle);
    }

    public void newCriteria(){
    }

    // Generates a random criteria
    public void generateNewCriteria(){
        int MAX = mCriteriaList.size() - 1;
        int MIN = 0;

        // Get a random Criteria
        Random rand = new Random();
        int next = rand.nextInt(MAX - MIN + 1) + MIN;
        int other = rand.nextInt(MAX - MIN + 1) + MIN;

        if (mNextCriteria == null){
            // Set the current criteria to a random criteria
            mCurrentCriteria = mCriteriaList.get(next);
            // Set the next criteria
            mNextCriteria = mCriteriaList.get(other);
        } else{
            // Set the current to the next
            mCurrentCriteria = mNextCriteria;
            // Get a random next criteria
            mNextCriteria = mCriteriaList.get(next);
        }
    }

    // Returns the current criteria
    public Criteria getCurrentCriteria(){
        return mCurrentCriteria;
    }

    // Returns the next criteria
    public Criteria getNextCriteria(){
        return mNextCriteria;
    }

    public Classification getClassification(Criteria criteria){
        return criteriaClassificationHashMap.get(criteria);
    }

}

