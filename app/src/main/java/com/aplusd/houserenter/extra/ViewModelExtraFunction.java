package com.aplusd.houserenter.extra;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.aplusd.houserenter.model.URLConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Azamat Dzhonov
 * @date 19.05.2018
 */
public class ViewModelExtraFunction extends ViewModel {

    public MutableLiveData<String> uploadImage(String str)
    {
        final MutableLiveData<String> pathMutableLiveData = new MutableLiveData<>();
        pathMutableLiveData.setValue(null);

        new UploadPhoto(str, new CallBack() {
            @Override
            public void resultCallBack(String str) {
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    pathMutableLiveData.setValue(jsonObject.getString("path"));
                }
                catch (JSONException ex)
                {

                }
            }
        }).execute();

        return pathMutableLiveData;
    }

    interface CallBack
    {
        void resultCallBack(String str);
    }

    private class UploadPhoto extends AsyncTask<Void, Void, String>
    {
        private String sourceFileUri = null;
        CallBack callBack = null;

        public UploadPhoto(String sourceFile, CallBack callBack)
        {
            this.sourceFileUri = sourceFile;
            this.callBack = callBack;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String fileName = sourceFileUri;
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            String serverResponseMessage = "";
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(URLConstants.UPLOAD_IMG);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("image", fileName);
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""
                        + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);


                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseMessage = conn.getResponseMessage();
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                serverResponseMessage =   new StreamReader().read(inputStream);

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return serverResponseMessage;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callBack.resultCallBack(s);
        }


    }

    public  class StreamReader {

        public   String read(InputStream inputStream){
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = inputStream.read();
                while(i != -1) {
                    bo.write(i);
                    i = inputStream.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }
    }
}
