package com.tny.volvr.more;

import java.util.ArrayList;
import java.util.Iterator;
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
import android.widget.ListView;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.JsonResponse;
import com.tny.utils.UIUtils;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.common.AddOffers;
import com.tny.volvr.dancer.home.ViewAppointmentDetails;
import com.volvr.beans.OfferBean;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class StripperViewOffer extends BaseFragment{
	private View view;
	Button searchbtn;
	private Button back,addOffer;
	EditText edittext;
	ListView listview;
	private  String t="StripperViewOffer";
	int textlength = 0;
	ArrayList<String> text_sort = new ArrayList<String>();
	ArrayList<String> image_sort = new ArrayList<String>();
	ArrayList<String> comment_sort = new ArrayList<String>();
	ArrayList<String> text = new ArrayList<String>();
	ArrayList<String> image= new ArrayList<String>();
	ArrayList<String> comment = new ArrayList<String>();
	ArrayList<String> start_date = new ArrayList<String>();
	String action="";
	Handler handler;
	 private OfferListAdapter offerListAdapter;
	public StripperViewOffer(String actionValue) {
		action=actionValue;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_view_offers, container, false);

		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d(t, JsonResponse.JsonResponse);
				/*try {
					JSONObject jsonResponse=new JSONObject(JsonResponse.JsonResponse);
					StripperInfo.ALUserOffer.clear();
					text.clear();
					image.clear();
					comment.clear();
					start_date.clear();
					if(jsonResponse.optString("status").equalsIgnoreCase("success"))
					{
						StripperInfo.parseAllClubOffer(jsonResponse);
						text=getClubOfferName();
						image=getClubOfferUrl();
						comment=getClubOfferDesc();
						start_date=getClubOfferStartDate();
					}
					else
					{
						Util.showMessage(getActivity(), "Message","No offer found.");
					}
					setContent();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}

		};
		return view;
	}

	private ArrayList<String> getClubOfferDesc() {
		ArrayList<String> address=new ArrayList<String>();
		address.clear();
		Iterator<OfferBean> iterator = StripperInfo.ALUserOffer.iterator();
		while (iterator.hasNext()) {
			OfferBean clubInfo=iterator.next();
			address.add(clubInfo.getOffer_desc());
		}
		return address;
	}

	private ArrayList<String> getClubOfferStartDate() {
		ArrayList<String> address=new ArrayList<String>();
		address.clear();
		Iterator<OfferBean> iterator = StripperInfo.ALUserOffer.iterator();
		while (iterator.hasNext()) {
			OfferBean clubInfo=iterator.next();
			address.add(clubInfo.getOffer_start_date());
		}
		return address;
	}
	
	private ArrayList<String> getClubOfferUrl() {
		ArrayList<String> imageUrl=new ArrayList<String>();
		imageUrl.clear();
		Iterator<OfferBean> iterator = StripperInfo.ALUserOffer.iterator();
		while (iterator.hasNext()) {
			OfferBean clubInfo=iterator.next();
			imageUrl.add(clubInfo.getClub_thumb_pic());
		}
		return imageUrl;
	}

	private ArrayList<String> getClubOfferName() {
		ArrayList<String> name=new ArrayList<String>();
		name.clear();
		Iterator<OfferBean> iterator = StripperInfo.ALUserOffer.iterator();
		while (iterator.hasNext()) {
			OfferBean clubInfo=iterator.next();
			name.add(clubInfo.getClub_name());
		}
		return name;
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.stripper_view_reviews_back:
				getActivity().onBackPressed();	
				break;

			case R.id.stripper_signup1_selectimage:

				break;

			case R.id.stripper_view_add_offer:
				Log.i("ADD OFFER", " club_profile_add_offers clicked");
				mActivity.pushFragments(AppConstants.TAB_MORE,
						new AddOffers(), true, true);
				break;

			default:
				break;
			}

		}
	};


	@Override
	public void onResume() {
		super.onResume();
		
			List<NameValuePair> entity = new ArrayList<NameValuePair>();
			entity.add(new BasicNameValuePair("action",action));
			entity.add(new BasicNameValuePair("club_id",Clubprofile.clubs.getClub_id()));
			
			String response="";
			
			new AsyncWebServiceProcessingTask(getActivity(), entity, "Loading",new Callback() {
					
					@Override
					public void run(String result) {
						// TODO Auto-generated method stub
						try {
						JSONObject jsonResponse=new JSONObject(result);
						StripperInfo.ALUserOffer.clear();
						text.clear();
						image.clear();
						comment.clear();
						start_date.clear();
						if(jsonResponse.optString("status").equalsIgnoreCase("success"))
						{
							StripperInfo.parseAllClubOffer(jsonResponse);
							text=getClubOfferName();
							image=getClubOfferUrl();
							comment=getClubOfferDesc();
							start_date=getClubOfferStartDate();
						}
						else
						{
							UIUtils.showMessage(getActivity(), "Message","No offer found.");
						}
						setContent();
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
				}).execute("");
			
			
	}

	private void setContent() {
		edittext = (EditText)view. findViewById(R.id.stripper_offer_search);
		listview = (ListView) view.findViewById(R.id.stripper_view_reviews_list);
		
		offerListAdapter=new OfferListAdapter(text, image,comment);
		listview.setAdapter(offerListAdapter);
		listview.setAdapter(new OfferListAdapter(text, image,comment));
		offerListAdapter.notifyDataSetChanged();
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mActivity.pushFragments(AppConstants.TAB_MORE, new ViewAppointmentDetails(text.get(arg2),comment.get(arg2),start_date.get(arg2)),true,true);
			}
		});
		back=(Button)view.findViewById(R.id.stripper_view_reviews_back);
		back.setOnClickListener(listener);

		addOffer=(Button)view.findViewById(R.id.stripper_view_add_offer);
		addOffer.setOnClickListener(listener);
		if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
		{
			addOffer.setVisibility(View.GONE);
		}
//		else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
//		{
//			addOffer.setVisibility(View.GONE);
//		}
		else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
		{
			addOffer.setVisibility(View.GONE);
		}

		edittext.addTextChangedListener(new TextWatcher()
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

				textlength = edittext.getText().length();
				text_sort.clear();
				comment_sort.clear();
				image_sort.clear();
				for (int i = 0; i < text.size(); i++)
				{
					if (textlength <= text.get(i).length())
					{
						if (edittext.getText().toString().
								equalsIgnoreCase((String) text.get(i).subSequence(0, textlength)))
						{
							text_sort.add(text.get(i));
							image_sort.add(image.get(i));
							comment_sort.add(comment.get(i));
						}
					}
				}
				listview.setAdapter(offerListAdapter);
				listview.setAdapter(new OfferListAdapter(text, image,comment));
				offerListAdapter.notifyDataSetChanged();

			}
		});

	}


	public class OfferListAdapter extends BaseAdapter
	{
		ArrayList<String> data_text,data_comment;
		ArrayList<String> data_image;
		LayoutInflater inflater;
	
		OfferListAdapter()
		{

		}

		OfferListAdapter(ArrayList<String> text, ArrayList<String> image,ArrayList<String> comment)
		{ 
			data_text = text;
			data_image = image;
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
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	
		offerListAdapter.notifyDataSetChanged();
	}
}
