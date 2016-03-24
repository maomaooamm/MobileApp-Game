package edu.uco.hwu9.p6hanye_w;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class GuessHintActivity extends Activity {
    private TextView txtkey, txtres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_hint);

        txtkey = (TextView) findViewById(R.id.hintkey);
        txtres = (TextView) findViewById(R.id.hintresult);

        Intent intent = getIntent();

        String key = intent.getStringExtra("key");
        ArrayList<String> guess = intent.getStringArrayListExtra("guess");

        String msg = "";
        for(int i = 0; i < guess.size(); i++){
            msg = (new StringBuilder()).append(msg).append(guess.get(i)).append("\n").toString();
        }
        txtkey.setText(key);
        txtres.setText(msg);
    }
}
