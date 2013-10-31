package com.tny.volvr.more;

import java.util.ArrayList;

import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class More_History extends BaseFragment{
	private View view;
	Button clubbtn,fanbtn;
	private ListView reviewrow;
	EditText edittextName,edittextDate;
	int textlength = 0;
	LinearLayout combineTab;
	ArrayList<String> text_sort = new ArrayList<String>();
	ArrayList<String> image_sort = new ArrayList<String>();
	ArrayList<String> comment_sort = new ArrayList<String>();
	static int selectedTab=1;

	String[] text = { "OmPrakash", "Krishna", "Triloki", "Arjun", "Krishna", "Shukla", "Jitendra",
			"Subhash", "Saurabh", "Mishra" };

	String[] comment = { "12/1/2012", "12/2/2012", "12/1/2012", "12/10/2012", "12/10/2012", "1/12/2012", "17/12/2012",
			"12/12/2012", "12/12/2012", "12/12/2012" };

	String[] time = { "10 am", "11 am", "12 am", "11 am", "10 am", "11 am", "12 am",
			"8 am", "9 am", "10 am" };

	String[] text1 =  { "JOHN", "JACK", "JOCCY", "CYMAN", "DONALDO", "SACHIN", "ERIC",
			"JACK", "HADDIN", "SHUKLA" };

	String[] comment1 = { "10/12/2012", "9/12/2012", "4/12/2012", "2/12/2012", "5/12/2012", "6/12/2012", "9/12/2012",
			"1/12/2012", "1/12/2012", "1/12/2012" };

	String[] time1 = { "2 am", "4 am", "4 am", "4 am", "4 am", "4 am", "4 am",
			"4 am", "4 am", "4 am" };


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_view_history, container, false);
		setContent();

		return view;
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.history_secondtab:
				selectedTab=2;
				if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
				{
				combineTab.setBackgroundResource(R.drawable.fan_aclub);
				}
				else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
				{
				combineTab.setBackgroundResource(R.drawable.fan_astripper);	
				}
				else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
				{
				combineTab.setBackgroundResource(R.drawable.club_astripper);	
				}
				reviewrow.setAdapter(new FavouriteListAdapter(text1, time1,comment1));
				break;

			case R.id.history_firsttab:
				selectedTab=1;
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
				reviewrow.setAdapter(new FavouriteListAdapter(text,time,comment));
				break;
			default:
				break;
			}
		}
	};

	private void setContent() {
		combineTab=(LinearLayout)view.findViewById(R.id.relative_picture2);
		clubbtn=(Button)view.findViewById(R.id.history_firsttab);
		clubbtn.setOnClickListener(listener);

		fanbtn=(Button)view.findViewById(R.id.history_secondtab);
		fanbtn.setOnClickListener(listener);

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
		combineTab.setBackgroundResource(R.drawable.club_astripper);	
		}
		reviewrow=(ListView)view.findViewById(R.id.stripper_view_history_list);
		reviewrow.setAdapter(new FavouriteListAdapter(text, time,comment));
		reviewrow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//			mActivity.pushFragments(AppConstants.TAB_HOME, new StripperViewReviewFull(),true,true);
			}
		});


		edittextDate = (EditText)view. findViewById(R.id.history_searchbydate_edittext);
		edittextDate.addTextChangedListener(new TextWatcher()
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
				textlength = edittextDate.getText().length();
				text_sort.clear();
				comment_sort.clear();
				image_sort.clear();
				if(selectedTab==1)
				{
					for (int i = 0; i < comment.length; i++)
					{
						if (textlength <= comment[i].length())
						{
							if (edittextDate.getText().toString().
									equalsIgnoreCase((String) comment[i].subSequence(0, textlength)))
							{
								text_sort.add(text[i]);
								image_sort.add(time[i]);
								comment_sort.add(comment[i]);
							}
						}
					}
					reviewrow.setAdapter(new FavouriteListAdapter
							(text_sort, image_sort,comment_sort));

				}
				else
				{
					for (int i = 0; i < comment1.length; i++)
					{
						if (textlength <=comment1[i].length())
						{
							if (edittextDate.getText().toString().
									equalsIgnoreCase((String) comment1[i].subSequence(0, textlength)))
							{
								text_sort.add(text1[i]);
								image_sort.add(time1[i]);
								comment_sort.add(comment1[i]);
							}
						}
					}
					reviewrow.setAdapter(new FavouriteListAdapter
							(text_sort, image_sort,comment_sort));
				}
			}
		});
		
		edittextName = (EditText)view. findViewById(R.id.stripper_history_search_byname);
		edittextName.addTextChangedListener(new TextWatcher()
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

				textlength = edittextName.getText().length();
				text_sort.clear();
				comment_sort.clear();
				image_sort.clear();
				if(selectedTab==1)
				{
					for (int i = 0; i < text.length; i++)
					{
						if (textlength <= text[i].length())
						{
							if (edittextName.getText().toString().
									equalsIgnoreCase((String) text[i].subSequence(0, textlength)))
							{
								text_sort.add(text[i]);
								image_sort.add(time[i]);
								comment_sort.add(comment[i]);
							}
						}
					}
					reviewrow.setAdapter(new FavouriteListAdapter
							(text_sort, image_sort,comment_sort));

				}
				else
				{
					for (int i = 0; i < text1.length; i++)
					{
						if (textlength <= text1[i].length())
						{
							if (edittextName.getText().toString().
									equalsIgnoreCase((String) text1[i].subSequence(0, textlength)))
							{
								text_sort.add(text1[i]);
								image_sort.add(time1[i]);
								comment_sort.add(comment1[i]);
							}
						}
					}
					reviewrow.setAdapter(new FavouriteListAdapter
							(text_sort, image_sort,comment_sort));
				}
			}
		});

	}

	public class FavouriteListAdapter extends BaseAdapter
	{
		String[] data_text,data_comment,data_image;

		FavouriteListAdapter()
		{

		}

		FavouriteListAdapter(String[] text, String[] image,String[] comment)
		{
			data_text = new String[text.length];
			data_image = new String[image.length];
			data_comment= new String[comment.length];

			data_text = text;
			data_image = image;
			data_comment = comment;
		}

		FavouriteListAdapter(ArrayList<String> text, ArrayList<String> image,ArrayList<String> comment)
		{ 

			data_text = new String[text.size()];
			data_image = new String[image.size()];
			data_comment= new String[comment.size()];

			for(int i=0;i<text.size();i++)
			{
				data_text[i] = text.get(i);
				data_image[i] = image.get(i);
				data_comment[i] = comment.get(i);
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

			row = inflater.inflate(R.layout.view_history_row_item, parent, false);

			com.tny.utils.MyTextView name = (com.tny.utils.MyTextView) row.findViewById(R.id.view_history_row_name);
			com.tny.utils.MyTextView comment = (com.tny.utils.MyTextView) row.findViewById(R.id.view_history_row_date);
			com.tny.utils.MyTextView time = (com.tny.utils.MyTextView) row.findViewById(R.id.view_history_row_time);

			name.setText(data_text[position]);
			comment.setText(data_comment[position]);
			time.setText(data_image[position]);

			return (row);

		}
	}


}
