package ufr.m1.quizz.GestionFragment;

/*
 *
 *  * Copyright (c) 2015. Petetin Cédric.
 *  * Master 1 - SAMP
 *  * Quizz/Questionnaires avec Android
 *
 */


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

import ufr.m1.quizz.R;
import ufr.m1.quizz.SQLite.Database;
import ufr.m1.quizz.parser.AddQuestionnaire;


public class ImportFragment extends Fragment {

    private Button btn_import;
    private EditText saisie_url;
    private Database myDb;


    public ImportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import, container, false);

        myDb = new Database(getContext());

        btn_import = (Button) view.findViewById(R.id.btn_import_questionnaire);
        saisie_url = (EditText) view.findViewById(R.id.edt_importfr_saisieurl);

        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (URLUtil.isValidUrl(saisie_url.getText().toString())) {
                    String url = Uri.parse(saisie_url.getText().toString())
                                    .buildUpon()
                                    .build()
                                    .toString();
                    new AddQuestionnaire(getActivity(), myDb).execute(url);
                } else {
                    showMessageErreur();
                }
            }
        });

        return view;
    }

    public void showMessageErreur() {

        new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.alertdialog_erreur_titre))
                .setMessage(getResources().getString(R.string.alertdialog_erreur_message))
                .setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

}
