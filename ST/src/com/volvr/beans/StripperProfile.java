package com.volvr.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class StripperProfile {

	       /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StripperProfile [user_type=" + user_type + ", user_name="
				+ user_name + ", user_fullname=" + user_fullname
				+ ", user_email=" + user_email + ", user_sex=" + user_sex
				+ ", user_phone=" + user_phone + ", user_birthday="
				+ user_birthday + ", user_avatar=" + user_avatar
				+ ", user_original_pic=" + user_original_pic
				+ ", user_address_country=" + user_address_country
				+ ", user_address_city=" + user_address_city
				+ ", user_address_state=" + user_address_state
				+ ", user_address_zip=" + user_address_zip
				+ ", user_address_street_1=" + user_address_street_1
				+ ", user_address_street_2=" + user_address_street_2
				+ ", user_active=" + user_active + ", user_account_type="
				+ user_account_type + ", user_created=" + user_created
				+ ", user_referral_id=" + user_referral_id
				+ ", user_paypal_id=" + user_paypal_id + ", user_last_logined="
				+ user_last_logined + ", user_occupation=" + user_occupation
				+ ", user_grossincome=" + user_grossincome
				+ ", user_avg_rating=" + user_avg_rating + ", hair_color="
				+ hair_color + ", eyes=" + eyes + ", height=" + height
				+ ", weight=" + weight + ", cup_size=" + cup_size + ", stats="
				+ stats + ", race=" + race + ", wage_rate=" + wage_rate
				+ ", travel=" + travel + ", language=" + language + "]";
	}

		public String user_type ; 
	       public String user_name ;  
	       public String user_fullname ;  
	       public String user_email ;  
	       public String user_sex ;  
	       public String user_phone ;  
	       public String user_birthday ; 
	       public String user_avatar ;  
	       public String user_original_pic ; 
	       public String user_address_country ; 
	       public String user_address_city ; 
	       public String user_address_state ;  
	       public String user_address_zip ; 
	       public String user_address_street_1 ;  
	       public String user_address_street_2 ;  
	       public String user_active ;  
	       public String user_account_type ;  
	       public String user_created ;  
	       public String user_referral_id ; 
	       public String user_paypal_id ;  
	       public String user_last_logined ;  
	       public String user_occupation ;  
	       public String user_grossincome ; 
	       public String user_avg_rating ; 
			  
	       public String hair_color ; 
	       public String eyes ;  
	       public String height ; 
	       public String weight ;
	       public String cup_size ;  
	       public String stats ; 
	       public String race ; 
	       public String wage_rate ; 
	       public String travel ; 
	       public String language ; 
	
	       static String t="StripperProfile";
	
	public static  ArrayList<WorkingAt> ALWorkingAt=new ArrayList<WorkingAt>();
	
	public static StripperProfile parseStripperInfoForProfile(JSONObject jsonObj)
	{
		StripperProfile stripperProfile=new StripperProfile();
		try{
			JSONObject User=jsonObj.getJSONObject("user");
			stripperProfile.user_original_pic=User.optString("user_original_pic");
			stripperProfile.user_address_country=User.optString("user_address_country");
			stripperProfile.user_type=User.optString("user_type");
			stripperProfile.user_paypal_id=User.optString("user_paypal_id");
			
			stripperProfile.user_address_city=User.optString("user_address_city");
			stripperProfile.user_email=User.optString("user_email");
			stripperProfile.user_created=User.optString("user_created");
			stripperProfile.user_address_zip=User.optString("user_address_zip");
			stripperProfile.user_name=User.optString("user_name");
			stripperProfile.user_referral_id=User.optString("user_referral_id");
			stripperProfile.user_address_street_2=User.optString("user_address_street_2");
			stripperProfile.user_address_state=User.optString("user_address_state");
			stripperProfile.user_phone=User.optString("user_phone");
			stripperProfile.user_address_street_1=User.optString("user_address_street_1");
			stripperProfile.user_last_logined=User.optString("user_last_logined");
			stripperProfile.user_birthday=User.optString("user_birthday");
			stripperProfile.user_sex=User.optString("user_sex");
			stripperProfile.user_account_type=User.optString("user_account_type");
			stripperProfile.user_active=User.optString("user_active");
			stripperProfile.user_avatar=User.optString("user_avatar");
			stripperProfile.user_fullname=User.optString("user_fullname");
			if (User.optString("user_avg_rating").equalsIgnoreCase("null")) {
				stripperProfile.user_avg_rating=""+0;	
			}
			else
			stripperProfile.user_avg_rating=(int)Float.parseFloat(User.optString("user_avg_rating"))+"";
			stripperProfile.user_account_type=User.optString("user_account_type");
			
		} catch (Exception e)
		{
			Log.i(t, ""+e);
		}   
		try{
			JSONObject StriperInfo=jsonObj.getJSONObject("stripper_info");

			stripperProfile.weight=StriperInfo.optString("weight");
			stripperProfile.height=StriperInfo.optString("height");
			stripperProfile.wage_rate=StriperInfo.optString("wage_rate");
			stripperProfile.hair_color=StriperInfo.optString("hair_color");
			stripperProfile.stats=StriperInfo.optString("stats");
			stripperProfile.cup_size=StriperInfo.optString("cup_size");
			stripperProfile.language=StriperInfo.optString("language");    
			stripperProfile.travel=StriperInfo.optString("travel");
			stripperProfile.race=StriperInfo.optString("race");
			stripperProfile.eyes=StriperInfo.optString("eyes");
		}  catch (JSONException e)
		{
			Log.i(t, ""+e);
		} 

		try{
			JSONArray StriperWorkingAt=jsonObj.getJSONArray("stripper_working_at");
			ALWorkingAt.clear();
			for (int i = 0; i < StriperWorkingAt.length(); i++) {
				JSONObject JSONWorkingAt =	StriperWorkingAt.getJSONObject(i);
				WorkingAt workingAt=new WorkingAt();
				
				workingAt.setClub_id(JSONWorkingAt.optString("club_id"));
				workingAt.setClub_zip_code(JSONWorkingAt.optString("club_zip_code"));
				workingAt.setClub_city(JSONWorkingAt.optString("club_city"));
				workingAt.setClub_contact_number(JSONWorkingAt.optString("club_contact_number"));
				workingAt.setClub_country(JSONWorkingAt.optString("club_country"));
				workingAt.setClub_address(JSONWorkingAt.optString("club_address"));
				workingAt.setClub_state(JSONWorkingAt.optString("club_state"));
				workingAt.setClub_name(JSONWorkingAt.optString("club_name"));
				ALWorkingAt.add(workingAt);
			}
		}catch (JSONException e)
		{
			Log.i(t, ""+e);
		}
		
		
        return stripperProfile;
	
	}
	



	
}
