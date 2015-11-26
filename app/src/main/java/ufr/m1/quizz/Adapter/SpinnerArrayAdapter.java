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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ufr.m1.quizz.Stockage.SujetItem;

public class SpinnerArrayAdapter extends ArrayAdapter<SujetItem> {


    public SpinnerArrayAdapter(Context context, List<SujetItem> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);


            viewHolder.itemView = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SujetItem item = getItem(position);
        if (item!= null) {
            viewHolder.itemView.setText(item.getSujet());
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView itemView;
    }
}
