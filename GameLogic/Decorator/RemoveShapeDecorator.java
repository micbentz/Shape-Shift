//package com.game.bentz.game.GameLogic.Decorator;
//
//import android.graphics.Bitmap;
//import android.graphics.Color;
//
//import com.game.bentz.game.GameLogic.Shape.Shape;
//
///**
// * Created by Michael on 6/8/16.
// *
// * A <code>RemoveShapeDecorator</code> will change the
// * given <code>Shape</code> to transparent and return it.
// */
//public class RemoveShapeDecorator extends ShapeDecorator {
//    public RemoveShapeDecorator(Shape decoratedShape) {
//        super(decoratedShape);
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
//                    mutableBitmap.setPixel(i,j,Color.RED);
//                }
//            }
//        }
//        // Change the shapes bitmap to the newly colored one
//        decoratedShape.setImage(mutableBitmap);
//    }
//}
