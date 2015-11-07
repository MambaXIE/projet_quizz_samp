package ufr.m1.quizz.GestionFragment;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ufr.m1.quizz.Adapter.ListeQuestionAdapter;
import ufr.m1.quizz.ListViewSwipeGesture.ListViewSwipeGesture;
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

        ListViewSwipeGesture touchListener = new ListViewSwipeGesture(listeQuestion , swipeListener, getActivity());
        touchListener.SwipeType = ListViewSwipeGesture.Double;


        return view;
    }

    ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {
        @Override
        public void FullSwipeListView(int position) {
            Toast.makeText(getContext(), "Modifier", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void HalfSwipeListView(int position) {
            Toast.makeText(getContext(),"Delete", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void OnClickListView(int position) {
            Toast.makeText(getContext(),"Click", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void LoadDataForScroll(int count) {

        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {

        }
    };

}
