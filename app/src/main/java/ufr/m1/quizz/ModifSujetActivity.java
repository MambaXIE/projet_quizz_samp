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
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ufr.m1.quizz.Adapter.ListeQuestionAdapter;
import ufr.m1.quizz.SQLite.Database;
import ufr.m1.quizz.Stockage.QuestionItem;

public class ModifSujetActivity extends AppCompatActivity {

    private EditText sujet;
    private ListView listQuestion;
    private Button btn_valide;

    private ArrayList<QuestionItem> arrayQuestion;
    private ListeQuestionAdapter adapter;
    private int idSujet;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_sujet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new Database(this);
        arrayQuestion = new ArrayList<>();
        //garder le clavier fermer
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        idSujet = intent.getIntExtra("idSujet", 1);
        String nomSujet = intent.getStringExtra("nomSujet");

        sujet = (EditText)findViewById(R.id.modif_sujet_sujet_name);
        listQuestion = (ListView) findViewById(R.id.lv_question_by_sujet);
        btn_valide = (Button) findViewById(R.id.btn_valide);

        sujet.setText(nomSujet);

        db.getListeQuestionsBySujet(arrayQuestion, idSujet);
        System.out.println(arrayQuestion.size());
        for (int i = 0; i< arrayQuestion.size(); i++){
            System.out.println(arrayQuestion.get(i).getQuestion());
        }
        adapter = new ListeQuestionAdapter(this,arrayQuestion);
        listQuestion.setAdapter(adapter);

        listQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked(position, view);
            }
        });

        btn_valide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateCategorieName(idSujet, sujet.getText().toString());
                Snackbar.make(v, getString(R.string.toast_message_modification), Snackbar.LENGTH_LONG).show();
            }
        });

    }


    private void itemClicked(final int position, final View view){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.alertdialog_deletesujet_message))
                .setTitle(getResources().getString(R.string.alertdialog_deletesujet_titre))

                        // Boutons de l'alert dialog
                .setPositiveButton(getString(R.string.supprimer), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteQuestion(arrayQuestion.get(position).getId());
                        arrayQuestion.remove(position);
                        adapter.notifyDataSetChanged();
                        Snackbar.make(view, getString(R.string.toast_message_suppression), Snackbar.LENGTH_LONG).show();
                    }
                })


                .setNeutralButton(getResources().getString(R.string.btn_annul), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // fermeture de l'alert dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
