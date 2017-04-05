package com.example.sampleapp;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.GameItem;
import com.example.dao.ListItem;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sample.adapter.CustomAdapter;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private TextView listenedTV;
	private TextView chatsTV;
	private Button wishButton;
	private Button seeChatButton;
	private Firebase refNotifications;
	private Firebase refChats;
	private Firebase refAgentsGames;

	private SharedPreferences sharedpreferences;

	private String username;

	private final static String LABEL_WISHES_STR = "Kudos!!!";

	private final static String LABEL_NEW_GAME_STR = "New Game ";

	private final static String WISHESBODY_STR = " You have won ";

	private final static String WELCOME_STR = "Welcome ";

	private final static String GAME_CREATED_BODY_STR = " has been created";

	private final static String GAME_WON_BODY_STR = " has won ";

	private final static String CHAT_MSG_START_STR = "Message from ";

	private final static String FIREBASE_CHAT_URL = "https://myfirstproject-24d69.firebaseio.com/chat";

	private final static String FIREBASE_NOTIFICATION_URL = "https://myfirstproject-24d69.firebaseio.com/notification";

	private final static String FIREBASE_AGENTS_URL = "https://myfirstproject-24d69.firebaseio.com/agents";

	ArrayList<ListItem> chatsList = new ArrayList<ListItem>();

	// chart data

	ArrayList<String> xAxis = new ArrayList<String>();

	ArrayList<IBarDataSet> dataSets = null;

	public ArrayList<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(ArrayList<String> xAxis) {
		this.xAxis = xAxis;
	}

	public ArrayList<IBarDataSet> getDataSets() {
		return dataSets;
	}

	public void setDataSets(ArrayList<IBarDataSet> dataSets) {
		this.dataSets = dataSets;
	}

	private ListView chatListView;

	CustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fb_layout);
		Firebase.setAndroidContext(this);

		sharedpreferences = getSharedPreferences("gamification",
				Context.MODE_PRIVATE);

		username = sharedpreferences.getString("username", "All");

		Toast.makeText(getApplicationContext(), WELCOME_STR + username,
				Toast.LENGTH_LONG).show();

		listenedTV = (TextView) findViewById(R.id.listenerTV);

		wishButton = (Button) findViewById(R.id.wishButton);
		wishButton.setOnClickListener(this);
		
		seeChatButton = (Button) findViewById(R.id.seeChat);
		seeChatButton.setOnClickListener(this);

		chatsTV = (TextView) findViewById(R.id.chatsTV);

		Resources res = getResources();
		chatListView = (ListView) findViewById(R.id.list); // List defined in
															// XML ( See Below )

		/**************** Create Custom Adapter *********/

		ListItem itemFirst = new ListItem();
		itemFirst.setChatMessage("Messages");

		chatsList.add(itemFirst);

		adapter = new CustomAdapter(MainActivity.this, chatsList, res);
		chatListView.setAdapter(adapter);

		 initialiseNotificationListener();
		
		 initialiseChatsListener();

		initialiseAgentsGamesListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == wishButton.getId()) {

			// refChats.child("messageObj").child("target").setValue("Shivdatta");
			// refChats.child("messageObj").child("message").setValue("Hey Congrats!");
			showDialog();
		}else if(view.getId() == seeChatButton.getId()){
			Intent chatActivityIntent = new Intent(getApplicationContext(), ChatActivity.class);
			startActivity(chatActivityIntent);
		}
	}

	private void initialiseNotificationListener() {
		
		try{
		
			refNotifications = new Firebase(FIREBASE_NOTIFICATION_URL);	
		
		}catch(Exception fbException){
			fbException.printStackTrace();
			Toast.makeText(getApplicationContext(), "Error accessing Firebase database", Toast.LENGTH_SHORT).show();
		}
		// Attach an listener to read the data at our posts reference

		refNotifications.addValueEventListener(new ValueEventListener() {

			Map<String, Object> valueMap = new HashMap<String, Object>();

			@Override
			public void onDataChange(DataSnapshot dataSnap) {
				
				try{
				valueMap = (Map<String, Object>) dataSnap.getValue();

				boolean isGame = false;

				String target = null, parameters = null, notification_point = null;
				String listenerText = null, notificationText = null;
				
				if(!valueMap.isEmpty() || null!=valueMap){

					for (Entry<String, Object> entry : valueMap.entrySet()) {
	//					Map<String, Object> value = entry.getValue();
	
	//					for (Entry<String, Object> valEntry : value.entrySet()) {
							if ((entry.getKey().equals("notification_type") && entry
									.getValue().equals("Game"))) {
								isGame = true;
							} else {
								isGame = false;
								
							}
							
							if (entry.getKey().equals("target")) {
								target = (String) entry.getValue();
							} else if (entry.getKey().equals("parameters")) {
								ArrayList<Object> params = (ArrayList<Object>) entry.getValue();
								parameters = (String) params.get(0);
							} else if (entry.getKey().equals(
									"notification_type")) {
								notification_point = (String) entry
										.getValue();
							}
	
	//					}
					}
	
					if (isGame) {
	
						listenerText = LABEL_NEW_GAME_STR + " '"
								+ parameters + "' " + GAME_CREATED_BODY_STR;
	
						notificationText = LABEL_NEW_GAME_STR + " '"
								+ parameters + "' " + GAME_CREATED_BODY_STR;
	
					} else if (target.equalsIgnoreCase(username)) {
						listenerText = LABEL_WISHES_STR + WISHESBODY_STR + " "
								+ parameters + " " + notification_point + "(s)";
						notificationText = LABEL_WISHES_STR + WISHESBODY_STR + " "
								+ parameters + " " + notification_point + "(s)";
	
						wishButton.setVisibility(View.GONE);
					} else {
	
						listenerText = LABEL_WISHES_STR + " " + target
								+ GAME_WON_BODY_STR + " " + parameters + " "
								+ notification_point + "(s)";
						notificationText = LABEL_WISHES_STR + " " + target
								+ GAME_WON_BODY_STR + " " + parameters + " "
								+ notification_point + "(s)";
	
					}
	
					listenedTV.setText(listenerText);
					Notification n = new Notification.Builder(
							getApplicationContext())
							.setContentTitle("Game Notification")
							.setContentText(notificationText)
							.setSmallIcon(R.drawable.icon_notification).build();
	
					NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	
					notificationManager.notify(0, n);
				}else{
					Toast.makeText(getApplicationContext(), "Data Incorrect!Please check.", Toast.LENGTH_SHORT).show();
				}
				}catch(Exception exception){
					exception.printStackTrace();
					Toast.makeText(getApplicationContext(), "Exception in Data Format!Please check.", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onCancelled(FirebaseError fbErr) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initialiseChatsListener() {

		try{
			refChats = new Firebase(FIREBASE_CHAT_URL);
		}catch(Exception fbException){
			fbException.printStackTrace();
			Toast.makeText(getApplicationContext(), "Error accessing Firebase database", Toast.LENGTH_SHORT).show();
		}
		
		
		refChats.addValueEventListener(new ValueEventListener() {

			Map<String, Map<String, Object>> chatsValueMap = new HashMap<String, Map<String, Object>>();
			String chatsTarget, chatsMessage;

			Map<String, Object> chat = new HashMap<String, Object>();

			@SuppressWarnings("unchecked")
			@Override
			public void onDataChange(DataSnapshot chatsDataSnapshot) {
				try{
				
				chatsValueMap = (Map<String, Map<String, Object>>) chatsDataSnapshot
						.getValue();

				if(!chatsValueMap.isEmpty() || null!=chatsValueMap){
				
				for (Entry<String, Map<String, Object>> chatsEntry : chatsValueMap
						.entrySet()) {
					chat = (Map<String, Object>) chatsEntry.getValue();

					for (Entry<String, Object> chatMessage : chat.entrySet()) {
						if (chatMessage.getKey().equals("message")) {
							chatsMessage = (String) chatMessage.getValue();
						} else if (chatMessage.getKey().equals("target")) {
							chatsTarget = (String) chatMessage.getValue();
						}
					}
				}
				
				}

				chatsTV.setText(CHAT_MSG_START_STR + chatsTarget + " : "
						+ chatsMessage);
				ListItem listItem = new ListItem();
				listItem.setChatMessage(CHAT_MSG_START_STR + chatsTarget
						+ " : " + chatsMessage);
				chatsList.add(listItem);
				adapter.notifyDataSetChanged();
				
				/*Intent chatActivityIntent = new Intent(getApplicationContext(), ChatActivity.class);
				startActivity(chatActivityIntent);*/
				}catch(Exception e){
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Data Incorrect!Please Check.", Toast.LENGTH_SHORT).show();
				
				}
			}

			@Override
			public void onCancelled(FirebaseError chatsFBErr) {

			}
		});
	}

	// Agent and games

	private void initialiseAgentsGamesListener() {

		try{
			
		refAgentsGames = new Firebase(FIREBASE_AGENTS_URL + "/" + username
				+ "/games");

		}catch(Exception fbException){
			fbException.printStackTrace();
			Toast.makeText(getApplicationContext(), "Error accessing Firebase database", Toast.LENGTH_SHORT).show();
		}
		
		refAgentsGames.addValueEventListener(new ValueEventListener() {

			Map<String, Map<String, Object>> agentGamesValueMap = new HashMap<String, Map<String, Object>>();
			String chatsTarget, chatsMessage;

			Map<String, Object> chat = new HashMap<String, Object>();

			ArrayList<Object> myGames = new ArrayList<Object>();
			ProgressDialog progress = ProgressDialog.show(MainActivity.this, "Loading..",
				    "Getting Your Data", true);
			
			@SuppressWarnings("unchecked")
			@Override
			public void onDataChange(DataSnapshot chatsDataSnapshot) {

				try{
				
					progress.dismiss();
					
				myGames = (ArrayList<Object>) chatsDataSnapshot.getValue();

				// agentGamesValueMap = (Map<String, Map<String, Object>>)
				// chatsDataSnapshot.getValue();

				/*
				 * for(Entry<String, Map<String, Object>> chatsEntry :
				 * agentGamesValueMap.entrySet()){ chat = (Map<String, Object>)
				 * chatsEntry.getValue();
				 * 
				 * for(Entry<String,Object> chatMessage : chat.entrySet()){
				 * if(chatMessage.getKey().equals("message")){ chatsMessage =
				 * (String) chatMessage.getValue(); }else
				 * if(chatMessage.getKey().equals("target")){ chatsTarget =
				 * (String) chatMessage.getValue(); } } }
				 */

				if (null != myGames) {

					Iterator<Object> myGamesItr = myGames.iterator();
					Object gameObj;
					ArrayList<GameItem> gamesList = new ArrayList<GameItem>();
					ArrayList<String> xAxisList = new ArrayList<String>();
					ArrayList<IBarDataSet> barDataSet = new ArrayList<IBarDataSet>();

					while (myGamesItr.hasNext()) {
						GameItem game = new GameItem();
						gameObj = myGamesItr.next();
						if (null != gameObj) {

							HashMap<String, Object> gameDataMap = (HashMap<String, Object>) gameObj;
							for (Entry<String, Object> gameData : gameDataMap
									.entrySet()) {

								if (gameData.getKey().equals("agent_id")) {
									game.setAgent_id(gameData.getValue()
											.toString());
								} else if (gameData.getKey().equals(
										"agent_name")) {
									game.setAgent_name(gameData.getValue()
											.toString());
								} else if (gameData.getKey().equals("game_id")) {
									game.setGame_id(gameData.getValue()
											.toString());
								} else if (gameData.getKey()
										.equals("game_name")) {
									game.setGame_name(gameData.getValue()
											.toString());
								} else if (gameData.getKey().equals("points")) {
									game.setPoints(gameData.getValue()
											.toString());
								} else if (gameData.getKey().equals(
										"winner_points")) {
									game.setWinner_points(gameData.getValue()
											.toString());
								}

							}
							gamesList.add(game);
						}

					}

					ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
					ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();

					int index = 0;
					for (GameItem gameItem : gamesList) {
						xAxisList.add(gameItem.getGame_name());
						BarEntry v1e1 = new BarEntry(Float.valueOf(gameItem
								.getPoints()), index); // Jan
						valueSet1.add(v1e1);
						index++;
					}

					BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Points");
					barDataSet1.setColor(Color.rgb(0, 155, 0));
					dataSets.add(barDataSet1);
					// setDataSets(dataSets);

					BarChart chart = (BarChart) findViewById(R.id.chart);

					BarData data = new BarData(xAxisList, dataSets);
					chart.setData(data);
					chart.setDescription("Points/Games");
					chart.animateXY(2000, 2000);
					chart.invalidate();
				}
				}catch(Exception exception){
					exception.printStackTrace();
					Toast.makeText(getApplicationContext(), "Data Incorrect!Please Check", Toast.LENGTH_SHORT).show();
				
				}
				/*
				 * chatsTV.setText(CHAT_MSG_START_STR+chatsTarget +
				 * " : "+chatsMessage); ListItem listItem = new ListItem();
				 * listItem.setChatMessage(CHAT_MSG_START_STR+chatsTarget +
				 * " : "+chatsMessage); chatsList.add(listItem);
				 * adapter.notifyDataSetChanged();
				 */

			}

			@Override
			public void onCancelled(FirebaseError chatsFBErr) {

			}
		});
	}

	private void showDialog() {
		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(getApplicationContext());
		View promptsView = li.inflate(R.layout.dialogue_layout, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MainActivity.this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.editTextDialogUserInput);

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// get user input and set it to result
						// edit text
						// result.setText(userInput.getText());
//						
						
//						String strMsgId = String.valueOf(Math.floor(Math.random()*100));
						String strMsgId = new BigInteger(130, new SecureRandom()).toString(32);
//						Random generator = new Random(); 
//						int i = generator.nextInt(10) + 1;
						
						refChats.child(strMsgId).child("target")
								.setValue(username);
						refChats.child(strMsgId).child("message")
								.setValue(userInput.getText().toString());
						
						Intent chatActivityIntent = new Intent(getApplicationContext(), ChatActivity.class);
						startActivity(chatActivityIntent);
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	// Chart Data

	private ArrayList<IBarDataSet> getDataSet() {
		ArrayList<IBarDataSet> dataSets = null;

		ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
		BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
		valueSet1.add(v1e1);
		BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
		valueSet1.add(v1e2);
		BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
		valueSet1.add(v1e3);
		BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
		valueSet1.add(v1e4);
		BarEntry v1e5 = new BarEntry(90.000f, 4); // May
		valueSet1.add(v1e5);
		BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
		valueSet1.add(v1e6);

		ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
		BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
		valueSet2.add(v2e1);
		BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
		valueSet2.add(v2e2);
		BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
		valueSet2.add(v2e3);
		BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
		valueSet2.add(v2e4);
		BarEntry v2e5 = new BarEntry(20.000f, 4); // May
		valueSet2.add(v2e5);
		BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
		valueSet2.add(v2e6);

		BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
		barDataSet1.setColor(Color.rgb(0, 155, 0));
		BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
		barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

		dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(barDataSet1);
		return dataSets;
	}

	private ArrayList<String> getXAxisValues() {
		ArrayList<String> xAxis = new ArrayList<String>();
		xAxis.add("JAN");
		xAxis.add("FEB");
		xAxis.add("MAR");
		xAxis.add("APR");
		xAxis.add("MAY");
		xAxis.add("JUN");
		return xAxis;
	}
}
