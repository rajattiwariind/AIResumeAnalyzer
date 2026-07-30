package com.example.airesumeanalyzer.networks;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GeminiService {

    public interface GeminiCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    private static final String API_KEY = "AIzaSyCfjDyLZJN13rmyEkPXw5lfSHpHTy-paAY";

    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    // Configured client with extended timeouts to handle large resume strings processing
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    public static void analyzeResume(String resumeText, final GeminiCallback callback) {
        try {
            // 1. Create the text part
            JSONObject part = new JSONObject();
            part.put("text", "Analyze this resume and suggest improvements:\n\n" + resumeText);

            // 2. Put part inside the parts array
            JSONArray parts = new JSONArray();
            parts.put(part);

            // 3. Create the content object AND add the "user" role
            JSONObject content = new JSONObject();
            content.put("role", "user");
            content.put("parts", parts);

            // 4. Put content inside the contents array
            JSONArray contents = new JSONArray();
            contents.put(content);

            // 5. Wrap inside the root body object
            JSONObject body = new JSONObject();
            body.put("contents", contents);

            RequestBody requestBody = RequestBody.create(
                    body.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body().string());
                    } else {
                        String errorBody = response.body() != null ? response.body().string() : "Empty body";
                        callback.onFailure("Server Error: " + response.code() + " - " + errorBody);
                    }
                }
            });

        } catch (Exception e) {
            callback.onFailure(e.getMessage());
        }
    }
}