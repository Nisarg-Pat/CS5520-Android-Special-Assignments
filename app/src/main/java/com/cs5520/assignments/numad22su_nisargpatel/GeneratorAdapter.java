package com.cs5520.assignments.numad22su_nisargpatel;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GeneratorAdapter extends RecyclerView.Adapter<GeneratorAdapter.GeneratorViewHolder> {

    private final List<Generator> generatorList;
    private BuyType buyType;
    private final Context context;
    private final Player player;

    private final TextView scoreTV;
    private final Handler scoreHandler = new Handler();

    public static class GeneratorViewHolder extends RecyclerView.ViewHolder {

        private final TextView countTV;
        private final ProgressBar earningProgress;
        private final TextView timeTV;
        private final TextView buyTV;
        private final TextView costTV;
        private final ConstraintLayout buyView;

        public GeneratorViewHolder(@NonNull View itemView) {
            super(itemView);
            countTV = itemView.findViewById(R.id.generator_item_count);
            earningProgress = itemView.findViewById(R.id.generator_item_earnings_progress_bar);
            buyTV = itemView.findViewById(R.id.generator_item_buy_tv);
            costTV = itemView.findViewById(R.id.generator_item_cost_tv);
            timeTV = itemView.findViewById(R.id.generator_item_time_tv);
            buyView = itemView.findViewById(R.id.generator_item_buy_view);
        }
    }

    public GeneratorAdapter(Context context, List<Generator> generatorList, Player player, BuyType buyType) {
        this.generatorList = generatorList;
        this.context = context;
        this.buyType = buyType;
        this.player = player;
        scoreTV = ((Activity)context).findViewById(R.id.score_tv);
    }

    @NonNull
    @Override
    public GeneratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GeneratorViewHolder holder = new GeneratorViewHolder(LayoutInflater.from(context).inflate(R.layout.generator_recycler_view_item, parent, false));
        holder.buyView.setOnClickListener((v) -> clickBuyView(holder.getAdapterPosition()));
        holder.earningProgress.setOnClickListener((v) -> {
            if (!generatorList.get(holder.getAdapterPosition()).isInProgress()) {
                GeneratorProgressThread gpt = new GeneratorProgressThread(holder, generatorList.get(holder.getAdapterPosition()), player);
                new Thread(gpt).start();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GeneratorViewHolder holder, int position) {
        Generator generator = generatorList.get(position);
        holder.countTV.setText(String.format("%d/%d", generator.getCurrentOwned(), generator.getNextBonusCount()));
        holder.buyTV.setText(String.format("Buy %d", generator.getBuyCount(buyType)));
        holder.costTV.setText(String.format("%.2f", generator.getCost(buyType)));
        holder.timeTV.setText(String.format("%.0f", generator.getInitialTime()));
    }


    @Override
    public int getItemCount() {
        return generatorList.size();
    }

    public void clickBuyView(int position) {
        generatorList.get(position).buy(buyType);
        notifyItemChanged(position);
    }

    public void updateBuyType(BuyType newBuyType) {
        this.buyType = newBuyType;
        notifyItemRangeChanged(0, getItemCount());
    }

    class GeneratorProgressThread implements Runnable {

        GeneratorViewHolder holder;
        Generator generator;
        Player player;

        public GeneratorProgressThread(GeneratorViewHolder holder, Generator generator, Player player) {
            this.holder = holder;
            this.generator = generator;
            this.player = player;
        }

        @Override
        public void run() {
            long sleepTime = (long) (generator.getInitialTime() * 10);
            generator.setInProgress(true);
            int current = 0;
            while (current != 100) {
                try {
                    Thread.sleep((sleepTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                current++;
                holder.earningProgress.setProgress(current);
            }
            holder.earningProgress.setProgress(0);
            generator.setInProgress(false);
            player.addAmount(generator.getProduction());
            scoreHandler.post(() -> {
                scoreTV.setText(String.format("%.2f", player.getTotalAmount()));
            });
            if (generator.isManagerEnabled()) {
                run();
            }
        }
    }
}
