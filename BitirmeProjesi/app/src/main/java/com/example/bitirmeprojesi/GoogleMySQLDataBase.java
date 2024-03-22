package com.example.bitirmeprojesi;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class GoogleMySQLDataBase extends DataBase
{
    private final String baseURL = "https://bitirmeprojesi-firebase.lm.r.appspot.com/handler.php";
    private final Context context;

    public GoogleMySQLDataBase(Context context)
    {
        this.context = context;
    }

    @Override
    public <T> T getById(DataTables dataTables, Class<T> type, String id, final SingleDataCallback<T> callback)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseURL + "?action=db_manager&operation=get_by_id&table_name=" + dataTables.getTableName() + "&id=" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    Log.d("Response", response);

                    Gson gson = new Gson();
                    T data = gson.fromJson(response, type);
                    callback.onResponse(data);
                },
                error ->
                {
                    Log.e("Error Response", error.toString());
                    callback.onError("Error: " + error.toString());
                });

        queue.add(stringRequest);

        return null;
    }

    @Override
    public <T> List<T> getTable(DataTables dataTables, Class<T> type, final MultipleDataCallback<T> callback)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseURL + "?action=db_manager&operation=get_all&table_name=" + dataTables.getTableName();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    Log.d("Response", response);
                    Gson gson = new Gson();
                    Type classType = TypeToken.getParameterized(List.class, type).getType();
                    List<T> dataList = gson.fromJson(response, classType);
                    callback.onResponse(dataList);
                },
                error ->
                {
                    Log.e("Error Response", error.toString());
                    callback.onError("Error: " + error.toString());
                });

        queue.add(stringRequest);

        return null;
    }

    @Override
    public <T> void insert(DataTables dataTables, Class<T> type, T entity)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = baseURL + "?action=db_manager&operation=insert&table_name=" + dataTables.getTableName();

        Gson gson = new Gson();
        String json = gson.toJson(entity);

        Log.e("JSONNNNN", json);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response ->
                {
                    Log.d("Insert Response", response);
                },
                error ->
                {
                    Log.e("Error Response", error.toString());
                    error.printStackTrace();
                })

        {
            @Override
            public byte[] getBody()
            {
                return json.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public <T> void insertTable(DataTables dataTables, Class<T> type, List<T> entities)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String json = gson.toJson(entities);
        String url = baseURL + "?action=db_manager&operation=insert&table_name=" + dataTables.getTableName();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response ->
                {
                    Log.d("Insert Table Response", response);
                },
                error ->
                {
                    Log.e("Error Response", error.toString());
                })
        {
            @Override
            public byte[] getBody()
            {
                return json.getBytes();
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public <T> void update(DataTables dataTables, Class<T> type, String id, T entity)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String json = gson.toJson(entity);
        String url = baseURL + "?action=db_manager&operation=update&table_name=" + dataTables.getTableName() + "&id=" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                response ->
                {
                    Log.d("Update Response", response);
                },
                error ->
                {
                    Log.e("Error Response", error.toString());
                })
        {
            @Override
            public byte[] getBody()
            {
                return json.getBytes();
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public <T> void delete(DataTables dataTables, Class<T> type, String id)
    {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = baseURL + "?action=db_manager&operation=delete&table_name=" + dataTables.getTableName() + "&id=" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                response ->
                {
                    Log.d("Delete Response", response);
                },
                error ->
                {
                    Log.e("Error Response", error.toString());
                });

        queue.add(stringRequest);
    }
}
