package tech.codesome.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class FirstScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_screen);
    }

    public void submitData(View view){
        EditText editText;
        Intent intent = new Intent(this,MainGameActivity.class);
        editText = (EditText) findViewById(R.id.p1_edittext);
        String name = editText.getText().toString();
        if(name.equals("")){
            toastMessage("Enter Player1 name");

        } else {
            intent.putExtra("player1", name);
            editText = (EditText) findViewById(R.id.p2_edittext);
            name = editText.getText().toString();
            if(name.equals("")){
                toastMessage("Enter Player2 name");
            } else {
                intent.putExtra("player2", name);
                startActivity(intent);
                finish();
            }
        }
    }

    private void toastMessage(String message){
        Toast.makeText(FirstScreen.this, message, Toast.LENGTH_SHORT).show();
    }
}
