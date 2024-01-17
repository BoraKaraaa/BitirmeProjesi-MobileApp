package com.example.bitirmeprojesi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


public class ChatGPTFragment extends Fragment
{
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;

    List<Message> messageList;
    MessageAdapter messageAdapter;

    ChatGPTHelper chatGPTHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_chat_g_p_t, container, false);

        chatGPTHelper = new ChatGPTHelper();

        messageList = new ArrayList<>();

        initViews(view);

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        setSendButtonListener();

        return view;
    }

    private void initViews(View view)
    {
        recyclerView = view.findViewById(R.id.recycler_view);
        welcomeTextView = view.findViewById(R.id.welcome_text);
        messageEditText = view.findViewById(R.id.message_edit_text);
        sendButton = view.findViewById(R.id.send_btn);
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
        getActivity().runOnUiThread(new Runnable()
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