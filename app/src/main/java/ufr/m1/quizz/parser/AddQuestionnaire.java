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

public class AddQuestionnaire extends AsyncTask<String, Integer, Long>{

    private Context context;
    private static final String TAG = "AddQuestionnaire";
    NodeList nodelist;
    ProgressDialog pdialog;

    public AddQuestionnaire(Context context) {
        this.context = context;
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
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            nodelist = doc.getElementsByTagName("Quizz");

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
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                //System.out.println(getNode("Quizz", eElement));
                //System.out.println(eElement.getAttribute("type"));
                System.out.println(getNode("Question", eElement));
            }
        }
        pdialog.dismiss();
    }

    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    private final String getTextNodeValue(Node node) {
        Node child;
        if (node != null) {
            if (node.hasChildNodes()) {
                child = node.getFirstChild();
                while(child != null) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                    child = child.getNextSibling();
                }
            }
        }
        return "";
    }
}
