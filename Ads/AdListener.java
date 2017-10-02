package com.game.bentz.ShapeShift.Ads;

/**
 * Created by Michael on 7/19/16.
 */
public abstract class AdListener {
    public void onAdLoaded(){};
    public void onAdFailedToLoad(int errorCode){};
    public void onAdOpened(){};
    public void onAdClosed(){};
    public void onAdLeftApplication(){};
}
