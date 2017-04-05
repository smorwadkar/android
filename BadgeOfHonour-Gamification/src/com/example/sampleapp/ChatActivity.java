package com.example.sampleapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.example.dao.ListItem;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sample.adapter.CustomAdapter;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity{

	private TextView chatsTV;
	
	private ListView chatListView;
	
	ArrayList<ListItem> chatsList = new ArrayList<ListItem>();
	
	CustomAdapter adapter;
	
	private Firebase refChats;
	private final static String FIREBASE_CHAT_URL = "https://myfirstproject-24d69.firebaseio.com/chat";
	
	private final static String CHAT_MSG_START_STR = "Message from ";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		chatsTV = (TextView) findViewById(R.id.chatsTV);
		
		Resources res = getResources();
		chatListView = (ListView) findViewById(R.id.list); // List defined in
															// XML ( See Below )

		/**************** Create Custom Adapter *********/

//		ListItem itemFirst = new ListItem();
//		itemFirst.setChatMessage("Messages");

//		chatsList.add(itemFirst);

		adapter = new CustomAdapter(ChatActivity.this, chatsList, res);
		chatListView.setAdapter(adapter);
		
		initialiseChatsListener();
	}
	
	
	private void initialiseChatsListener() {

		refChats = new Firebase(FIREBASE_CHAT_URL);

		refChats.addValueEventListener(new ValueEventListener() {

			Map<String, Map<String, Object>> chatsValueMap = new HashMap<String, Map<String, Object>>();
			String chatsTarget, chatsMessage;

			Map<String, Object> chat = new HashMap<String, Object>();

			@SuppressWarnings("unchecked")
			@Override
			public void onDataChange(DataSnapshot chatsDataSnapshot) {
				chatsValueMap = (Map<String, Map<String, Object>>) chatsDataSnapshot
						.getValue();
				
				
				for (Entry<String, Map<String, Object>> chatsEntry : chatsValueMap
						.entrySet()) {
					chat = (Map<String, Object>) chatsEntry.getValue();

					ListItem listItem = new ListItem();
					for (Entry<String, Object> chatMessage : chat.entrySet()) {
						if (chatMessage.getKey().equals("message")) {
							chatsMessage = (String) chatMessage.getValue();
							listItem.setChatMessage(chatsMessage);
						} else if (chatMessage.getKey().equals("target")) {
							chatsTarget = (String) chatMessage.getValue();
							listItem.setChatTarget(chatsTarget);
						}
						
					}
					chatsList.add(listItem);
				}

				chatsTV.setText("Chat Messages");
				
				chatsTV.setText(CHAT_MSG_START_STR + chatsTarget + " : "
						+ chatsMessage);
				
				adapter.notifyDataSetChanged();

			}

			@Override
			public void onCancelled(FirebaseError chatsFBErr) {

			}
		});
	}
}
