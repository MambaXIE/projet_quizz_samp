package ufr.m1.quizz;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ufr.m1.quizz.Adapter.GridReponseAdapter;
import ufr.m1.quizz.SQLite.Database;
import ufr.m1.quizz.Stockage.QuestionItem;
import ufr.m1.quizz.Stockage.ReponseItem;

public class QuizzActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{


    private static final String TAG = "QUIZZ";
    private static final int REPONSE_REQUEST = 11;

    private Database myDb;
    private ArrayList<QuestionItem> listeQuestions;
    private ArrayList<ReponseItem> listeReponse;
    private String reponseCourante = "";

    private Bundle extras;
    private int idSujet;
    private int compteurQuestion = 0;
    private Boolean reponseVue = false;
    private int cptPoints = 0;

    private TextView tvQuestion;
    private Button btn_suivant;
    private Button btn_reponse;
    private GridView gv_reponse;
    private GridReponseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_quizz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recuperation du quizz jouer
        extras = getIntent().getExtras();
        idSujet = extras.getInt("idsujet");
        this.setTitle(extras.getString("sujet"));

        //recupération de la liste des question dans la base
        myDb = new Database(this);
        listeQuestions = new ArrayList<>();
        myDb.getListeQuestionsBySujet(listeQuestions, idSujet);

        if (listeQuestions.isEmpty()){
            finish();
        }else {

            //initialisation des element du layout
            tvQuestion = (TextView) findViewById(R.id.tv_affiche_question);
            btn_reponse = (Button) findViewById(R.id.btn_voir_reponse);
            btn_suivant = (Button) findViewById(R.id.btn_question_suivante);
            gv_reponse = (GridView) findViewById(R.id.gv_affiche_reponse);

            listeReponse = listeQuestions.get(compteurQuestion).getListReponse();
            Collections.shuffle(listeReponse);
            adapter = new GridReponseAdapter(this, listeReponse);
            gv_reponse.setAdapter(adapter);


            //TODO
            ArrayList<ReponseItem> tets = listeQuestions.get(compteurQuestion).getListReponse();
            System.out.println(tets.size());
            for (int i = 0; i < tets.size(); i++) {
                System.out.println(tets.get(i).getReponse());
            }

            btn_suivant.setOnClickListener(this);
            btn_reponse.setOnClickListener(this);
            gv_reponse.setOnItemClickListener(this);


            actualiseQuestion();
        }
    }

    private void actualiseQuestion(){
        if (listeQuestions.size()<=compteurQuestion){
            showScore();
            //finish();
            compteurQuestion = 0;
        }
        reponseVue = false;
        tvQuestion.setText(listeQuestions.get(compteurQuestion).getQuestion());
        listeReponse = listeQuestions.get(compteurQuestion).getListReponse();
        Collections.shuffle(listeReponse);
        adapter = new GridReponseAdapter(this, listeReponse);
        gv_reponse.setAdapter(adapter);
    }

    public void showScore() {

        new AlertDialog.Builder(QuizzActivity.this)
                .setTitle(getResources().getString(R.string.alertdialog_score_titre))
                .setMessage(cptPoints+"/"+listeQuestions.size())
                .setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        dialog.cancel();
                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_question_suivante:
                compteurQuestion++;
                actualiseQuestion();
                break;
            case R.id.btn_voir_reponse:
                //Aller voir la reponse
                Intent goToReponse = new Intent(this, ReponseActivity.class);
                goToReponse.putExtra("reponse", listeQuestions.get(compteurQuestion).getBonneReponseString());
                startActivityForResult(goToReponse, REPONSE_REQUEST);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick");

        Button btn_Cliquer = (Button)view.findViewById(R.id.btn_reponse_in_gv);
        if (listeQuestions.get(compteurQuestion).getBonneReponseId() == listeReponse.get(position).getId() && !reponseVue){
            btn_Cliquer.setBackgroundColor(Color.GREEN);
            cptPoints++;
        }else if(listeQuestions.get(compteurQuestion).getBonneReponseId() == listeReponse.get(position).getId() && reponseVue) {
            btn_Cliquer.setBackgroundColor(Color.YELLOW);
        }else{
            btn_Cliquer.setBackgroundColor(Color.RED);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                compteurQuestion++;
                actualiseQuestion();
            }
        }, 3000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        if (requestCode == REPONSE_REQUEST && resultCode == RESULT_OK){
            Log.i(TAG, "récupération variable acti 2");
            reponseVue = data.getBooleanExtra("data", false);//recuperation du booleen permettant de savoir si la reponse a ete vu ou non
        }
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
