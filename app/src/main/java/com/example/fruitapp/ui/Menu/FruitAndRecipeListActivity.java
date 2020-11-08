package com.example.fruitapp.ui.Menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FruitAndRecipeListActivity extends AppCompatActivity {

    private RecyclerView liRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference itemListRef;
    private String categoryId = "";
    private FirebaseRecyclerOptions<FruitsAndRecipeModel> options;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_and_recipe_list);

        ///firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        itemListRef = database.getReference("fruit_and_recipe");
        Log.d("MyApp", String.valueOf(itemListRef));

        ///recycler
        liRecycler = findViewById(R.id.itemListRecycler);
        liRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        liRecycler.setLayoutManager(linearLayoutManager);
        //loadListItem();

        ////get Intent From Category Activity
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryID");
        }
        if (!categoryId.isEmpty()) {
            loadListItem(categoryId);
        }



    }

    private void loadListItem(String categoryId) {

        options = new FirebaseRecyclerOptions.Builder<FruitsAndRecipeModel>()
                .setQuery(itemListRef.orderByChild("menu_id").equalTo(categoryId), FruitsAndRecipeModel.class)
                .build();

        FirebaseRecyclerAdapter<FruitsAndRecipeModel, FruitsAndRecipeViewHolder> adapter = new FirebaseRecyclerAdapter<FruitsAndRecipeModel, FruitsAndRecipeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FruitsAndRecipeViewHolder holder, int position, @NonNull FruitsAndRecipeModel model) {
                holder.listItemName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.listItemImage);


            }

            @NonNull
            @Override
            public FruitsAndRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                return new FruitsAndRecipeViewHolder(view);
            }
        };
        adapter.startListening();
        liRecycler.setAdapter(adapter);
    }
}