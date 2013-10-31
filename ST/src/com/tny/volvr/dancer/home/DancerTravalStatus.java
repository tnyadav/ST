package com.tny.volvr.dancer.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.UIUtils;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.TravelStatusList;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class DancerTravalStatus extends BaseFragment{
private String t="StripperViewReview";
private View view;
private com.tny.utils.MyTextView name;
private Button back,addmore;
private ListView reviewrow;
private String stripperId;

	public DancerTravalStatus(String stripperId) {
	super();
	this.stripperId=stripperId;
	
}
	public DancerTravalStatus() {
		super();
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view       =   inflater.inflate(R.layout.stripper_traval_status, container, false);
	        setContent();
	       
	        return view;
	}
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
				case R.id.stripper_traval_back:
				getActivity().onBackPressed();	
				break;
				case R.id.stripper_traval_add_more:
					mActivity.pushFragments(AppConstants.TAB_HOME,new AddTravelStatus(), true, true);
					break;
			default: 
				break;
			}
		}
	};
	
private void setContent() {
	back=(Button)view.findViewById(R.id.stripper_traval_back);
	back.setOnClickListener(listener);
	addmore=(Button)view.findViewById(R.id.stripper_traval_add_more);
	addmore.setOnClickListener(listener);
	reviewrow=(ListView)view.findViewById(R.id.stripper_traval_status_list);
	String id="";
	if (UserInfo.USERTYPE.equalsIgnoreCase("stripper")) {
		id=StripperInfo.user_id;
	}else if (UserInfo.USERTYPE.equalsIgnoreCase("fan")) {
	id=stripperId;	
	}
	List<NameValuePair> entity = new ArrayList<NameValuePair>();
	entity.add(new BasicNameValuePair("action","travelstatus_get"));
	entity.add(new BasicNameValuePair("strip_user_id",id));
	
	new AsyncWebServiceProcessingTask(mActivity, entity, "Getting travel status",new Callback() {
		
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
						TravelStatusList.parseTravelStatuslist(jsonObject);
						reviewrow.setAdapter(new TravalListAdapter(mActivity));
					}
					else if(jsonObject.optString("status").equals("error")){
						UIUtils.showMessage(getActivity(), "Message","No travel status found");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
			
		}
	}).execute("");
	
	if (!UserInfo.USERTYPE.equalsIgnoreCase("stripper")) {
		addmore.setVisibility(View.GONE);
	}
//	reviewrow.setOnItemClickListener(new OnItemClickListener() {
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			mActivity.pushFragments(AppConstants.TAB_HOME, new StripperViewReviewFull(),true,true);
//		}
//	});
}

private class TravalListAdapter extends BaseAdapter
{
	LayoutInflater nflater;
	Context mCntxt;
	ArrayList<TravelStatusList> travelStatusLists;
    TravalListAdapter(Context cntext) 
	{
		mCntxt = cntext;
		nflater = (LayoutInflater) mCntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 travelStatusLists=TravelStatusList.altravelStatusLists;
	}
	
	@Override
	public int getCount() 
	{
		return travelStatusLists.size();
	}

	@Override
	public Object getItem(int arg0) 
	{
		return arg0;
	}

	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}

	public class ViewHolder 
	{
		com.tny.utils.MyTextView dateTextView,titleTextView,fromTextView,toTextView;
		Button edit,delete;
	}
	
	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) 
	{
		ViewHolder objHolder;
		View view = arg1;	
		if (arg1 == null) 
		{
			objHolder = new ViewHolder();
			nflater = (LayoutInflater)mCntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = nflater.inflate(R.layout.traval_status_row_item, null);
			objHolder.titleTextView = (com.tny.utils.MyTextView) view.findViewById(R.id.traval_title_date);
			objHolder.fromTextView=(com.tny.utils.MyTextView)view.findViewById(R.id.traval_status_from);
			objHolder.toTextView=(com.tny.utils.MyTextView)view.findViewById(R.id.traval_status_to);
			objHolder.titleTextView.setText(travelStatusLists.get(position).ts_title);
			objHolder.fromTextView.setText(travelStatusLists.get(position).ts_start_datetime);
			objHolder.toTextView.setText(travelStatusLists.get(position).ts_end_datetime);
			objHolder.edit=(Button)view.findViewById(R.id.edit);
			objHolder.edit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					mActivity.pushFragments(AppConstants.TAB_HOME,
							new EditTravelStatus(position), true, true);
				}
			});
			objHolder.delete=(Button)view.findViewById(R.id.delet);
			
			if (!UserInfo.USERTYPE.equalsIgnoreCase("stripper")) {
				objHolder.edit.setVisibility(View.GONE);
				objHolder.delete.setVisibility(View.GONE);
			}
            objHolder.delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Log.e("ts_id", travelStatusLists.get(position).ts_id);
					Builder dlg = new AlertDialog.Builder(mActivity);
					dlg.setCancelable(false);
					dlg.setTitle("Message");
					dlg.setMessage("Are you sure. you want to delete ?");
					dlg.setPositiveButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					dlg.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							List<NameValuePair> entity = new ArrayList<NameValuePair>();
							entity.add(new BasicNameValuePair("action","travelstatus_delete"));
							entity.add(new BasicNameValuePair("ts_id",travelStatusLists.get(position).ts_id));
							
							new AsyncWebServiceProcessingTask(mActivity, entity, "Getting travel status",new Callback() {
								
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
												Toast.makeText(mActivity, "Deleted",Toast.LENGTH_SHORT).show();
											}
											else{
												UIUtils.showMessage(getActivity(), "Message","Request Failed");
											}
											getActivity().onBackPressed();	
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											
										}
										
									}
								
									
								}
							}).execute("");
						}
					});
					dlg.show();
					
				}
			});
		}
		else 
		{
			objHolder = (ViewHolder) view.getTag();
		}
//		objHolder.sponsorsNameTextView.setText(StripperInfo.ALStripperReviews.get(arg0).get());
//		objHolder.sponsorsLinkTextView.setText(mDataList.get(arg0).getLink());
		return view;
	}

}



}
