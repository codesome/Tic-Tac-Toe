package tech.codesome.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        String player1 = intent.getStringExtra("Player1Name");
        String player2 = intent.getStringExtra("Player2Name");
        int p1Score = intent.getIntExtra("Player1Score", 0);
        int p2Score = intent.getIntExtra("Player2Score",0);

        if(p1Score == p2Score){
            ((TextView) findViewById(R.id.result)).setText("Draw");
        } else if (p1Score>p2Score) {
            ((TextView) findViewById(R.id.result)).setText(player1+" Wins!");
        } else{
            ((TextView) findViewById(R.id.result)).setText(player2+" Wins!");
        }
        ((TextView) findViewById(R.id.p1Name)).setText(player1);
        ((TextView) findViewById(R.id.p1Score)).setText(""+p1Score);
        ((TextView) findViewById(R.id.p2Name)).setText(player2);
        ((TextView) findViewById(R.id.p2Score)).setText(""+p2Score);
    }

    public void RestartGame(View view){
        Intent intent = new Intent(EndActivity.this,FirstScreen.class);
        startActivity(intent);
        finish();
    }

}
