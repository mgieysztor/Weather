package com.sdacademy.gieysztor.michal.weather;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.test.espresso.core.deps.guava.net.MediaType;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncTask<String, Integer, String> execute = DownLoadWeather.execute();

//        startService(new Intent(this, MyService.class));
//        stopService(new Intent(this, MyService.class));


    }

    private void getCurrentWeather(String city) throws IOException, JSONException {
        JSONObject json = new JSONObject(sendPost());
        Log.i("JSON-weather", json.toString());

//        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
//        Intent intent = new Intent();
//        intent.setAction("com.example.action.CURRENT_WEATHER_RESPONSE");
//        intent.putExtra("com.example.extra.SUCCESS", true);
//        intent.putExtra("com.example.extra.TEMPERATURE", jsonObject.optDouble("temp"));
//        ...
//
//        broadcastManager.sendBroadcast(intent);
    }

    public class DownLoadWeather extends AsyncTask<String, Integer, String> {
        String result = null;

        @Override
        protected String doInBackground(String... params) {

            try {
                result = sendPost();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonString = null;

            try {
                jsonString = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(result);
        }
    }


    private String sendPost() throws IOException {


        okhttp3.MediaType jsonMediaType = okhttp3.MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String base_url = "http://api.openweathermap.org/data/2.5/weather?";
        String city_url = "q=Wroclaw,pl";
        String lang_url = "lang=pl";
        String units_url = "units=metric";
        String appId_url = "appid=8ba00e1208dbb514da4b9df230d82bf1";
        String url = base_url
                + city_url + "&"
                + lang_url + "&"
                + units_url + "&"
                + appId_url + "&";

        String json = "null";

        RequestBody body = RequestBody.create(jsonMediaType, json);

        Request.Builder builder = new Request.Builder();
//        builder.addHeader("Authorization", "Bearer dDMgJ7xRgDAAAAAAAAAAEqcYA8eVmS_U6gKpcFFNM8SroZZe69BgNM36ABaE3Ykg");
        builder.addHeader("Content-Type", "application/json");
        builder.url(url);
        builder.post(body);


        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response.body().string();

    }


}

//public class MyIntentService extends IntentService {
//    public MyIntentService() {
//        super("MyIntentService");
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//
//    }
//}
