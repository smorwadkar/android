package com.sajag.nagarik.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sajag.nagarik.R;
import com.sajag.nagarik.dto.ComplaintsDetails;
import com.sajag.nagarik.dto.FullComplaintDetails;

public class ComplaintsListActivity extends ListActivity{

	private List<FullComplaintDetails> userComplaints = new ArrayList<FullComplaintDetails>();
	private ComplaintsAdapter taskAdapter = new ComplaintsAdapter();
	private Button viewDetailsBtn;
	private Long userMobileNo;
	private String userMobile;
	private SharedPreferences  sharedpreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		userMobileNo = savedInstanceState.getLong("userMobileNo");
		setContentView(R.layout.activity_complaints_list);
		sharedpreferences = getSharedPreferences("sajag", Context.MODE_PRIVATE);
		userMobile = sharedpreferences.getString("userMobileNo", "All");
		
		taskAdapter.setTasks(userComplaints);

		setListAdapter(taskAdapter);
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
	
	public class ComplaintsAdapter extends BaseAdapter {

		List<FullComplaintDetails> tasks = new ArrayList<FullComplaintDetails>();

		public List<FullComplaintDetails> getTasks() {
			return tasks;
		}

		public void setTasks(List<FullComplaintDetails> tasks) {
			this.tasks = tasks;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tasks.size();
		}

		@Override
		public FullComplaintDetails getItem(int arg0) {
			// TODO Auto-generated method stub
			return tasks.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			if (arg1 == null) {
				LayoutInflater inflater = (LayoutInflater) ( LayoutInflater )getApplicationContext().
	                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				arg1 = inflater.inflate(R.layout.list_row, arg2, false);
			}

			TextView status = (TextView) arg1.findViewById(R.id.title);
			TextView comments = (TextView) arg1.findViewById(R.id.artist);
			TextView lastUpdateTime = (TextView) arg1.findViewById(R.id.duration);
			FullComplaintDetails complaintDetails = tasks.get(arg0);
			status.setText(complaintDetails.getComplaintsDetails().getComplaintStatus());
			comments.setText(complaintDetails.getComplaintsDetails().getComments());
			Long lastUpdateTimeLong = complaintDetails.getComplaintsDetails().getLastUpdateTime();
//			TimeZone tz = TimeZone.getDefault();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(lastUpdateTimeLong);
			Date lastDate = calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			lastUpdateTime.setText(sdf.format(lastDate));
			return arg1;
		}

		public FullComplaintDetails getTask(int position) {
			return tasks.get(position);
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
		params.add("userMobileNo", userMobile);
		final ProgressDialog Dialog = new ProgressDialog(ComplaintsListActivity.this);
		Dialog.setMessage("Please wait..");
        Dialog.show();
		
        String serverIp = getResources().getString(R.string.server_ip);
        
		client.get("http://" + serverIp + ":8080/sajag-complaints-management/api/request/getAllUserRequests", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
            	Dialog.dismiss();
                /*Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
                Type listType = new TypeToken<List<FullComplaintDetails>>() {}.getType();
				userComplaints  = gson.fromJson(response, listType);*/
            	
            	try {
            		List<FullComplaintDetails> userComplaintsList = new ArrayList<FullComplaintDetails>();
					JSONArray complaintsArray = new JSONArray(response);
					for (int complaintNo = 0;complaintNo < complaintsArray.length();complaintNo++) {
						FullComplaintDetails fullComplaintDetail = new FullComplaintDetails();
						ComplaintsDetails complaintDetail = new ComplaintsDetails();
						JSONObject fullComplaintObj = (JSONObject) complaintsArray.get(complaintNo);
						
						fullComplaintDetail.setUserMobileNo(fullComplaintObj.getLong("userMobileNo"));
						JSONObject complaintObj = (JSONObject) fullComplaintObj.get("complaintsDetails");
						
						complaintDetail.setComplaintId((Integer) complaintObj.get("complaintId"));
						complaintDetail.setComplaintStatus((String) complaintObj.get("complaintStatus"));
						complaintDetail.setCreationTime(complaintObj.getLong("creationTime"));
						complaintDetail.setLastUpdateTime(complaintObj.getLong("lastUpdateTime"));
						complaintDetail.setComments((String) complaintObj.get("comments"));
						complaintDetail.setDepartmentId((Integer) complaintObj.get("departmentId"));
						
						
						fullComplaintDetail.setComplaintsDetails(complaintDetail);
						userComplaints.add(fullComplaintDetail);
						
						Collections.sort(userComplaints, new Comparator<FullComplaintDetails>() {
					        @Override public int compare(FullComplaintDetails p1, FullComplaintDetails p2) {
					            return (int) (p2.getComplaintsDetails().getCreationTime() - p1.getComplaintsDetails().getCreationTime()); // Ascending
					        }

					    });
						
						userComplaintsList = userComplaints;
					}
            	} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Toast.makeText(getApplicationContext(), "All requests fetched", Toast.LENGTH_LONG).show();
				taskAdapter.setTasks(userComplaints);
				taskAdapter.notifyDataSetChanged();
//            	Toast.makeText(getApplicationContext(), "User : Shivdatta is registered successfully", Toast.LENGTH_LONG).show();
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                String content) {
            	Dialog.dismiss();
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
		
	}
	
		   public Long stringToLong(String jsonDateString) throws JsonParseException {
		      Long dateLong = Long.valueOf(jsonDateString); 
		      return dateLong; 
		   } 
	
	
	public void myClickHandler(View v) 
    {
          
        //reset all the listView items background colours 
        //before we set the clicked one..

        ListView lvItems = getListView();
        for (int i=0; i < lvItems.getChildCount(); i++) 
        {
            lvItems.getChildAt(i).setBackgroundColor(Color.BLUE);        
        }
        
        
        //get the row the clicked button is in
        LinearLayout vwParentRow = (LinearLayout)v.getParent();
         
        TextView child = (TextView)vwParentRow.getChildAt(0);
        Button btnChild = (Button)vwParentRow.getChildAt(1);
        btnChild.setText(child.getText());
        btnChild.setText("I've been clicked!");
        
        int c = Color.CYAN;
        
        vwParentRow.setBackgroundColor(c); 
        vwParentRow.refreshDrawableState();       
    }


	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putInt("complaintId", userComplaints.get(position).getComplaintsDetails().getComplaintId());
        editor.commit();
		Intent complaintDetailsIntent = new Intent(getApplicationContext(), ComplaintDetailsActivity.class);
		startActivity(complaintDetailsIntent);
	}
	
	
}
