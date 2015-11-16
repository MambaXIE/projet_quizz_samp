/*
 *
 *  *
 *  *  * Copyright (c) 2015. Petetin Cédric.
 *  *  * Master 1 - SAMP
 *  *  * Quizz/Questionnaires avec Android
 *  *
 *
 */

package ufr.m1.quizz.Adapter;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ufr.m1.quizz.R;

public class ListeSaisieReponseAdapter extends BaseAdapter{

    private ArrayList<String> reponses;
    private Context context;

    public ListeSaisieReponseAdapter(ArrayList<String> reponses, Context context) {
        this.reponses = reponses;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
            return reponses.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return reponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //ViewHolder holder = null;
        final ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_editetext_reponse, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.editText1 = (EditText) convertView.findViewById(R.id.editText1);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.ref = position;

        holder.textView1.setText("Reponse "+(position+1)+ ":");
        holder.editText1.setText(reponses.get(position));
        holder.editText1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                reponses.set(position, arg0.toString());
                //((AjoutQuestionActivity)context).ajoutReponse();
            }


        });

        return convertView;
    }

    private class ViewHolder {
        TextView textView1;
        EditText editText1;
        int ref;
    }

    public ArrayList<String> getReponses() {
        return reponses;
    }
}
