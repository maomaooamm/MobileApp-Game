package edu.uco.hwu9.p6hanye_w;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class BaseballActivity extends Activity {
    private Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    private Button b[] = new Button[10];
    private Button btnNew, btnClear, btnHint;
    private TextView txtkey, txtguess, txtresult;
    private RelativeLayout rl;
    private final int key[] = new int[3];
    private final int guess[] = new int[3];
    private int guessct;
    private int ballct;
    private int strikect;
    private boolean isDone;
    public ArrayList<String> array = new ArrayList<String>();
    int alpha = 127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseball);
        rl = (RelativeLayout) findViewById(R.id.drawBall);

        btnNew = (Button) findViewById(R.id.btnNew);
        btnClear = (Button) findViewById(R.id.btnclear);
        btnHint = (Button) findViewById(R.id.btnHint);
        b[0] = b0 = (Button) findViewById(R.id.btn0);
        b[1] = b1 = (Button) findViewById(R.id.btn1);
        b[2] = b2 = (Button) findViewById(R.id.btn2);
        b[3] = b3 = (Button) findViewById(R.id.btn3);
        b[4] = b4 = (Button) findViewById(R.id.btn4);
        b[5] = b5 = (Button) findViewById(R.id.btn5);
        b[6] = b6 = (Button) findViewById(R.id.btn6);
        b[7] = b7 = (Button) findViewById(R.id.btn7);
        b[8] = b8 = (Button) findViewById(R.id.btn8);
        b[9] = b9 = (Button) findViewById(R.id.btn9);
        txtkey = (TextView) findViewById(R.id.txtKey);
        txtresult = (TextView) findViewById(R.id.txtResult);
        txtguess = (TextView) findViewById(R.id.txtGuess);
        guessct = 0;
        ballct = 0;
        strikect = 0;

        disableAllButtons();

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // generate new key
                Random random = new Random();
                key[0] = random.nextInt(10);
                do
                    key[1] = random.nextInt(10);
                while (key[0] == key[1]);
                do
                    key[2] = random.nextInt(10);
                while (key[2] == key[0] || key[2] == key[1]);

                txtkey.setText((new StringBuilder()).append("Key = ")
                        .append(key[0]).append(key[1]).append(key[2]).toString());
                guessct = ballct = strikect = 0;
                isDone = false;
                enableAllButtons();
                btnClear.setEnabled(false);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessct = 0;
                int guess[] = new int[3];
                txtguess.setText("");
                txtresult.setText("");
                rl.removeAllViews();
                enableAllButtons();
                btnClear.setEnabled(false);
            }
        });

        for (int n = 0; n < 10; n++) {
            b[n].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i;
                    for (i = 0; i <= 9 && v != b[i]; i++) ;
                    b[i].setEnabled(false);
                    guess[guessct++] = i;
                    String message = "Guess = ";
                    for (int j = 0; j < guessct; j++)
                        message = (new StringBuilder()).append(message).append(guess[j]).toString();
                    txtguess.setText(message);

                    if (guessct == 3) {
                        disableAllButtons();
                        btnClear.setEnabled(true);
                        showResult();
                    }
                }
            });
        }

        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String K = "Key = " + key[0] + key[1] + key[2];
                String G = "" + guess[0] + guess[1] + guess[2] + "   " + ballct + "   " + strikect;
                array.add(G);
                Intent intent = new Intent(BaseballActivity.this, GuessHintActivity.class);
                intent.putExtra("key", K)
                        .putExtra("guess", array);
                startActivity(intent);
            }
        });
    }

    private class DrawBall extends View{
        public DrawBall(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint blue = new Paint();
            blue.setColor(Color.BLUE);
            blue.setStyle(Paint.Style.FILL);

            for (int i = 0; i < ballct; i++)
                canvas.drawCircle(300 + i*100, 60, 40, blue);

            Paint red = new Paint();
            red.setColor(Color.RED);
            red.setStyle(Paint.Style.FILL);

            for (int i = 0; i < strikect; i++)
                canvas.drawCircle(300 + i*100, 260, 40, red);
        }
    }

    private void drawBall() {

        DrawBall ball = new DrawBall(getApplicationContext());
        rl.addView(ball);
    }

    private void showResult() {
        ballct = strikect = 0;
        for (int i = 0; i < 3; i++)
            if (key[i] == guess[i])
                strikect++;

        if (guess[0] == key[1] || guess[0] == key[2])
            ballct++;
        if (guess[1] == key[0] || guess[1] == key[2])
            ballct++;
        if (guess[2] == key[0] || guess[2] == key[1])
            ballct++;
        txtresult.setText((new StringBuilder()).append(txtresult.getText()).append("B=")
                .append(ballct).append(" S=").append(strikect).toString());
        int n = 0;
        do {
            drawBall();
            n++;
        }while(n<=ballct);
        isDone = true;
    }

    private void disableAllButtons() {
        for (int i = 0; i <= 9; i++)
            b[i].setEnabled(false);

        btnClear.setEnabled(false);
    }

    private void enableAllButtons() {
        for (int i = 0; i <= 9; i++)
            b[i].setEnabled(true);

        btnClear.setEnabled(true);
    }


}
