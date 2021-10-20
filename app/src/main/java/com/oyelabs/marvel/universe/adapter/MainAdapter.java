package com.oyelabs.marvel.universe.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.oyelabs.marvel.universe.CharacterDetailsActivity;
import com.oyelabs.marvel.universe.R;
import com.oyelabs.marvel.universe.model.CharacterModel;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyHolder> {

    Context context;
    List<CharacterModel> characterModelList;

    public MainAdapter(Context context, List<CharacterModel> characterModelList) {
        this.context = context;
        this.characterModelList = characterModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String characterName = characterModelList.get(position).getName();
        String image = characterModelList.get(position).getImage();
        String description = characterModelList.get(position).getDescription();
        String id = characterModelList.get(position).getId();
        String moreDetails = characterModelList.get(position).getMoreDetails();
        try {
            holder.textView.setText(characterName);
            Glide.with(context)
                    .load(image)
                    .into(holder.imageView);

            holder.materialCardView.setOnClickListener(V->{
                Intent intent = new Intent(context, CharacterDetailsActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", characterName);
                intent.putExtra("description", description);
                intent.putExtra("image", image);
                intent.putExtra("more", moreDetails);
                context.startActivity(intent);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return characterModelList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        MaterialCardView materialCardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.character_image1);
            textView = itemView.findViewById(R.id.character_name1);
            materialCardView = itemView.findViewById(R.id.character_card);
        }
    }
}
