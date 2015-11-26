package ufr.m1.quizz.Adapter;

/*
 *  Copyright (c) 2015. Petetin Cédric.
 *  Master 1 - SAMP
 *  Quizz/Questionnaires avec Android
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

import ufr.m1.quizz.R;
import ufr.m1.quizz.Stockage.ReponseItem;

public class GridReponseAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<ReponseItem> listeReponses;

    public GridReponseAdapter(Context context, ArrayList<ReponseItem> reponses) {
        this.context = context;
        this.listeReponses = reponses;
    }

    @Override
    public int getCount() {
        return listeReponses.size();
    }

    @Override
    public Object getItem(int position) {
        return listeReponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listeReponses.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //chargement du layout d'un element du la listView
            convertView = inflater.inflate(R.layout.gv_reponse_btn, null);
            holder = new ViewHolder();

            holder.reponse = (Button) convertView.findViewById(R.id.btn_reponse_in_gv);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.reponse.setText(listeReponses.get(position).getReponse());

        return convertView;
    }

    private class ViewHolder{
        Button reponse;
    }
}
