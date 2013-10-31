package com.tny.volvr.dancer.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tny.utils.ProjectUtils;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class DancerViewReviewFull extends BaseFragment{
private static String t="StripperViewReviewFull";
private View view;
private Button back;
private Bitmap imageBitmap;
String providerName="",providerComment="";
	public DancerViewReviewFull(String providerName, String providerComment,
		Bitmap bm) {
		this.providerName=providerName;
		this.providerComment=providerComment;
		imageBitmap=bm;
}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		 view       =   inflater.inflate(R.layout.stripper_view_reviews_full, container, false);
	        setContent();
	       
	        return view;
	}
	
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
				case R.id.stripper_view_reviews_full_back:
				getActivity().onBackPressed();	
				break;
				case R.id.stripper_view_reviews_full_report:
						
					break;
				
		
			default:
				break;
			}

		}
	};
	
	
	
private void setContent() {
	ImageView profileimage,reviewerimage;
	com.tny.utils.MyTextView name,reviewername,reviewComment;
	Button report;
	profileimage=(ImageView)view.findViewById(R.id.stripper_view_reviews_profile_image);
	profileimage.setImageBitmap(ProjectUtils.getBitmapFromURL(StripperInfo.user_avatar));
	name=(com.tny.utils.MyTextView)view.findViewById(R.id.stripper_view_reviews_name);
	name.setText(StripperInfo.user_fullname);
	reviewerimage=(ImageView)view.findViewById(R.id.Stripper_view_reviews_full_reviewerimage);
//	reviewerimage.setImageBitmap(imageBitmap);
	reviewername=(com.tny.utils.MyTextView)view.findViewById(R.id.Stripper_view_reviews_full_reviewername);
	reviewername.setText(providerName);
	reviewComment=(com.tny.utils.MyTextView)view.findViewById(R.id.Stripper_view_reviews_full_reviewer_comment);
	reviewComment.setText(providerComment);
	report=(Button)view.findViewById(R.id.stripper_view_reviews_full_report);
	report.setOnClickListener(listener);
	back=(Button)view.findViewById(R.id.stripper_view_reviews_full_back);
	back.setOnClickListener(listener);
	setRating();
	
	
}
private void setRating() {
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
}
}
