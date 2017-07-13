package com.adacherSoft.unsplashy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adacherSoft.unsplashy.R;
import com.adacherSoft.unsplashy.models.Unsplash;
import com.adacherSoft.unsplashy.photos.PhotoListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adacher on 11-07-17.
 */

public class UnsplashyAdapter extends RecyclerView.Adapter<UnsplashyAdapter.ViewHolder> {


    private List<Unsplash> unsplashList = new ArrayList<>();
    private PhotoListener listener;

    public UnsplashyAdapter(PhotoListener listener) {
        this.listener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.splashy_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Unsplash unsplash = unsplashList.get(position);

        ImageView photo = holder.unsplashPhoto;

        Picasso.with(photo.getContext()).load(unsplash.getUrls().getSmall()).centerCrop().fit().into(photo);


        //  Glide.with(photo.getContext()).load(unsplash.getUrls().getFull()).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().fitCenter().into(photo);

        holder.userTv.setText(unsplash.getUser().getUsername());


        // you have to add this to GIST

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // new SavePhoto().downloadExample(unsplashList.get(holder.getAdapterPosition()).getUrls().getFull());

                listener.photosave(unsplashList.get(holder.getAdapterPosition()));

                return true;
            }

        });


       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","click");
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return unsplashList.size();
    }


    public void update(List<Unsplash> unsplashes) {
        unsplashList.addAll(unsplashes);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView unsplashPhoto;
        private TextView userTv;

        public ViewHolder(View itemView) {
            super(itemView);

            unsplashPhoto = (ImageView) itemView.findViewById(R.id.splashPhotoIV);
            userTv = (TextView) itemView.findViewById(R.id.userTv);


        }


    }


}

