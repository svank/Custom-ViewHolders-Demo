/*
 * Copyright 2019 Sam Van Kooten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.samvankooten.customviewholderdemo;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;
    
    private OnClickListener clickListener;
    private final List<Uri> items;
    
    interface ViewHolderWithImage {
        ImageView getImageView();
    }
    
    public class ImageViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, ViewHolderWithImage {
        final ImageView imageView;
        
        ImageViewHolder(FrameLayout v) {
            super(v);
            imageView = v.findViewById(R.id.image_view);
            imageView.setOnClickListener(this);
            v.findViewById(R.id.play_icon).setVisibility(View.GONE);
        }
        
        @Override
        public void onClick(View view) {
            clickListener.onClick(getAdapterPosition(), imageView);
        }
        
        @Override
        public ImageView getImageView() { return imageView; }
    }
    
    public class VideoViewHolder extends  RecyclerView.ViewHolder
            implements View.OnClickListener, ViewHolderWithImage {
        final ImageView imageView;
        
        VideoViewHolder(FrameLayout v) {
            super(v);
            imageView = v.findViewById(R.id.image_view);
            imageView.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View view) {
            clickListener.onClick(getAdapterPosition(), imageView);
        }
    
        @Override
        public ImageView getImageView() { return imageView; }
    }
    
    public interface OnClickListener {
        void onClick(int position, ImageView view);
    }
    
    MainActivityAdapter(List<Uri> uris, OnClickListener clickListener) {
        this.items = uris;
        this.clickListener = clickListener;
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout fl = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        switch (viewType) {
            case TYPE_IMAGE:
                return new ImageViewHolder(fl);
            case TYPE_VIDEO:
                return new VideoViewHolder(fl);
            default:
                return null;
        }
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView iv = ((ViewHolderWithImage) holder).getImageView();
        Glide.with(iv.getContext()).load(getItem(position)).into(iv);
    }
    
    private Uri getItem(int position) {
        return items.get(position);
    }
    
    @Override
    public int getItemCount() {
        return items.size();
    }
    
    @Override
    public int getItemViewType(int position) {
        String item = getItem(position).toString();
        if (item.contains("video"))
            return TYPE_VIDEO;
        return TYPE_IMAGE;
    }
}
