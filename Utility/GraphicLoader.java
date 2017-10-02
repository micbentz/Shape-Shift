package com.game.bentz.ShapeShift.Utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

import com.game.bentz.ShapeShift.R;
import com.game.bentz.ShapeShift.ImagePacks.CategoryPack.CategoryPack;
import com.game.bentz.ShapeShift.ImagePacks.ColorPack.PatternPack;

import java.util.HashMap;

/**
 * Created by Michael on 5/4/16.
 * The <code>GraphicLoader</code> loads in all of the resource images and sets it to the current <code>ImagePack</code>
 */
//TODO Add method to change color pack
public class GraphicLoader {
    private final String TAG = "GraphicLoader";

    public static HashMap<Key,Bitmap> imageHashMap;
    public static Bitmap slowDownPowerUp;
    public static Bitmap doublePointsPowerUp;
    public static Bitmap clearScreenPowerUp;
    public static Bitmap keepCriteriaPowerUp;
    public static HashMap<Key,Bitmap[]> imagesHashMap;

    /**
     * Initializes and sets images to the current <code>ImagePack</code>
     * @param context
     */
    public static void init(Context context){
        Resources resources = context.getResources();
        imageHashMap = new HashMap<>();
        imagesHashMap = new HashMap<>();
        loadImageSet(resources);
        slowDownPowerUp = BitmapFactory.decodeResource(resources, R.drawable.slow);
//        slowDownPowerUp = Bitmap.createScaledBitmap(slowDownPowerUp,Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), false);
        slowDownPowerUp = Bitmap.createScaledBitmap(slowDownPowerUp,Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
        clearScreenPowerUp = BitmapFactory.decodeResource(resources, R.drawable.explosion);
//        clearScreenPowerUp = Bitmap.createScaledBitmap(clearScreenPowerUp,Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), false);
        clearScreenPowerUp = Bitmap.createScaledBitmap(clearScreenPowerUp,Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
        keepCriteriaPowerUp = BitmapFactory.decodeResource(resources, R.drawable.lock);
//        keepCriteriaPowerUp = Bitmap.createScaledBitmap(keepCriteriaPowerUp,Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), false);
        keepCriteriaPowerUp = Bitmap.createScaledBitmap(keepCriteriaPowerUp,Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
        doublePointsPowerUp = BitmapFactory.decodeResource(resources, R.drawable.double_points);
//        doublePointsPowerUp = Bitmap.createScaledBitmap(doublePointsPowerUp,Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), false);
        doublePointsPowerUp = Bitmap.createScaledBitmap(doublePointsPowerUp,Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
    }

    private static void loadImageSet(Resources resources){
        Integer[] categoryId = Constants.categoryPack.getResources();
        Integer[] patternId = Constants.patternPack.getResources();
        for(Integer i = 0; i < categoryId.length; i++){
            // Get the category resource
            Bitmap category = BitmapFactory.decodeResource(resources, categoryId[i]);
            // Create the scaled bitmap
            category = Bitmap.createScaledBitmap(category, Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
            for(Integer j = 0; j < patternId.length; j++) {
                // Get the pattern resource
                Bitmap pattern = BitmapFactory.decodeResource(resources, patternId[j]);
                // Create a scaled bitmap
                pattern = Bitmap.createScaledBitmap(pattern, Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
                //Overlay the bitmaps
                Bitmap overlay = overlay(category,pattern);
                // Store the bitmap in HashMap
                imageHashMap.put(new Key(i,j), overlay);
                Bitmap[] scaledImages = new Bitmap[Constants.SCALING_SIZE];
                for(int k = 0; k < Constants.SCALING_SIZE; k++){
//                    Bitmap[] scaledImages = new Bitmap[25];
                    scaledImages[k] = scaleBitmap(k,overlay);
                    if (k == Constants.SCALING_SIZE - 1){
                        imagesHashMap.put(new Key(i,j),scaledImages);
                    }
                }
            }
        }
    }

    /**
     * Sets the <code>CategoryPack</code>
     * @param resources the <code>Context</code> provided for the Application
     * @param categoryPack the <code>CategoryPack</code> to change the current categories to
     */
    public static void setImageSet(Resources resources, CategoryPack categoryPack){
        Integer[] categoryId = categoryPack.getResources();
        Integer[] patternId = Constants.patternPack.getResources();
        for(Integer i = 0; i < categoryId.length; i++){
            // Get the category resource
            Bitmap category = BitmapFactory.decodeResource(resources, categoryId[i]);
            // Create the scaled bitmap
//            category = Bitmap.createScaledBitmap(category, Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), false);
            category = Bitmap.createScaledBitmap(category, Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
            for(Integer j = 0; j < patternId.length; j++) {
                // Get the pattern resource
                Bitmap pattern = BitmapFactory.decodeResource(resources, patternId[j]);
                // Create a scaled bitmap
                pattern = Bitmap.createScaledBitmap(pattern, Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
//                pattern = Bitmap.createScaledBitmap(pattern, Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), Constants.getPhysicalPx(Constants.SCREEN_WIDTH / 8), false);
                //Overlay the bitmaps
                Bitmap overlay = overlay(category,pattern);
                // Store the bitmap in hashmap
                imageHashMap.put(new Key(i,j), overlay);
                Bitmap[] scaledImages = new Bitmap[Constants.SCALING_SIZE];
                for(int k = 0; k < Constants.SCALING_SIZE; k++){
//                    Bitmap[] scaledImages = new Bitmap[25];
                    scaledImages[k] = scaleBitmap(k,overlay);
                    if (k == Constants.SCALING_SIZE - 1){
                        imagesHashMap.put(new Key(i,j),scaledImages);
                    }
                }
            }
        }
    }

    /**
     * Sets the <code>PatternPack</code>
     * @param resources the <code>Context</code> provided for the Application
     * @param patternPack the <code>PatternPack</code> to change the current patterns to
     */
    public static void setImageSet(Resources resources, PatternPack patternPack){
        Integer[] categoryId = Constants.categoryPack.getResources();
        Integer[] patternId = patternPack.getResources();
        for(Integer i = 0; i < categoryId.length; i++){
            // Get the category resource
            Bitmap category = BitmapFactory.decodeResource(resources, categoryId[i]);
            // Create the scaled bitmap
//            category = Bitmap.createScaledBitmap(category, Constants.getPhysicalPx(30), Constants.getPhysicalPx(30), false);
            category = Bitmap.createScaledBitmap(category, Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
            for(Integer j = 0; j < patternId.length; j++) {
                // Get the pattern resource
                Bitmap pattern = BitmapFactory.decodeResource(resources, patternId[j]);
                // Create a scaled bitmap
                pattern = Bitmap.createScaledBitmap(pattern, Constants.BITMAP_WIDTH, Constants.BITMAP_WIDTH, false);
//                pattern = Bitmap.createScaledBitmap(pattern, Constants.getPhysicalPx(30), Constants.getPhysicalPx(30), false);
                //Overlay the bitmaps
                Bitmap overlay = overlay(category,pattern);
                // Store the bitmap in hashmap
                imageHashMap.put(new Key(i,j), overlay);
                Bitmap[] scaledImages = new Bitmap[Constants.SCALING_SIZE];
                for(int k = 0; k < Constants.SCALING_SIZE; k++){
//                    Bitmap[] scaledImages = new Bitmap[25];
                    scaledImages[k] = scaleBitmap(k,overlay);
                    if (k == Constants.SCALING_SIZE - 1){
                        imagesHashMap.put(new Key(i,j),scaledImages);
                    }
                }
            }
        }
    }


    // Overlay's the pattern bitmap on top of the category bitmap
    private static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        // Get the width
        int width = bmp1.getWidth();
        // Iterate through and form the pattern to the category bitmap
        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                if (bmp1.getPixel(i,j) == Color.TRANSPARENT){
                    bmp2.setPixel(i,j,Color.TRANSPARENT);
                }
            }
        }
        // Draw the new bitmap on top
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    private static Bitmap scaleBitmap(int i, Bitmap bitmap){
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float scaleWidth = width * ((100f - i) / 100);
        float scaleHeight = height * ((100f - i) / 100);
        return Bitmap.createScaledBitmap(bitmap, (int)scaleWidth, (int)scaleHeight, false);
    }
}
