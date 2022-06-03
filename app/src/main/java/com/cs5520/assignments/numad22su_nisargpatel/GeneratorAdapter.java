package com.cs5520.assignments.numad22su_nisargpatel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GeneratorAdapter extends RecyclerView.Adapter<GeneratorViewHolder> {

    List<Generator> generatorList;
    Context context;

    public GeneratorAdapter(List<Generator> generatorList, Context context) {
        this.generatorList = generatorList;
        this.context = context;
    }

    @NonNull
    @Override
    public GeneratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GeneratorViewHolder(LayoutInflater.from(context).inflate(R.layout.generator_recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GeneratorViewHolder holder, int position) {
        holder.bindData(generatorList.get(position), this);
    }

    @Override
    public int getItemCount() {
        return generatorList.size();
    }
}
