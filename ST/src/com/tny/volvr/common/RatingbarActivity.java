package com.tny.volvr.common;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class RatingbarActivity extends BaseFragment {
private View view;
RatingBar ratingBar_default;
TextView text;
Button cancel,send;
EditText review;
String mstrUserId="",rating;
public RatingbarActivity(String userId) {
	mstrUserId=userId;
}

@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.rate, container, false);
		 setContent();
		 
		
		 
		 return view;
		
	}
	private void setContent() {
		 text = (TextView)view.findViewById(R.id.rate_text);
	     ratingBar_default = (RatingBar)view.findViewById(R.id.ratingbar);
	     cancel=(Button)view.findViewById(R.id.rate_cancel);
	     cancel.setOnClickListener(onClickListener);
	     send=(Button)view.findViewById(R.id.rate_send);
	     send.setOnClickListener(onClickListener);
	     review=(EditText)view.findViewById(R.id.rate_review_text);
	     ratingBar_default.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){ 
	    	    @Override
	    	    public void onRatingChanged(RatingBar ratingBar, float Rating,
	    	      boolean fromUser) {
	    	     text.setText(Rating+"/5"); 
	    	     rating=""+Rating;
	    	    }}); 
	}     
OnClickListener onClickListener=new OnClickListener() {
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rate_cancel:
			getActivity().onBackPressed();
			break;
		case R.id.rate_send:
			if(Util1.isNetworkAvailable(getActivity())){
				String user_Id="";
				 if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
					 user_Id=StripperInfo.user_id;
		         	if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
		         		 user_Id=StripperInfo.user_id;
		         	if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
		         		 user_Id=FanInfo.fanproFileInfo.user_id;
				List<NameValuePair> entity = new ArrayList<NameValuePair>();
				entity.add(new BasicNameValuePair("action","providereview"));
				entity.add(new BasicNameValuePair("review_to_id",mstrUserId));
				entity.add(new BasicNameValuePair("review_from_id",user_Id));
				entity.add(new BasicNameValuePair("review_rating",rating));
				entity.add(new BasicNameValuePair("review_comments",text.getText().toString().trim()));
				new AsyncWebServiceProcessingTask(getActivity(), entity, "Loading",new Callback() {
					
					@Override
					public void run(String result) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonResponse=new JSONObject(result);
							/*if(jsonResponse.optString("status").equalsIgnoreCase("success"))
							{
								Toast.makeText(mActivity,jsonResponse.optString("msg"), Toast.LENGTH_SHORT).show();
								
							}else if (jsonResponse.optString("status").equalsIgnoreCase("error")) {*/
								Toast.makeText(mActivity,jsonResponse.optString("msg"), Toast.LENGTH_SHORT).show();
								getActivity().onBackPressed();
						/*	}
							else{
								
							}*/
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).execute("");
			}
			else
				UIUtils.showNetworkErrorMessage(getActivity());
			break;

		default:
			break;
		}
	}
};
      
       
    
}
