package com.sajag.nagarik.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sajag.nagarik.R;

public class RegisterActivity extends Activity implements OnClickListener {

	private Button registerBtn;
	private EditText mobileNoEditText;
	private EditText firstNameEditText;
	private EditText lastNameEditText;
	private EditText emailIdEditText;
	private EditText cityEditText;
	private EditText pincodeEditText;
	
	private Spinner bloodGroupSpinner;
	List<String> bloodGroups = new ArrayList<String>();
	
	String registerURL = "http://139.59.20.112:8080/sajag-complaints-management/api/product/allCategories";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initializeUI();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		invokeWS();
		/*if (view.getId() == registerBtn.getId()) {
			Intent loginIntent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(loginIntent);
		}*/
	}

	// creating own http client

	public static HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(conMgr, params);
	}

	
	/**
	 * Method to invoke Rest Web Services without using async task
	 */
	private void invokeWS() {
		/**
		 * Using AsyncHttp
		 */
		AsyncHttpClient client = new AsyncHttpClient();
		JSONObject jsonParams = new JSONObject();
		StringEntity entity = null;
		try {
			jsonParams.put("mobileNo", mobileNoEditText.getText().toString());
			jsonParams.put("firstName", firstNameEditText.getText().toString());
			jsonParams.put("lastName", lastNameEditText.getText().toString());
			jsonParams.put("password", pincodeEditText.getText().toString()+"_"+mobileNoEditText.getText().toString());
			jsonParams.put("emailId", emailIdEditText.getText().toString());
			jsonParams.put("bloodGroup", bloodGroups.get((int) bloodGroupSpinner.getSelectedItemId()));
			jsonParams.put("city", cityEditText.getText().toString());
			jsonParams.put("pincode", pincodeEditText.getText().toString());
			
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String serverIp = getResources().getString(R.string.server_ip);
		
		client.post(getApplicationContext(), "http://" + serverIp +":8080/sajag-complaints-management/api/userRegistration", entity, "application/json;charset=UTF-8", new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                /*try {
                        // JSON Object
                        JSONObject userObj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if(userObj.getBoolean("status")){
                            Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                            // Navigate to Home screen
//                            navigatetoHomeActivity();
                        } 
                        // Else display error message
                        else{
//                            errorMsg.setText(obj.getString("error_msg"));
                            Toast.makeText(getApplicationContext(), userObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                        }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }*/
            	
            	Toast.makeText(getApplicationContext(), "User : "+firstNameEditText.getText().toString()+" is registered successfully", Toast.LENGTH_LONG).show();
            	Intent loginIntent = new Intent(getApplicationContext(),
    					LoginActivity.class);
    			startActivity(loginIntent);
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                String content) {
                // Hide Progress Dialog 
//                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } 
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } 
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
		
		/*client.post("http://139.59.20.112:8080/sajag-complaints-management/api/userRegistration",params ,new AsyncHttpResponseHandler() {
             // When the response returned by REST has Http response code '200'
             @Override
             public void onSuccess(String response) {
                 // Hide Progress Dialog
                 try {
                         // JSON Object
                         JSONArray objArr = new JSONArray(response);
                         // When the JSON response has status boolean value assigned with true
                         if(obj.getBoolean("status")){
                             Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                             // Navigate to Home screen
//                             navigatetoHomeActivity();
                         } 
                         // Else display error message
                         else{
//                             errorMsg.setText(obj.getString("error_msg"));
                             Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                         }
                 } catch (JSONException e) {
                     // TODO Auto-generated catch block
                     Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                     e.printStackTrace();
 
                 }
             }
             // When the response returned by REST has Http response code other than '200'
             @Override
             public void onFailure(int statusCode, Throwable error,
                 String content) {
                 // Hide Progress Dialog 
//                 prgDialog.hide();
                 // When Http response code is '404'
                 if(statusCode == 404){
                     Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                 } 
                 // When Http response code is '500'
                 else if(statusCode == 500){
                     Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                 } 
                 // When Http response code other than 404, 500
                 else{
                     Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                 }
             }
         });*/
		
	}
	
	private void initializeUI(){
		mobileNoEditText = (EditText) findViewById(R.id.mobileno);
		firstNameEditText = (EditText) findViewById(R.id.firstname);
		lastNameEditText = (EditText) findViewById(R.id.lastname);
		emailIdEditText = (EditText) findViewById(R.id.emailId);
		bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroup_spinner);
		cityEditText = (EditText) findViewById(R.id.city);
		pincodeEditText = (EditText) findViewById(R.id.pincode);
		registerBtn = (Button) findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(this);
		
		 
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_title));
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_type1));
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_type2));
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_type3));
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_type4));
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_type5));
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_type6));
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_type7));
	      bloodGroups.add(getResources().getString(R.string.bloodGroup_type8));
	      
		
		// Creating adapter for spinner
	      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroups);
	      
	      // Drop down layout style - list view with radio button
	      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      
	      // attaching data adapter to spinner
	      bloodGroupSpinner.setAdapter(dataAdapter);
	}
}
