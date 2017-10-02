package com.game.bentz.ShapeShift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.game.bentz.ShapeShift.Activities.GameState.GameActivity;
import com.game.bentz.ShapeShift.R;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.FontsOverride;
import com.game.bentz.ShapeShift.Utility.GraphicLoader;
import com.game.bentz.ShapeShift.ImagePacks.CategoryPack.TestImagePack;
import com.game.bentz.ShapeShift.ImagePacks.ColorPack.PYOColorPack;
import com.game.bentz.ShapeShift.Utility.MediaManager;
import com.game.bentz.ShapeShift.Utility.Pool;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * The <code>MainActivity</code> is the Main View and allows the user to:
 * Play the game, access settings or the store
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private MediaManager mediaManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/ka1.ttf");
        setContentView(R.layout.activity_main);


        /**
         * Testing ad
         */
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        /**
         * Uncomment for real ad
         */
//        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5203449806249832/9027089709");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // TODO: move these to loading screen
        Constants.init(getApplicationContext());
        Pool.getInstance();
    }

    public void handleAction(View v){
        switch(v.getId()){
            case R.id.button_GameActivity:
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
                break;
            case R.id.button_change_category_set:
                Constants.categoryPack = TestImagePack.getInstance();
                GraphicLoader.setImageSet(getResources(),TestImagePack.getInstance());
                break;
            case R.id.button_change_pattern_set:
                Constants.patternPack = PYOColorPack.getInstance();
                GraphicLoader.setImageSet(getResources(),PYOColorPack.getInstance());
                break;
            default:
                // nothing happens
                break;
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();;
    }
}
