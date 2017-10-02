package com.game.bentz.ShapeShift.GameLogic;

import android.graphics.Rect;

/**
 * Created by Michael on 5/6/16.
 */
public interface Interactable {
    public boolean checkBounds(int x, int y);
    public void updateBounds(int x, int y);
    public Rect getBounds();
}
