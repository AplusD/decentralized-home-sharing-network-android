package com.aplusd.houserenter.chat;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.chat.model.Message;

import java.util.ArrayList;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

/**
 * @author Azamat Dzhonov
 * @date 12.05.2018
 */
public class ChatActivity extends AppCompatActivity {

    public static String CONTRACT_ID = "contractId";
    public static String PARTNER_NAME = "partnerName";

    private  ViewModelChat viewModelChat = null;
    private ChatView chatView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(PARTNER_NAME));

        chatView = (ChatView) findViewById(R.id.chat_view);

        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                viewModelChat.sendMsg(getIntent().getIntExtra(CONTRACT_ID, 0),chatMessage.getMessage(), getBaseContext());
                return true;
            }
        });



        viewModelChat = ViewModelProviders.of(this).get(ViewModelChat.class);
        viewModelChat.getMessageMutableLiveData(getIntent().getIntExtra(CONTRACT_ID, 0), getBaseContext()).observe(this, new Observer<ArrayList<Message>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Message> messages) {
                if(messages == null)
                    return;

                chatView.clearMessages();

                for(int i = 0; i < messages.size(); i++)
                    chatView.addMessage(messages.get(i).getMessage(getBaseContext()));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item1)
        {
            viewModelChat.getMessageMutableLiveData(getIntent().getIntExtra(CONTRACT_ID, 0), getBaseContext()).observe(this, new Observer<ArrayList<Message>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Message> messages) {
                    if(messages == null)
                        return;

                    chatView.clearMessages();
                    for(int i = 0; i < messages.size(); i++)
                        chatView.addMessage(messages.get(i).getMessage(getBaseContext()));
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
