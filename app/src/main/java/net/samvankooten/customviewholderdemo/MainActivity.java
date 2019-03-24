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
