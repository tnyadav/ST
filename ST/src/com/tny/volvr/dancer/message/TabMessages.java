package com.tny.volvr.dancer.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.JsonResponse;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.dancer.calendar.AddSchedule;
import com.volvr.beans.ClubInfo;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperList;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class TabMessages extends BaseFragment{
	private static String TAG="More_Search";

	private View view;
	private Button clubbtn,fanbtn,mBackBtn;
	private EditText edittextSearchbyName,edittextSearchbyLocation;
	int textlength = 0;
	private LinearLayout combineTab;
	private ArrayList<String> text_sort = new ArrayList<String>();
	private ArrayList<String> image_sort = new ArrayList<String>();
	private ArrayList<String> comment_sort = new ArrayList<String>();
	private ArrayList<String> location_sort = new ArrayList<String>();
	static int selectedTab=1;
	private Handler handler;
	private String action="",mselectionStr="";
	private List<NameValuePair> entity = new ArrayList<NameValuePair>();
	ArrayList<String> text=new ArrayList<String>(),
			comment=new ArrayList<String>(),
			image=new ArrayList<String>(),
			user_id = new ArrayList<String>();
    private FavouriteListAdapter favouriteListAdapter;
	
	public TabMessages(String string) {
		mselectionStr=string;
	}
	public TabMessages() {
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,

			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_view_new_message, container, false);

		entity.clear();

		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d(TAG, JsonResponse.JsonResponse);
				try {
					text.clear();
					image.clear();
					comment.clear();
					if(action.equalsIgnoreCase("stripper"))
					{
						StripperList.parseStripperInfoList(new JSONObject(JsonResponse.JsonResponse));
						text=StripperList.getName();
						image=StripperList.getImageUrl();
						comment=StripperList.getAddress();
						user_id=StripperList.getUser_Id();
					}
					else if(action.equalsIgnoreCase("club"))
					{
						ClubInfo.parseJson(new JSONObject(JsonResponse.JsonResponse));
						text=ClubInfo.getName();
						image=ClubInfo.getImageUrl();
						comment=ClubInfo.getAddress();
						user_id=ClubInfo.getUser_Id();
					}
					else if(action.equalsIgnoreCase("fan"))
					{
						FanInfo.parseJson(new JSONObject(JsonResponse.JsonResponse));
						text=FanInfo.getName();
						image=FanInfo.getImageUrl();
						comment=FanInfo.getAddress();
						user_id=FanInfo.getUser_Id();
					}
					favouriteListAdapter=new FavouriteListAdapter(text, image,comment);
					reviewrow.setAdapter(favouriteListAdapter);
					favouriteListAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		if(Util1.isNetworkAvailable(getActivity())){
			setContent();
		}
		else
			UIUtils.showNetworkErrorMessage(getActivity());
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		selectedTab=1;
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			entity.clear();
			switch (v.getId()) {
			case R.id.favourite_secondtab:
				if (selectedTab!=2) {
					if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
					{
						combineTab.setBackgroundResource(R.drawable.fan_aclub);
						action="club";
						entity.add(new BasicNameValuePair("action","getclublist"));
						new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
					}
					else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
					{
						combineTab.setBackgroundResource(R.drawable.fan_astripper);	
						action="stripper";
						entity.add(new BasicNameValuePair("action","getstripperlist"));
						new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
					}
					else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
					{
						combineTab.setBackgroundResource(R.drawable.club_astripper);	
						action="stripper";
						entity.add(new BasicNameValuePair("action","getstripperlist"));
						new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
					}
				}
				selectedTab=2;
				break;

			case R.id.favourite_firsttab:
				if (selectedTab!=1) {
					if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
					{
						combineTab.setBackgroundResource(R.drawable.afan_club);
						action="fan";
						entity.add(new BasicNameValuePair("action","getfanlist"));
						new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
					}
					else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
					{
						combineTab.setBackgroundResource(R.drawable.afan_stripper);	
						action="fan";
						entity.add(new BasicNameValuePair("action","getfanlist"));
						new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
					}
					else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
					{
						combineTab.setBackgroundResource(R.drawable.aclub_stripper);	
						action="club";
						entity.add(new BasicNameValuePair("action","getclublist"));
						new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
					}
				}
				selectedTab=1;
				break;
			case R.id.stripper_newmessage_back:
				getActivity().onBackPressed();
                   break;
			
			default:
				break;
			}
		}
	};

	private GridView reviewrow;
	private ImageView headerText;
	
	private void setContent() {
		combineTab=(LinearLayout)view.findViewById(R.id.relative_picture2);
		clubbtn=(Button)view.findViewById(R.id.favourite_firsttab);
		clubbtn.setOnClickListener(listener);
		mBackBtn=(Button)view.findViewById(R.id.stripper_newmessage_back);
		mBackBtn.setOnClickListener(listener);
		headerText=(ImageView)view.findViewById(R.id.headerselection);
		if (mselectionStr.equalsIgnoreCase("appointment")) {
			headerText.setImageResource(R.drawable.add_schedule);
		}
		fanbtn=(Button)view.findViewById(R.id.favourite_secondtab);
		fanbtn.setOnClickListener(listener);

		if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
		{
			combineTab.setBackgroundResource(R.drawable.afan_club);
			action="fan";
			entity.add(new BasicNameValuePair("action","getfanlist"));
			new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
		}
		else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
		{
			combineTab.setBackgroundResource(R.drawable.afan_stripper);	
			action="fan";
			entity.add(new BasicNameValuePair("action","getfanlist"));
			new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
		}
		else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
		{
			combineTab.setBackgroundResource(R.drawable.aclub_stripper);	
			action="club";
			entity.add(new BasicNameValuePair("action","getclublist"));
			new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
		}
		reviewrow=(GridView)view.findViewById(R.id.stripper_view_reviews_list);
		reviewrow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				
			//	Toast.makeText(mActivity, mselectionStr, 3).show();
				
				TextView name=(TextView)arg1.findViewById(R.id.imgQueueMultiSelected);
				if (mselectionStr.equalsIgnoreCase("appointment")) {
					mActivity.pushFragments(AppConstants.TAB_CALENDAR, new AddSchedule(user_id.get(arg2).toString()),true,true);	
				}
				else{
				//	Toast.makeText(mActivity,arg2+ "   "+image.get(arg2), 3).show();
			     	Log.e("url#######",arg2+ "   "+image.get(arg2) );
					mActivity.pushFragments(AppConstants.TAB_MESSAGES, new ChatMessages(name.getText().toString(),user_id.get(arg2).toString(),image.get(arg2)),true,true);
				}
				}
		});

		edittextSearchbyName = (EditText)view. findViewById(R.id.stripper_offer_search_byname);
		edittextSearchbyName.addTextChangedListener(new TextWatcher()
		{

			public void afterTextChanged(Editable s)
			{

			}

			public void beforeTextChanged(CharSequence s, int start,
					int count, int after)
			{

			}

			public void onTextChanged(CharSequence s, int start,
					int before, int count)
			{
				searchByName();
			}	
		});

	}

	public class FavouriteListAdapter extends BaseAdapter
	{
		ArrayList<String> data_text,data_comment;
		ArrayList<String> data_image;
		LayoutInflater inflater;
	
		FavouriteListAdapter()
		{

		}

		FavouriteListAdapter(ArrayList<String> text, ArrayList<String> image,ArrayList<String> comment)
		{ 
			data_text =text;
			data_image =image;
			data_comment= comment;
		
		    inflater =getActivity().getLayoutInflater();
		
		}

		public int getCount()
		{
			return data_text.size();
		}

		public String getItem(int position)
		{
			return null;
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView( int position, View convertView, ViewGroup parent)
		{
			View row = convertView;
			
				row = inflater.inflate(R.layout.item_grid_new_message, null);
				TextView cancelImageView = (TextView) row.findViewById(R.id.imgQueueMultiSelected);
				 ImageView imageview = (ImageView) row.findViewById(R.id.image);
				 cancelImageView.setText(data_text.get(position));
				 imageLoader.DisplayImage(data_image.get(position), imageview);
		
			return row;
		
		}
	}
	private void searchByName()
	{
		textlength = edittextSearchbyName.getText().length();
		text_sort.clear();
		comment_sort.clear();
		image_sort.clear();
		for (int i = 0; i < text.size(); i++)
		{
			if (textlength <= text.get(i).length())
			{
				if (edittextSearchbyName.getText().toString().
						equalsIgnoreCase((String) text.get(i).subSequence(0, textlength)))
				{
					text_sort.add(text.get(i));
					image_sort.add(image.get(i));
					comment_sort.add(comment.get(i));
				}
			}
		}
		favouriteListAdapter=new FavouriteListAdapter(text_sort, image_sort,comment_sort);
		reviewrow.setAdapter(favouriteListAdapter);
		favouriteListAdapter.notifyDataSetChanged();
	  }

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		favouriteListAdapter.notifyDataSetChanged();
	}
	
}

