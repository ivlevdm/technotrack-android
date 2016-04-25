package ru.technotrack.divlev.homework2;


import android.graphics.Bitmap;
import android.util.LruCache;

public class LruCacheBitmapStorage implements DataStorage<String, Bitmap> {

    private final int cacheSize;

    private LruCache<String, Bitmap> cache;

    public LruCacheBitmapStorage(int cacheSize){
        this.cacheSize = cacheSize;

        cache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public LruCacheBitmapStorage() {
        this((int) (Runtime.getRuntime().maxMemory() / 8));
    }

    @Override
    public void put(String key, Bitmap value) {
        if (key == null || value == null){
            return;
        }
        cache.put(key, value);
    }

    @Override
    public Bitmap get(String key) {
        if (key == null){
            return null;
        }
        return cache.get(key);
    }

    @Override
    public boolean contains(String key) {
        if (key == null){
            return false;
        }
        return cache.get(key) != null;
    }
}
