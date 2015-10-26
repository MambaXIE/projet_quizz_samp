/*
 *
 *  * Copyright (c) 2015. Petetin Cédric.
 *  * Master 1 - SAMP
 *  * Quizz/Questionnaires avec Android
 *
 */

package ufr.m1.quizz.GestionFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ufr.m1.quizz.Adapter.ListeSujetAdapter;
import ufr.m1.quizz.R;
import ufr.m1.quizz.SQLite.Database;
import ufr.m1.quizz.Stockage.SujetItem;


public class SujetFragment extends Fragment {

    private Button ajoutSujet;
    private ListView listeSujet;
    private Database myDb;

    private ArrayList<SujetItem> arraySujets;
    private ListeSujetAdapter adapter;

    public SujetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sujet, container, false);

        myDb = new Database(getContext());

        arraySujets = new ArrayList<>();
        myDb.getListeSujets(arraySujets);

        ajoutSujet = (Button) view.findViewById(R.id.btn_ajout_sujet);
        listeSujet = (ListView) view.findViewById(R.id.lv_liste_sujet);

        adapter = new ListeSujetAdapter(arraySujets,getContext());
        listeSujet.setAdapter(adapter);

        return view;
    }
}
