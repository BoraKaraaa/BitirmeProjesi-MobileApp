package com.example.bitirmeprojesi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ChatGPTActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton, backButton;

    private HashSet<String> selectedLabels = new HashSet<>();
    private int defaultLabelColor, selectedLabelColor;

    private String labelString = null;
    String activityTitle;

    List<Message> messageList;
    MessageAdapter messageAdapter;

    ChatGPTHelper chatGPTHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_gpt);

        chatGPTHelper = new ChatGPTHelper();

        messageList = new ArrayList<>();

        activityTitle = getIntent().getStringExtra("activity_title");

        defaultLabelColor = ContextCompat.getColor(this, R.color.white);
        selectedLabelColor = ContextCompat.getColor(this, R.color.gray);

        initViews();
        setWelcomeTextView();

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        initLabelListeners();

        setSendButtonListener();
        setBackButtonListener();
    }

    private void initViews()
    {
        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);

        sendButton = findViewById(R.id.send_btn);
        backButton = findViewById(R.id.back_button);
    }

    private void setWelcomeTextView()
    {
        if (activityTitle != null && activityTitle.equals("Recipe"))
        {
            welcomeTextView.setText("Create Custom Recipe");
        }
        else if(activityTitle != null && activityTitle.equals("Calendar"))
        {
            welcomeTextView.setText("Create Custom Program");
        }
    }

    private void setBackButtonListener()
    {
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void initLabelListeners()
    {
        LinearLayout labelContainer = findViewById(R.id.label_area_linear_layout);

        for (int i = 0; i < labelContainer.getChildCount(); i++)
        {
            View cardView = labelContainer.getChildAt(i);

            if (cardView instanceof CardView)
            {
                cardView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        TextView labelTextView = (TextView) ((CardView) v).getChildAt(0);
                        String labelText = labelTextView.getText().toString();

                        if (selectedLabels.contains(labelText))
                        {
                            selectedLabels.remove(labelText);
                            labelTextView.setBackgroundColor(defaultLabelColor);
                        }
                        else
                        {
                            selectedLabels.add(labelText);
                            labelTextView.setBackgroundColor(selectedLabelColor);
                        }

                        updateSelectedLabels();
                    }
                });
            }
        }
    }

    private void updateSelectedLabels()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (String label : selectedLabels)
        {
            stringBuilder.append(label);
            stringBuilder.append(" ");
        }

        labelString = stringBuilder.toString();
    }

    private void setSendButtonListener()
    {
        sendButton.setOnClickListener((v)->
        {
            String question = messageEditText.getText().toString().trim();

            addToChat(question, Message.SENT_BY_ME);
            messageEditText.setText("");

            messageList.add(new Message("Typing...", Message.SENT_BY_BOT));

            chatGPTHelper.callChatGPT(question, new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    addResponse(e.getMessage());
                }
            }, new OnSuccessListener<String>()
            {
                @Override
                public void onSuccess(String response)
                {
                    addResponse(response);
                }
            });

            welcomeTextView.setVisibility(View.GONE);
        });
    }

    private void addToChat(String message, String sentBy)
    {
        this.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    private void addResponse(String response)
    {
        messageList.remove(messageList.size()-1);
        addToChat(response, Message.SENT_BY_BOT);
    }
}