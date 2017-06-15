package com.sajag.nagarik.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sajag.nagarik.R;

public class LoginActivity extends ActionBarActivity implements OnClickListener {

	private Button loginBtn;
	private Button registerBtn;
	private EditText mobileNoEditText;
	private SharedPreferences  sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sharedpreferences = getSharedPreferences("sajag", Context.MODE_PRIVATE);
		initializeUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		/*
		 * if(view.getId() == loginBtn.getId()){ Intent registerIntent = new
		 * Intent(getApplicationContext(),CreateComplaintActivity.class);
		 * startActivity(registerIntent); }
		 */
		if (view.getId() == loginBtn.getId()) {
			invokeWS();
		} else if (view.getId() == registerBtn.getId()) {
			Intent registerIntent = new Intent(getApplicationContext(),
					RegisterActivity.class);
			registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(registerIntent);
		}
	}

	/**
	 * Method to invoke Rest Web Services without using async task
	 */
	private void invokeWS() {
		/**
		 * Using AsyncHttp
		 */
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("userMobileNo", mobileNoEditText.getText().toString());
		
		String serverIp = getResources().getString(R.string.server_ip);
		
		final ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);
		Dialog.setMessage("Please wait..");
        Dialog.show();
		// params.p
		client.post(
				"http://" + serverIp + ":8080/sajag-complaints-management/api/userLogin",
				params, new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
						Dialog.dismiss();
						// Hide Progress Dialog
						try {
							// JSON Object
							JSONObject userObj = new JSONObject(response);
							// When the JSON response has status boolean value
							// assigned with true
							// if(userObj.getBoolean("lastName")){
							String firstName = userObj.getString("firstName");
							Toast.makeText(
									getApplicationContext(),
									firstName
											+ " You are successfully logged in!",
									Toast.LENGTH_LONG).show();
							// Navigate to Home screen
							// navigatetoHomeActivity();
							// }
							// Else display error message
							/*
							 * else{ //
							 * errorMsg.setText(obj.getString("error_msg"));
							 * Toast.makeText(getApplicationContext(),
							 * userObj.getString("error_msg"),
							 * Toast.LENGTH_LONG).show(); }
							 */
							SharedPreferences.Editor editor = sharedpreferences.edit();
					        
					        editor.putString("userMobileNo", mobileNoEditText.getText().toString());
					        editor.commit();
							
							Intent complaintsListIntent = new Intent(
									getApplicationContext(),
									ComplaintsListActivity.class);
							complaintsListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(complaintsListIntent);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							Toast.makeText(
									getApplicationContext(),
									"Error Occured [Server's JSON response might be invalid]!",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();

						}

						// Toast.makeText(getApplicationContext(),
						// "User : Shivdatta is registered successfully",
						// Toast.LENGTH_LONG).show();
					}

					// When the response returned by REST has Http response code
					// other than '200'
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						Dialog.dismiss();
						// Hide Progress Dialog
						// prgDialog.hide();
						// When Http response code is '404'
						if (statusCode == 404) {
							Toast.makeText(getApplicationContext(),
									"Requested resource not found",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code is '500'
						else if (statusCode == 500) {
							Toast.makeText(getApplicationContext(),
									"Something went wrong at server end",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code other than 404, 500
						else {
							Toast.makeText(
									getApplicationContext(),
									"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
									Toast.LENGTH_LONG).show();
						}
					}
				});

	}

	private void initializeUI() {
		mobileNoEditText = (EditText) findViewById(R.id.mobileno);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		registerBtn = (Button) findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(this);
	}
}
