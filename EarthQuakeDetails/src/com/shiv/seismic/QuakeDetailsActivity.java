package com.shiv.seismic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiv.earthquakedetails.R;
import com.shiv.secure.DataLoader;
import com.shiv.seismic.detailedquake.Cap;
import com.shiv.seismic.detailedquake.DetailedQuakes;
import com.shiv.seismic.detailedquake.MainEventProperties;
import com.shiv.seismic.detailedquake.Products;
import com.shiv.seismic.pojo.QuakeDetails;

public class QuakeDetailsActivity extends Activity {

	TextView tv3;
	TextView tv4;
	TextView tv5;
	Button btn1;
	Button btnNBCities;
	Button btnTectSummary;
	String URLStr = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/us10002bd8.geojson";
	QuakeDetails quakeDetails;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quake_details_layout);
		Intent intent = getIntent();
		quakeDetails = (QuakeDetails) intent.getExtras().getSerializable("quakeDetails");
		System.out.println(quakeDetails.quakeTitle);
		tv3 = (TextView)findViewById(R.id.textView3);
		tv4 = (TextView)findViewById(R.id.textView4);
		tv5 = (TextView)findViewById(R.id.textView5);
		tv3.setText(quakeDetails.getQuakeTitle());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		tv4.setText(quakeDetails.getTime());
		tv5.setText("Epicentre is at Depth "+quakeDetails.getDepth()+" KMs");
		btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Calling Map Activity
				String geoUri = "http://maps.google.com/maps?q=loc:" + quakeDetails.getLatitude() + "," + quakeDetails.getLongitude() + " (" + "Epicentre" + ")"+ "&z=10";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
				startActivity(intent);
			}
		});
		
		btnNBCities = (Button) findViewById(R.id.buttonNearByCities);
		btnNBCities.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				new LongOperation().execute(quakeDetails.getDetailsURL());
				new LongOperation().execute(quakeDetails.getDetailsURL());
			}
		});
		btnTectSummary = (Button) findViewById(R.id.buttonTectSummary);
		btnTectSummary.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new LongOperationTectSummary().execute(quakeDetails.getDetailsURL());
			}
		});
	}
	
//Nearby Cities	
private class LongOperation  extends AsyncTask<String, Void, Void> {
        
        // Required initialization
         
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(QuakeDetailsActivity.this);
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
                   URL url = new URL(urls[0]);
                      
                  // Send POST data request
        
                  /*URLConnection conn = url.openConnection(); 
                  conn.setDoOutput(true); 
                  OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
                  wr.write( data ); 
                  wr.flush(); 
               
                  // Get the server response 
                    
                  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                 
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                        {
                               // Append server response in string
                               sb.append(line + "");
                        }
                     */
                   
               	/**
               	 * New Code starts
               	 */
                  
               	DataLoader dl = new DataLoader();
                   HttpResponse response = dl.secureLoadData(urls[0]); 

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
                    // Append Server Response To Content String 
                   Content = out.toString();
                   
                   System.out.println("@@@@@@@@@@@@@@@@@"+Content);
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
//            Quakes quake = gson.fromJson(Content, Quakes.class);
            DetailedQuakes quake = gson.fromJson(Content, DetailedQuakes.class);
            MainEventProperties mainEventProperties = quake.getProperties();
            Products products = mainEventProperties.getProducts();
            
//            String nearByCitiesPageURL = "http://earthquake.usgs.gov/realtime/product/nearby-cities/us"+mainEventProperties.getCode()+"/us/"+updateTime+"/nearby-cities.inc.html";
            if(products.getNearbyCities()!=null){
            String nearByCitiesPageURL = products.getNearbyCities()[0].getContents().getNearByCitiesHtml().getUrl();
            
            Intent nbCitiesIntent = new Intent(getApplicationContext(), QuakeFullDetailsActivity.class);
            
            nbCitiesIntent.putExtra("nearByCitiesPageURL", nearByCitiesPageURL);
            
            startActivity(nbCitiesIntent);
            }else{
            	Toast.makeText(getApplicationContext(), "No Data available", Toast.LENGTH_LONG).show();
            }
            
            try{
            if (Error != null) {
                  
            } else {
               
                                 }
                 /****************** End Parse Response JSON Data *************/    
                      
                       
        }  catch (Exception e) {
           
                     e.printStackTrace();
                 }
   
                  
             }
        }

	
//Tectonic Summary Async

private class LongOperationTectSummary  extends AsyncTask<String, Void, Void> {
    
    // Required initialization
     
    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    private String Error = null;
    private ProgressDialog Dialog = new ProgressDialog(QuakeDetailsActivity.this);
    String data =""; 
//    TextView uiUpdate = (TextView) findViewById(R.id.jsonParsed);
//    TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
    int sizeData = 0;  
//    EditText serverText = (EditText) findViewById(R.id.serverText);
     
     
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
               URL url = new URL(urls[0]);
                  
              // Send POST data request
    
              /*URLConnection conn = url.openConnection(); 
              conn.setDoOutput(true); 
              OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
              wr.write( data ); 
              wr.flush(); 
           
              // Get the server response 
                
              reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
              StringBuilder sb = new StringBuilder();
              String line = null;
             
                // Read Server Response
                while((line = reader.readLine()) != null)
                    {
                           // Append server response in string
                           sb.append(line + "");
                    }*/
               
               /**
           	 * New Code starts
           	 */
              
           	DataLoader dl = new DataLoader();
               HttpResponse response = dl.secureLoadData(urls[0]); 

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
                 
                // Append Server Response To Content String 
               Content = out.toString();
               
               System.out.println("@@@@@@@@@@@@@@@@@"+Content);
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
//        Quakes quake = gson.fromJson(Content, Quakes.class);
        DetailedQuakes quake = gson.fromJson(Content, DetailedQuakes.class);
        MainEventProperties mainEventProperties = quake.getProperties();
        Products products = mainEventProperties.getProducts();
//        String tectonicSummaryURL = "http://earthquake.usgs.gov/realtime/product/tectonic-summary/us"+mainEventProperties.getCode()+"/us/"+updateTime+"/tectonic-summary.inc.html";
        
        if(products.gettectonicSummary()!=null){
        String tectonicSummaryURL = products.gettectonicSummary()[0].getContents().getTechHtml().getUrl();
        
        Intent nbCitiesIntent = new Intent(getApplicationContext(), QuakeFullDetailsActivity.class);
        
        nbCitiesIntent.putExtra("nearByCitiesPageURL", tectonicSummaryURL);
        
        startActivity(nbCitiesIntent);
        }else{
        	Toast.makeText(getApplicationContext(), "No Data available", Toast.LENGTH_LONG).show();
        }
        
        try{
        if (Error != null) {
              
              
        } else {
           
                             }
             /****************** End Parse Response JSON Data *************/    
                  
                   
    }  catch (Exception e) {
       
                 e.printStackTrace();
             }

              
         }
    }

	
}
