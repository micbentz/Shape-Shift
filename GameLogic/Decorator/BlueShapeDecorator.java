//package com.game.bentz.game.GameLogic.Decorator;
//
//import android.graphics.Bitmap;
//import android.graphics.Color;
//
//import com.game.bentz.game.GameLogic.Shape.Shape;
//
///**
// * Created by Michael on 5/6/16.
// * A <code>BlueShapeDecorator</code> will change the color of the
// * given <code>Shape</code> to "Blue" and return it.
// */
//public class BlueShapeDecorator extends ShapeDecorator{
//    private static final String TAG = "BlueShapeDecorator";
//
//    public BlueShapeDecorator(Shape decoratedShape) {
//        super(decoratedShape);
//        decoratedShape.setColor("BLUE");
//        setColor(decoratedShape.getImage());
//    }
//
//    private void setColor(Bitmap bitmap){
//        // Get a mutable bitmap
//        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//        int width = mutableBitmap.getWidth() - 1;
//
//        // Iterate through and change the color
//        for(int i = 0; i < width; i++){
//            for(int j = 0; j < width; j++){
//                if (mutableBitmap.getPixel(i,j) != Color.TRANSPARENT){
//                    mutableBitmap.setPixel(i,j,Color.BLUE);
//                }
//            }
//        }
//        // Change the shapes bitmap to the newly colored one
//        decoratedShape.setImage(mutableBitmap);
//    }
//}
