package com.tny.volvr.club.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.ProjectUtils;
import com.tny.utils.UIUtils;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.dancer.home.DancerEditPrfile1;
import com.volvr.beans.ClubInfo;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class ClubProfileAllInfoForOthers extends ClubProfileAllInfo {
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}
	@Override
	protected void setClubInformation() {
		// TODO Auto-generated method stub
		super.setClubInformation();
		

		Log.e("id", id);
		List<NameValuePair> entity = new ArrayList<NameValuePair>();
		entity.add(new BasicNameValuePair("action","getuserprofile"));
		entity.add(new BasicNameValuePair("user_id",id));
		
		new AsyncWebServiceProcessingTask(mActivity, entity, "Please wait",new Callback() {
			
			@Override
			public void run(String result) {
				// TODO Auto-generated method stub
				if (result.equals("")) {
					UIUtils.showNetworkErrorMessage(getActivity());
				} else {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(result);
						if (jsonObject.optString("status").equals("success")) {
							
							Clubprofile.parseProfileData(jsonObject);
							
							Name.setText(Clubprofile.clubs.getClub_name());
							Email.setText(StripperInfo.user_email);
							Country.setText(Clubprofile.clubs.getClub_country());
							City.setText(Clubprofile.clubs.getClub_city());
							Zipcode.setText(Clubprofile.clubs.getClub_zip_code());
							Referalid.setText(StripperInfo.user_referral_id);
							SquareFoot.setText(Clubprofile.clubs.getClub_area());
							MinAge.setText(ProjectUtils.getAge(Clubprofile.clubs.getClub_allowed_minage()));
							DancerCount.setText(Clubprofile.clubs.getClub_dancercount());
						//	YearCount.setText(Clubprofile.clubs.getCluby);
							Topless.setText(Clubprofile.clubs.getClub_topless());
							FullNude.setText(Clubprofile.clubs.getClub_nude());
							JouseBar.setText(Clubprofile.clubs.getClub_juicebar());
							BearBar.setText(Clubprofile.clubs.getClub_beerbar());
							FullBar.setText(Clubprofile.clubs.getClub_fulbar());
							FoodKitchen.setText(Clubprofile.clubs.getClub_foodkitchen());
							EventCast.setText(Clubprofile.clubs.getClub_eventcost());
						}else {
							
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			
				
			}
		}).execute("");
		
		
		
	}
}
