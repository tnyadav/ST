package com.tny.volvr.club.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tny.utils.ProjectUtils;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.dancer.home.DancerEditPrfile1;
import com.volvr.beans.StripperInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class ClubProfileAllInfo extends BaseFragment {

	protected TextView Name,Email,Country,City,Zipcode,Referalid,
	SquareFoot,MinAge,DancerCount,YearCount,
	Topless,FullNude,JouseBar,BearBar,FullBar,FoodKitchen,EventCast;
	protected Button back,edit;

	protected View view;

	protected static String t = "ClubEditProfile";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.club_profile_all_info, container, false);
		setContent();
		/*
		 * mActivity.mStacks.get(mActivity.mCurrentTab).pop();
		 * mActivity.mStacks.get(mActivity.mCurrentTab).pop();
		 * mActivity.mStacks.get(mActivity.mCurrentTab).pop();
		 */
		/*
		 * Log.i(t, StripperInfo.age); Log.i(t, StripperInfo.name); Log.i(t,
		 * StripperInfo.id); Log.i(t, StripperInfo.usertype); Log.i(t,
		 * StripperInfo.thumbnailimage); Log.i(t, StripperInfo.originalimage);
		 */
		return view;
	}

	protected void setContent() 
	{
		back=(Button)view.findViewById(R.id.club_edit_profile_back);
		back.setOnClickListener(listener);
		edit=(Button)view.findViewById(R.id.club_edit_profile_edit);
		edit.setVisibility(View.GONE);
		Name=(TextView)view.findViewById(R.id.club_edit_profile_name);

		Email=(TextView)view.findViewById(R.id.club_edit_profile_email);

		Country=(TextView)view.findViewById(R.id.club_edit_profile_country);

		City=(TextView)view.findViewById(R.id.club_edit_profile_city);

		Zipcode=(TextView)view.findViewById(R.id.club_edit_profile_zipcode);

		Referalid=(TextView)view.findViewById(R.id.club_edit_profile_referralid);

		SquareFoot=(TextView)view.findViewById(R.id.club_edit_profile_squarefootage);

		MinAge=(TextView)view.findViewById(R.id.club_edit_profile_minimumage);

		DancerCount=(TextView)view.findViewById(R.id.club_edit_profile_dancers);

		YearCount=(TextView)view.findViewById(R.id.club_edit_profile_business);

		Topless=(TextView)view.findViewById(R.id.club_edit_profile_topless);

		FullNude=(TextView)view.findViewById(R.id.club_edit_profile_fullnude);

		JouseBar=(TextView)view.findViewById(R.id.club_edit_profile_juicebar);

		BearBar=(TextView)view.findViewById(R.id.club_edit_profile_beerbar);

		FullBar=(TextView)view.findViewById(R.id.club_edit_profile_fullbar);

		FoodKitchen=(TextView)view.findViewById(R.id.club_edit_profile_foodkitchen);

		EventCast=(TextView)view.findViewById(R.id.club_edit_profile_eventcast);	
		setClubInformation();
	}

	protected void setClubInformation()
	{
		
		Name.setText(Clubprofile.clubs.getClub_name());
		Email.setText(StripperInfo.user_email);
		Country.setText(Clubprofile.clubs.getClub_country());
		City.setText(Clubprofile.clubs.getClub_city());
		Zipcode.setText(Clubprofile.clubs.getClub_zip_code());
		Referalid.setText(StripperInfo.user_referral_id);
		SquareFoot.setText(Clubprofile.clubs.getClub_area());
		//MinAge.setText(ProjectUtils.getAge(Clubprofile.clubs.getClub_allowed_minage()));
		MinAge.setText(Clubprofile.clubs.getClub_allowed_minage());
		
		DancerCount.setText(Clubprofile.clubs.getClub_dancercount());
	//	YearCount.setText(Clubprofile.clubs.getCluby);
		Topless.setText(Clubprofile.clubs.getClub_topless());
		FullNude.setText(Clubprofile.clubs.getClub_nude());
		JouseBar.setText(Clubprofile.clubs.getClub_juicebar());
		BearBar.setText(Clubprofile.clubs.getClub_beerbar());
		FullBar.setText(Clubprofile.clubs.getClub_fulbar());
		FoodKitchen.setText(Clubprofile.clubs.getClub_foodkitchen());
		EventCast.setText(Clubprofile.clubs.getClub_eventcost());
	}

	protected OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.club_edit_profile_edit:
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new DancerEditPrfile1(), true, true);
				break;

			case R.id.club_edit_profile_back:
				getActivity().onBackPressed();
				break;
			default:
				break;
			}
		}
	};
}
