package com.game.bentz.ShapeShift.ImagePacks.CategoryPack;

import com.game.bentz.ShapeShift.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 6/28/16.
 */
public class TestImagePack implements CategoryPack {
    private static TestImagePack instance;
    private Integer[] imageSet;

    private TestImagePack(){
        this.imageSet = initImages();
    }

    public static TestImagePack getInstance(){
        if (instance == null){
            instance = new TestImagePack();
        }
        return instance;
    }

    private Integer[] initImages(){
        Integer[] temp = new Integer[3];
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.circle_criteria);
        images.add(R.drawable.square_criteria);
        images.add(R.drawable.triangle_criteria);
        return images.toArray(temp);
    }

    @Override
    public Integer[] getResources(){
        return imageSet;
    }
}
