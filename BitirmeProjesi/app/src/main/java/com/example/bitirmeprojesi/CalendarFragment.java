package com.example.bitirmeprojesi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment
{
    private LinearLayout linearLayout;

    private CardView addProgramCard;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        initViews(view);

        setAddProgramCardListener();

        return view;
    }

    private void initViews(View view)
    {
        linearLayout = view.findViewById(R.id.program_layout);

        addProgramCard = view.findViewById(R.id.addProgramCard);
    }

    private void setAddProgramCardListener()
    {
        addProgramCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), ChatGPTActivity.class);

                intent.putExtra("activity_title", "Calendar");

                startActivity(intent);
            }
        });
    }
}
