package com.volvr.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class TravelStatusList implements Serializable{
	 /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */


	public String ts_id;
	public String strip_user_id;
	public String club_user_id;
	public String ts_title;
	public String ts_description;
	public String ts_start_datetime;
	public String ts_end_datetime;
	public String ts_is_active;
	public String ts_created_datetime;
	public String ts_modified_datetime;
	 
	 public static ArrayList<TravelStatusList> altravelStatusLists=new ArrayList<TravelStatusList>();
	 
	 public static void parseTravelStatuslist(JSONObject jsonObj) {
		 altravelStatusLists.clear();
		try {
			JSONArray jsonArray=jsonObj.getJSONArray("travelstatus");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				TravelStatusList travelStatusList=new TravelStatusList();
				 
				 travelStatusList.ts_id=jsonObject.optString("ts_id");
				 travelStatusList.strip_user_id=jsonObject.optString("strip_user_id");
				 travelStatusList.club_user_id=jsonObject.optString("club_user_id");
				 travelStatusList.ts_title=jsonObject.optString("ts_title");
				 travelStatusList.ts_description=jsonObject.optString("ts_description");
				 travelStatusList.ts_start_datetime=jsonObject.optString("ts_start_datetime");
				 travelStatusList.ts_end_datetime=jsonObject.optString("ts_end_datetime");
				 travelStatusList.ts_is_active=jsonObject.optString("ts_is_active");
				 travelStatusList.ts_created_datetime=jsonObject.optString("ts_created_datetime");
				 travelStatusList.ts_modified_datetime=jsonObject.optString("ts_modified_datetime");
				 
				 altravelStatusLists.add(travelStatusList);
			}
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		@Override
		public String toString() {
			return "TravelStatusList [ts_id=" + ts_id + ", strip_user_id="
					+ strip_user_id + ", club_user_id=" + club_user_id
					+ ", ts_title=" + ts_title + ", ts_description="
					+ ts_description + ", ts_start_datetime=" + ts_start_datetime
					+ ", ts_end_datetime=" + ts_end_datetime + ", ts_is_active="
					+ ts_is_active + ", ts_created_datetime=" + ts_created_datetime
					+ ", ts_modified_datetime=" + ts_modified_datetime + "]";
		}
}
