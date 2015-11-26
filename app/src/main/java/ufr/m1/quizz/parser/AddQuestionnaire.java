package ufr.m1.quizz.parser;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

import ufr.m1.quizz.MainActivity;
import ufr.m1.quizz.SQLite.Database;

public class AddQuestionnaire extends AsyncTask<String, Integer, Long>{

    private Context context;
    private static final String TAG = "AddQuestionnaire";
    ProgressDialog pdialog;
    Database db;

    boolean flag = false;

    public AddQuestionnaire(Context context, Database db) {
        this.context = context;
        this.db = db;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "onPreExecute");
        pdialog = new ProgressDialog(context);
        pdialog.setCancelable(false);
        pdialog.setMessage("Chargement des questions ....");
        pdialog.show();
    }

    @Override
    protected Long doInBackground(String... params) {
        Log.i(TAG, "doInBackground");
        try {
            URL url = new URL(params[0]);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), "utf-8");

            int idSujet = 0;
            int idQuestion = 0;
            String positionBonneReponse = "";
            String tag = "";
            String question = "";
            ArrayList<Integer> reponsesQuestion = new ArrayList<>();
            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {

                if(eventType == XmlPullParser.START_TAG) {
                    tag = parser.getName();
                    System.out.println(tag);
                    if (tag.equalsIgnoreCase("Quizz")){
                        System.out.println("Type "+parser.getAttributeValue(0));
                        idSujet = db.insertCategorie(parser.getAttributeValue(0));
                    }else if (tag.equalsIgnoreCase("Reponse")){
                        System.out.println("reponse "+parser.getAttributeValue(0));
                        positionBonneReponse = parser.getAttributeValue(0);
                        db.updateQuestionReponse(idQuestion, reponsesQuestion.get(Integer.parseInt(positionBonneReponse)-1));
                    }

                } else if (eventType == XmlPullParser.TEXT) {
                    if (tag.equalsIgnoreCase("Question")){
                        question = parser.getText();
                        idQuestion = db.insertQuestion(idSujet, question);
                    }else if (tag.equalsIgnoreCase("Proposition")){
                        String reponse = (parser.getText().replaceAll("\t", ""));
                        reponse = reponse.replaceAll("\n", "");
                        if (!reponse.isEmpty()) {
                            reponsesQuestion.add(db.insertReponse(reponse, idQuestion));
                            System.out.println("insert " + reponse + ";");
                        }
                    }
                    System.out.println(parser.getText().replaceAll("\t", ""));
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equalsIgnoreCase("Question")){
                        idQuestion = 0;
                        reponsesQuestion.clear();
                        positionBonneReponse = "";
                    }else if (parser.getName().equalsIgnoreCase("Quizz")){
                        idSujet = 0;
                    }
                    System.out.println(parser.getName());
                }
                eventType = parser.next();
            }
            flag = true;
        } catch (Exception e) {
            Log.e("Error", "problème dans le chargement du fichier");
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Long aLong){
        super.onPostExecute(aLong);
        Log.i(TAG, "onPostExecute");

        if(context instanceof MainActivity){
            ((MainActivity)context).onResume();
        }

        pdialog.dismiss();
    }
}
