
package tech.codesome.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainGameActivity extends AppCompatActivity {

    private String player1 , player2 , currentPlayer;
    private int p1Score = 0 , p2Score = 0 , count=0;
    private boolean running = false;
    private int[][] boxes = new int[3][3];
    private GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game);
        grid = (GridLayout) findViewById(R.id.grid);
        Intent intent = getIntent();
        player1 = intent.getStringExtra("player1");
        player2 = intent.getStringExtra("player2");
        currentPlayer = player1;
        ((TextView) findViewById(R.id.p1_name)).setText(player1);
        ((TextView) findViewById(R.id.p1_name)).setBackground(getResources().getDrawable(R.drawable.player_name_active));
        ((TextView) findViewById(R.id.p1_name)).setTextColor(Color.parseColor("#162f39"));
        ((TextView) findViewById(R.id.p2_name)).setText(player2);
        running = true;
    }


    public void fieldClicked(View btn){
        if(count != 9) {
            if (running) {
                Button b = (Button) btn;
                String IdAsString = b.getResources().getResourceName(b.getId());
                int x = Integer.parseInt(IdAsString.substring(IdAsString.length() - 2, IdAsString.length() - 1));
                int y = Integer.parseInt(IdAsString.substring(IdAsString.length() - 1));
                if (boxes[x - 1][y - 1] == 0) {
                    count++;
                    if (currentPlayer.equals(player1)) {
                        b.setText("X");
                        boxes[x - 1][y - 1] = 1;
                    } else {
                        b.setText("O");
                        boxes[x - 1][y - 1] = 2;
                    }
                    winnerObject winner = checkStatus();
                    if (winner.winnerFound) {
                        toastMessage(currentPlayer + " won");

                        if (currentPlayer.equals(player1)) {
                            ((TextView) findViewById(R.id.p1_score)).setText("" + (++p1Score));
                        } else {
                            ((TextView) findViewById(R.id.p2_score)).setText("" + (++p2Score));
                        }

                        if (winner.Alignment.equals("horizontal")) {
                            int pos = winner.startingPosition;
                            Button v;
                            for (int i = pos * 3; i < (3 * pos) + 3; i++) {
                                v = (Button) grid.getChildAt(i);
                                v.setTextColor(Color.parseColor("#00ff00"));
                            }
                        } else if (winner.Alignment.equals("vertical")) {
                            int pos = winner.startingPosition;
                            Button v;
                            for (int i = pos; i < pos + 7; i = i + 3) {
                                v = (Button) grid.getChildAt(i);
                                v.setTextColor(Color.parseColor("#00ff00"));
                            }
                        } else if (winner.Alignment.equals("cross")) {
                            int pos = winner.startingPosition;
                            Button v;
                            if (pos == 0) {
                                for (int i = pos; i < 9; i = i + 4) {
                                    v = (Button) grid.getChildAt(i);
                                    v.setTextColor(Color.parseColor("#00ff00"));
                                }
                            } else {
                                for (int i = pos; i < 7; i = i + 2) {
                                    v = (Button) grid.getChildAt(i);
                                    v.setTextColor(Color.parseColor("#00ff00"));
                                }
                            }

                        }
                        running = false;
                    } else {
                        if(count==9){
                            toastMessage("Draw. Start again");
                        } else {
                            togglePlayer();
                        }
                    }
                }
            } else {
                toastMessage("Game has ended. Start again");
            }
        } else {
            toastMessage("Draw. Start again");
        }
    }


    private void togglePlayer(){
        if(currentPlayer.equals(player1)){
            currentPlayer = player2;
            ((TextView) findViewById(R.id.p2_name)).setBackground(getResources().getDrawable(R.drawable.player_name_active));
            ((TextView) findViewById(R.id.p2_name)).setTextColor(Color.parseColor("#162f39"));
            ((TextView) findViewById(R.id.p1_name)).setBackground(getResources().getDrawable(R.drawable.player_name));
            ((TextView) findViewById(R.id.p1_name)).setTextColor(Color.parseColor("#a3cbdb"));
        } else {
            currentPlayer = player1;
            ((TextView) findViewById(R.id.p1_name)).setBackground(getResources().getDrawable(R.drawable.player_name_active));
            ((TextView) findViewById(R.id.p1_name)).setTextColor(Color.parseColor("#162f39"));
            ((TextView) findViewById(R.id.p2_name)).setBackground(getResources().getDrawable(R.drawable.player_name));
            ((TextView) findViewById(R.id.p2_name)).setTextColor(Color.parseColor("#a3cbdb"));
        }
    }

    public void rematch(View view){
        Button b;
        for (int i = 0; i < 9; i++) {
            b = (Button) grid.getChildAt(i);
            b.setText("");
            b.setTextColor(Color.WHITE);
        }

        for (int i=0 ; i<3 ; i++){
            for (int j=0 ; j<3 ; j++){
                boxes[i][j] = 0;
            }
        }
        running = true;
        count = 0;
    }

    private winnerObject checkStatus(){
        int count;
        for (int i = 0; i<3 ; i++ ){
            if(boxes[i][0]!=0) {
                count = 0;
                for (int j = 1; j < 3; j++) {
                    if (boxes[i][j] == boxes[i][0]) {
                        count++;
                    }
                }
                if (count == 2) {
                    winnerObject x = new winnerObject(true, "horizontal", i);
                    return x;
                }
            }
        }

        for (int i = 0; i<3 ; i++ ){
            if(boxes[0][i] !=0) {
                count = 0;
                for (int j = 1; j < 3; j++) {
                    if (boxes[j][i] == boxes[0][i]) {
                        count++;
                    }
                }
                if (count == 2) {
                    winnerObject x = new winnerObject(true, "vertical", i);
                    return x;
                }
            }
        }

        if(boxes[0][0]!=0 && (boxes[0][0]==boxes[1][1]) && (boxes[0][0]==boxes[2][2])){
            winnerObject x = new winnerObject(true, "cross", 0);
            return x;
        }
        if(boxes[0][2]!=0 && (boxes[0][2]==boxes[1][1]) && (boxes[0][2]==boxes[2][0])){
            winnerObject x = new winnerObject(true, "cross", 2);
            return x;
        }

        winnerObject x = new winnerObject(false, "", 0);
        return x;

    }

    private class winnerObject{
        Boolean winnerFound;
        String Alignment;
        int startingPosition;
        winnerObject(Boolean b , String a , int sp){
            winnerFound = b;
            Alignment = a;
            startingPosition = sp;
        }
    }

    public void endGame(View view){
        Intent intent = new Intent(MainGameActivity.this,EndActivity.class);
        intent.putExtra("Player1Score",p1Score);
        intent.putExtra("Player2Score",p2Score);
        intent.putExtra("Player1Name", player1);
        intent.putExtra("Player2Name", player2);
        startActivity(intent);
        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(MainGameActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}