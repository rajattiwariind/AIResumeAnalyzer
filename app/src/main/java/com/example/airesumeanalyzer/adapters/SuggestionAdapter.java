package com.example.airesumeanalyzer.adapters;


import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.Holder>{


    List<String> list;


    public SuggestionAdapter(List<String> list){

        this.list=list;

    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent,
                                     int viewType){


        View view=LayoutInflater.from(parent.getContext())

                .inflate(android.R.layout.simple_list_item_1,

                        parent,

                        false);


        return new Holder(view);

    }



    @Override
    public void onBindViewHolder(Holder holder,
                                 int position){


        holder.text.setText(

                list.get(position));

    }


    @Override
    public int getItemCount(){

        return list.size();

    }



    class Holder extends RecyclerView.ViewHolder{


        TextView text;


        public Holder(View itemView){

            super(itemView);

            text=itemView.findViewById(

                    android.R.id.text1);

        }


    }

}
