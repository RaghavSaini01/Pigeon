package com.example.pigeon.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pigeon.Activities.MainActivity;
import com.example.pigeon.Activities.MessagingRoomActivity;
import com.example.pigeon.FirebaseManagers.Messaging.MessagingHelper;
import com.example.pigeon.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChatListAdapter extends ArrayAdapter<HashMap<String, MessagingHelper.ChatInfo>> {

    public ChatListAdapter(Context context, int resource, List<HashMap<String, MessagingHelper.ChatInfo>> objects) {
        super(context, resource, objects);
    }
    public ChatListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Gets the current item which should be the chat
        Map<String, MessagingHelper.ChatInfo> currItem = getItem(position);

        //Inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_menu_item, parent, false);
        }

        //Set the textviews with their respective ids
        final TextView chatTitle = convertView.findViewById(R.id.chatTitle);
        TextView previousMessage = convertView.findViewById(R.id.previousMessage);
        TextView timeStamp = convertView.findViewById(R.id.timeStamp);

        //Get the set of our chat info
        Set<Map.Entry<String, MessagingHelper.ChatInfo>> entries = currItem.entrySet();

        if(currItem != null){
            //Iterate through
            Iterator<Map.Entry<String, MessagingHelper.ChatInfo>> iterator = entries.iterator();
            Map.Entry<String, MessagingHelper.ChatInfo> entry = null;
            while (iterator.hasNext()) {
                entry = iterator.next(); //Sets entry as the map value for our chat info
            }

            final MessagingHelper.ChatInfo chatInfo = entry.getValue(); //gets chat info

            //Iterates through the map of chat members at the specified id and makes the title of the chat to the user.
            String chatId = entry.getKey();
            StringBuilder sb = new StringBuilder();
            if(chatInfo.title.equals("")){ //Check whether there's a title or not
                HashMap<String, String> membersMap = MessagingHelper.chatMembers.get(chatId);
                if(membersMap != null){
                    Set<Map.Entry<String,String>> members = membersMap.entrySet();

                    Iterator<Map.Entry<String, String>> membersIterators = members.iterator();

                    while (membersIterators.hasNext()){
                        Map.Entry<String, String> member = membersIterators.next();
                        if(!member.getKey().equals(MainActivity.user.getuID())){
                            if(members.size() > 2){
                                sb.append(member.getValue());
                            } else {
                                sb.append(member.getValue() + ", ");
                            }
                        }
                    }
                    if(sb.charAt(sb.length()-2) == ','){
                        sb.deleteCharAt(sb.length()-2);
                        sb.deleteCharAt(sb.length()-1);
                    }
                }
            } else {
                sb.append(chatInfo.title); //This is a custom title the users have put
            }
            chatTitle.setText(sb.toString());

            //Sets the previousMessage into textview
            previousMessage.setText(chatInfo.previousMessage);

            //Creates the time in normal form h:mm pm/am
            Date date = new Date(chatInfo.TimeCreated);
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
            String time = dateFormat.format(date);

            //Sets the time into the textview
            timeStamp.setText(time.toLowerCase());
        }

        return convertView;
    }

}
