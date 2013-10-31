package com.tny.volvr.more;

import java.util.ArrayList;
import java.util.Date;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.JsonResponse;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.club.home.ClubViewProfile;
import com.tny.volvr.dancer.home.DancerViewProfile;
import com.tny.volvr.fan.home.FanViewProfile;
import com.volvr.beans.ClubInfo;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperList;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class More_Search extends BaseFragment{
	private String TAG="More_Search";
	private View view;
	Button clubbtn,fanbtn;
	private ListView reviewrow;
	EditText edittextSearchbyName,edittextSearchbyLocation;
	int textlength = 0;
	LinearLayout combineTab;
	ArrayList<String> text_sort = new ArrayList<String>();
	ArrayList<String> image_sort = new ArrayList<String>();
	ArrayList<String> comment_sort = new ArrayList<String>();
	ArrayList<String> location_sort = new ArrayList<String>();
	static int selectedTab=1;
	Handler handler;
	String action=""/*,profileselection=""*/;
	List<NameValuePair> entity = new ArrayList<NameValuePair>();
	ArrayList<String> text=new ArrayList<String>(),
			comment=new ArrayList<String>(),
			image=new ArrayList<String>(),
			user_id = new ArrayList<String>();
	
	String mexpiry_date="",mcurrentDate="";
	private Date expiryDate=new Date(),currentDate=new Date();
	  private FavouriteListAdapter favouriteListAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_view_search, container, false);
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
					JSONObject obj = new JSONObject(JsonResponse.JsonResponse);
					if(action.equalsIgnoreCase("stripper"))
					{
						StripperList.parseStripperInfoList(obj);
						text=StripperList.getName();
						image=StripperList.getImageUrl();
						comment=StripperList.getAddress();
						user_id=StripperList.getUser_Id();
						favouriteListAdapter=new FavouriteListAdapter(text, image,comment);
						reviewrow.setAdapter(favouriteListAdapter);
						favouriteListAdapter.notifyDataSetChanged();
						
						//reviewrow.setAdapter(new FavouriteListAdapter(text, image,comment));
					}
					else if(action.equalsIgnoreCase("club"))
					{
						ClubInfo.parseJson(obj);
						text=ClubInfo.getName();
						image=ClubInfo.getImageUrl();
						comment=ClubInfo.getAddress();
						user_id=ClubInfo.getUser_Id();
						favouriteListAdapter=new FavouriteListAdapter(text, image,comment);
						reviewrow.setAdapter(favouriteListAdapter);
						favouriteListAdapter.notifyDataSetChanged();
						//reviewrow.setAdapter(new FavouriteListAdapter(text, image,comment));
					}
					else if(action.equalsIgnoreCase("fan"))
					{
						FanInfo.parseJson(obj);
						text=FanInfo.getName();
						image=FanInfo.getImageUrl();
						comment=FanInfo.getAddress();
						user_id=FanInfo.getUser_Id();
						favouriteListAdapter=new FavouriteListAdapter(text, image,comment);
						reviewrow.setAdapter(favouriteListAdapter);
						favouriteListAdapter.notifyDataSetChanged();
						//reviewrow.setAdapter(new FavouriteListAdapter(text, image,comment));
					}
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
			if(Util1.isNetworkAvailable(getActivity())){
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
				default:
					break;
				}
			}
			else
				UIUtils.showNetworkErrorMessage(getActivity());
		}
	};

	private void setContent() {
		combineTab=(LinearLayout)view.findViewById(R.id.relative_picture2);
		clubbtn=(Button)view.findViewById(R.id.favourite_firsttab);
		clubbtn.setOnClickListener(listener);
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
		reviewrow=(ListView)view.findViewById(R.id.stripper_view_reviews_list);
		reviewrow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					//*********************for fan
					if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
					{
						/*	if(FanInfo.fanproFileInfo.user_account_type.equalsIgnoreCase("Free"))
						{
							UIUtils.showMessage(getActivity(), "Message","Please subscribe to view profile.");
						}
								else
						{
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						try {
							expiryDate = dateFormat.parse(FanInfo.fanproFileInfo.expiry_date);
							mexpiry_date = dateFormat.format(expiryDate);
							System.out.println("mexpiry_date="+mexpiry_date);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Date date = new Date();
							mcurrentDate=dateFormat.format(date);
							currentDate=dateFormat.parse(mcurrentDate);
							System.out.println("current date="+dateFormat.format(date));
						} catch (Exception e) {
							e.printStackTrace();
						}*/
				/*		if(expiryDate.after(currentDate))
						{*/
							if(action.equalsIgnoreCase("stripper"))
							{
								mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new DancerViewProfile(arg2,user_id.get(arg2).toString()),true,true);
							}
							else if(action.equalsIgnoreCase("club"))
							{
								Log.e("thid", arg2+"    "+user_id.get(arg2));
								mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new ClubViewProfile(arg2,user_id.get(arg2)),true,true);
							}
							/*	}
					else
							UIUtils.showMessage(getActivity(), "Message","your subscription expired.");
						}*/
					}
					//*********************for club
					if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
					{
					/*	if(StripperInfo.user_account_type.equalsIgnoreCase("Free"))
						{
							UIUtils.showMessage(getActivity(), "Message","Please subscribe to view profile.");
						}
						else
						{
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						try {
							expiryDate = dateFormat.parse(StripperInfo.subscription_expire_date);
							mexpiry_date = dateFormat.format(expiryDate);
							System.out.println("mexpiry_date="+mexpiry_date);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Date date = new Date();
							mcurrentDate=dateFormat.format(date);
							currentDate=dateFormat.parse(mcurrentDate);
							System.out.println("current date="+dateFormat.format(date));
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(expiryDate.after(currentDate))
						{*/
							if(action.equalsIgnoreCase("stripper"))
							{
								mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new DancerViewProfile(arg2,user_id.get(arg2).toString()),true,true);
							}
							else if(action.equalsIgnoreCase("fan"))
							{
								mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new FanViewProfile(arg2,user_id.get(arg2).toString()),true,true);
							}
						/*}
						else
							UIUtils.showMessage(getActivity(), "Message","your subscription expired.");
						}*/
					}
					//*********************for stripper
					if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
					{
					/*	if(StripperInfo.user_account_type.equalsIgnoreCase("Free"))
						{
							UIUtils.showMessage(getActivity(), "Message","Please subscribe to view profile.");
						}
						else
						{
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						try {
							expiryDate = dateFormat.parse(StripperInfo.subscription_expire_date);
							mexpiry_date = dateFormat.format(expiryDate);
							System.out.println("mexpiry_date="+mexpiry_date);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Date date = new Date();
							mcurrentDate=dateFormat.format(date);
							currentDate=dateFormat.parse(mcurrentDate);
							System.out.println("current date="+dateFormat.format(date));
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(expiryDate.after(currentDate))
						{*/
							if(action.equalsIgnoreCase("fan"))
							{
								mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new FanViewProfile(arg2,user_id.get(arg2).toString()),true,true);
							}
							else if(action.equalsIgnoreCase("club"))
							{
								Log.e("thid", arg2+"    "+user_id.size());
								Log.e("thid", arg2+"    "+user_id.get(arg2) );
								mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new ClubViewProfile(arg2,user_id.get(arg2).toString()),true,true);
							}
				/*		}
						else
							UIUtils.showMessage(getActivity(), "Message","your subscription expired.");
						}*/
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				//				action="profile";
				//				entity.add(new BasicNameValuePair("action","getprofile"));
				//				entity.add(new BasicNameValuePair("strip_id",user_id.get(arg2).toString()));
				//				new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading...").execute("");
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

		edittextSearchbyLocation = (EditText)view. findViewById(R.id.stripper_offer_search_bylocation);
		edittextSearchbyLocation.addTextChangedListener(new TextWatcher()
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
				searchByLocation();
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

		public View getView(int position, View convertView, ViewGroup parent)
		{

		
			View row;

			row = inflater.inflate(R.layout.view_review_row_item, parent, false);

			com.tny.utils.MyTextView name = (com.tny.utils.MyTextView) row.findViewById(R.id.view_review_row_name);
			com.tny.utils.MyTextView comment = (com.tny.utils.MyTextView) row.findViewById(R.id.view_review_row_review);
			ImageView imageview = (ImageView) row
					.findViewById(R.id.view_review_row_image);
			name.setText(data_text.get(position));
			comment.setText(data_comment.get(position));
			 imageLoader.DisplayImage(data_image.get(position), imageview);

			return (row);

		}
	}

	private void searchByName()
	{
		textlength = edittextSearchbyName.getText().length();
		text_sort.clear();
		comment_sort.clear();
		image_sort.clear();
		//		if(selectedTab==1)
		//		{
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
		reviewrow.setAdapter(new FavouriteListAdapter
				(text_sort, image_sort,comment_sort));
	}

	private void searchByLocation()
	{
		textlength = edittextSearchbyLocation.getText().length();
		text_sort.clear();
		comment_sort.clear();
		image_sort.clear();
		location_sort.clear();
		for (int i = 0; i < comment.size(); i++)
		{
			if (textlength <= comment.get(i).length())
			{
				if (edittextSearchbyLocation.getText().toString().
						equalsIgnoreCase((String) comment.get(i).subSequence(0, textlength)))
				{
					text_sort.add(text.get(i));
					image_sort.add(image.get(i));
					comment_sort.add(comment.get(i));
					location_sort.add(comment.get(i));						
				}
			}
		}
		reviewrow.setAdapter(new FavouriteListAdapter
				(text_sort, image_sort,comment_sort));

	}
@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	/*if (favouriteListAdapter!=null) {
		
		favouriteListAdapter.notifyDataSetChanged();	
	}*/
	
}
}
