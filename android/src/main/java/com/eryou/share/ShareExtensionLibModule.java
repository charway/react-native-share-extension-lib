package com.eryou.share;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import android.graphics.Bitmap;
import java.io.InputStream;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShareExtensionLibModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    private File tempFolder;

    public ShareExtensionLibModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ShareExtensionLib";
    }

    @ReactMethod
    public void close() {
        getCurrentActivity().finish();
    }

    @ReactMethod
    public void openURL() {
        // Intent intent = new Intent();
        // intent.setComponent(new ComponentName("所要打开的程序包名", "所要打开的程序包名+主运行类名"));
        // intent.setAction(Intent.ACTION_VIEW);
        // startActivity(intent);
        // getCurrentActivity().finish();
    }

    @ReactMethod
    public void data(Promise promise) {
        promise.resolve(processIntent());
    }

    public WritableMap processIntent() {
        WritableMap map = Arguments.createMap();

        String text = "";
        String type = "";
        String action = "";

        Activity currentActivity = getCurrentActivity();

        if (currentActivity != null) {
            this.tempFolder = new File(currentActivity.getCacheDir(), "rcShare");
            Intent intent = currentActivity.getIntent();
            action = intent.getAction();
            type = intent.getType();
            if (type == null) {
                type = "";
            }

            if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type)) {
                text = intent.getStringExtra(Intent.EXTRA_TEXT);
                map.putString("value", text);
                map.putString("type", type);
            } else if (Intent.ACTION_SEND.equals(action)) {
                Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (uri != null) {
                    text = "file://" + RealPathUtil.getRealPathFromURI(currentActivity, uri);
                    map.putString("value", text);
                    if (type.equals("image/*")) {
                        type = "image/jpeg";
                    } else if (type.equals("video/*")) {
                        type = "video/mp4";
                    }
                    map.putString("type", "media");
                }
            }
        }

        return map;
    }
}
