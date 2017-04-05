package com.shiv.seismic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.shiv.earthquakedetails.R;
import com.shiv.secure.DataLoader;
import com.shiv.seismic.SeismicActivity.TasksAdapter;
import com.shiv.seismic.pojo.QuakeDetails;
import com.shiv.seismic.quakes.Features;
import com.shiv.seismic.quakes.Properties;
import com.shiv.seismic.quakes.Quakes;

public class SeismicActivity<TasksAdapter> extends ListActivity{

	TasksAdapter taskAdapter;
	List<QuakeDetails> taskList;

	//Summary URL
//	String URLStr = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
	
	//Detailed URL
	String URLStr = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";

	/*static {
	    HttpsURLConnection.setDefaultSSLSocketFactory(new NoSSLv3Factory());
	}*/
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		taskList = new ArrayList<QuakeDetails>();
		taskAdapter = new TasksAdapter();

		setListAdapter(taskAdapter);

		List<QuakeDetails> tasks = new ArrayList<QuakeDetails>();
		
		new LongOperation().execute(URLStr);
		
		/*//Adbuddiz
		
		AdBuddiz.setPublisherKey("798ce947-9871-49d0-93a6-2404562de709");
	    AdBuddiz.cacheAds(this); // this = current Activity
*/	    
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
		QuakeDetails task = taskAdapter.getTask(position);

		AdBuddiz.showAd(this);
		
		Intent quakeDetailsIntent = new Intent(getApplicationContext(),QuakeDetailsActivity.class);
		quakeDetailsIntent.putExtra("quakeDetails", task);
		startActivity(quakeDetailsIntent);
    }
	public class TasksAdapter extends BaseAdapter {

		List<QuakeDetails> tasks = new ArrayList<QuakeDetails>();

		public List<QuakeDetails> getTasks() {
			return tasks;
		}

		public void setTasks(List<QuakeDetails> tasks) {
			this.tasks = tasks;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tasks.size();
		}

		@Override
		public QuakeDetails getItem(int arg0) {
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
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				arg1 = inflater.inflate(R.layout.listitem, arg2, false);
			}

			TextView customer = (TextView) arg1.findViewById(R.id.textView1);
			TextView taskInfo = (TextView) arg1.findViewById(R.id.textView2);

			QuakeDetails chapter = tasks.get(arg0);

			customer.setText(chapter.quakeTitle);
			taskInfo.setText(chapter.quakeInfo);

			return arg1;
		}

		public QuakeDetails getTask(int position) {
			return tasks.get(position);
		}

	}

	private void addItemsToList(String taskData) {
		QuakeDetails td = new QuakeDetails();
		td.setQuakeTitle(taskData);
		td.setQuakeInfo("TestData");
		taskList.add(td);
		taskAdapter.notifyDataSetChanged();
	}

	public List<QuakeDetails> getDataForListView() {
		List<QuakeDetails> taskList = new ArrayList<QuakeDetails>();

		for (int i = 0; i < 1; i++) {

			QuakeDetails chapter = new QuakeDetails();
			chapter.quakeTitle = "Tata Motors";
			chapter.quakeInfo = "PMP";
			// chapter.taskInfo = "INV20110 | $800";
			taskList.add(chapter);
		}
		
		
		return taskList;

	}
	
	private class LongOperation  extends AsyncTask<String, Void, Void> {
        
        // Required initialization
         
        private final HttpClient Client = createHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SeismicActivity.this);
        String data =""; 
//        TextView uiUpdate = (TextView) findViewById(R.id.jsonParsed);
//        TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
        int sizeData = 0;  
//        EditText serverText = (EditText) findViewById(R.id.serverText);
         
         
        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
              
            //Start Progress Dialog (Message)
            
            Dialog.setMessage("Please wait..");
            Dialog.show();
             
            try{
                // Set Request parameter
                data +="&" + URLEncoder.encode("data", "UTF-8") + "="+URLStr;
                     
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
             
        }
  
        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
             
            /************ Make Post Call To Web Server ***********/
            BufferedReader reader=null;
    
                 // Send data 
                try
                { 
                   
                   // Defined URL  where to send data
//                   URL url = new URL(urls[0]);
                      
                  // Send POST data request
                	String SetServerString = "";
                	
                   /*HttpClient httpClient = new DefaultHttpClient();
                   
                   HttpGet httpGet = new HttpGet(urls[0]);
                   
                   ResponseHandler<String> responseHandler = new BasicResponseHandler();
                   SetServerString = httpClient.execute(httpGet, responseHandler);*/
        
                	/**
                	 * New Code starts
                	 */
                   
                	DataLoader dl = new DataLoader();
                    String url = urls[0];
                    HttpResponse response = dl.secureLoadData(url); 

                    StringBuilder sb = new StringBuilder();
                    sb.append("HEADERS:\n\n");

                    Header[] headers = response.getAllHeaders();
                    for (int i = 0; i < headers.length; i++) {
                        Header h = headers[i];
                        sb.append(h.getName()).append(":\t").append(h.getValue()).append("\n");
                    }

                    InputStream is = response.getEntity().getContent();
                    StringBuilder out = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    for (String line = br.readLine(); line != null; line = br.readLine())
                        out.append(line);
                    br.close();

                    sb.append("\n\nCONTENT:\n\n").append(out.toString()); 

                    Log.i("response", sb.toString());
                   /**
                    * New code ends
                    */
//                  URLConnection conn = url.openConnection(); 
//                  conn.setDoOutput(true); 
//                  OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
//                  wr.write( data ); 
//                  wr.flush(); 
               
                  // Get the server response 
                    
                 /* reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                 
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                        {
                               // Append server response in string
                               sb.append(line + "");
                        }*/
                     
                    // Append Server Response To Content String 
                   Content = out.toString();
                   
                   System.out.println("@@@@@@@@@@@@@@@@@"+Content);
                   System.out.println(SetServerString);
                }
                catch(Exception ex)
                {
                    Error = ex.getMessage();
                }
                finally
                {
                    try
                    {
          
                        reader.close();
                    }
        
                    catch(Exception ex) {}
                }
             
            /*****************************************************/
            return null;
        }
          
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
              
            // Close progress dialog
            Dialog.dismiss();
        
            
            
            Gson gson =  new Gson();
            Quakes quake = gson.fromJson(Content, Quakes.class);
            
        	Features [] features = quake.getFeatures();
        	List<Features> featuresList = Arrays.asList(features);
        	for (Features features2 : featuresList) {
				Properties properties = features2.getProperties();
				QuakeDetails td = new QuakeDetails();
	            td.setQuakeTitle(properties.getTitle());
	            String quakeTime = properties.getTime();
	        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        	System.out.println(sdf.format(new Date(Long.parseLong(quakeTime))));
	    		td.setQuakeInfo(getEventHappanedBefore(quakeTime));
	    		td.setLongitude(features2.getGeometry().getCoordinates()[0]);
	    		td.setLatitude(features2.getGeometry().getCoordinates()[1]);
	    		td.setDepth(features2.getGeometry().getCoordinates()[2]);
	    		td.setTime(sdf.format(new Date(Long.parseLong(quakeTime))));
	    		td.setDetailsURL(properties.getDetail());
	    		taskList.add(td);
			}
            
    		taskAdapter.setTasks(taskList);
    		taskAdapter.notifyDataSetChanged();
            
            try{
            if (Error != null) {
                  
//                uiUpdate.setText("Output : "+Error);
//                addItemsToList(Content.substring(0, 10));
                  
            } else {
               
                                 }
                 /****************** End Parse Response JSON Data *************/    
                      
                     //Show Parsed Output on screen (activity)
//                     jsonParsed.setText( OutputData );
                      
                       
        }  catch (Exception e) {
           
                     e.printStackTrace();
                 }
   
                  
             }
        }

	//Event Happened before 
	
	public String getEventHappanedBefore(String eventDateStr){
		
		String eventHappenedBefore;
		
		Date eventDate = new Date(Long.parseLong(eventDateStr));
    	
		Calendar c = Calendar.getInstance();
        Date currentTime = new Date(c.getTimeInMillis());

        long diff = currentTime.getTime() - eventDate.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) ((currentTime.getTime() - eventDate.getTime()) / (1000 * 60 * 60 * 24));

        eventHappenedBefore = diffHours + " Hours "+diffMinutes+" Minutes "+diffSeconds+" Seconds before";
        
        if (diffInDays > 1) {
            System.err.println("Difference in number of days (2) : " + diffInDays);
        } else if (diffHours > 24) {

            System.err.println(">24");
        } else if ((diffHours == 24) && (diffMinutes >= 1)) {
            System.err.println("minutes");
        }
		return eventHappenedBefore;

	}
	
	//creating own http client
	
	public static HttpClient createHttpClient()
	{
	    HttpParams params = new BasicHttpParams();
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	    HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
	    HttpProtocolParams.setUseExpectContinue(params, true);

	    SchemeRegistry schReg = new SchemeRegistry();
	    schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	    schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
	    ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

	    return new DefaultHttpClient(conMgr, params);
	}
}
