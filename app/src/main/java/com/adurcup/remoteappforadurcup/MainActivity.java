package com.adurcup.remoteappforadurcup;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import org.apache.commons.io.IOUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button Up, Right, Left, Down, Select, Home, Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Up = (Button) findViewById(R.id.up);
        Right = (Button) findViewById(R.id.right);
        Left = (Button) findViewById(R.id.left);
        Down = (Button) findViewById(R.id.down);
        Select = (Button) findViewById(R.id.select);
        Home = (Button) findViewById(R.id.home);
        Back = (Button) findViewById(R.id.back);

        Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("soup");
            }
        });
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("soup");
            }
        });
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("home");
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("home");
            }
        });
        Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("pizza");
            }
        });
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("pizza");
            }
        });
    }
    public class GcmSender extends AsyncTask<String, Void, Integer> {

        public static final String API_KEY = "AIzaSyCy5V5pHG30ZvHASDL5rNmF0fPe9H-XBTk";
        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                // Prepare JSON containing the GCM message content. What to send and where to send.
                JSONObject jGcmData = new JSONObject();
                JSONObject jData = new JSONObject();
                try {
                    jData.put("title","Test notification");
                    jData.put("message", params[0]);
                    // Where to send GCM message.
                    jGcmData.put("to", "/topics/global");
                    // What to send in GCM message.
                    jGcmData.put("data", jData);
                    jGcmData.put("collapse_key", "awesome_update");
                    jGcmData.put("priority", "high");
                    jGcmData.put("delay_while_idle", false);
                } catch (JSONException e){
                    e.printStackTrace();
                }

                // Create connection to send GCM Message request.
                URL url = new URL("https://android.googleapis.com/gcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization", "key=" + API_KEY);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                // Send GCM message content.
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(jGcmData.toString().getBytes());

                // Read GCM response.
                InputStream inputStream = conn.getInputStream();
                resp = IOUtils.toString(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }
        @Override
        protected void onPostExecute(Integer result) {
            if (resp!=null) {
                Toast.makeText(getApplicationContext(), "Response Sent", Toast.LENGTH_SHORT).show();
            }
        }
        String resp;

    }
}
