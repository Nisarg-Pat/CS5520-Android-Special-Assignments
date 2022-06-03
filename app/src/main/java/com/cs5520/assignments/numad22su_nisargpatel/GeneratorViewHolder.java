package com.cs5520.assignments.numad22su_nisargpatel;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GeneratorViewHolder extends RecyclerView.ViewHolder {

    public TextView countTV;
    public TextView earningTV;
    public TextView timeTV;
    public TextView buyTV;
    public TextView costTV;

    public GeneratorViewHolder(@NonNull View itemView) {
        super(itemView);
        countTV = itemView.findViewById(R.id.generator_item_count);
        earningTV = itemView.findViewById(R.id.generator_item_earnings_tv);
        buyTV = itemView.findViewById(R.id.generator_item_buy_tv);
        costTV = itemView.findViewById(R.id.generator_item_cost_tv);
        timeTV = itemView.findViewById(R.id.generator_item_time_tv);

    }

    public void bindData(Generator generator) {
        countTV.setText(String.format("%d/%d", generator.getCurrentOwned(), generator.getNextBonusCount()));
        earningTV.setText(String.format("%.0f", generator.getProduction()));
        buyTV.setText(String.format("Buy %d", generator.getNextBonusCount()-generator.getCurrentOwned()));
        costTV.setText(String.format("%.0f", generator.getCost()));
        timeTV.setText(String.format("%.0f", generator.getInitialTime()));

    }
}
