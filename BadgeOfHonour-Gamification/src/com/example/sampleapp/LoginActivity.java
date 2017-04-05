package com.example.sampleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

	EditText usernameET;
	
	Button loginBtn;

	private SharedPreferences  sharedpreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		sharedpreferences = getSharedPreferences("gamification", Context.MODE_PRIVATE);
		
		usernameET = (EditText) findViewById(R.id.username);
		
		loginBtn = (Button) findViewById(R.id.loginBtn);
		
		loginBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
//		mainActivityIntent.putExtra("username", usernameET.getText().toString());
		SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putString("username", usernameET.getText().toString());
        editor.commit();
		startActivity(mainActivityIntent);
	}
	
}
