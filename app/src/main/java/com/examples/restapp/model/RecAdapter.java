package com.examples.restapp.model;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.restapp.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.viewHolder> {
    List<PostModel> listItem = new ArrayList<>();
    OnClickItemListener listener;
    Context context;

    public RecAdapter(Context context, OnClickItemListener listener) {

        this.context = context;
        this.listener = listener;
    }

    public void setListItem(List<PostModel> listItem) {
        this.listItem = listItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PostModel item = listItem.get(position);
        Picasso.get().load(item.getDownload_url()).fit()
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.VISIBLE);
                        Sprite doubleBounce = new DoubleBounce();
                        holder.progressBar.setIndeterminateDrawable(doubleBounce);
                    }
                });
        holder.itemView.setOnClickListener(view -> {
            listener.OnClickItem(item.getDownload_url());
        });


    }

    public interface OnClickItemListener {
        void OnClickItem(String imgURl);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    static class viewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ProgressBar progressBar;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.spin_kit);



        }
    }
}
