package com.game.bentz.ShapeShift.Utility;

/**
 * Created by Michael on 6/29/16.
 */
public class Key {
    private final Integer x;
    private final Integer y;

    public Key(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x.equals(key.x) && y.equals(key.y);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

}
