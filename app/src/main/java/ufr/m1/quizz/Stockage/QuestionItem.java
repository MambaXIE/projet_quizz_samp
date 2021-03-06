package ufr.m1.quizz.Stockage;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */

import java.util.ArrayList;

public class QuestionItem {

    private int id;
    private int sujet;
    private int bonneReponseId;
    private String question;
    private ArrayList<ReponseItem> listReponse;
    private String image;

    public QuestionItem(int id, int sujet, int bonneReponseId, String question, ArrayList<ReponseItem> listReponse, String image) {
        this.id = id;
        this.sujet = sujet;
        this.bonneReponseId = bonneReponseId;
        this.question = question;
        this.listReponse = listReponse;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSujet() {
        return sujet;
    }

    public void setSujet(int sujet) {
        this.sujet = sujet;
    }

    public int getBonneReponseId() {
        return bonneReponseId;
    }

    public void setBonneReponseId(int bonneReponseId) {
        this.bonneReponseId = bonneReponseId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<ReponseItem> getListReponse() {
        ArrayList<ReponseItem> arrayReponse = new ArrayList<>();
        for (int i = 0; i<listReponse.size(); i++){
            if (listReponse.get(i).getId() == bonneReponseId){
                arrayReponse.add(0,listReponse.get(i));
            }else {
                arrayReponse.add(listReponse.get(i));
            }
        }
        return arrayReponse;
    }

    public void setListReponse(ArrayList<ReponseItem> listReponse) {
        this.listReponse = listReponse;
    }


    public String getBonneReponseString() {

        for (int i = 0; i < listReponse.size(); i++){
            if (listReponse.get(i).getId() == bonneReponseId){
                return listReponse.get(i).getReponse();
            }
        }
        return "";
    }

    public ArrayList<String> getListReponseToString() {
        ArrayList<String> listReponseToString = new ArrayList<>();
        for (int i = 0; i<listReponse.size(); i++){
            if (listReponse.get(i).getId() == bonneReponseId){
                listReponseToString.add(0,listReponse.get(i).getReponse());
            }else {
                listReponseToString.add(listReponse.get(i).getReponse());
            }
        }
        return listReponseToString;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}