package com.cs5520.assignments.numad22su_nisargpatel;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GeneratorViewHolder extends RecyclerView.ViewHolder {

    public TextView nameTV;
    public TextView intialCostTV;

    public GeneratorViewHolder(@NonNull View itemView) {
        super(itemView);
        this.nameTV = itemView.findViewById(R.id.generator_item_name_tv);
        this.intialCostTV = itemView.findViewById(R.id.generator_item_initPrice_tv);
    }

    public void bindData(Generator generator) {
        nameTV.setText(generator.getName());
        intialCostTV.setText(String.valueOf(generator.getInitialPrice()));
    }
}
