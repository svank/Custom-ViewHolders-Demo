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
 import android.os.Bundle;
 import android.widget.ImageView;

 import com.bumptech.glide.Glide;
 import com.stfalcon.imageviewer.StfalconImageViewer;

 import java.util.ArrayList;
 import java.util.List;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.GridLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 public class MainActivity extends AppCompatActivity {
    
    private RecyclerView mainView;
    private StfalconImageViewer viewer;
    private List<Uri> items;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainView = findViewById(R.id.main_view);
        setupRecyclerView();
    }
    
    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mainView.setLayoutManager(layoutManager);
        
        items = new ArrayList<>(4);
        items.add(Uri.parse("android.resource://" + getPackageName() + "/raw/image1"));
        items.add(Uri.parse("android.resource://" + getPackageName() + "/raw/video1"));
        items.add(Uri.parse("android.resource://" + getPackageName() + "/raw/video2"));
        items.add(Uri.parse("android.resource://" + getPackageName() + "/raw/image2"));
        
        MainActivityAdapter adapter = new MainActivityAdapter(items, this::startPopupViewer);
        mainView.setAdapter(adapter);
    }
    
    private void startPopupViewer(int position, ImageView view) {
        viewer = new StfalconImageViewer.Builder<>(this, items,
                // The image loader method is used for preparing images in the transition
                // animation, and so should be provided even if not used in the custom ViewHolder
                this::loadImage,
                CustomViewHolder::buildViewHolder)
                .withStartPosition(position)
                .withTransitionFrom(view)
                .withHiddenStatusBar(false)
                .withImageChangeListener(this::updateTransitionImage)
                .show();
    }
    
    private void loadImage(ImageView imageView, Uri uri) {
        Glide.with(this).load(uri).into(imageView);
    }
    
    private void updateTransitionImage(int position) {
        if (viewer == null)
            return;
        
        RecyclerView.ViewHolder holder = mainView.findViewHolderForAdapterPosition(position);
        if (holder instanceof MainActivityAdapter.ViewHolderWithImage) {
            viewer.updateTransitionImage(((MainActivityAdapter.ViewHolderWithImage) holder).getImageView());
        }
    }
}
