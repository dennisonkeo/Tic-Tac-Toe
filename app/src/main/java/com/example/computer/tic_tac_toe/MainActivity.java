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
        if (playerOneTurn){
            ((Button) v).setText("x");
        }
        else {
            ((Button) v).setText("o");
        }

        count++;

        if (checkWinner()){
            if (playerOneTurn){
                playerOneWins();
            }
            else {
                playerTwoWins();
            }
        }
        else if (count == 9){
                draw();
        }

        else {
            playerOneTurn =! playerOneTurn;
        }

    }

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

    private void playerOneWins(){
        playerOneScore++;

        Toast.makeText(getApplicationContext(),"Player One Wins",Toast.LENGTH_LONG).show();

        updatePointText();
        resetBoard();
    }

    private void playerTwoWins() {
        playerTwoScore++;

        Toast.makeText(getApplicationContext(),"Player Two Wins",Toast.LENGTH_LONG).show();

        updatePointText();
        resetBoard();
    }

    private void draw() {

        Toast.makeText(getApplicationContext(),"Draw", Toast.LENGTH_LONG).show();

        resetBoard();
    }

    private void updatePointText(){
        tvPlayer1.setText("Player 1: "+playerOneScore);
        tvPlayer2.setText("Player 2: "+playerTwoScore);
    }

    private void resetBoard(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        count = 0;
        playerOneTurn = true;
    }

    private void resetGame(){
        playerOneScore = 0;
        playerTwoScore = 0;

        updatePointText();
        resetBoard();
    }

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
