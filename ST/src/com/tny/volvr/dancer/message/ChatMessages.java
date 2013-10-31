package com.tny.volvr.dancer.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class ChatMessages extends BaseFragment{
	private View view;
	com.tny.utils.MyTextView mName,mrequest;
	EditText mMessagetext;
	ImageView chatUserImage;
	Button mSendbtn,mBackbbtn,mreportbtn,mblockbtn,mreport1;
	String userName="",userId="";
	String url;
	@SuppressLint("ValidFragment")
	public ChatMessages(String name,String id,String url) {
		userName=name;
		userId=id;
		this.url=url;
	}
	public ChatMessages() {
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_new_message, container, false);
		setContent();
		return view;
	}

	private void setContent() 
	{
		mName=(com.tny.utils.MyTextView)view.findViewById(R.id.chat_newmessage_name);
		mName.setText(userName);
		mrequest=(com.tny.utils.MyTextView)view.findViewById(R.id.chat_newmessage_request);
		mrequest.setOnClickListener(listener);
		mSendbtn=(Button)view.findViewById(R.id.chat_sendbtn);
		mSendbtn.setOnClickListener(listener);
		mMessagetext=(EditText)view.findViewById(R.id.chat_msg_textbox);
		mreportbtn=(Button)view.findViewById(R.id.chat_reportBtn);
		mreportbtn.setOnClickListener(listener);

		mblockbtn=(Button)view.findViewById(R.id.chat_blockBtn);
		mblockbtn.setOnClickListener(listener);

		mreport1=(Button)view.findViewById(R.id.chat_request);
		mreport1.setOnClickListener(listener);
		chatUserImage=(ImageView)view.findViewById(R.id.chat_newmessage_imageView1);
		imageLoader.DisplayImage(url, chatUserImage);
		Log.e("chat message##############", "####"+url);
	
		
		if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
		{
			mreportbtn.setVisibility(View.INVISIBLE);
			mblockbtn.setVisibility(View.INVISIBLE);
		}
		if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
		{
			mreport1.setVisibility(View.INVISIBLE);
			mrequest.setVisibility(View.INVISIBLE);
			mrequest.setVisibility(View.GONE);
		}
		if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
		{
			mreportbtn.setVisibility(View.INVISIBLE);
			mblockbtn.setVisibility(View.INVISIBLE);
			mrequest.setVisibility(View.GONE);
		}
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

			case R.id.chat_blockBtn:
				break;

			case R.id.chat_reportBtn:
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
				//	Toast.makeText(mActivity,userId, 3).show();
					entity.add(new BasicNameValuePair("action","sendmessage"));
					entity.add(new BasicNameValuePair("to_id",userId));
					entity.add(new BasicNameValuePair("from_id",user_Id));
					
					Log.e("userides", userId+"   "+user_Id);
					entity.add(new BasicNameValuePair("message",strMessage));
					new AsyncWebServiceProcessingTask(getActivity(), entity, "sending...",new Callback() {
						
						@Override
						public void run(String result) {
							// TODO Auto-generated method stub
							try {
								JSONObject jsonResponse=new JSONObject(result);
								if(jsonResponse.optString("status").equalsIgnoreCase("success"))
								{
									getActivity().onBackPressed();
									//mActivity.mStacks.get(mActivity.mCurrentTab).pop();
								//	getActivity().onBackPressed();
								//	mActivity.mStacks.get(mActivity.mCurrentTab).pop();
									//mActivity.pushFragments(AppConstants.TAB_MESSAGES, new InboxActivity(), true,true);
									
									//getActivity().onBackPressed();
								//	mActivity.mStacks.get(mActivity.mCurrentTab).pop();
									
								}
								else{
									Toast.makeText(mActivity, "Problem in sending message", Toast.LENGTH_SHORT).show();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).execute("");
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
	
}
