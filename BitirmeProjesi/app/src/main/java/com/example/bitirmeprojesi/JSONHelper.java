package com.example.bitirmeprojesi;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONHelper<T>
{
    public static class JSONDictionary
    {
        private static Map<DataTables, String> jsonFileNames = new HashMap<>();

        private static final String postFix = "Data";

        static
        {
            for (DataTables value : DataTables.values())
            {
                jsonFileNames.put(value, value.toString() + postFix);
            }
        }

        public static String toString(DataTables dataTable)
        {
            return jsonFileNames.get(dataTable);
        }
    }

    private final String FILE_NAME;

    public JSONHelper(String fileName)
    {
        FILE_NAME = fileName;
    }

    public void writeJSON(Context context, Class<T> type, List<T> dataList)
    {
        JSONArray jsonArray = new JSONArray();

        for (T data : dataList)
        {
            JSONObject jsonObject;

            try
            {
                jsonObject = new JSONObject(new Gson().toJson(data));
                jsonArray.put(jsonObject);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            File file = new File(context.getFilesDir(), FILE_NAME);
            if (!file.exists())
            {
                file.createNewFile();
            }

            FileOutputStream outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(jsonArray.toString().getBytes());
            outputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void writeJSON(Context context, Class<T> type, T data)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(data));

            File file = new File(context.getFilesDir(), FILE_NAME);

            if (!file.exists())
            {
                file.createNewFile();
            }

            FileOutputStream outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }



    public List<T> readMultipleDataJSON(Context context, Class<T> type)
    {
        List<T> dataList = new ArrayList<>();

        try
        {
            FileInputStream inputStream = context.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }

            inputStream.close();
            String json = stringBuilder.toString();
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                T data = new Gson().fromJson(jsonObject.toString(), type);
                dataList.add(data);
            }

        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

        return dataList;
    }

    public T readSingleDataJSON(Context context, Class<T> type)
    {
        T data = null;

        try
        {
            FileInputStream inputStream = context.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }

            inputStream.close();
            String json = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(json);

            Gson gson = new Gson();
            data = gson.fromJson(jsonObject.toString(), type);

        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

        return data;
    }


    public boolean doesFileExist(Context context)
    {
        File file = context.getFileStreamPath(FILE_NAME);
        return file.exists();
    }
}

