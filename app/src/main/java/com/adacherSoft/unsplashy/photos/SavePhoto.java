package com.adacherSoft.unsplashy.photos;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.adacherSoft.unsplashy.models.Unsplash;

/**
 * Created by adacher on 12-07-17.
 */

public class SavePhoto {


    public void downloadExample(Unsplash unsplash, Context context) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(unsplash.getUrls().getFull()));
        String description = unsplash.getDescription();
        request.setDescription((description != null) ? description : "El autor no entregó descripción");
        request.setTitle(unsplash.getUser().getUsername());
// in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES + "/unsplashy", String.valueOf(System.currentTimeMillis()) + ".jpg");

// get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }


}
