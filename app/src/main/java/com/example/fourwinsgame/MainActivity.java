package com.example.fourwinsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button [] buttons = new Button[42];

    public enum Player {Empty ,One , Two};
    private Player activePlayer;

    private Player [][] board = new Player [7][6];
    boolean winner = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        activePlayer = Player.One;

        for (int c=0; c<7; c++){
            for (int r=0; r<6; r++){
                board[c][r] = Player.Empty;
            }
        }

}


    @Override
    public void onClick(View view) {



        // check if button cell is already used then exit
        if (!((Button) view).getText().toString().equals("")){
            return;
        }

        Button button = (Button) view;

        if (button==null){
            return;
        }

        int row;
        int column;
        String buttonId = getResources().getResourceEntryName(button.getId());

        buttonId = buttonId.substring(4);
        int buttonIndex = Integer.parseInt(buttonId);

        column = buttonIndex/6;
        row = buttonIndex % 6;//modulo operation gives us the rest of the division
        //row = buttonIndex - column*6;


        if (activePlayer == Player.One){
            button.setText("X");
            button.setTextColor(Color.parseColor("#FFC34A"));
            board[column][row] = Player.One;
        }

        else if(activePlayer == Player.Two){
            button.setText("O");
            button.setTextColor(Color.parseColor("#70FFEA"));
            board[column][row] = Player.Two;

        }




        //switching players
        if (activePlayer == Player.One){
            activePlayer = Player.Two;
        }

        else if(activePlayer == Player.Two){
            activePlayer = Player.One;
        }

    }

}


