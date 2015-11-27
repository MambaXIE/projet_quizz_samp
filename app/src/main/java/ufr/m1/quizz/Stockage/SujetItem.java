package ufr.m1.quizz.Stockage;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */

public class SujetItem {

    private String sujet;
    private int id;
    private int score;
    private int taille;

    public SujetItem(int id, String sujet, int score, int taille) {
        this.id = id;
        this.sujet = sujet;
        this.score = score;
        this.taille = taille;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    @Override
    public String toString() {
        return sujet;
    }

    public String getScoreString() {
        return getScore() + "/"+ getTaille();
    }
}
