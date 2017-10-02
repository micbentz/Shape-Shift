package com.game.bentz.ShapeShift.Activities.GameState;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.game.bentz.ShapeShift.R;

import java.lang.reflect.Field;

/**
 * Created by Michael on 6/8/16.
 */
public class GameOverFragment extends Fragment {
    private static final String TAG = "GameOverFragment";

    private GameState mGameState;
    private Integer currentScore;
    private int highScore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_gameover,container,false);

//        mGameState = GameState.getInstance(getContext());


        // Get the current and highscore arguments
        currentScore = getArguments().getInt("current_score",0);
        highScore = getArguments().getInt("high_score",0);
        mGameState = GameState.getInstance();
//        initButtons(view);

        TextView textView = (TextView)view.findViewById(R.id.textview_score);
        textView.setText(currentScore.toString());
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
