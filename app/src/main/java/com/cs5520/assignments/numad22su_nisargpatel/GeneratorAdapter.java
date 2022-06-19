package com.cs5520.assignments.numad22su_nisargpatel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.SoundPool;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GeneratorAdapter extends RecyclerView.Adapter<GeneratorAdapter.GeneratorViewHolder> {

    private final List<Generator> generatorList;
    private BuyType buyType;
    private final Context context;
    private final Player player;
    private final SoundPool soundPool;
    private final int generatorBuySound;

    private final TextView scoreTV;
    private final Handler scoreHandler = new Handler();
    private final Handler timeHandler = new Handler();

    private static final Object playerScoreLock = new Object();

    static class GeneratorViewHolder extends RecyclerView.ViewHolder {

        private final TextView countTV;
        private final ProgressBar earningProgress;
        private final TextView earningsTV;
        private final TextView timeTV;
        private final TextView buyTV;
        private final TextView costTV;
        private final ConstraintLayout buyView;
        private final ImageView generatorImage;

        public GeneratorViewHolder(@NonNull View itemView) {
            super(itemView);
            countTV = itemView.findViewById(R.id.generator_item_count);
            earningProgress = itemView.findViewById(R.id.generator_item_earnings_progress_bar);
            earningsTV = itemView.findViewById(R.id.generator_item_earnings_tv);
            buyTV = itemView.findViewById(R.id.generator_item_buy_tv);
            costTV = itemView.findViewById(R.id.generator_item_cost_tv);
            timeTV = itemView.findViewById(R.id.generator_item_time_tv);
            buyView = itemView.findViewById(R.id.generator_item_buy_view);
            generatorImage = itemView.findViewById(R.id.generator_item_icon);
        }
    }

    public GeneratorAdapter(Context context, List<Generator> generatorList, Player player, BuyType buyType, SoundPool soundPool) {
        this.generatorList = generatorList;
        this.context = context;
        this.buyType = buyType;
        this.player = player;
        this.soundPool = soundPool;
        this.generatorBuySound = soundPool.load(context, R.raw.cash, 1);
        scoreTV = ((Activity) context).findViewById(R.id.score_tv);
    }

    @NonNull
    @Override
    public GeneratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GeneratorViewHolder holder = new GeneratorViewHolder(LayoutInflater.from(context).inflate(R.layout.generator_recycler_view_item, parent, false));
        holder.buyView.setOnClickListener((v) -> clickBuyView(holder.getAdapterPosition()));
        holder.earningProgress.setOnClickListener((v) -> clickGeneratorRevenue(holder));
        holder.generatorImage.setOnClickListener((v) -> clickGeneratorRevenue(holder));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GeneratorViewHolder holder, int position) {
        Generator generator = generatorList.get(position);
        holder.countTV.setText(String.format("%d/%d", generator.getCurrentOwned(), generator.getNextBonusCount()));
        holder.buyTV.setText(String.format("Buy %d", generator.getBuyCount(buyType)));
        holder.costTV.setText(String.format("%.2f", generator.getCost(buyType)));
        if (player.getTotalAmount() >= generator.getCost(buyType)) {
            holder.buyView.setBackgroundColor(context.getColor(R.color.adcap_orange));
        } else {
            holder.buyView.setBackgroundColor(context.getColor(R.color.adcap_dark_grey));
        }
        holder.timeTV.setText(String.format("%d", generator.getRemainingTime()));
        holder.earningsTV.setText(String.format("%.2f", generator.getRevenue()));
    }


    @Override
    public int getItemCount() {
        return generatorList.size();
    }

    public void clickBuyView(int position) {
        double cost = generatorList.get(position).getCost(buyType);
        if (cost > player.getTotalAmount()) {
            return;
        }
        synchronized (playerScoreLock) {
            player.subtractAmount(cost);
            generatorList.get(position).buy(buyType);
        }
        soundPool.play(generatorBuySound, 1, 1, 0, 0, 1);
        if(generatorList.get(position).getCurrentOwned() >=25) {
            generatorList.get(position).setManagerEnabled(true);
        }
        updateAmountChange();
        notifyItemChanged(position);
    }

    public void clickGeneratorRevenue(GeneratorViewHolder holder) {
        Generator generator = generatorList.get(holder.getAdapterPosition());
        if (generator.isInProgress() || generator.getCurrentOwned() == 0) {
            return;
        }
        GeneratorProgressThread gpt = new GeneratorProgressThread(holder, generator, player);
        new Thread(gpt).start();
    }

    public void updateBuyType(BuyType newBuyType) {
        this.buyType = newBuyType;
        notifyItemRangeChanged(0, getItemCount());
    }

    public void updateAmountChange() {
        scoreTV.setText(String.format("%.2f", player.getTotalAmount()));
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
                generator.setRemainingTimePercent(100-current);
                timeHandler.post(() -> {
                    notifyItemChanged(holder.getAdapterPosition());
                });
            }
            holder.earningProgress.setProgress(0);
            generator.resetRemainingTime();
            generator.setInProgress(false);
            synchronized (playerScoreLock) {
                player.addAmount(generator.getRevenue());
            }
            scoreHandler.post(() -> {
                updateAmountChange();
            });
            if (generator.isManagerEnabled()) {
                run();
            }
        }
    }
}
