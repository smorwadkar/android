/**
 * 
 */
package com.sajag.nagarik.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sajag.nagarik.R;
import com.sajag.nagarik.dto.ComplaintsDetails;

/**
 * @author smorwadkar
 * 
 */
public class ComplaintDetailsActivity extends Activity implements
		OnClickListener {

	private Spinner deptSpinner;
	private TextView commentsTextView;
	private Button viewImagesButton;
	
	List<ImageView> images = new ArrayList<ImageView>();
	ComplaintsDetails complaintDetail = new ComplaintsDetails();
	Integer complaintId;
	private SharedPreferences  sharedpreferences;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_details);
		deptSpinner = (Spinner) findViewById(R.id.details_dept_spinner);
		
		// Spinner Drop down elements
	      List<String> categories = new ArrayList<String>();
	      categories.add(getResources().getString(R.string.spinner_title));
	      categories.add(getResources().getString(R.string.spinner_dept_1));
	      categories.add(getResources().getString(R.string.spinner_dept_2));
	      categories.add(getResources().getString(R.string.spinner_dept_3));
	      categories.add(getResources().getString(R.string.spinner_dept_4));
	      categories.add(getResources().getString(R.string.spinner_dept_other));
	      
		
		// Creating adapter for spinner
	      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
	      
	      // Drop down layout style - list view with radio button
	      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      
	      // attaching data adapter to spinner
	      deptSpinner.setAdapter(dataAdapter);
		
		commentsTextView = (TextView) findViewById(R.id.comments);
		viewImagesButton = (Button) findViewById(R.id.viewImgBtn);
		viewImagesButton.setOnClickListener(this);
		sharedpreferences = getSharedPreferences("sajag", Context.MODE_PRIVATE);
		complaintId = sharedpreferences.getInt("complaintId",0); 
		invokeWS();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_complaints, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent createComplaintIntent = new Intent(getApplicationContext(),
					CreateComplaintActivity.class);
			createComplaintIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(createComplaintIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == viewImagesButton.getId()){
			/*CustomDialog cdd=new CustomDialog(ComplaintDetailsActivity.this);
			cdd.show();*/
			
			// custom dialog
						final Dialog dialog = new Dialog(ComplaintDetailsActivity.this);
						dialog.setContentView(R.layout.custom_dialog);
						dialog.setTitle("Images");

						// set the custom dialog components - text, image and button
						/*TextView text = (TextView) dialog.findViewById(R.id.txt_dia);
						text.setText("Images");*/
						/*ImageView image = (ImageView) dialog.findViewById(R.id.image);
						image.setImageResource(R.drawable.ic_launcher);*/
						
						ImageView image1;
						ImageView image2;
						image1 = (ImageView) dialog.findViewById(R.id.detailImageView1);
						image2 = (ImageView) dialog.findViewById(R.id.detailImageView2);
						images.add(image1);
						images.add(image2);
						invokeFetchImagesWS();
						Button dialogButton = (Button) dialog.findViewById(R.id.btn_yes);
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});

						dialog.show();
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
		params.add("complaintId", String.valueOf(complaintId));
		final ProgressDialog Dialog = new ProgressDialog(
				ComplaintDetailsActivity.this);
		Dialog.setMessage("Please wait..");
		Dialog.show();

		String serverIp = getResources().getString(R.string.server_ip);
		
		client.get(
				"http://" + serverIp + ":8080/sajag-complaints-management/api/request/complaintDetailsById",
				params, new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
						Dialog.dismiss();

						try {
							
							JSONObject complaintObj = new JSONObject(response);

							complaintDetail
									.setComplaintId((Integer) complaintObj
											.get("complaintId"));
							complaintDetail
									.setComplaintStatus((String) complaintObj
											.get("complaintStatus"));
							complaintDetail.setCreationTime(complaintObj
									.getLong("creationTime"));
							complaintDetail.setLastUpdateTime(complaintObj
									.getLong("lastUpdateTime"));
							complaintDetail.setComments((String) complaintObj
									.get("comments"));
							complaintDetail
									.setDepartmentId((Integer) complaintObj
											.get("departmentId"));
							
							updateUI(complaintDetail);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Toast.makeText(getApplicationContext(),
								"All requests fetched", Toast.LENGTH_LONG)
								.show();
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
	
	private void updateUI(ComplaintsDetails complaintDetail) {
		deptSpinner.setSelection(complaintDetail.getDepartmentId());
		commentsTextView.setText(complaintDetail.getComments());
	}
	
	
	/**
	 * Method to invoke Rest Web Services without using async task
	 */
	private void invokeFetchImagesWS() {
		/**
		 * Using AsyncHttp
		 */
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("complaintId", String.valueOf(complaintId));
//		final ProgressDialog Dialog = new ProgressDialog(COmpla);
//		Dialog.setMessage("Please wait..");
//		Dialog.show();

		String serverIp = getResources().getString(R.string.server_ip);
		
		client.get(
				"http://" + serverIp + ":8080/sajag-complaints-management/api/request/getComplaintImages",
				params, new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
//						Dialog.dismiss();
						JSONObject imagesBytesJson;
						try {
							ObjectMapper mapper = new ObjectMapper();
							Map<Integer, String> map = new HashMap<Integer, String>();
							map = mapper.readValue(response, new TypeReference<Map<Integer, String>>(){});
							int imageCnt = 0;
							for (Entry<Integer,String> entry : map.entrySet()) {
								byte [] imageBytes = Base64.decode(entry.getValue(), Base64.DEFAULT);
								images.get(imageCnt).setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
								imageCnt++;
							}
						}catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JsonMappingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Toast.makeText(getApplicationContext(),
								"All images fetched", Toast.LENGTH_LONG)
								.show();
					}

					// When the response returned by REST has Http response code
					// other than '200'
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						// Dialog.dismiss();
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
}
