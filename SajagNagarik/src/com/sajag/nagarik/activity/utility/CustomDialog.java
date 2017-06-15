package com.sajag.nagarik.activity.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sajag.nagarik.R;

public class CustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public Button yes;
	private ImageView image1;
	private ImageView image2;
	List<ImageView> images = new ArrayList<ImageView>();
	
	public CustomDialog(Activity a) {
		super(a);
		this.c = a;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);
		yes = (Button) findViewById(R.id.btn_yes);
		yes.setOnClickListener(this);
		image1 = (ImageView) findViewById(R.id.detailImageView1);
		image2 = (ImageView) findViewById(R.id.detailImageView2);
		images.add(image1);
		images.add(image2);
		invokeFetchImagesWS();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_yes:
			c.finish();
			break;
		}
		dismiss();
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
		params.add("complaintId", String.valueOf(51));
//		final ProgressDialog Dialog = new ProgressDialog(COmpla);
//		Dialog.setMessage("Please wait..");
//		Dialog.show();

		client.get(
				"http://139.59.20.112:8080/sajag-complaints-management/api/request/getComplaintImages",
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

						Toast.makeText(c.getApplicationContext(),
								"All images fetched", Toast.LENGTH_LONG)
								.show();
					}

					// When the response returned by REST has Http response code
					// other than '200'
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
//						Dialog.dismiss();
						// Hide Progress Dialog
						// prgDialog.hide();
						// When Http response code is '404'
						if (statusCode == 404) {
							Toast.makeText(c.getApplicationContext(),
									"Requested resource not found",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code is '500'
						else if (statusCode == 500) {
							Toast.makeText(c.getApplicationContext(),
									"Something went wrong at server end",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code other than 404, 500
						else {
							Toast.makeText(
									c.getApplicationContext(),
									"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
									Toast.LENGTH_LONG).show();
						}
					}
				});

	}
	
	/*public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
	    Map<Integer, Object> retMap = new HashMap<Integer, Object>();

	    if(json != JSONObject.NULL) {
	        retMap = toMap(json);
	    }
	    return retMap;
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<Integer, Object> map = new HashMap<Integer, Object>();

	    Iterator<Integer> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}*/
}