package com.game.bentz.ShapeShift.ImagePacks.ColorPack;

import com.game.bentz.ShapeShift.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 6/29/16.
 */
public class PYOColorPack implements PatternPack {
    private static PYOColorPack instance;
    private Integer[] imageSet;

    private PYOColorPack(){
        this.imageSet = initImages();
    }

    public static PYOColorPack getInstance(){
        if (instance == null){
            instance = new PYOColorPack();
        }
        return instance;
    }

    private Integer[] initImages(){
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.purple);
        images.add(R.drawable.yellow);
        images.add(R.drawable.orange);
        return images.toArray(new Integer[3]);
    }

    @Override
    public Integer[] getResources(){
        return imageSet;
    }

}
