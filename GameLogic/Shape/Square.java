//package com.game.bentz.game.GameLogic.Shape;
//
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Rect;
//
//import com.game.bentz.game.Utility.GraphicLoader;
//
///**
// * Created by Michael on 5/4/16.
// */
//public class Square extends Shape {
//    private static final String TAG = "Square";
//
//    public Square(){
//        super();
//        this.type = "SQUARE";
////        this.image = GraphicLoader.category_2;
//        this.image = GraphicLoader.imageMap.get(1);
//        this.bounds = new Rect(0,0,image.getWidth(),image.getHeight() + image.getHeight() * 1/10);
//    }
//
//    @Override
//    public void draw(Canvas canvas, Paint paint){
//        canvas.drawBitmap(GraphicLoader.imageMap.get(1),getLocation().getX(),getLocation().getY(),paint);
//    }
//}
