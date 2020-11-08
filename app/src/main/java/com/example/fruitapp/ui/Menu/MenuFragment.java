package com.example.fruitapp.ui.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitapp.ItemClickListener;
import com.example.fruitapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MenuFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference category;
    private RecyclerView menuRecycler;
   private FirebaseRecyclerAdapter<CategoryModel, MenuViewHolder> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MenuViewModel menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        // final TextView textView = root.findViewById(R.id.text_home);
        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        database = FirebaseDatabase.getInstance();
        category = database.getReference("categories");


        /////load menu image & name
        menuRecycler = root.findViewById(R.id.recycler_menu);
        menuRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        menuRecycler.setLayoutManager(linearLayoutManager);

        loadMenuItem();


        return root;
    }

    private void loadMenuItem() {

        FirebaseRecyclerOptions<CategoryModel> options =
                new FirebaseRecyclerOptions.Builder<CategoryModel>()
                        .setQuery(category, CategoryModel.class)
                        .build();

      adapter = new FirebaseRecyclerAdapter<CategoryModel, MenuViewHolder>(options) {
            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_item, parent, false);

                return new MenuViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull CategoryModel model) {
                holder.txtMenuName.setText(model.getName());

                //  Picasso.with(getBaseContext().load(model.getImage())).into(holder.imgMenuImage);
                Picasso.get().load(model.getImage()).into(holder.imgMenuImage);

                CategoryModel clickedItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                      //  Toast.makeText(getContext(), "" + clickedItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent itemListIntent=new Intent(getContext(), FruitAndRecipeListActivity.class );
                       itemListIntent.putExtra("CategoryID", adapter.getRef(position).getKey());
                        startActivity(itemListIntent);


                    }
                });
            }
        };
        adapter.startListening();
        menuRecycler.setAdapter(adapter);
    }
}