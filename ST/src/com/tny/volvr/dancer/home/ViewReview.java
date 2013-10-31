package com.tny.volvr.dancer.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.adapter.ReviewListAdapter;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class ViewReview extends BaseFragment{
	private  String t="StripperViewReview";
	private View view;
	private ImageView profileimage;
	private com.tny.utils.MyTextView name;
	private Button back;
	private ListView reviewrow;
	private RatingBar ratingBar;
	String userName,userIdStr,rating,imageurl;
	/*public ViewReview(String username,String userId) {
		userName=username;
		userIdStr=userId;
	}*/
	public ViewReview(String username,String userId,String rating,String imageurl) {
		this.userName=username;
		this.userIdStr=userId;
		this.rating=rating;
		this.imageurl=imageurl;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_view_reviews, container, false);
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		try{
			List<NameValuePair> entity = new ArrayList<NameValuePair>();
			entity.add(new BasicNameValuePair("action","getreviews"));
			entity.add(new BasicNameValuePair("user_id",userIdStr));
			new AsyncWebServiceProcessingTask(getActivity(), entity, "Loading",new Callback() {
				
				@Override
				public void run(String result) {
					// TODO Auto-generated method stub
					try {
						JSONObject jsonResponse=new JSONObject(result);
						StripperInfo.ALStripperReviews.clear();
						if(jsonResponse.optString("status").equalsIgnoreCase("success"))
						{
							StripperInfo.parseReviewProfile(jsonResponse);
						}
						else
						{
							UIUtils.showMessage(getActivity(), "Message","No Review found.");
						}
						setContent();
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			}).execute("");
		}
		catch(Exception e)
		{
			UIUtils.showMessage(getActivity(), "Message","Network error has occured. Please check the network status of your phone and retry");
		}
	}
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.stripper_view_reviews_back:
				getActivity().onBackPressed();	
				break;

			default:
				break;
			}

		}
	};

	private void setContent() {
		profileimage=(ImageView)view.findViewById(R.id.stripper_view_reviews_profile_image);
		imageLoader.DisplayImage(imageurl, profileimage);
		
		name=(com.tny.utils.MyTextView)view.findViewById(R.id.stripper_view_reviews_name);
		name.setText(userName);
		back=(Button)view.findViewById(R.id.stripper_view_reviews_back);
		back.setOnClickListener(listener);
		reviewrow=(ListView)view.findViewById(R.id.stripper_view_reviews_list);
		reviewrow.setAdapter(new ReviewListAdapter(mActivity));
		ratingBar=(RatingBar)view.findViewById(R.id.ratingbar);
		ratingBar.setRating(Float.parseFloat(rating));
		ratingBar.setIsIndicator(true);
		
		reviewrow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				    String providerName=((TextView) arg1.findViewById(R.id.view_review_row_name)).getText().toString();
					String providerComment=((TextView) arg1.findViewById(R.id.view_review_row_review)).getText().toString();
					ImageView image= ((ImageView) arg1.findViewById(R.id.view_review_row_image));
					BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
					Bitmap bm = drawable.getBitmap();
					bm=Util1.scaleDownBitmap(bm,100,getActivity());
					mActivity.pushFragments(AppConstants.TAB_HOME, new DancerViewReviewFull(providerName,providerComment,bm),true,true);
			}
		});
	

	}
	/*private void setRating() {
		ImageView r1=(ImageView)view.findViewById(R.id.stripper_view_reviews_r1);
		ImageView r2=(ImageView)view.findViewById(R.id.stripper_view_reviews_r2);
		ImageView r3=(ImageView)view.findViewById(R.id.stripper_view_reviews_r3);
		ImageView r4=(ImageView)view.findViewById(R.id.stripper_view_reviews_r4);
		ImageView r5=(ImageView)view.findViewById(R.id.stripper_view_reviews_r5);
		int r=0;
		int size=StripperInfo.ALStripperReviews.size();
		for (int i = 0; i <size ; i++) {
			r=r+Integer.parseInt(StripperInfo.ALStripperReviews.get(i).getReview_rating());

		}
		Log.i(t, ""+r);
		try{
			int rating=r/size;
			if (rating==1) {
				r1.setImageResource(R.drawable.star_act);	
			}else if (rating==2) {
				r1.setImageResource(R.drawable.star_act);	
				r2.setImageResource(R.drawable.star_act);	
			}else if (rating==3) {
				r1.setImageResource(R.drawable.star_act);	
				r2.setImageResource(R.drawable.star_act);
				r3.setImageResource(R.drawable.star_act);
			}else if (rating==4) {
				r1.setImageResource(R.drawable.star_act);	
				r2.setImageResource(R.drawable.star_act);
				r3.setImageResource(R.drawable.star_act);
				r4.setImageResource(R.drawable.star_act);
			}else if (rating==5) {
				r1.setImageResource(R.drawable.star_act);	
				r2.setImageResource(R.drawable.star_act);
				r3.setImageResource(R.drawable.star_act);
				r4.setImageResource(R.drawable.star_act);
				r5.setImageResource(R.drawable.star_act);
			}
			com.tny.utils.MyTextView ratingtext=(com.tny.utils.MyTextView)view.findViewById(R.id.stripper_view_reviews_ratingtext);
			ratingtext.setText(rating+"/5");
		}
		catch(Exception e){

		}
	}*/
}
