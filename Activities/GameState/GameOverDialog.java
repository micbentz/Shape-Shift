package com.game.bentz.ShapeShift.Activities.GameState;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.game.bentz.ShapeShift.R;

/**
 * Created by Michael on 6/13/16.
 * A <code>GameOverDialog</code> represents the Game Over Screen
 * Allows the user to "Play Again" or "Quit"
 */
public class GameOverDialog extends DialogFragment {
    private final String TAG = "DialogFragment";

    private GameState mGameState;
    private int currentScore;
    private int highScore;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_gameover,container,false);


        mGameState = GameState.getInstance();

        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getDialog().getWindow().getDecorView().setSystemUiVisibility(getActivity().getWindow().getDecorView().getSystemUiVisibility());

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //Clear the not focusable flag from the window
                getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

                //Update the WindowManager with the new attributes (no nicer way I know of to do this)..
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                wm.updateViewLayout(getDialog().getWindow().getDecorView(), getDialog().getWindow().getAttributes());
            }
        });

        // Get the current and highscore arguments
        currentScore = getArguments().getInt("current_score",0);
        highScore = getArguments().getInt("high_score",0);

        // Setup the Buttons and textview
        initButtons(view);
        initTextView(view);

        return view;
    }

    /**
     * Initializes the <code>Button</code> elements
     * @param view the <code>View</code> associated with the Dialog elements
     */
    private void initButtons(View view){
        Button retryButton  = (Button)view.findViewById(R.id.button_retry);
        retryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG,"Play Again");
                // Dismiss the dialog
                dismiss();
                // Reset all variables
                mGameState.resetVariables();
            }
        });

        Button quitButton = (Button)view.findViewById(R.id.button_quit);
        quitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG,"Quit");
                // Dismiss the dialog
                dismiss();
                // Go back to the main activity
                getActivity().finish();
            }
        });
    }

    /**
     * Initializes the and <code>TextView</code>
     * @param view the <code>View</code> associated with the Dialog elements
     */
    private void initTextView(View view){
        TextView textView = (TextView)view.findViewById(R.id.textview_score);
        // Set the textview to the highscore
        textView.setText("Your highscore: " + highScore);
    }
}
