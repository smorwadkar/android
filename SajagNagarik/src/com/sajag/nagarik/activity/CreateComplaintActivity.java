/**
 * 
 */
package com.sajag.nagarik.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sajag.nagarik.R;
/**
 * @author smorwadkar
 *
 */
public class CreateComplaintActivity extends Activity implements OnItemSelectedListener,OnClickListener{

	private ImageView capturedImage1;
	private ImageView capturedImage2;
	private Button btnCaptureImage;
	
	private Uri uriFilePath;
	private Uri compressedUriFilePath;
	private Integer imageCount = 0;
	private ScrollView imagesScrolLayout;
	private LinearLayout image1Layout;
	private LinearLayout image2Layout;
	private Button btnEditImage1;
	private Button btnEditImage2;
	private Button submitBtn;
	private Spinner deptSpinner ;
	private EditText commentsEditText;
	
	private SharedPreferences sharedpreferences;
	private String userMobile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 if (savedInstanceState != null) {
	         if (uriFilePath == null && savedInstanceState.getString("uri_file_path") != null) {
	             uriFilePath = Uri.parse(savedInstanceState.getString("uri_file_path"));
	         }
	    } 
		setContentView(R.layout.activity_create_complaint);
		sharedpreferences = getSharedPreferences("sajag", Context.MODE_PRIVATE);
		userMobile = sharedpreferences.getString("userMobileNo", "All");
		deptSpinner = (Spinner) findViewById(R.id.dept_spinner);
		
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
	      
	      commentsEditText = (EditText) findViewById(R.id.details);
	      
	      imagesScrolLayout = (ScrollView) findViewById(R.id.imagesScrolLayout);
	      imagesScrolLayout.setVisibility(View.INVISIBLE);
	      
	      btnCaptureImage = (Button) findViewById(R.id.captureImgBtn);
	      btnCaptureImage.setOnClickListener(this);
	      
	      capturedImage1 = (ImageView) findViewById(R.id.imageView1);
	      capturedImage2 = (ImageView) findViewById(R.id.imageView2);
	      if (uriFilePath != null){
	    	  Bitmap photo = BitmapFactory.decodeFile(uriFilePath.getPath()); 
	            capturedImage1.setImageBitmap(photo);
	      }
	      
	      image1Layout = (LinearLayout) findViewById(R.id.image1Layout);
	      image2Layout = (LinearLayout) findViewById(R.id.image2Layout);
	      image1Layout.setVisibility(View.INVISIBLE);
	      image2Layout.setVisibility(View.INVISIBLE);
	      
	      btnEditImage1 =  (Button) findViewById(R.id.editImgBtn1);
	      btnEditImage1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				captureImage();
			}
		});
	      btnEditImage2 =  (Button) findViewById(R.id.editImgBtn2);
	      btnEditImage2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				captureImage();
			}
		});
	      
	      submitBtn = (Button) findViewById(R.id.submitBtn);
	      submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				invokeWS();
				/*Intent listComplaints = new Intent(getApplicationContext(), ComplaintsListActivity.class);
				startActivity(listComplaints);*/
			}
		});
	}
	
	@Override
	   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	      // On selecting a spinner item
	      String item = parent.getItemAtPosition(position).toString();
	      
	      // Showing selected spinner item
	      Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
	   }

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == btnCaptureImage.getId()){
			
			/**
			 * to use in only portrait mode
			 */
			/*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
            startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
			
			/**
			 * To save state on rotation
			 */
			
			
			captureImage();
		}
	}
	
	private void captureImage() {
		// TODO Auto-generated method stub
		PackageManager packageManager = this.getPackageManager();
		if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
		    File mainDirectory = new File(Environment.getExternalStorageDirectory(), "sajag/tmp");
		         if (!mainDirectory.exists())
		             mainDirectory.mkdirs();

		          Calendar calendar = Calendar.getInstance();

		          uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG_" + calendar.getTimeInMillis() +".jpg"));
		          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		          intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
		          startActivityForResult(intent, 1);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (resultCode == Activity.RESULT_OK) {  
            
        	/**
			 * to use in only portrait mode
			 */
        	/*Bitmap photo = (Bitmap) data.getExtras().get("data"); 
            capturedImage.setImageBitmap(photo);*/
        	
        	/**
        	 * To save state on rotation
        	 */
        	String filePath = uriFilePath.getPath();
        	
        	compressedUriFilePath = Uri.fromFile(new File(this.compressImage(filePath)));
        	
        	File fdelete = new File(uriFilePath.getPath());
        	if (fdelete.exists()) {
        	    if (fdelete.delete()) {
        	        System.out.println("file Deleted :" + uriFilePath.getPath());
        	    } else {
        	        System.out.println("file not Deleted :" + uriFilePath.getPath());
        	    }
        	}
        	
        	uriFilePath = compressedUriFilePath;
        	Bitmap photo = BitmapFactory.decodeFile(compressedUriFilePath.getPath()); 
            
        	if(imageCount<1){
        		capturedImage1.setImageBitmap(photo);
        		imageCount++;
        		image1Layout.setVisibility(View.VISIBLE);
        	}else{
        		capturedImage2.setImageBitmap(photo);
        		imageCount--;
        		image2Layout.setVisibility(View.VISIBLE);
        		btnCaptureImage.setVisibility(View.INVISIBLE);
        	}
        	imagesScrolLayout.setVisibility(View.VISIBLE);
        }  
        
    } 
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	     if (uriFilePath != null)
	         outState.putString("uri_file_path", uriFilePath.toString());
	     super.onSaveInstanceState(outState);
	}
	
	
	
	public String compressImage(String imageUri) {
		 
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;
 
        BitmapFactory.Options options = new BitmapFactory.Options();
 
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
 
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
 
//      max Height and width values of the compressed image is taken as 816x612
 
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;
 
//      width and height values are set maintaining the aspect ratio of the image
 
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;                actualWidth = (int) (imgRatio * actualWidth);               actualHeight = (int) maxHeight;             } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
 
            }
        }
 
//      setting inSampleSize value allows to load a scaled down version of the original image
 
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
 
//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
 
//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
 
        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
 
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
 
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
 
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
 
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
 
//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
 
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
 
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        return filename;
 
    }
	
	public String getFilename() {
	    File file = new File(Environment.getExternalStorageDirectory().getPath(), "sajag/tmp");
	    if (!file.exists()) {
	        file.mkdirs();
	    }
	    String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
	    return uriSting;
	 
	}
	
	private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
        
        
    }
	
	public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	 
	    if (height > reqHeight || width > reqWidth) {
	        final int heightRatio = Math.round((float) height/ (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }       final float totalPixels = width * height;       final float totalReqPixelsCap = reqWidth * reqHeight * 2;       while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
	        inSampleSize++;
	    }
	 
	    return inSampleSize;
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
		
		try {
			params.put("mobileNo",userMobile);
			params.put("comments",commentsEditText.getText().toString());
			params.put("complaintStatus","In Progress");
			params.put("department_id", String.valueOf(deptSpinner.getSelectedItemPosition()));
			params.put("image1",new File(compressedUriFilePath.getPath()),"application/octet-stream");
			params.put("image2",new File(compressedUriFilePath.getPath()),"application/octet-stream");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		String serverIp = getResources().getString(R.string.server_ip);
		
		client.post("http://" + serverIp + ":8080/sajag-complaints-management/api/request/createRequest", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // JSON Object
                  /*JSONObject userObj = new JSONObject(response);*/
                  // When the JSON response has status boolean value assigned with true
//                        if(userObj.getBoolean("lastName")){
                  /*String lastName = userObj.getString("lastName");*/
				Toast.makeText(getApplicationContext(), "Request created successfully", Toast.LENGTH_LONG).show();
				Intent listComplaints = new Intent(getApplicationContext(), ComplaintsListActivity.class);
				startActivity(listComplaints);
				// Navigate to Home screen
//                            navigatetoHomeActivity();
//                        } 
                  // Else display error message
                  /*else{
//                            errorMsg.setText(obj.getString("error_msg"));
				Toast.makeText(getApplicationContext(), userObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                  }*/
            	
//            	Toast.makeText(getApplicationContext(), "User : Shivdatta is registered successfully", Toast.LENGTH_LONG).show();
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
		
	}
}
