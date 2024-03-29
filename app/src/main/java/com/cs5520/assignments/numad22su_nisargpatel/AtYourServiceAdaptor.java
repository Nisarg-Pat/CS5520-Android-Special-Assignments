package com.cs5520.assignments.numad22su_nisargpatel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AtYourServiceAdaptor extends RecyclerView.Adapter<AtYourServiceAdaptor.AtYourServiceViewHolder> {

    List<FoodItem> foodItems;
    Context context;
    Handler imageIconHandler = new Handler();
    int previouslyClickedPosition = -1;

    static class AtYourServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodItemIcon;
        private TextView foodItemName;
        private TextView foodItemDescription;
        private TextView foodItemCalories;

        public AtYourServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItemIcon = itemView.findViewById(R.id.food_item_icon);
            foodItemName = itemView.findViewById(R.id.food_item_name);
            foodItemDescription = itemView.findViewById(R.id.food_item_description);
            foodItemCalories = itemView.findViewById(R.id.food_item_calories);
        }
    }

    public AtYourServiceAdaptor(Context context, List<FoodItem> foodItems) {
        this.context = context;
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public AtYourServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AtYourServiceViewHolder holder = new AtYourServiceViewHolder(LayoutInflater.from(context).inflate(R.layout.at_your_service_recycler_view_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AtYourServiceViewHolder holder, int position) {
        FoodItem item = foodItems.get(position);
        holder.foodItemName.setText(item.getName());
        holder.foodItemCalories.setText(item.getCalories()+" "+item.getCalorieUnit());
        holder.foodItemDescription.setVisibility(item.isExpanded() ? View.VISIBLE : View.GONE);
        holder.foodItemDescription.setText(Html.fromHtml(item.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        if (item.getImageIconBmp() == null) {
            holder.foodItemIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.loading));
            new Thread(new DownloadImageThread(holder.foodItemIcon, item)).start();
        } else {
            holder.foodItemIcon.setImageBitmap(item.getImageIconBmp());
        }
        holder.itemView.setOnClickListener((v) -> {
            if (previouslyClickedPosition >= 0 && previouslyClickedPosition != position) {
                foodItems.get(previouslyClickedPosition).setExpanded(false);
            }
            item.setExpanded(!item.isExpanded());
            notifyItemChanged(position);
            notifyItemChanged(previouslyClickedPosition);
            previouslyClickedPosition = holder.getAdapterPosition();
        });
    }

    class DownloadImageThread implements Runnable {

        ImageView imageView;
        FoodItem item;

        public DownloadImageThread(ImageView imageView, FoodItem item) {
            this.imageView = imageView;
            this.item = item;
        }

        @Override
        public void run() {
            try {
                InputStream in = new java.net.URL(item.getImageURL()).openStream();
                Bitmap icon = BitmapFactory.decodeStream(in);
                item.setImageIconBmp(icon);
                imageIconHandler.post(() -> {
                    imageView.setImageBitmap(icon);
                });
            } catch (Exception e) {
                imageIconHandler.post(() -> {
                    imageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.loading));
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public void clearFoodItems() {
        int previousSize = getItemCount();
        foodItems = new ArrayList<>();
        notifyItemRangeRemoved(0, previousSize);
    }
}
