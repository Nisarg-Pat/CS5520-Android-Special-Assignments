package com.cs5520.assignments.numad22su_nisargpatel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GeneratorAdapter extends RecyclerView.Adapter<GeneratorAdapter.GeneratorViewHolder> {

    List<Generator> generatorList;
    Context context;

    public static class GeneratorViewHolder extends RecyclerView.ViewHolder {

        private final TextView countTV;
        private final TextView earningTV;
        private final TextView timeTV;
        private final TextView buyTV;
        private final TextView costTV;
        private final ConstraintLayout buyView;

        public GeneratorViewHolder(@NonNull View itemView) {
            super(itemView);
            countTV = itemView.findViewById(R.id.generator_item_count);
            earningTV = itemView.findViewById(R.id.generator_item_earnings_tv);
            buyTV = itemView.findViewById(R.id.generator_item_buy_tv);
            costTV = itemView.findViewById(R.id.generator_item_cost_tv);
            timeTV = itemView.findViewById(R.id.generator_item_time_tv);
            buyView = itemView.findViewById(R.id.generator_item_buy_view);
        }

        public void bindData(Generator generator) {
            countTV.setText(String.format("%d/%d", generator.getCurrentOwned(), generator.getNextBonusCount()));
            earningTV.setText(String.format("%.2f per sec", generator.getProduction()));
            buyTV.setText(String.format("Buy %d", generator.getNextBonusCount()-generator.getCurrentOwned()));
            costTV.setText(String.format("%.2f", generator.getCost()));
            timeTV.setText(String.format("%.0f", generator.getInitialTime()));
        }
    }

    public GeneratorAdapter(List<Generator> generatorList, Context context) {
        this.generatorList = generatorList;
        this.context = context;
    }

    @NonNull
    @Override
    public GeneratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GeneratorViewHolder holder = new GeneratorViewHolder(LayoutInflater.from(context).inflate(R.layout.generator_recycler_view_item, parent, false));
        holder.buyView.setOnClickListener((v) -> clickBuyView(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GeneratorViewHolder holder, int position) {
        holder.bindData(generatorList.get(position));
    }

    @Override
    public int getItemCount() {
        return generatorList.size();
    }

    public void clickBuyView(int position) {
        generatorList.get(position).buy();
        notifyItemChanged(position);
    }
}
