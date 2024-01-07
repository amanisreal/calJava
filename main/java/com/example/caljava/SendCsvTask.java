package com.example.caljava;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendCsvTask extends AsyncTask<String, Void, Boolean> {

    private static final String SERVER_URL = "http://34.172.14.130:8000/api/file/upload/aman/";
    private static final String LINE_END = "\r\n";
    private static final String TWO_HYPHENS = "--";
    private static final String BOUNDARY = "*****";

    @Override
    protected Boolean doInBackground(String... filePaths) {
        if (filePaths.length == 0) {
            return false; // No file path provided
        }

        String csvFilePath = filePaths[0];

        try {
            // Get the CSV file from the provided path
            File csvFile = new File(csvFilePath);

            // Create connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(SERVER_URL).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

            // Create output stream to write data
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            // Write file data
            dos.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
            dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + csvFile.getName() + "\"" + LINE_END);
            dos.writeBytes(LINE_END);

            FileInputStream fileInputStream = new FileInputStream(csvFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();

            dos.writeBytes(LINE_END);
            dos.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END);

            // Get the server response code
            int responseCode = httpURLConnection.getResponseCode();

            // Read the server response
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            // Close streams and connections
            fileInputStream.close();
            dos.flush();
            dos.close();
            bufferedReader.close();

            // Check if the file was successfully uploaded (you may need to customize this condition)
            Log.i("msg" , String.valueOf(HttpURLConnection.HTTP_OK));
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // This method is called on the UI thread after doInBackground finishes
        if (result) {
            // Task was successful
           // Toast.makeText(this, "s", Toast.LENGTH_SHORT).show();
        } else {
            // Task failed
        }
    }
}