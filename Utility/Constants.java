package com.game.bentz.ShapeShift.Utility;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.game.bentz.ShapeShift.ImagePacks.CategoryPack.CategoryPack;
import com.game.bentz.ShapeShift.ImagePacks.ColorPack.PatternPack;
import com.game.bentz.ShapeShift.ImagePacks.ColorPack.RGBColorPack;
import com.game.bentz.ShapeShift.ImagePacks.CategoryPack.ShapeImagePack;

/**
 * Created by Michael on 5/17/16.
 * This class is responsible for holding the game constants such as screen size
 * TODO Add constants for screen dimensions, e.g., TOP, RIGHT, BOTTOM, LEFT, etc.
 */
public class Constants {

    public static final int BASE_DPI = 160;
    public static int CURRENT_DPI;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int ROW_SIZE = 4;
    public static int SPEED;
    public static float DENSITY;
    public static int SCREEN_END;
    public static int HEIGHT_TOP_BAR;
    public static int HEIGHT_TIMER_BAR;
    public static int X_CURRENT_CRITERIA;
    public static int X_NEXT_CRITERIA;
    public static int HEIGHT_CURRENT_CRITERIA;
    public static int HEIGHT_GAME_STATUS;
    public static int HEIGHT_NOTIFICATIONS;
    public static int DP_SCREEN_WIDTH;
    public static int DP_SCREEN_HEIGHT;


    public static int DEFAULT_FONT_SIZE;
    public static Typeface DEFAULT_TYPEFACE;

    public static boolean FX_ACTIVE = true;
    public static boolean MUSIC_ACTIVE = true;
    public static boolean GAMEMODE_REVERSE = false;
    public static boolean KEEP_CRITERIA = false;

    public static CategoryPack categoryPack;
    public static PatternPack patternPack;

    public static final float ANIMATION_TIMER = 0.01f;
    public final static int SCALING_SIZE = 30;
    public static int ALPHA_DECREMENT;
    public static int BITMAP_WIDTH;
    public static Rect BITMAP_BOUNDS;
    public static int SPAWN_AREA;
    public static int SPAWN_INDICATOR;
    public static int COL_1;
    public static int COL_2;
    public static int COL_3;
    public static int COL_4;
    public static int COL_5;

    public static int POINT_MODIFIER = 1;
    public static double SPEED_MODIFIER = 1;
    public static double CURRENT_SPEED_MODIFIER = 1;
    public static float CRITERIA_MODIFIER = 5.0f;

    public static final Integer COOLDOWN_TIME = 10;
    public static final Integer TIME_INCREASE = 5;
    public static final Integer TIME_DECREASE = 3;
    public static final int PLAY_TIME = 30000;
//    public static final int TIME_TO_BOTTOM = 300;
    public static final int TIME_TO_BOTTOM = 100;


    public static void init(Context context){
        categoryPack = ShapeImagePack.getInstance();
        patternPack = RGBColorPack.getInstance();
        Constants.DEFAULT_TYPEFACE = Typeface.createFromAsset(context.getAssets(),"fonts/ka1.ttf");
        initScreenDimensions(context);
        initGraphics(context);
    }

    private static void initScreenDimensions(Context context){
        // Get the device's screen size
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Constants.SCREEN_WIDTH = width;
        Constants.SCREEN_HEIGHT = height;
        Constants.DENSITY = metrics.density;
        Constants.CURRENT_DPI = metrics.densityDpi;
    }

    private static void initGraphics(Context context){
        // default font size
        Constants.DEFAULT_FONT_SIZE = getPhysicalPx(30);
        // 8% of DP Height (640)
        Constants.HEIGHT_TIMER_BAR = getPhysicalPx(51);
        // 11% of DP Height (640)
        Constants.HEIGHT_TOP_BAR = getPhysicalPx(70);
        // 6% of DP Height (640)
        Constants.HEIGHT_GAME_STATUS = getPhysicalPx(38);
        // 13% of DP Height (640)
        Constants.HEIGHT_NOTIFICATIONS = getPhysicalPx(96);
        // 17% of DP Width (360)
        Constants.BITMAP_WIDTH = getPhysicalPx(61);
        // Unused
        Constants.X_CURRENT_CRITERIA = getPhysicalPx(119);
        // 5% of DP WIDTH (360)
        Constants.X_NEXT_CRITERIA = getPhysicalPx(18);
        // 1% of DP Height (640)
        Constants.HEIGHT_CURRENT_CRITERIA = getPhysicalPx(6);
        Constants.DP_SCREEN_HEIGHT = (640);
        Constants.DP_SCREEN_WIDTH = (180);

        // Initialize all graphics
        GraphicLoader.init(context);

        // Set the end of the screen
        Constants.SCREEN_END = Constants.SCREEN_HEIGHT * 9/10;

        // Set the alpha decrement
        Constants.ALPHA_DECREMENT = 1 / 5;

        // Set the bitmap width
//        Constants.BITMAP_WIDTH = Constants.getPhysicalPx(Constants.SCREEN_WIDTH/8);
//        Constants.BITMAP_WIDTH = getDP(Constants.SCREEN_WIDTH/4);

        // Set the bitmap bounds
//        Constants.BITMAP_BOUNDS = new Rect(0,0,GraphicLoader.imageHashMap.get(new Key(0,0)).getWidth(),GraphicLoader.imageHashMap.get(new Key(0,0)).getHeight() + GraphicLoader.imageHashMap.get(new Key(0,0)).getHeight() * 1/10 );
        Constants.BITMAP_BOUNDS = new Rect(0,0,GraphicLoader.imageHashMap.get(new Key(0,0)).getWidth(),GraphicLoader.imageHashMap.get(new Key(0,0)).getHeight() + GraphicLoader.imageHashMap.get(new Key(0,0)).getHeight() * 1/10);

        // Set the spawn area
//        Constants.SPAWN_AREA = 0 - Constants.BITMAP_WIDTH;

        Constants.SPAWN_AREA = 0;

        Constants.SPAWN_INDICATOR += 3f/32f * Constants.SCREEN_HEIGHT;

        // Set the speed
//        Constants.SPEED = getDP(Constants.SCREEN_HEIGHT/TIME_TO_BOTTOM);
        Constants.SPEED = getPhysicalPx(2) * 2/3;

        // Set the column positions
//        Constants.COL_1 = Constants.SCREEN_WIDTH * 1/8;
//        Constants.COL_2 = Constants.SCREEN_WIDTH * 3/8;
//        Constants.COL_3 = Constants.SCREEN_WIDTH * 5/8;
//        Constants.COL_4 = Constants.SCREEN_WIDTH * 7/8;
//        Constants.COL_5 = Constants.SCREEN_WIDTH * 9/10;
        Constants.COL_1 = getPhysicalPx(45);
        Constants.COL_2 = getPhysicalPx(135);
        Constants.COL_3 = getPhysicalPx(225);
        Constants.COL_4 = getPhysicalPx(315);
        Constants.COL_5 = getPhysicalPx(45);


    }

    public static void updateScreenDimensions(Context context){

    }

    // Returns the real pixel value of a given DP
    public static int getPhysicalPx(int x){
        return (x * CURRENT_DPI)/ BASE_DPI;
    }

    public static int getDP(int x){
        return (x * BASE_DPI)/ CURRENT_DPI;
    }
}
