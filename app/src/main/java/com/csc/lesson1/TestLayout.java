package com.csc.lesson1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.widget.ImageView;

public class TestLayout extends AppCompatActivity {
    private LruCache<String, Bitmap> cardCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        cardCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        setContentView(R.layout.activity_test_layout);
        getBusinessCardPhoto();
    }

    private void getBusinessCardPhoto() {
        ImageView imageView = (ImageView) findViewById(R.id.businessImage);
        String token = getString(R.string.BusinessCardURL);
        if (cardCache.get(token) != null) {
            imageView.setImageBitmap(cardCache.get(token));
        } else {
            ImageDownloader downloadTask = new ImageDownloader(imageView, cardCache);
            downloadTask.execute(token);
        }
    }
}
