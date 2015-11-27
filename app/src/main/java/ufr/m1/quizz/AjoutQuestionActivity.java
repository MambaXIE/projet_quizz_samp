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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ufr.m1.quizz.Adapter.ListeSaisieReponseAdapter;
import ufr.m1.quizz.SQLite.Database;
import ufr.m1.quizz.Stockage.QuestionItem;
import ufr.m1.quizz.Stockage.SujetItem;

public class AjoutQuestionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int SELECT_PHOTO = 100;

    private EditText questionSaisie;
    private Spinner spin_listSujet;
    private ListView lv_reponsesSaisie;
    private RelativeLayout rl_ajoute_question;
    private RelativeLayout rl_ajoute_image;
    private Button btn_ajouter;
    private TextView tv_ajoute_image;

    private ListeSaisieReponseAdapter lvSaisieAdapter;
    private ArrayList<String> reponses;

    private Database mydb;
    private ArrayList<SujetItem> listeSujet;
    private int idQuestion = 0;
    private QuestionItem questionCourante;
    private String image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();


        //garder le clavier fermé
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mydb = new Database(this);
        listeSujet = new ArrayList<>();
        mydb.getListeSujets(listeSujet);

        questionSaisie = (EditText)findViewById(R.id.edt_question_enoncer_saisie);
        spin_listSujet = (Spinner)findViewById(R.id.spinner_liste_sujet);
        lv_reponsesSaisie = (ListView)findViewById(R.id.lv_saisie_reponse);
        rl_ajoute_question = (RelativeLayout) findViewById(R.id.relativelayout_ajoute_reponse);
        rl_ajoute_image = (RelativeLayout) findViewById(R.id.relativelayout_ajoute_image);
        btn_ajouter = (Button)findViewById(R.id.btn_ajoute);
        tv_ajoute_image = (TextView)findViewById(R.id.tv_ajoute_image);

        rl_ajoute_question.setOnClickListener(this);
        rl_ajoute_image.setOnClickListener(this);
        btn_ajouter.setOnClickListener(this);

        ArrayAdapter<SujetItem> adapter = new ArrayAdapter<SujetItem>(this, android.R.layout.simple_spinner_item,listeSujet);
        spin_listSujet.setAdapter(adapter);

        reponses = new ArrayList<>();
        if (i.hasExtra("idQuestion")){
            idQuestion = i.getIntExtra("idQuestion", 0);
            questionCourante = mydb.getQuestionById(idQuestion);
            questionSaisie.setText(questionCourante.getQuestion());
            reponses = questionCourante.getListReponseToString();
            for (int j = 0; j<listeSujet.size(); j++){
                if (listeSujet.get(j).getId() == questionCourante.getSujet()){
                    spin_listSujet.setSelection(j);
                }
            }

        }else{
            reponses.add("");
        }

        lvSaisieAdapter = new ListeSaisieReponseAdapter(reponses, this);
        lv_reponsesSaisie.setAdapter(lvSaisieAdapter);

    }


    public void ajoutReponse(){

        if (reponses.size()<4) {
            reponses.add("");
        }
        lvSaisieAdapter = new ListeSaisieReponseAdapter(reponses, this);
        lv_reponsesSaisie.setAdapter(lvSaisieAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relativelayout_ajoute_reponse:
                ajoutReponse();
                break;
            case R.id.btn_ajoute:
                if (idQuestion == 0){
                    if (questionSaisie.getText().toString().isEmpty()){
                        AfficheErreur();
                    }else {
                        insertNewQuestion(v);
                    }
                }else{
                    if (questionSaisie.getText().toString().isEmpty()){
                        AfficheErreur();
                    }else {
                        updateQuestion(v);
                    }
                }
                break;
            case R.id.relativelayout_ajoute_image:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO);
                break;
        }
    }

    private void AfficheErreur() {
        AlertDialog.Builder erreur = new AlertDialog.Builder(this);
        erreur.setTitle(getString(R.string.alertdialog_erreur_titre))
                .setMessage(getString(R.string.alertdialog_erreur_message_nonsaisie))
                .setNegativeButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public void updateQuestion(View v){
        String question = questionSaisie.getText().toString();
        int idSujet = listeSujet.get((int) spin_listSujet.getSelectedItemId()).getId();
        mydb.updateQuestion(question, idSujet, idQuestion);
        for (int i = 0; i<reponses.size(); i++){
            if (i<questionCourante.getListReponse().size()) {
                mydb.updateReponse(reponses.get(i), questionCourante.getListReponse().get(i).getId());
            }else{
                mydb.insertReponse(reponses.get(i), idQuestion);
            }
        }
        if (!image.isEmpty()){
            mydb.updateQuestionAddImage(image,idQuestion);
        }
        Snackbar.make(v, getString(R.string.toast_message_modification), Snackbar.LENGTH_LONG).show();
    }

    public void insertNewQuestion(View v){
        String question = questionSaisie.getText().toString();
        int idSujet = listeSujet.get((int) spin_listSujet.getSelectedItemId()).getId();
        System.out.println("item select " + listeSujet.get((int) spin_listSujet.getSelectedItemId()).getId());
        idQuestion = mydb.insertQuestion(idSujet, question);
        for (int i = 0; i<reponses.size(); i++){
            int idResponse =  mydb.insertReponse(reponses.get(i), idQuestion);
            if (i == 0){
                mydb.updateQuestionReponse(idQuestion, idResponse);
            }
        }
        if (!image.isEmpty()){
            mydb.updateQuestionAddImage(image,idQuestion);
        }
        Snackbar.make(v, getString(R.string.toast_message_ajout), Snackbar.LENGTH_LONG).show();
        questionSaisie.setText("");
        reponses.clear();
        reponses.add("");
        lvSaisieAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == SELECT_PHOTO){
            Uri selectedImage = data.getData();
            tv_ajoute_image.setText(selectedImage.getPath());
            image = selectedImage.getPath();
        }
    }

}
