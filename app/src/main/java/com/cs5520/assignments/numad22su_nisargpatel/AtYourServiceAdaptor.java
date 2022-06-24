package com.cs5520.assignments.numad22su_nisargpatel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
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

    static class AtYourServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodItemIcon;
        private TextView foodItemName;


        public AtYourServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItemIcon = itemView.findViewById(R.id.food_item_icon);
            foodItemName = itemView.findViewById(R.id.food_item_name);
        }

        public void bind(FoodItem foodItem) {

            try {
                URL url = new URL(foodItem.getImageURL());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                foodItemIcon.setImageBitmap(bmp);
            } catch (MalformedURLException e) {
                //Empty catch
            } catch (IOException e) {
                //Empty Catch
            }

        }

//        private class DownloadImageTask extends concurrent {
//            ImageView bmImage;
//
//            public DownloadImageTask(ImageView bmImage) {
//                this.bmImage = bmImage;
//            }
//
//            protected Bitmap doInBackground(String... urls) {
//                String urldisplay = urls[0];
//                Bitmap mIcon11 = null;
//                try {
//                    InputStream in = new java.net.URL(urldisplay).openStream();
//                    mIcon11 = BitmapFactory.decodeStream(in);
//                } catch (Exception e) {
//                    Log.e("Error", e.getMessage());
//                    e.printStackTrace();
//                }
//                return mIcon11;
//            }
//
//            protected void onPostExecute(Bitmap result) {
//                bmImage.setImageBitmap(result);
//            }
//        }
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
        holder.foodItemIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.button_custom));
        new Thread(new DownloadImageThread(holder.foodItemIcon, item.getImageURL())).start();
    }

    class DownloadImageThread implements Runnable {

        ImageView imageView;
        String url;

        public DownloadImageThread(ImageView imageView, String url) {
            this.imageView = imageView;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                InputStream in = new java.net.URL(url).openStream();
                Bitmap icon = BitmapFactory.decodeStream(in);
                imageIconHandler.post(() -> {
                    imageView.setImageBitmap(icon);
                });
            } catch (Exception e) {
                imageIconHandler.post(() -> {
                    imageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.button_custom));
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }
}
