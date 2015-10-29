package ufr.m1.quizz.SQLite;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */

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
        new AddQuestionnaire(context).execute("http://raphaello.univ-fcomte.fr/m1/Quizzs.xml");
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
}
