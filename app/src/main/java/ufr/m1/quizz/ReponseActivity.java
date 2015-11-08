/*
 *
 *  *
 *  *  * Copyright (c) 2015. Petetin Cédric.
 *  *  * Master 1 - SAMP
 *  *  * Quizz/Questionnaires avec Android
 *  *
 *
 */

package ufr.m1.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReponseActivity extends AppCompatActivity implements View.OnClickListener{


    TextView reponse;
    Button montrer;
    Bundle extras;
    boolean reponseAfficher;

    private static final String TAG = "ReponseActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        reponseAfficher = false;

        extras = getIntent().getExtras();

        reponse = (TextView)findViewById(R.id.showReponse);
        montrer = (Button)findViewById(R.id.montrerReponse);
        montrer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.montrerReponse){
            reponse.setText(extras.getString("reponse"));
            reponseAfficher = true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent backToMain = new Intent(this, QuizzActivity.class);
        backToMain.putExtra("data",reponseAfficher);
        setResult(RESULT_OK,backToMain);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

}
