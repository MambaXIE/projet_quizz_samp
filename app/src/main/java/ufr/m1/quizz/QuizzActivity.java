package ufr.m1.quizz;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ufr.m1.quizz.Adapter.GridReponseAdapter;
import ufr.m1.quizz.SQLite.Database;
import ufr.m1.quizz.Stockage.QuestionItem;
import ufr.m1.quizz.Stockage.ReponseItem;

public class QuizzActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String TAG = "QUIZZ";

    private Database myDb;
    private ArrayList<QuestionItem> listeQuestions;
    private ArrayList<ReponseItem> listeReponse;
    private String reponseCourante = "";

    private Bundle extras;
    private int idSujet;
    private int compteurQuestion = 0;

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

        //initialisation des element du layout
        tvQuestion = (TextView)findViewById(R.id.tv_affiche_question);
        btn_reponse = (Button)findViewById(R.id.btn_voir_reponse);
        btn_suivant = (Button)findViewById(R.id.btn_question_suivante);
        gv_reponse = (GridView)findViewById(R.id.gv_affiche_reponse);

        listeReponse = listeQuestions.get(compteurQuestion).getListReponse();
        Collections.shuffle(listeReponse);

        adapter = new GridReponseAdapter(this, listeReponse);
        gv_reponse.setAdapter(adapter);

        //TODO
        ArrayList<ReponseItem> tets = listeQuestions.get(compteurQuestion).getListReponse();
        System.out.println(tets.size());
        for (int i = 0; i < tets.size(); i++){
            System.out.println(tets.get(i).getReponse());
        }

        btn_suivant.setOnClickListener(this);


        actualiseQuestion();
    }

    private void actualiseQuestion(){
        if (listeQuestions.size()<=compteurQuestion){
            compteurQuestion = 0;
        }
        tvQuestion.setText(listeQuestions.get(compteurQuestion).getQuestion());
        listeReponse = listeQuestions.get(compteurQuestion).getListReponse();
        Collections.shuffle(listeReponse);
        adapter = new GridReponseAdapter(this, listeReponse);
        gv_reponse.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_question_suivante:
                compteurQuestion++;
                actualiseQuestion();
                break;
            /*case R.id.Vreponse:
                //Aller voir la reponse
                Intent goToReponse = new Intent(this, ReponseActivity.class);
                goToReponse.putExtra("reponse", reponseCourante);
                startActivityForResult(goToReponse, REPONSE_REQUEST);
                break;*/
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
