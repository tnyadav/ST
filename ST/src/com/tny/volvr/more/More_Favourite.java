
package com.tny.volvr.more;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.UIUtils;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.FanInfo;
import com.volvr.beans.Favourites;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class More_Favourite extends BaseFragment{
	private static String TAG="More_Favourite";

	private View view;
	Button clubbtn,fanbtn;
	private ListView reviewrow;
	EditText edittextSearchbyName,edittextSearchbyLocation;
	int textlength = 0;
	LinearLayout combineTab;
	ArrayList<String> text_sort = new ArrayList<String>();
	ArrayList<String> image_sort = new ArrayList<String>();
	ArrayList<String> id_sort = new ArrayList<String>();
	ArrayList<String> location_sort = new ArrayList<String>();
	static int selectedTab=1;
	//EditText mSearchLayout;
	Handler handler;
	String action="";
	
	ArrayList<String> text=new ArrayList<String>(),
			          id=new ArrayList<String>(),
			          image=new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,

			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_fav_search, container, false);

			setContent();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		selectedTab=1;
		List<NameValuePair> entity = new ArrayList<NameValuePair>();
		entity.add(new BasicNameValuePair("action", "favourites_get"));
		String user_Id = "";
		
		if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
			user_Id=StripperInfo.user_id;
		if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
		   user_Id=Clubprofile.clubs.getClub_id();
		if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
			user_Id=FanInfo.fanproFileInfo.user_id;
		
		entity.add(new BasicNameValuePair("user_id", user_Id));
		new AsyncWebServiceProcessingTask(mActivity, entity, "Please wait...", new Callback() {
			
			@Override
			public void run(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonResponse=new JSONObject(result);
					if(jsonResponse.optString("status").equalsIgnoreCase("success"))
					{
						Favourites.parseFavouriteList(jsonResponse);
						
						if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
						{
						getAllLists("club");
						combineTab.setBackgroundResource(R.drawable.fan_aclub);
					
						}else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
						{
							combineTab.setBackgroundResource(R.drawable.fan_astripper);
							getAllLists("stripper");
							
						}
						else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
						{
							combineTab.setBackgroundResource(R.drawable.club_astripper);	
							getAllLists("stripper");
							
						}
						reviewrow.setAdapter(new FavouriteListAdapter(text, image,id));
					}
					else
						UIUtils.showMessage(getActivity(), "Message","Try again later. ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).execute("");
	}


	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.favourite_secondtab:
				if (selectedTab!=2) {
					if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
					{
					getAllLists("club");
					reviewrow.setAdapter(new FavouriteListAdapter(text, image,id));
					combineTab.setBackgroundResource(R.drawable.fan_aclub);
					}else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
					{
						combineTab.setBackgroundResource(R.drawable.fan_astripper);
						getAllLists("stripper");
						reviewrow.setAdapter(new FavouriteListAdapter(text, image,id));
						
					}
					else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
					{
						combineTab.setBackgroundResource(R.drawable.club_astripper);	
						getAllLists("stripper");
						reviewrow.setAdapter(new FavouriteListAdapter(text, image,id));
					}
				
				}
				
				selectedTab=2;
				break;

			case R.id.favourite_firsttab:
				if (selectedTab!=1) {
				
					if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
					{
						getAllLists("fan");	
						reviewrow.setAdapter(new FavouriteListAdapter(text, image,id));
						combineTab.setBackgroundResource(R.drawable.afan_club);
					
					}
					else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
					{
						getAllLists("fan");	
						reviewrow.setAdapter(new FavouriteListAdapter(text, image,id));
						combineTab.setBackgroundResource(R.drawable.afan_stripper);	
						
					}
					else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
					{
						getAllLists("club");
						reviewrow.setAdapter(new FavouriteListAdapter(text, image,id));
						combineTab.setBackgroundResource(R.drawable.aclub_stripper);	
						
					}
				}
				
				selectedTab=1;
				break;
			default:
				break;
			}
		}
	};

	//private EditText edittextSearchbyName;

	private void setContent() {
		combineTab=(LinearLayout)view.findViewById(R.id.relative_picture2);
		clubbtn=(Button)view.findViewById(R.id.favourite_firsttab);
		clubbtn.setOnClickListener(listener);

		ImageView title=(ImageView)view.findViewById(R.id.ImageView1);
		title.setImageResource(R.drawable.favourite);
		fanbtn=(Button)view.findViewById(R.id.favourite_secondtab);
		fanbtn.setOnClickListener(listener);

	//	mSearchLayout=(EditText)view.findViewById(R.id.stripper_offer_search_bylocation);
	//	mSearchLayout.setVisibility(View.GONE);
		
		if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
		{
			combineTab.setBackgroundResource(R.drawable.afan_club);
		
		}
		else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
		{
			combineTab.setBackgroundResource(R.drawable.afan_stripper);	
			
		}
		else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
		{
			combineTab.setBackgroundResource(R.drawable.aclub_stripper);	
			
		}
		reviewrow=(ListView)view.findViewById(R.id.stripper_view_reviews_list);
		reviewrow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (selectedTab==1) {
					if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
					{
				//	Toast.makeText(getActivity(), "club", 1).show();
					
					}else{
						
						Toast.makeText(getActivity(), "stripper", 1).show();
						
						}
					
				
				}else {
					
					if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
					{
						
					//	Toast.makeText(getActivity(), "club", 1).show();
					
					}else {
					//	Toast.makeText(getActivity(), "fan", 1).show();
						
					}
					
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
	
	private void searchByName()
	{
		textlength = edittextSearchbyName.getText().length();
		text_sort.clear();
		id_sort.clear();
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
					id_sort.add(id.get(i));
				}
			}
		}
		reviewrow.setAdapter(new FavouriteListAdapter
				(text_sort, image_sort,id_sort));
	}

	public class FavouriteListAdapter extends BaseAdapter
	{
		String[] data_text,data_id;
		String[] data_image;

		FavouriteListAdapter()
		{

		}

		FavouriteListAdapter(ArrayList<String> text, ArrayList<String> image,ArrayList<String> id)
		{ 
			data_text = new String[text.size()];
			data_image = new String[image.size()];
			data_id= new String[id.size()];

			for(int i=0;i<text.size();i++)
			{
				data_text[i] = text.get(i);
				data_image[i] = image.get(i);
				data_id[i] = id.get(i);
			}
		}

		public int getCount()
		{
			return data_text.length;
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
			LayoutInflater inflater =getActivity().getLayoutInflater();
			View row;

			row = inflater.inflate(R.layout.view_review_row_item, parent, false);

			com.tny.utils.MyTextView name = (com.tny.utils.MyTextView) row.findViewById(R.id.view_review_row_name);
			com.tny.utils.MyTextView id = (com.tny.utils.MyTextView) row.findViewById(R.id.view_review_row_review);
			ImageView imageview = (ImageView) row
					.findViewById(R.id.view_review_row_image);

			name.setText(data_text[position]);
			id.setText(data_id[position]);
		    imageLoader.DisplayImage(data_image[position], imageview);

			return (row);

		}
	}
	private void getAllLists(String userType) {
		text.clear();
	    image.clear();
		id.clear();
		text=Favourites.getName(userType);
		image=Favourites.getImageUrl(userType);
		id=Favourites.getId(userType);
		
	}

}
