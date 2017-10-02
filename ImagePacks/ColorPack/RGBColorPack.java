package com.game.bentz.ShapeShift.ImagePacks.ColorPack;

import com.game.bentz.ShapeShift.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 6/29/16.
 */
public class RGBColorPack implements PatternPack {
    private static RGBColorPack instance;
    private Integer[] imageSet;

    private RGBColorPack(){
        this.imageSet = initImages();
    }

    public static RGBColorPack getInstance(){
        if(instance == null){
            instance = new RGBColorPack();
        }
        return instance;
    }

    private Integer[] initImages(){
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.red);
        images.add(R.drawable.green);
        images.add(R.drawable.blue);
        return images.toArray(new Integer[3]);
    }


    @Override
    public Integer[] getResources(){
        return imageSet;
    }
}
