package com.game.bentz.ShapeShift.ImagePacks.CategoryPack;
import com.game.bentz.ShapeShift.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 6/28/16.
 * A <code>ShapeImagePack</code> is the shape collection of images
 */
public class ShapeImagePack implements CategoryPack {
    private static ShapeImagePack instance;
    private Integer[] imageSet;

    /**
     * Constructor
     */
    private ShapeImagePack(){
        this.imageSet = initImages();
    }

    public static ShapeImagePack getInstance(){
        if (instance == null){
            instance = new ShapeImagePack();
        }
        return instance;
    }

    private Integer[] initImages(){
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.circle);
        images.add(R.drawable.square);
        images.add(R.drawable.triangle);
        return images.toArray(new Integer[3]);
    }

    @Override
    public Integer[] getResources(){
        return imageSet;
    }
}
