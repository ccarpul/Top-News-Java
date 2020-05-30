package com.example.app2241.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2241.R;
import com.example.app2241.model.Elements;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.Holder> {

    private ArrayList<Elements> arrayListElements = new ArrayList<>();
    private OnClickItemSelected onClickItemSelected;

    public void AdapterRecycler(ArrayList<Elements> arrayListElements) {
        this.arrayListElements.addAll(arrayListElements);
        notifyDataSetChanged();
    }

    public void setOnClickItemSelected(OnClickItemSelected onClickItemSelected) {
        this.onClickItemSelected = onClickItemSelected;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_structure, viewGroup, false);
        Holder holder = new Holder(v, onClickItemSelected);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {

        final Elements elements = arrayListElements.get(position);
        holder.bindHolder(elements);
    }

    @Override
    public int getItemCount() {
        if (arrayListElements != null) {
            return arrayListElements.size();
        } else {
            return 0;
        }
    }

    public interface OnClickItemSelected {
        void Onclick(String url);
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewId, textViewName, textViewAuthor, textViewTitle, textViewDescription,
                textViewPublishedAt, textViewContent;
        private ImageView imageViewUrl;
        private String url;
        private OnClickItemSelected onClickItemSelected;

        public Holder(View itemView, OnClickItemSelected onClickItemSelected) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.id);
            textViewName = itemView.findViewById(R.id.name);
            textViewAuthor = itemView.findViewById(R.id.author);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewDescription = itemView.findViewById(R.id.description);
            imageViewUrl = itemView.findViewById(R.id.urlToImage);
            textViewPublishedAt = itemView.findViewById(R.id.publishedAt);
            textViewContent = itemView.findViewById(R.id.content);
            this.onClickItemSelected = onClickItemSelected;

            itemView.setOnClickListener(this);
        }

        public void bindHolder(Elements item) {

            url = item.getUrl();
            textViewId.setText(itemView.getResources().getString(R.string.id) + item.getId());
            textViewName.setText(itemView.getResources().getString(R.string.name) + item.getName());
            textViewAuthor.setText(itemView.getResources().getString(R.string.author) + item.getAuthor());
            textViewTitle.setText(item.getTitle());
            textViewDescription.setText(item.getDescription());

            if (!item.getUrlToImage().equals("")) {
                Picasso.with(itemView.getContext()).load(item.getUrlToImage())
                        .placeholder(R.drawable.diarynews_image)
                        .resize(360, 280)
                        .centerCrop()
                        .into(imageViewUrl);
            } else {
                imageViewUrl.setImageResource(R.drawable.ic_launcher_background);
            }

            textViewPublishedAt.setText(itemView.getResources().getString(R.string.publishedAt) + item.getPublishedAt());
            textViewContent.setText(item.getContent());
        }

        @Override
        public void onClick(View v) {
            onClickItemSelected.Onclick(url);
        }
    }
}
