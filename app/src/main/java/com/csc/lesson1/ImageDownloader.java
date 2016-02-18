package com.csc.lesson1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    private final ImageView _target;
    private final LruCache<String, Bitmap> _cardCache;
    private static final String TAG = "ImageDownloader";
    private String _targetUrl;

    ImageDownloader(ImageView target, LruCache<String, Bitmap> cardCache) {
        _target = target;
        _cardCache = cardCache;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        String targetUrl = params[0];
        _targetUrl = targetUrl;
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
        _cardCache.put(_targetUrl, result);
    }
}
