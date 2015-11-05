package ufr.m1.quizz.parser;

/*
 *
 *  * Copyright (c) 2015. Petetin Cédric.
 *  * Master 1 - SAMP
 *  * Quizz/Questionnaires avec Android
 *
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ufr.m1.quizz.SQLite.Database;

public class AddQuestionnaire extends AsyncTask<String, Integer, Long>{

    private Context context;
    private static final String TAG = "AddQuestionnaire";
    NodeList nodelist;
    ProgressDialog pdialog;
    Document doc;
    Database db;

    public AddQuestionnaire(Context context, Database db) {
        this.context = context;
        this.db = db;
    }

    @Override
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
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Telechargement du fichier
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();


        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Log.i(TAG, "onPostExecute");
        nodelist = doc.getElementsByTagName("Quizz");
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) nodelist.item(temp);
                db.insertCategorie(e.getAttribute("type"));
                System.out.println(e.getAttribute("type"));
                //System.out.println(eElement.getAttribute("type"));
                //System.out.println(getNode("Question", eElement));
            }
        }
        pdialog.dismiss();
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
