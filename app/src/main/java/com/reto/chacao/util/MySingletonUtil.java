package com.reto.chacao.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Created by Eduardo Luttinger on 19/05/2015.
 * <p/>
 * Agregar aqui todas los objetos cuyo comportamiento sea de ambito de aplicacion
 * <p/>
 * <p/>
 * ------------------------------------------------------------------
 * Volley:
 * <p/>
 * Acceso al RequestQueue desde una Actividad Ej:
 * MySingletonUtil.getInstance(this.getApplicationContext()).getRequestQueue();
 * <p/>
 * Agregado de request al Queue Ej:
 * <p/>
 * MySingletonUtil.getInstance(this).addToRequestQueue(stringRequest);
 * ------------------------------------------------------------------
 */
public class MySingletonUtil {

    /**
     *
     */
    private static MySingletonUtil sMySingletonUtil;

    /**
     *
     */
    private RequestQueue mRequestQueue;

    /**
     *
     */
    private static Context mContext;

    /**
     *
     */
    private static Gson GSON;

    /**
     *
     */
    private ImageLoader mImageLoader;

    private MySingletonUtil(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        GSON = new Gson();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * @param context
     * @return
     */
    public static synchronized MySingletonUtil getInstance(Context context) {
        if (sMySingletonUtil == null) {
            sMySingletonUtil = new MySingletonUtil(context);
        }
        return sMySingletonUtil;
    }

    /**
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public Gson getGSON() {
        if (GSON == null) {
            GSON = new Gson();
        }
        return GSON;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
