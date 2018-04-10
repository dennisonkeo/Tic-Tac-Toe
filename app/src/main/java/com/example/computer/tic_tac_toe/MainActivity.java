package com.example.computer.tic_tac_toe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean playerOneTurn = true;

    private int count;

    private int playerOneScore;
    private int playerTwoScore;

    private TextView tvPlayer1;
    private TextView tvPlayer2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPlayer1 = (TextView)findViewById(R.id.tvPlayerOne);
        tvPlayer2 = (TextView)findViewById(R.id.tvPlayerTwo);

//        Loop through the play-board
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                String buttonID = "btn"+i+j;
                int resId = getResources().getIdentifier(buttonID,"id",getPackageName());

                buttons[i][j] =(Button)findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }
//        Button to reset the game

        Button btnReset = (Button)findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
//        If is player one turn set the square board to x
        if (playerOneTurn){
            ((Button) v).setText("x");
        }
//        If player two set it to "o"
        else {
            ((Button) v).setText("o");
        }

//        Increment the round count
        count++;

//        Check and return the winner
        if (checkWinner()){
            if (playerOneTurn){
                playerOneWins();
            }
            else {
                playerTwoWins();
            }
        }
//        Return a draw if there is no winner
        else if (count == 9){
                draw();
        }

//        Else toggle player one turn
        else {
            playerOneTurn =! playerOneTurn;
        }

    }

//    Function to Check and return winner or a draw
    private boolean checkWinner(){
        String[][] field = new String[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0; i<3; i++){
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")
                    ){
                return true;
            }
        }

        for (int i=0; i<3; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")
                    ){
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")
                ){
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")
                ){
            return true;
        }

        return false;
    }

//    Function to display player one as winner with the score points
    private void playerOneWins(){
        playerOneScore++;

        Toast.makeText(getApplicationContext(),"Player One Wins",Toast.LENGTH_LONG).show();

//        Update the score points for player two
        updatePointText();

//        Reset the board
        resetBoard();
    }

    //    Function to display player two as winner with the score points
    private void playerTwoWins() {
        playerTwoScore++;

        Toast.makeText(getApplicationContext(),"Player Two Wins",Toast.LENGTH_LONG).show();

//        Update the score points for player two
        updatePointText();
        resetBoard();
    }

//    Function to return a draw
    private void draw() {

        Toast.makeText(getApplicationContext(),"Draw", Toast.LENGTH_LONG).show();

        resetBoard();
    }

    private void updatePointText(){
        tvPlayer1.setText("Player 1: "+playerOneScore);
        tvPlayer2.setText("Player 2: "+playerTwoScore);
    }

//    Function to reset the board
    private void resetBoard(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        count = 0;
        playerOneTurn = true;
    }

//    Function to reset the game
    private void resetGame(){
        playerOneScore = 0;
        playerTwoScore = 0;

        updatePointText();
        resetBoard();
    }

//    Save the game state when the device's screen is rotated
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("count", count);
        outState.putInt("playerOneScore", playerOneScore);
        outState.putInt("playerTwoScore", playerTwoScore);
        outState.putBoolean("playerOneTurn", playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        count = savedInstanceState.getInt("count");
        playerOneScore = savedInstanceState.getInt("playerOneScore");
        playerTwoScore = savedInstanceState.getInt("playerTwoScore");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }
}
