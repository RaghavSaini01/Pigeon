package com.example.pigeon.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pigeon.Activities.Adapters.MessageListAdapter;
import com.example.pigeon.FirebaseManagers.Messaging.MessagingHelper;
import com.example.pigeon.R;


public class MessagingRoomActivity extends AppCompatActivity {

    private static String chatTitle;
    private static String prevChatMessage;
    private static long timestamp;

    private ImageButton backButton;
    private TextView otherName;
    private ImageView profile;
    private ImageView photos;
    private ImageView cam;
    private EditText messageInput;
    private ImageView emoteButton;
    private ImageButton sendButton;

    private static ListView messageList;

    private boolean isClicked = false;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_room);
        backButton = findViewById(R.id.backButton);
        otherName = findViewById(R.id.otherName);
        profile = findViewById(R.id.profileMessage);
        photos = findViewById(R.id.photos);
        cam = findViewById(R.id.cam);
        messageInput = findViewById(R.id.messageInput);
        emoteButton = findViewById(R.id.emoteButton);
        sendButton = findViewById(R.id.sendButton);
        messageList = findViewById(R.id.messageList);

        final InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);

        otherName.setText(chatTitle);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageInput.getText().toString().isEmpty()){
                    System.out.println("Sending");
                    String message = messageInput.getText().toString();
                    messageInput.setText("");
                    MessagingHelper.sendTextMessage(message);
                }
            }
        });

        messageList.setAdapter(MessagingHelper.adapters.get(MessagingHelper.currentChatID));

        final int width = messageInput.getWidth();

        messageList.setClickable(false);
        messageInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!isClicked){
                    inputMethodManager.showSoftInput(v,0);
                    messageInput.setWidth((int)(messageInput.getWidth() * 1.25));
                    isClicked = true;
                }
                return false;
            }
        });


        messageList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isClicked){
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    messageInput.setWidth(width);
                    isClicked = false;
                }
                return false;
            }
        });
        messageInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(!messageInput.getText().toString().isEmpty()){
                    sendButton.setBackgroundTintList(v.getContext().getResources().getColorStateList(R.color.colorButtons));
                } else {
                    sendButton.setBackgroundTintList(v.getContext().getResources().getColorStateList(R.color.deselected));
                }

                return false;
            }
        });

    }

    public static void setChatInfo(MessagingHelper.ChatInfo c) {
        chatTitle = c.title;
        prevChatMessage = c.previousMessage;
        timestamp = c.TimeCreated;
    }

}
