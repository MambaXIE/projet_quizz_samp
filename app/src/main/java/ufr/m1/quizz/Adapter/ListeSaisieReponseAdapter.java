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

    /*@Override
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
        if (position == 0){
            holder.textView1.setText("Bonne reponse:");
        }else {
            holder.textView1.setText("Reponse " + (position + 1) + ":");
        }
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
                reponses.set(holder.ref, arg0.toString());
                System.out.println(reponses.get(holder.ref));
            }
        });

        return convertView;
    }*/

    @Override

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lv_editetext_reponse, null, true);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.Item_name);
            viewHolder.editText1 = (EditText) view.findViewById(R.id.Item_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
// loadSavedValues();
        }
        viewHolder.textView1.setText(reponses.get(position));
        viewHolder.editText1.setId(position);
        viewHolder.ref = position;
        /*if (selItems != null && selItems.get(position) != null) {
            viewHolder.itmPrice.setText(selItems.get(position));
        } else {
            viewHolder.itmPrice.setText(null);
        }*/

// Add listener for edit text
        viewHolder.editText1
                .setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            int itemIndex = v.getId();
                            String enteredPrice = ((EditText) v).getText()
                                    .toString();
                            reponses.set(itemIndex, enteredPrice);
                        }
                    }
                });
        return view;
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
