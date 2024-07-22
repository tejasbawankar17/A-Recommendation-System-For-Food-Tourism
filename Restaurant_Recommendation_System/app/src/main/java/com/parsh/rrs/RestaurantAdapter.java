package com.parsh.rrs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private List<Restaurant> restaurantList;
    private Context context;

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.txtName.setText(restaurant.getName());
        holder.txtCategory.setText(restaurant.getCategoryName());
        holder.txtRating.setText(String.valueOf(restaurant.getRating()));
        holder.txtDistance.setText(String.valueOf(restaurant.getDistance()));

        // Handle click event
        holder.itemView.setOnClickListener(v -> {
            // Open the Google Maps URL associated with the restaurant
            String url = restaurant.getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtCategory;
        TextView txtRating;
        TextView txtDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtDistance = itemView.findViewById(R.id.txtDistance);
        }
    }
}
