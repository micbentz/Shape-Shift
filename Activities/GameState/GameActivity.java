package com.game.bentz.ShapeShift.Activities.GameState;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.game.bentz.ShapeShift.R;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.FontsOverride;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by Michael on 5/10/16.
 * The <code>GameActivity</code> creates the <code>GameView</code>
 * and handles touch events
 */
  public class GameActivity extends Activity {
    private static final String TAG = "GameActivity";

    private final String FRAGMENT_TAG = "Game_Over";
    private View mView;
    private GameState mGameState;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Intent sent by GameState when ending game
        LocalBroadcastManager.getInstance(this).registerReceiver(new MessageHandler(),
                new IntentFilter("end_game"));
//        // Intent sent by gameoverfragment when button is pressed
//        LocalBroadcastManager.getInstance(this).registerReceiver(new MessageHandler(),
//                new IntentFilter("remove_fragment"));
        super.onCreate(savedInstanceState);

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/ka1.ttf");

        // Initialize the Game State
        mGameState = GameState.getInstance(this);

        // Set the view
        setContentView(R.layout.activity_game);
        mView = new GameView(this);

        // Get and add the layout to the view
        RelativeLayout mLayout = (RelativeLayout)findViewById(R.id.fragment_container);
//        mLayout = (FrameLayout)findViewById(R.id.fragment_container);
//        mLayout.addView(mView);

        // Create and load the AdView.
        adView = new AdView(this);
        // TODO Replace test with: ca-app-pub-5203449806249832/1503822900 is actual add
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.setAdSize(new AdSize(Constants.getDP(Constants.SCREEN_WIDTH),Constants.getDP(Constants.SCREEN_HEIGHT - Constants.SCREEN_END)));

        // Create a RelativeLayout as the main layout and add the gameView.
//        RelativeLayout mLayout = new RelativeLayout(this);

        // Add adView to the bottom of the screen.
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mLayout.addView(mView);
        mLayout.addView(adView, adParams);

        setContentView(mLayout);
        loadAd();
        hideBanner();
    }

    /**
     * Handles single and multitouch events
     * @param event the <code>MotionEvent</code>
     * @return "true" | "false" if <code>MotionEvent</code> occurred
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);

        int action = MotionEventCompat.getActionMasked(event);
        // Get the index of the pointer associated with the action.
        int index = MotionEventCompat.getActionIndex(event);
        switch(action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                handleActionDown(event,index);
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(event);
                break;
            default:
                break;
        }
        return false;
    }


    /**
     * Handles <code>MotionEvent.ACTION_DOWN</code>
     * @param event the <code>MotionEvent</code>
     * @param index the index of the event
     */
    private void handleActionDown(MotionEvent event,int index){
        int xPos;
        int yPos;
        if (event.getPointerCount() > 1) {
//            Log.d(TAG,"Multitouch event");
            // The coordinates of the current screen contact, relative to
            // the responding View or Activity.
            xPos = (int)MotionEventCompat.getX(event, index);
            yPos = (int)MotionEventCompat.getY(event, index);
//            Log.d(TAG,"X: " + xPos + "Y: " + yPos);

        } else {
            // Single touch event
//            Log.d(TAG,"Single touch event");
            xPos = (int)MotionEventCompat.getX(event, index);
            yPos = (int)MotionEventCompat.getY(event, index);
//            Log.d(TAG,"X: " + xPos + "Y: " + yPos);
        }
        mGameState.checkTouch(xPos,yPos);
    }

    private void handleActionUp(MotionEvent event){
//        Log.d(TAG," Action Up event");
        int x = (int)event.getX();
        int y = (int)event.getY();
        //GameState.checkTouch(x,y);
    }

    private  void handleActionMove(MotionEvent event){
//        Log.d(TAG," Action Move event");
        int x = (int)event.getX();
        int y = (int)event.getY();
        //GameState.checkTouch(x,y);
    }


    private void loadAd(){
        adView.loadAd(new AdRequest.Builder()
                .addTestDevice("877ECE4E7D1E49AAC6890B56AA0E6CA0")
                .build());
    }

    private void showBanner() {
        adView.setVisibility(View.VISIBLE);
    }

    private void hideBanner() {
        adView.setVisibility(View.GONE);
    }
    /**
     * Displays the Game Over Fragment
     * @param args the arguments passed by the intent
     */
    private void showGameOverFragment(Bundle args){
//         Get the Fragment Manager
        FragmentManager fm = getFragmentManager();

        // Check if an instance of the fragment exists
//        GameOverDialog gameOverDialog = (GameOverDialog)fm.findFragmentByTag(FRAGMENT_TAG);
        GameOverFragment gameOverFragment = (GameOverFragment)fm.findFragmentByTag(FRAGMENT_TAG);

        // TODO see why gameoverdialog.show() cant be used outside of this if statement
        if (gameOverFragment == null){
            gameOverFragment = new GameOverFragment();
            // Fix for android bug with onDetach for child fragments
            try {
                gameOverFragment.setArguments(args);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, gameOverFragment,FRAGMENT_TAG).commit();
                // Make the dialog uncancelled
//                gameOverFragment.setCancelable(false);
                // Pass the arguments to the dialog
                // Show the dialog
//                gameOverFragment.show(fm, FRAGMENT_TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        showBanner();
    }

    public void handleTouch(View v){
        FragmentTransaction ft;
        switch(v.getId()){
            case R.id.button_retry:
                ft = getFragmentManager().beginTransaction();
                ft.remove(getFragmentManager()
                        .findFragmentByTag(FRAGMENT_TAG))
                        .commit();
                hideBanner();
                mGameState.resetVariables();
                break;
            case R.id.button_quit:
                ft = getFragmentManager().beginTransaction();
                ft.remove(getFragmentManager()
                        .findFragmentByTag(FRAGMENT_TAG))
                        .commit();
                hideBanner();
                finish();
//                mGameState.resetVariables();
                break;
            default:
                // do nothing
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


    /**
     * The <code>MessageHandler</code> extends <code>BroadcastReceiver</code>
     * and handles local broadcasts
     */
    public class MessageHandler extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "end_game":
                    showGameOverFragment(intent.getExtras());
                    break;
                case "remove_fragment":
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.remove(getFragmentManager()
                            .findFragmentByTag(FRAGMENT_TAG))
                            .commit();
                    break;
                default:
                    // Do nothing
                    break;
            }
        }
    }
}
