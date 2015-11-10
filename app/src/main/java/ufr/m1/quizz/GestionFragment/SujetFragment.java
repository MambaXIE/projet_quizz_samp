package ufr.m1.quizz.GestionFragment;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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


        ajoutSujet = (Button) view.findViewById(R.id.btn_ajout_sujet);
        listeSujet = (ListView) view.findViewById(R.id.lv_liste_sujet);

        ajoutSujet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouteSujet();
            }
        });

        initListView();

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
            initListView();
        }

        @Override
        public void LoadDataForScroll(int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            // TODO Auto-generated method stub
            Toast.makeText(getContext(),getResources().getString(R.string.toast_message_suppression), Toast.LENGTH_SHORT).show();
            myDb.deleteCategorie(arraySujets.get(reverseSortedPositions[0]).getId());
            initListView();
        }

        @Override
        public void OnClickListView(int position) {
            // TODO Auto-generated method stub
            //startActivity(new Intent(getContext(),MainActivity.class));
        }

    };

    private void initListView(){
        myDb.getListeSujets(arraySujets);
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
    }

    private void ajouteSujet() {
        // EditText dans lequel sera saisie le nom du nouveau sujet
        final EditText editText = new EditText(getActivity());

        // Creation de l'alert Dialog
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage(getResources().getString(R.string.alertdialog_addsujet_message))
                .setTitle(getResources().getString(R.string.alertdialog_addsujet_titre))
                .setView(editText)

                        // Boutons de l'alert dialog
                .setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        myDb.insertCategorie(editText.getText().toString());
                        initListView();
                        dialog.dismiss();
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

        // focus sur l'editText
        // permet d'afficheir le clavier
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();
    }



}

