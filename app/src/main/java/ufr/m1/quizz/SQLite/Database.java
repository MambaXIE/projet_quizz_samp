package ufr.m1.quizz.SQLite;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ufr.m1.quizz.Stockage.QuestionItem;
import ufr.m1.quizz.Stockage.ReponseItem;
import ufr.m1.quizz.Stockage.SujetItem;
import ufr.m1.quizz.parser.AddQuestionnaire;


public class Database extends SQLiteOpenHelper {

    final static String TAG = "SQLite";

    final static int DB_VERSION = 1;
    final static String DB_NAME = "quizz.db";//lien vers la base de données
    Context context;

    private SQLiteDatabase db;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // Store the context for later use
        this.context = context;
        Log.i(TAG, "Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i(TAG, "onCreate bbbbb");
        this.db = database;
        executeSQLScript("bdd.sql");
        new AddQuestionnaire(context, this).execute("https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpdate");
    }

    /**
     * Fonction executant les requete sql contenu dans des fichiers
     * @param       fileName        le liens vers le fichier contenant les requete
     */
    public void executeSQLScript(String fileName) {
        Log.i(TAG, "executeSQLScript");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;

        try{
            inputStream = assetManager.open(fileName);
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            String[] createScript = outputStream.toString().split(";");
            for (int i = 0; i < createScript.length; i++) {
                String sqlStatement = createScript[i].trim();
                if (sqlStatement.length() > 0) {
                    db.execSQL(sqlStatement + ";");
                }
            }
        } catch (IOException e){
            Log.e(TAG,"Fail to load .sql file");
            e.printStackTrace();
        }
    }

    public void getListeSujets(ArrayList<SujetItem> listeSujets) {
        this.db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Sujet", null);
        if (c.moveToFirst()){
            while (!c.isAfterLast()) {
                String sujet = c.getString(1) ;
                int id = c.getInt(0);
                listeSujets.add(new SujetItem(id, sujet));
                c.moveToNext();
            }
            c.close();
        }
    }

    public int insertCategorie(String type) {
        try {
            this.db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT id FROM Sujet WHERE nom = '" + type +"'", null);
            if (c.moveToFirst()){
                return c.getInt(0);
            }else {
                ContentValues values = new ContentValues();
                values.put("nom", type);
                int id = (int) db.insert("Sujet", null, values);
                return id;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    public void deleteCategorie(int id) {
        db.delete("Sujet", "id  =?", new String[]{""+id});
        Log.i(TAG,"Suppression du sujet");
    }


    public void getListeQuestions(ArrayList<QuestionItem> arrayQuestion) {
        this.db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Question", null);
        if (c.moveToFirst()){
            while (!c.isAfterLast()) {
                String question = c.getString(1) ;
                int id = c.getInt(0);
                int bonneReponse = c.getInt(2);
                int sujet = c.getInt(3);
                ArrayList<ReponseItem> listReponse = new ArrayList<>();
                getListeReponse(listReponse, id);
                arrayQuestion.add(new QuestionItem(id, sujet,bonneReponse, question, listReponse));
                c.moveToNext();
            }
            c.close();
        }
    }

    public void getListeReponse(ArrayList<ReponseItem> listReponse, int questionId){
        this.db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Reponse WHERE question = "+questionId, null);
        if (c.moveToFirst()){
            while (!c.isAfterLast()) {
                int id = c.getInt(0);
                String reponse = c.getString(1);
                int question = c.getInt(2);
                listReponse.add(new ReponseItem(id, reponse, question));
                c.moveToNext();
            }
            c.close();
        }
    }


    public int insertQuestion(int idSujet, String question) {
        this.db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM Question WHERE question = '" + question.replaceAll("'"," ") +"' AND sujet = "+ idSujet, null);
        if (c.moveToFirst()){
            return c.getInt(0);
        }else {
            ContentValues values = new ContentValues();
            values.put("question", question);
            values.put("sujet", idSujet);
            int id = (int) db.insert("Question", null, values);
            return id;
        }
    }

    public Integer insertReponse(String text, int idQuestion) {
        this.db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM Reponse WHERE reponse = '" + text.replaceAll("'"," ") + "' AND question = " + idQuestion, null);
        if (c.moveToFirst()){
            return c.getInt(0);
        }else {
            ContentValues values = new ContentValues();
            values.put("reponse", text);
            values.put("question", idQuestion);
            int id = (int) db.insert("Reponse", null, values);
            return id;
        }
    }

    public void updateQuestionReponse(int idQuestion, Integer idBonneReponse) {
        ContentValues values = new ContentValues();
        values.put("bonneReponse", idBonneReponse);
        db.update("Question", values, "id =? ", new String[]{String.valueOf(idQuestion)});
    }

    public void getListeQuestionsBySujet(ArrayList<QuestionItem> listeQuestions, int idSujet) {
        this.db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Question WHERE sujet = "+idSujet, null);
        if (c.moveToFirst()){
            while (!c.isAfterLast()) {
                String question = c.getString(1) ;
                int id = c.getInt(0);
                int bonneReponse = c.getInt(2);
                int sujet = c.getInt(3);
                ArrayList<ReponseItem> listReponse = new ArrayList<>();
                getListeReponse(listReponse, id);
                listeQuestions.add(new QuestionItem(id, sujet,bonneReponse, question, listReponse));
                c.moveToNext();
            }
            c.close();
        }
    }

    public void deleteQuestion(int id) {
        db.delete("Question", "id  =?", new String[]{"" + id});
    }

    public void updateCategorieName(int idSujet, String s) {
        ContentValues values = new ContentValues();
        values.put("nom", s);
        db.update("Sujet", values, "id =? ", new String[]{String.valueOf(idSujet)});
    }

    public QuestionItem getQuestionById(int idQuestion) {
        this.db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Question WHERE id = "+idQuestion, null);
        if (c.moveToFirst()){
            while (!c.isAfterLast()) {
                String question = c.getString(1) ;
                int id = c.getInt(0);
                int bonneReponse = c.getInt(2);
                int sujet = c.getInt(3);
                ArrayList<ReponseItem> listReponse = new ArrayList<>();
                getListeReponse(listReponse, id);
                return new QuestionItem(id, sujet,bonneReponse, question, listReponse);
            }
            c.close();
        }
        return null;
    }

    public void updateQuestion(String question, int idQuestion) {
        ContentValues values = new ContentValues();
        values.put("question", question);
        db.update("Question", values, "id =? ", new String[]{String.valueOf(idQuestion)});
    }
}
