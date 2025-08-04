package com.example.fusmobilni.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UriConverter {

    private static final String TAG = "UriConverter";

    public static MultipartBody.Part getImageFromUri(Context context, Uri uri) throws IOException {
        File file = getFileFromUri(context, uri);
        if (file != null) {
            RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
            return MultipartBody.Part.createFormData("images", file.getName(), requestBody);
        }
        throw new IOException("Error getting file from URI: " + uri);
    }

    public static List<MultipartBody.Part> getImagesFromUris(Context context, List<Uri> uris) throws IOException {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (Uri uri : uris) {
            parts.add(getImageFromUri(context, uri));
        }
        return parts;
    }

    public static File getFileFromUri(Context context, Uri uri) throws IOException {
        String fileName = getFileName(context, uri);
        if (fileName == null) {
            fileName = "temp_image_" + System.currentTimeMillis() + ".jpg";
        }

        File file = new File(context.getCacheDir(), fileName);

        if ("content".equals(uri.getScheme())) {
            try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
                 FileOutputStream outputStream = new FileOutputStream(file)) {

                if (inputStream == null) {
                    throw new IOException("Unable to open InputStream for URI: " + uri);
                }

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } else if ("file".equals(uri.getScheme())) {
            File sourceFile = new File(uri.getPath());
            if (sourceFile.exists()) {
                return sourceFile;
            } else {
                throw new IOException("File not found for URI: " + uri);
            }
        } else {
            throw new IllegalArgumentException("Unsupported URI scheme: " + uri.getScheme());
        }

        return file;
    }

    private static String getFileName(Context context, Uri uri) {
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    return cursor.getString(nameIndex);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error getting file name from content URI", e);
            }
        } else if ("file".equals(uri.getScheme())) {
            return new File(uri.getPath()).getName();
        }
        return null;
    }

    public static Uri convertToUriFromBase64(Context context, String base64String) throws IOException {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

        File tempFile = File.createTempFile("image_", ".jpg", context.getCacheDir());
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(decodedBytes);
        }

        return Uri.fromFile(tempFile);
    }
}
