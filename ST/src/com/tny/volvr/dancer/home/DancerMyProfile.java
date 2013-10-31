package com.tny.volvr.dancer.home;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.WorkingAt;
import com.volvr.search.R;

public class DancerMyProfile extends BaseFragment {

	private TextView name;
	private TextView rece;
	private TextView age;
	private TextView status;
	private TextView rateing;
	private TextView viewreviews;
	private TextView viewallinfo;
	private TextView currency;
	private TextView address1;
	private TextView address2;
	private Button edit;
	private Button morepicture;
	private Button mytravelstatus;
	
	private Button addresspre;
	private Button addressnext;
	private ImageView profileimage;
	private View view;
	private static String TAG= "StripperMyProfile";
	private static int i;
	//Handler handler;
	private RatingBar ratingBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.stripper_myprofile, container, false);
		//setContent();

		return view;
	}
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	setContent();
	
}
	private void setContent() 
	{
		name = (TextView) view.findViewById(R.id.stripper_profile_name);
		name.setText(StripperInfo.user_fullname);
		rece = (TextView) view.findViewById(R.id.stripper_profile_race);
		rece.setText("Ethnicity "+StripperInfo.race);
		age = (TextView) view.findViewById(R.id.stripper_profile_age);
		Log.i(TAG, StripperInfo.user_birthday);
		

		status = (TextView) view.findViewById(R.id.stripper_profile_status);
		// status.setText("Stats: "+StripperInfo.stats);
		status.setText("Stats: " + StripperInfo.stats);
		rateing = (TextView) view
				.findViewById(R.id.stripper_profile_rateingtextview);
		String rating=StripperInfo.user_avg_rating;
		Log.e("rating", rating);
		rateing.setText(""+Float.parseFloat(StripperInfo.user_avg_rating)+"/5");
		viewreviews = (TextView) view
				.findViewById(R.id.stripper_profile_viewreview);
		viewreviews.setOnClickListener(listener);
		viewallinfo = (TextView) view
				.findViewById(R.id.stripper_profile_viewallinfo);
		viewallinfo.setVisibility(View.INVISIBLE);
		viewallinfo.setOnClickListener(listener);
		currency = (TextView) view
				.findViewById(R.id.stripper_profile_strippercurrencytext);
		address1 = (TextView) view.findViewById(R.id.stripper_profile_address1);
		// address1.setText(StripperInfo.location);
		address2 = (TextView) view.findViewById(R.id.stripper_profile_address2);
		// address2.setText(StripperInfo.);
		/*
		 * address3=(TextView)view.findViewById(R.id.stripper_profile_address3);
		 * // address3.setText(StripperInfo.zip);
		 * address4=(TextView)view.findViewById(R.id.stripper_profile_address4);
		 */
		edit = (Button) view.findViewById(R.id.stripper_Profile_edit);
		edit.setOnClickListener(listener);
		morepicture = (Button) view
				.findViewById(R.id.stripper_profile_morepicture);
		morepicture.setOnClickListener(listener);
		mytravelstatus = (Button) view
				.findViewById(R.id.stripper_profile_mytravlestatus);
		mytravelstatus.setOnClickListener(listener);
		//buy = (Button) view.findViewById(R.id.stripper_profile_buy);
		//buy.setOnClickListener(listener);
		addresspre = (Button) view
				.findViewById(R.id.stripper_profile_addressprevious);
		addresspre.setOnClickListener(listener);
		addressnext = (Button) view
				.findViewById(R.id.stripper_profile_addressnext);
		addressnext.setOnClickListener(listener);
		profileimage = (ImageView) view
				.findViewById(R.id.stripper_profile_imageview);
		profileimage.setOnClickListener(listener);
	//	profileimage.setImageBitmap(ProjectUtils.getBitmapFromURL(StripperInfo.user_avatar));
		
		if(StripperInfo.user_avatar.contains("http"))
		{
			imageLoader.DisplayImage(StripperInfo.user_avatar, profileimage);
		}
		else 
		{
			profileimage.setImageBitmap(Util1.decodeFile(new File(StripperInfo.user_avatar)));
		}
		

		// ************************************************************************************
		i = 0;
		ratingBar=(RatingBar) view.findViewById(R.id.ratingbar);
		ratingBar.setRating(Float.parseFloat(StripperInfo.user_avg_rating));
		Log.e("rating", ""+Float.parseFloat(StripperInfo.user_avg_rating));
		ratingBar.setIsIndicator(true);
		
		if (StripperInfo.ALWorkingAt.size() > 0) {
			setAddress(i);
		}
	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.stripper_Profile_edit:
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new DancerEditPrfile1(), true, true);
				break;
			case R.id.stripper_profile_morepicture:
				if(Util1.isNetworkAvailable(getActivity())){
					List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","getstripperimages"));
					entity.add(new BasicNameValuePair("user_id",StripperInfo.user_id));
					new AsyncWebServiceProcessingTask(getActivity(), entity, "Loading",new Callback() {
						
						@Override
						public void run(String result) {
							// TODO Auto-generated method stub
							try {
								ArrayList<String> imageUrls=new ArrayList<String>();
								JSONObject jsonResponse=new JSONObject(result);
								if(jsonResponse.optString("status").equalsIgnoreCase("success"))
								{
									JSONArray imageArray=new JSONArray(jsonResponse.getString("stripper_images"));
									for (int i = 0; i < imageArray.length(); i++) {
										JSONObject imageURL=imageArray.getJSONObject(i);
										imageUrls.add(imageURL.getString("original_image"));
									}
									DancerAddMore.imageUrls=imageUrls;
									//						DancerAddMore.InitialImageUrls=imageUrls;
									mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(), true, true);
								}
								else
									mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(), true, true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).execute("");
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());
				break;
			case R.id.stripper_profile_mytravlestatus:
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new DancerTravalStatus(), true, true);
				break;
			case R.id.stripper_profile_viewallinfo:

				
				break;
			case R.id.stripper_profile_viewreview:
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new ViewReview(StripperInfo.user_fullname,StripperInfo.user_id,StripperInfo.user_avg_rating,StripperInfo.user_avatar), true, true);
				break;
		/*	case R.id.stripper_profile_buy:
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new ClubSubscription(), true, true);
				break;*/
			case R.id.stripper_profile_addressprevious:
				if (StripperInfo.ALWorkingAt.size() > 0) {
					i -= 1;
					Log.i(TAG, "" + i);
					if (i == -1) {
						i += 1;
					}
					if (i > -1) 
					{
						setAddress(i);
					}
				}

				break;
			case R.id.stripper_profile_addressnext:
				if (StripperInfo.ALWorkingAt.size() > 0) {
					i += 1;
					if (i == StripperInfo.ALWorkingAt.size()) {
						i -= 1;
					}
					Log.i(TAG, StripperInfo.ALWorkingAt.size() + "  " + i);

					if (i < StripperInfo.ALWorkingAt.size()) {
						setAddress(i);
					}
				}
				break;
			case R.id.stripper_profile_imageview:
			
		UpdateProfileImage updateProfileImage=new UpdateProfileImage();
		Bundle bundle=new Bundle();
		bundle.putStringArray("value", new String[]{StripperInfo.user_id,StripperInfo.user_fullname,StripperInfo.user_avatar});
		updateProfileImage.setArguments(bundle);
				mActivity.pushFragments(AppConstants.TAB_HOME,
						updateProfileImage, true, true);
				break;
			default:
				break;
			}
		}
	};

	

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void setAddress(int i) {
		WorkingAt workingat = StripperInfo.ALWorkingAt.get(i);
		String address="";
		if(workingat.getClub_name().isEmpty())
		{

		}
		else
		{
			address1.setText(workingat.getClub_name());
		}
		if(!workingat.getClub_address().isEmpty())
		{
			address=""+workingat.getClub_address();
		}
		if(!workingat.getClub_city().isEmpty())
		{
			address=address+"\n"+workingat.getClub_city();
		}
		if(!workingat.getClub_state().isEmpty())
		{
			address=address+","+workingat.getClub_state();
		}
		if(!workingat.getClub_country().isEmpty())
		{
			address=address+","+workingat.getClub_country();
		}
		if(!workingat.getClub_contact_number().isEmpty())
		{
			address=address+"\n"+workingat.getClub_contact_number();
		}
	
		address2.setText(address);
	}

}
