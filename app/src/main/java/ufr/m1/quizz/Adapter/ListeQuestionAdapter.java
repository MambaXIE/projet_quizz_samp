package ufr.m1.quizz.Adapter;

/*
 * Copyright (c) 2015. Petetin Cédric.
 * Master 1 - SAMP
 * Quizz/Questionnaires avec Android
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ufr.m1.quizz.R;
import ufr.m1.quizz.Stockage.QuestionItem;


public class ListeQuestionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<QuestionItem> listQuestion;

    public ListeQuestionAdapter(Context context, ArrayList<QuestionItem> listQuestion) {
        this.context = context;
        this.listQuestion = listQuestion;
    }

    @Override
    public int getCount() {
        return listQuestion.size();
    }

    @Override
    public Object getItem(int position) {
        return listQuestion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listQuestion.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //chargelent du layout d'un element du la listView
            convertView = inflater.inflate(R.layout.lv_question_item, null);
            holder = new ViewHolder();

            holder.reponse = (TextView) convertView.findViewById(R.id.lv_item_tv_question);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.reponse.setText(listQuestion.get(position).getQuestion());

        return convertView;
    }

    private class ViewHolder{
        TextView reponse;
    }
}
