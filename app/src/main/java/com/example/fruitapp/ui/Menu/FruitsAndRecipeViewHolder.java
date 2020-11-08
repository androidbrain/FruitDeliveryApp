package com.example.fruitapp.ui.Menu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitapp.ItemClickListener;
import com.example.fruitapp.R;

public class FruitsAndRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView listItemName;
    public ImageView listItemImage;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FruitsAndRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        listItemName=itemView.findViewById(R.id.menu_name2);
        listItemImage=itemView.findViewById(R.id.menu_image2);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

}
