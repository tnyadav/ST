package com.tny.volvr.dancer.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import com.tny.utils.Callback;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.FanInfo;
import com.volvr.beans.Inbox;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class InboxActivity extends BaseFragment{
	private String TAG="InboxActivity";
	private View view;
	private Button clubbtn,fanbtn;
	private ListView reviewrow;
	private EditText edittextSearchbyName;
	private int textlength = 0;
	private LinearLayout combineTab;
	private ArrayList<String> text_sort = new ArrayList<String>();
	private ArrayList<String> image_sort = new ArrayList<String>();
	private ArrayList<String> comment_sort = new ArrayList<String>();
	//private Handler handler;
	private List<NameValuePair> entity = new ArrayList<NameValuePair>();
	private ArrayList<String> text=new ArrayList<String>(),
			comment=new ArrayList<String>(),
			image=new ArrayList<String>(),
	        id=new ArrayList<String>();
	 private FavouriteListAdapter favouriteListAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,

			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_inbox_ui, container, false);
		entity.clear();
		
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if(Util1.isNetworkAvailable(getActivity())){
			setContent();
		}
		else
			UIUtils.showNetworkErrorMessage(getActivity());
	}

	private OnClickListener listener        =   new OnClickListener()
	{
		@Override
		public void onClick(View v){
			entity.clear();
			switch (v.getId()) {
			case R.id.favourite_secondtab:
				combineTab.setBackgroundResource(R.drawable.newmsg_inbox);	
				mActivity.pushFragments(AppConstants.TAB_MESSAGES, new TabMessages("inbox"), true,true);
				break;
			default:
				break;
			}
		}
	};

	private void setContent() {
		combineTab=(LinearLayout)view.findViewById(R.id.relative_picture2);
		clubbtn=(Button)view.findViewById(R.id.favourite_firsttab);
		clubbtn.setOnClickListener(listener);

		fanbtn=(Button)view.findViewById(R.id.favourite_secondtab);
		fanbtn.setOnClickListener(listener);
				String user_Id="";
		 if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
			 user_Id=StripperInfo.user_id;
        	if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
        		 user_Id=Clubprofile.clubs.getClub_id();
        	if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
        		 user_Id=FanInfo.fanproFileInfo.user_id;
        //	Toast.makeText(mActivity, user_Id, 3).show();
		List<NameValuePair> entity = new ArrayList<NameValuePair>();
		entity.add(new BasicNameValuePair("action","getmessages"));
		entity.add(new BasicNameValuePair("user_id",user_Id));
		
		
	//	try {
			 new AsyncWebServiceProcessingTask(getActivity(),  entity, "Loading...",new Callback() {
				
				@Override
				public void run(String response) {
					// TODO Auto-generated method stub
					try {
						
						if(new JSONObject(response).optString("status").equalsIgnoreCase("error"))
						{
							UIUtils.showMessage(getActivity(), "Message",new JSONObject(response).optString("msg"));
						}
						else
						{
						Inbox.parseInbox(new JSONObject(response));
						text=Inbox.getName();
						image=Inbox.getImageUrl();
						comment=Inbox.getDescription();
						id=Inbox.getId();
						//reviewrow.setAdapter(new FavouriteListAdapter(text, image,comment));
						favouriteListAdapter=new FavouriteListAdapter(text, image,comment);
						reviewrow.setAdapter(favouriteListAdapter);
						//favouriteListAdapter.notifyDataSetChanged();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).execute("");
		/*} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		text.clear();
		image.clear();
		comment.clear();
		id.clear();

		reviewrow=(ListView)view.findViewById(R.id.stripper_view_reviews_list);
		
		reviewrow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				mActivity.pushFragments(AppConstants.TAB_MESSAGES, new ReplayMessages(text.get(arg2),id.get(arg2).toString(),image.get(arg2),comment.get(arg2)),true,true);
				
			}
		});

		edittextSearchbyName = (EditText)view. findViewById(R.id.stripper_history_search_byname);
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
	//	public ImageLoader imageLoader; 

		FavouriteListAdapter()
		{

		}

		FavouriteListAdapter(ArrayList<String> text, ArrayList<String> image,ArrayList<String> comment)
		{ 
			data_text =text;
			data_image =image;
			data_comment= comment;
		//	imageLoader=new ImageLoader(mActivity);
		    inflater =getActivity().getLayoutInflater();
		    Log.e("data_text",TextUtils.join(",",data_text));
		    Log.e("data_comment",TextUtils.join(",",data_comment));
		    Log.e("data_image",TextUtils.join(",",data_image));
		

		}

		public int getCount()
		{
			return data_image.size();
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
			
			View row = convertView;
			
			row = inflater.inflate(R.layout.view_review_row_item, parent, false);

			com.tny.utils.MyTextView name = (com.tny.utils.MyTextView) row.findViewById(R.id.view_review_row_name);
			com.tny.utils.MyTextView comment = (com.tny.utils.MyTextView) row.findViewById(R.id.view_review_row_review);
			ImageView imageview = (ImageView) row
					.findViewById(R.id.view_review_row_image);

			name.setText(data_text.get(position));
			comment.setText(data_comment.get(position));
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
		/*reviewrow.setAdapter(new FavouriteListAdapter
				(text_sort, image_sort,comment_sort));*/
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (favouriteListAdapter!=null) {
			
			favouriteListAdapter.notifyDataSetChanged();
		}
		
	}
}
