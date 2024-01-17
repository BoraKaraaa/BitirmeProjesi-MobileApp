package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatGPTHelper
{
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();

    public void callChatGPT(String question, OnFailureListener onFailureListener,
                            OnSuccessListener<String> onSuccessListener)
    {
        JSONObject jsonBody = new JSONObject();

        try
        {
            jsonBody.put("model", "gpt-3.5-turbo-instruct");
            jsonBody.put("prompt", question);
            jsonBody.put("max_tokens", 1000);
            jsonBody.put("temperature", 0);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {
                onFailureListener.onFailure(new Exception("Failed to load response " + e.getMessage()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");

                        String result = jsonArray.getJSONObject(0).getString("text");

                        onSuccessListener.onSuccess(result.trim());
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    onFailureListener.onFailure(new Exception("Failed to load response " + response.code()));
                }
            }
        });
    }
}
