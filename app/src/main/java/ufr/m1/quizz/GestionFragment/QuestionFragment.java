package ufr.m1.quizz.GestionFragment;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ufr.m1.quizz.Adapter.ListeQuestionAdapter;
import ufr.m1.quizz.AjoutQuestionActivity;
import ufr.m1.quizz.R;
import ufr.m1.quizz.SQLite.Database;
import ufr.m1.quizz.Stockage.QuestionItem;

public class QuestionFragment extends Fragment {
    private Button ajoutQuestion;
    private ListView listeQuestion;
    private Database myDb;

    private ArrayList<QuestionItem> arrayQuestion;
    private ListeQuestionAdapter adapter;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        myDb = new Database(getContext());

        arrayQuestion = new ArrayList<>();
        myDb.getListeQuestions(arrayQuestion);

        listeQuestion = (ListView)view.findViewById(R.id.lv_liste_question);

        adapter = new ListeQuestionAdapter( getContext(),arrayQuestion);
        listeQuestion.setAdapter(adapter);

        ajoutQuestion = (Button)view.findViewById(R.id.btn_ajout_question);
        ajoutQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AjoutQuestionActivity.class);
                startActivity(i);
            }
        });

        listeQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked(position);
            }
        });

        return view;
    }

    private void itemClicked(final int position){
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage(getResources().getString(R.string.alertdialog_deletesujet_message))
                .setTitle(getResources().getString(R.string.alertdialog_deletesujet_titre))

                        // Boutons de l'alert dialog
                .setPositiveButton(getString(R.string.supprimer), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        myDb.deleteQuestion(arrayQuestion.get(position).getId());
                        arrayQuestion.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                })

                .setNegativeButton(getResources().getString(R.string.btn_annul), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // fermeture de l'alert dialog
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

}
