/*package com.sajag.nagarik.ws;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

private class LongOperation extends AsyncTask<String, Void, Void> {
	// Required initialization

	private final HttpClient Client = createHttpClient();
	private String Content;
	private String Error = null;
	private ProgressDialog Dialog = new ProgressDialog(SeismicActivity.this);
	String data = "";
	// TextView uiUpdate = (TextView) findViewById(R.id.jsonParsed);
	// TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
	int sizeData = 0;

	// EditText serverText = (EditText) findViewById(R.id.serverText);

	protected void onPreExecute() {
		// NOTE: You can call UI Element here.

		// Start Progress Dialog (Message)

		Dialog.setMessage("Please wait..");
		Dialog.show();

		try {
			// Set Request parameter
			data += "&" + URLEncoder.encode("data", "UTF-8") + "=" + URLStr;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Call after onPreExecute method
	protected Void doInBackground(String... urls) {

		*//************ Make Post Call To Web Server ***********//*
		BufferedReader reader = null;

		// Send data
		try {
			// Send POST data request
			String SetServerString = "";

			*//**
			 * New Code starts
			 *//*

			DataLoader dl = new DataLoader();
			String url = urls[0];
			HttpResponse response = dl.secureLoadData(url);

			StringBuilder sb = new StringBuilder();
			sb.append("HEADERS:\n\n");

			Header[] headers = response.getAllHeaders();
			for (int i = 0; i < headers.length; i++) {
				Header h = headers[i];
				sb.append(h.getName()).append(":\t").append(h.getValue())
						.append("\n");
			}

			InputStream is = response.getEntity().getContent();
			StringBuilder out = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (String line = br.readLine(); line != null; line = br
					.readLine())
				out.append(line);
			br.close();

			sb.append("\n\nCONTENT:\n\n").append(out.toString());

			Log.i("response", sb.toString());
			*//**
			 * New code ends
			 *//*
			Content = out.toString();

			System.out.println("@@@@@@@@@@@@@@@@@" + Content);
			System.out.println(SetServerString);
		} catch (Exception ex) {
			Error = ex.getMessage();
		} finally {
			try {

				reader.close();
			}

			catch (Exception ex) {
			}
		}

		*//*****************************************************//*
		return null;
	}

	protected void onPostExecute(Void unused) {
		// NOTE: You can call UI Element here.

		// Close progress dialog
		Dialog.dismiss();

		try {
			if (Error != null) {

			} else {

			}
			*//****************** End Parse Response JSON Data *************//*

		} catch (Exception e) {

			e.printStackTrace();
		}

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
}*/