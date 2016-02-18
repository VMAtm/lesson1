package com.csc.lesson1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class TestLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);
        new ImageDownloader((ImageView) findViewById(R.id.businessImage))
                .execute(getString(R.string.BusinessCardURL));
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        private final ImageView _target;
        private static final String TAG = "ImageDownloader";

        ImageDownloader(ImageView target) {
            _target = target;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String targetUrl = params[0];
            Bitmap result = null;
            try {
                URL u = new URL(targetUrl);
                InputStream s = null;
                try {
                    s = u.openStream();
                    result = BitmapFactory.decodeStream(s);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                } finally {
                    if (s != null) {
                        s.close();
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            _target.setImageBitmap(result);
        }
    }
}
