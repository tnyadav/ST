package com.tny.volvr.dancer.home;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tny.utils.Constant;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class UpdateProfileImage extends BaseFragment{

	View view;
	private ImageView profileImage;
	private TextView name;
	private  Button select,update;
	private String strImgepath;
	private Bundle bundle;
	private String[] value;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view       =   inflater.inflate(R.layout.change_profileimage, container, false);
	 this.bundle=getArguments();
	 value=bundle.getStringArray("value");
	
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContent();
	}
void setContent()
{
profileImage=(ImageView)view.findViewById(R.id.image);

if(value[2].contains("http"))
{
	imageLoader.DisplayImage( value[2],profileImage);
}
else 
{
	profileImage.setImageBitmap(Util1.decodeFile(new File(value[2])));
}

name=(TextView)view.findViewById(R.id.name);
name.setText(value[1]);
select=(Button)view.findViewById(R.id.select);
select.setOnClickListener(listener);
update=(Button)view.findViewById(R.id.update);
update.setOnClickListener(listener);
}

private OnClickListener listener        =   new OnClickListener(){
	@Override
	public void onClick(View v){
	
		switch (v.getId()) {
		case R.id.select:
			Intent i = new Intent(
					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			getActivity().startActivityForResult(i, 1);
			break;
		case R.id.update:
		new ImageUpdatingAsyncTask().execute();
			break;
		}
	}
 };
 
 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1&&resultCode==Activity.RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = mActivity.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
			cursor.moveToFirst(); 
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		    strImgepath = cursor.getString(columnIndex);
		    cursor.close();     
		 //   imageLoader.DisplayImage(strImgepath, profileImage);
		   // profileImage.setImageBitmap(BitmapFactory.decodeFile(strImgepath));
		    profileImage.setImageBitmap(Util1.decodeFile(new File(strImgepath)));
	}
 }

 
 public class ImageUpdatingAsyncTask extends AsyncTask<Void,Void , String>{

		private ProgressDialog bar; 
		@Override
		protected String doInBackground(Void... params) {
			String responseData="no response";
			Log.e("image name", new File(strImgepath).getName());
			byte[] bytearray_of_drawable=null;
			try{
				
					Drawable d = Drawable.createFromPath(strImgepath);
					Bitmap drawable_bitmap = ((BitmapDrawable) d)
							.getBitmap();
					ByteArrayOutputStream outstream = new ByteArrayOutputStream();
					drawable_bitmap.compress(Bitmap.CompressFormat.PNG,
							100, outstream);
					bytearray_of_drawable = outstream.toByteArray();
				
				
					HttpPost httppost = new HttpPost(Constant.SERVER_URL);
					HttpParams params1 = new BasicHttpParams();
					params1.setParameter(
							CoreProtocolPNames.PROTOCOL_VERSION,
							HttpVersion.HTTP_1_1);
					HttpClient httpclient = new DefaultHttpClient(params1);
					MultipartEntity multipartEntity = new MultipartEntity();
					
					multipartEntity.addPart("action", new StringBody(
							"profile_img_update"));
					multipartEntity.addPart("user_id", new StringBody(
							 value[0]));
					multipartEntity.addPart("user_avatar",
					   new ByteArrayBody(bytearray_of_drawable,
											"image/bmp", "one.png"));
					
					
					httppost.setEntity(multipartEntity);
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity getresponse = response.getEntity();
					String jsonstr = EntityUtils.toString(getresponse);
					Log.d("response", "" + jsonstr);
					responseData=jsonstr;
			   
			}
			catch (Exception e) 
			{
				e.printStackTrace();

			}
			return responseData;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			bar.dismiss();
			if (result.equals("no response")) {
				UIUtils.showNetworkErrorMessage(mActivity);
			}else {
				Toast.makeText(getActivity(), "Profile image updated successfuly", Toast.LENGTH_SHORT).show();
				StripperInfo.user_avatar=strImgepath;
				mActivity.onBackPressed();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar = new ProgressDialog(mActivity);
			bar.setMessage("Updating picture.............");
			bar.setCancelable(false);
			bar.show();

		}

	}
 
}
