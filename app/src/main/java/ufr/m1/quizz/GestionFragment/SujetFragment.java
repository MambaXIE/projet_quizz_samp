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
import android.widget.Toast;

import java.util.ArrayList;

import ufr.m1.quizz.Adapter.ListeSujetAdapter;
import ufr.m1.quizz.ListViewSwipeGesture.ListViewSwipeGesture;
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

        ListViewSwipeGesture touchListener = new ListViewSwipeGesture(listeSujet, swipeListener, getActivity());
        touchListener.SwipeType	=	ListViewSwipeGesture.Double;    //Set two options at background of list item

        //iniialisation des bouton en background de listView
        //Premier bouton
        touchListener.HalfColor = getResources().getString(R.string.supprimer_color);
        touchListener.HalfText = getResources().getString(R.string.supprimer);
        touchListener.HalfDrawable = getResources().getDrawable(R.drawable.ic_trash);

        //deuxieme bouton
        touchListener.FullColor = getResources().getString(R.string.editer_color);
        touchListener.FullText = getResources().getString(R.string.editer);
        touchListener.FullDrawable = getResources().getDrawable(R.drawable.ic_edit);

        listeSujet.setOnTouchListener(touchListener);

        return view;
    }

    ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

        @Override
        public void FullSwipeListView(int position) {
            // TODO Auto-generated method stub
            Toast.makeText(getContext(), "Action_2", Toast.LENGTH_SHORT).show();
        }

        //appeler pour delete un sujet
        @Override
        public void HalfSwipeListView(int position) {
            Toast.makeText(getContext(),"Delete", Toast.LENGTH_SHORT).show();
            myDb.deleteCategorie(arraySujets.get(position).getId());
            arraySujets.remove(position);
            adapter.notifyDataSetChanged();

        }

        @Override
        public void LoadDataForScroll(int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            // TODO Auto-generated method stub
            Toast.makeText(getContext(),"Delete", Toast.LENGTH_SHORT).show();
            for(int i:reverseSortedPositions){
                arraySujets.remove(i);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void OnClickListView(int position) {
            // TODO Auto-generated method stub
            //startActivity(new Intent(getContext(),MainActivity.class));
        }

    };

}

