package com.example.bitirmeprojesi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;


public class CalendarFragment extends Fragment
{
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        initViews(view);

        setDataText();

        return view;
    }

    private void initViews(View view)
    {
        textView = view.findViewById(R.id.data_test);
    }

    private void setDataText()
    {
        GoogleMySQLDataBase sqlDataBase = new GoogleMySQLDataBase(getContext());

        UserData userData = new UserData("30", "BoraTest", "test@gmail.com",
                "B", 10, 180, 80, "bad", 1, 5, 8);

        sqlDataBase.delete(DataTables.User, UserData.class, 13);

        /*
        sqlDataBase.getById(DataTables.User, UserData.class, 13, new DataBase.SingleDataCallback<UserData>() {
            @Override
            public void onResponse(UserData data)
            {
                textView.setText(data.toString());
            }
        });
        */
    }
}