package com.example.fourwinsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[] buttons = new Button[42];

    public enum Player {Empty, One, Two};

    public Player activePlayer;

    public Player [][] board = new Player[7][6];

    private TextView playerOneScore, playerTwoScore;

    private int playerOneScoreCount, playerTwoScoreCount;




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

        for (int c = 0; c < 7; c++) {
            for (int r = 0; r < 6; r++) {
                board[c][r] = Player.Empty;
            }
        }

        int resourceID = getResources().getIdentifier("playerOneScore", "id", getPackageName());
        playerOneScore = (TextView) findViewById(resourceID);

        resourceID = getResources().getIdentifier("playerTwoScore", "id", getPackageName());
        playerTwoScore = (TextView) findViewById(resourceID);

    }


    @Override
    public void onClick(View view) {

        Button button = (Button) view;

        //click on empty space => do nothing
        if (button == null) {
            return;
        }

        int row;
        int column;
        String buttonId = getResources().getResourceEntryName(button.getId());

        buttonId = buttonId.substring(4);//removes "btn_" from the button id

        int buttonIndex = Integer.parseInt(buttonId);//type casting of the id - from string to integer

        column = buttonIndex / 6;
        row = buttonIndex % 6;//modulo operation gives us the rest of the division => the same as : row = buttonIndex - column*6;

        //if a spot is occupied show this alert
        if(board[column][row] != Player.Empty){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("spot occupied");
            dlgAlert.setTitle("invalid move !!!");

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });

            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            return;
        }

        //drop the token to the last empty spot in the column
        while (row < 5 && board[column][row + 1] == Player.Empty){
            row++;
        }

        //caculates the button index
        buttonIndex = column * 6 + row;
        button = buttons[buttonIndex];


        //assignes the text and the spot for the player number 1
        if (activePlayer == Player.One) {
            button.setText("X");
            button.setTextColor(Color.parseColor("#FFC34A"));
            board[column][row] = Player.One;

        }

        //assignes the text and the spot for the player number 2
        else if (activePlayer == Player.Two) {
            button.setText("O");
            button.setTextColor(Color.parseColor("#70FFEA"));
            board[column][row] = Player.Two;
        }


        //if one of the winning conditions is realised do this :
        if(checkWinner()){
            if (activePlayer == Player.One) {
                playerOneScoreCount++;
                updatePlayerScore();

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("player one has won");
                dlgAlert.setTitle("Game over !!!");

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        });

                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            else {
                    playerTwoScoreCount++;
                    updatePlayerScore();

                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setMessage(" player two has won");
                    dlgAlert.setTitle("Game over !!!");

                    dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        });

                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
            }


        };

        //switching players
        if (activePlayer == Player.One) {
            activePlayer = Player.Two;
        } else if (activePlayer == Player.Two) {
            activePlayer = Player.One;
        }





    }

    public boolean checkWinner() {

        //check for 4 across
        for(int col = 0; col< board.length - 3; col++){
            for (int row = 0;row < board[0].length;row++){
                if (board[col][row] == activePlayer   &&
                        board[col+1][row] == activePlayer &&
                        board[col+2][row] == activePlayer &&
                        board[col+3][row] == activePlayer){
                    return true;
                }
            }
        }

        //check for 4 up and down
        for(int col = 0; col < board.length; col++){
            for(int row = 0; row < board[0].length - 3; row++){
                if (board[col][row] == activePlayer   &&
                        board[col][row+1] == activePlayer &&
                        board[col][row+2] == activePlayer &&
                        board[col][row+3] == activePlayer){
                    return true;
                }
            }
        }

        //check upward diagonal
        for(int col = 0; col < board.length - 3; col++){
            for(int row = 3; row < board[0].length; row++){
                if (board[col][row] == activePlayer   &&
                        board[col+1][row-1] == activePlayer &&
                        board[col+2][row-2] == activePlayer &&
                        board[col+3][row-3] == activePlayer){
                    return true;
                }
            }
        }

        //check downward diagonal
        for(int col = 0; col < board.length - 3; col++){
            for(int row = 0; row < board[0].length - 3; row++){
                if (board[col][row] == activePlayer   &&
                        board[col+1][row+1] == activePlayer &&
                        board[col+2][row+2] == activePlayer &&
                        board[col+3][row+3] == activePlayer){
                    return true;
                }
            }
        }
        return false;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }



}


