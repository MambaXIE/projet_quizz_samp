/*
 *
 *  * Copyright (c) 2015. Petetin Cédric.
 *  * Master 1 - SAMP
 *  * Quizz/Questionnaires avec Android
 *
 */

package ufr.m1.quizz.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ufr.m1.quizz.R;
import ufr.m1.quizz.Stockage.SujetItem;

public class ListeSujetAdapter extends BaseAdapter {

    private ArrayList<SujetItem> listeSujet;
    private Context context;


    public ListeSujetAdapter(ArrayList<SujetItem> listeSujet, Context context) {
        this.listeSujet = listeSujet;
        this.context = context;
    }


    @Override
    public int getCount() {
        return listeSujet.size();
    }

    @Override
    public Object getItem(int position) {
        return listeSujet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listeSujet.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //chargelent du layout d'un element du la listView
            convertView = inflater.inflate(R.layout.lv_sujet_item, null);
            holder = new ViewHolder();

            holder.reponse = (TextView) convertView.findViewById(R.id.lv_item_tv_sujet);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.reponse.setText(listeSujet.get(position).getSujet());

        return convertView;
    }


    private class ViewHolder{
        TextView reponse;
    }

}

