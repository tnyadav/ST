package com.tny.volvr.dancer.message;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.JsonResponse;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class ReplayMessages extends BaseFragment{
	private View view;
	com.tny.utils.MyTextView mName,tvmessage;
	EditText mMessagetext;
	ImageView chatUserImage;
	Button mSendbtn,mBackbbtn;
	String userName="",userId="",message="";
	String url;
	private Handler handler;
	public ReplayMessages(String name,String id,String url,String message) {
		this.userName=name;
		this.userId=id;
		this.message=message;
		this.url=url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.replay_message, container, false);
		setContent();

		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				try {
					JSONObject jsonResponse=new JSONObject(JsonResponse.JsonResponse);
					if(jsonResponse.optString("status").equalsIgnoreCase("success"))
					{
						getActivity().onBackPressed();
					}
					else{
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		return view;
	}

	private void setContent() 
	{
		mName=(com.tny.utils.MyTextView)view.findViewById(R.id.chat_newmessage_name);
		mName.setText(userName);
		tvmessage=(com.tny.utils.MyTextView)view.findViewById(R.id.replay_message_message);
		tvmessage.setText(message);
		mSendbtn=(Button)view.findViewById(R.id.chat_sendbtn);
		mSendbtn.setOnClickListener(listener);
		mMessagetext=(EditText)view.findViewById(R.id.chat_msg_textbox);
		
		chatUserImage=(ImageView)view.findViewById(R.id.chat_newmessage_imageView1);
		imageLoader.DisplayImage(url, chatUserImage);
		
		
		mBackbbtn=(Button)view.findViewById(R.id.stripper_newmessage_back);
		mBackbbtn.setOnClickListener(listener);
	}

	List<NameValuePair> entity = new ArrayList<NameValuePair>();
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v)
		{
			String strMessage=mMessagetext.getText().toString();
			switch (v.getId()) {
			case R.id.stripper_newmessage_back:
				getActivity().onBackPressed();
				break;

			case R.id.chat_newmessage_request:
				break;

			case R.id.chat_request:
				break;

		
			case R.id.chat_sendbtn:
				if(!Util1.isFieldEmpty(strMessage))
				{
					UIUtils.showMessage(getActivity(), "Message", "Enter message");
				}
				else
				if(Util1.isNetworkAvailable(getActivity()))
				{
					String user_Id="";
					if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
						user_Id=StripperInfo.user_id;
					if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
					{
					user_Id=Clubprofile.clubs.getClub_id();
					Log.e("club_id",user_Id);
					}
					if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
						user_Id=FanInfo.fanproFileInfo.user_id;
					entity.add(new BasicNameValuePair("action","sendmessage"));
					entity.add(new BasicNameValuePair("to_id",userId));
					entity.add(new BasicNameValuePair("from_id",user_Id));
					
					Log.e("userides", userId+"   "+user_Id);
					entity.add(new BasicNameValuePair("message",strMessage));
					new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "sending...").execute("");
				}
				else
				{
					UIUtils.showNetworkErrorMessage(getActivity());
				}
				break;

			default:
				break;
			}
		}
	};
	public static Bitmap getBitmapFromURL(String src) 
	{
		try {
			Log.e("src", src);
			//			URL url = new URL(src);
			//			HttpURLConnection connection = (HttpURLConnection) url
			//					.openConnection();
			//			connection.setDoInput(true);
			//			connection.connect();
			//			InputStream input = connection.getInputStream();
			//			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			InputStream in = new java.net.URL(src).openStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(in);
			Log.e("Bitmap", "returned");
			return myBitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
