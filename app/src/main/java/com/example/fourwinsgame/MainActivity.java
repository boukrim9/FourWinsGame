package com.example.fourwinsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[] buttons = new Button[42];

    public enum Player {Empty, One, Two};

    public Player activePlayer;

    public Player [][] board = new Player[7][6];




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


    }


    @Override
    public void onClick(View view) {




        Button button = (Button) view;

        if (button == null) {
            return;
        }

        int row;
        int column;
        String buttonId = getResources().getResourceEntryName(button.getId());

        buttonId = buttonId.substring(4);
        int buttonIndex = Integer.parseInt(buttonId);

        column = buttonIndex / 6;
        row = buttonIndex % 6;//modulo operation gives us the rest of the division
        //row = buttonIndex - column*6;

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


        while (row < 5 && board[column][row + 1] == Player.Empty){
            row++;
        }

        buttonIndex = column * 6 + row;
        button = buttons[buttonIndex];











        if (activePlayer == Player.One) {
            button.setText("X");
            button.setTextColor(Color.parseColor("#FFC34A"));
            board[column][row] = Player.One;

        } else if (activePlayer == Player.Two) {
            button.setText("O");
            button.setTextColor(Color.parseColor("#70FFEA"));
            board[column][row] = Player.Two;
        }

        if(checkWinner()){
            //Toast.makeText(this, "player won !", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("a player has won");
            dlgAlert.setTitle("Game over !!!");

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });

            dlgAlert.setCancelable(true);
            dlgAlert.create().show();


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



}


