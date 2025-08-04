package com.example.fusmobilni.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AdapterUtils {
    public static Uri convertToUrisFromBase64(Context context, String base64String) throws IOException {


        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

        File tempFile = File.createTempFile("image_", ".jpg", context.getCacheDir());
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(decodedBytes);
        }

        return Uri.fromFile(tempFile);
    }
}
