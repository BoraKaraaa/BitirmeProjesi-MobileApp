package com.example.bitirmeprojesi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class HomeFragment extends Fragment
{
    FrameLayout[] dayFrameLayouts = new FrameLayout[7];
    ImageView[] dayPointImage = new ImageView[7];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        initViews(view);

        setDayFramesListener();

        return view;
    }

    private void initViews(View view)
    {
        dayFrameLayouts[0] = view.findViewById(R.id.day_1_frame);
        dayFrameLayouts[1] = view.findViewById(R.id.day_2_frame);
        dayFrameLayouts[2] = view.findViewById(R.id.day_3_frame);
        dayFrameLayouts[3] = view.findViewById(R.id.day_4_frame);
        dayFrameLayouts[4] = view.findViewById(R.id.day_5_frame);
        dayFrameLayouts[5] = view.findViewById(R.id.day_6_frame);
        dayFrameLayouts[6] = view.findViewById(R.id.day_7_frame);

        dayPointImage[0] = view.findViewById(R.id.day_point_image_1);
        dayPointImage[1] = view.findViewById(R.id.day_point_image_2);
        dayPointImage[2] = view.findViewById(R.id.day_point_image_3);
        dayPointImage[3] = view.findViewById(R.id.day_point_image_4);
        dayPointImage[4] = view.findViewById(R.id.day_point_image_5);
        dayPointImage[5] = view.findViewById(R.id.day_point_image_6);
        dayPointImage[6] = view.findViewById(R.id.day_point_image_7);
    }

    private void setDayFramesListener()
    {
        View.OnClickListener dayFrameListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                for (int i = 0; i < 7; i++)
                {
                    dayFrameLayouts[i].setBackground(null);

                    if (dayFrameLayouts[i].getId() == v.getId())
                    {
                        v.setBackgroundResource(R.drawable.selected_day_background);
                    }
                }
            }
        };

        for (int i = 0; i < 7; i++)
        {
            dayFrameLayouts[i].setOnClickListener(dayFrameListener);
        }
    }
}