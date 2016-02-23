package com.adurcup.remoteappforadurcup;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import org.apache.commons.io.IOUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageButton Up, Right, Left, Down, Select, Home, Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Up = (ImageButton) findViewById(R.id.up);
        Right = (ImageButton) findViewById(R.id.right);
        Left = (ImageButton) findViewById(R.id.left);
        Down = (ImageButton) findViewById(R.id.down);
        Select = (ImageButton) findViewById(R.id.select);
        Home = (ImageButton) findViewById(R.id.home);
        Back = (ImageButton) findViewById(R.id.back);

        Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("up");
            }
        });
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("left");
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
                new GcmSender().execute("blank");
            }
        });
        Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("down");
            }
        });
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("right");
            }
        });
        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmSender().execute("select");
            }
        });
    }

    public class GcmSender extends AsyncTask<String, Void, Integer> {

        public static final String API_KEY = "AIzaSyCy5V5pHG30ZvHASDL5rNmF0fPe9H-XBTk";
        public final String REGISTRATION_ID = "dzF4qA6mRPE:APA91bFqpz3_RO-rfwVrFZe2geZPPtKFjiX93AUN1aKUUjsfmbQ7LLfMk_hZYgshGKP2fVRbiy2w-G_dKckjW6KqbjT5eMT2MY8dDVr4uQmZVlCLp_CO-BLr8xqluauJ0qYpetYu9R37";
        @Override
        protected void onPreExecute() {
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
                    jGcmData.put("to", REGISTRATION_ID);
                    // What to send in GCM message.
                    jGcmData.put("data", jData);
                    jGcmData.put("collapse_key", "awesome_update");
                    jGcmData.put("restricted_package_name","gcm.play.android.samples.com.gcmquickstart");
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
                String resp = IOUtils.toString(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }
        @Override
        protected void onPostExecute(Integer result) {
            Toast.makeText(getApplicationContext(), "Response Sent", Toast.LENGTH_SHORT).show();
        }

    }
}
