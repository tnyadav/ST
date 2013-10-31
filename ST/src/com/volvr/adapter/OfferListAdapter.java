package com.volvr.adapter;
//package com.strippers.adapter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.strippers.search.R;
//
//public class OfferListAdapter extends BaseAdapter
//{
//	LayoutInflater nflater;
//	Context mCntxt;
//	ArrayList<HashMap<String, Object>> searchResults=new ArrayList<HashMap<String,Object>>();
//	public OfferListAdapter(Context cntext,ArrayList<HashMap<String, Object>> Stringss) 
//	{
//		mCntxt = cntext;
//		nflater = (LayoutInflater) mCntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		searchResults=Stringss;
//	}
//	
//	@Override
//	public int getCount() 
//	{
//		return searchResults.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) 
//	{
//		return arg0;
//	}
//
//	@Override
//	public long getItemId(int arg0) 
//	{
//		return arg0;
//	}
//
//	public class ViewHolder 
//	{
//		TextView sponsorsNameTextView, sponsorsLinkTextView;
//		ImageView reviewimage;
//	}
//	
//	@Override
//	public View getView(int position, View arg1, ViewGroup arg2) 
//	{
//		ViewHolder objHolder;
//		View view = arg1;	
//		if (arg1 == null) 
//		{
//			objHolder = new ViewHolder();
//			nflater = (LayoutInflater)mCntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			view = nflater.inflate(R.layout.view_review_row_item, null);
//			objHolder.sponsorsNameTextView = (TextView) view.findViewById(R.id.view_review_row_name);
//			objHolder.sponsorsLinkTextView = (TextView) view.findViewById(R.id.view_review_row_review);
//			objHolder.reviewimage=(ImageView)view.findViewById(R.id.view_review_row_image);
//		}
//		else 
//		{
//			objHolder = (ViewHolder) view.getTag();
//		}
//		
//		   int photoId=(Integer) searchResults.get(position).get("photo");
//		 
//		   //set the data to be displayed
//		   objHolder.reviewimage.setImageDrawable(mCntxt.getResources().getDrawable(photoId));
//		   objHolder.sponsorsNameTextView.setText(searchResults.get(position).get("name").toString());
//		   objHolder.sponsorsLinkTextView.setText(searchResults.get(position).get("team").toString());
////		objHolder.sponsorsNameTextView.setText(StripperInfo.ALStripperReviews.get(arg0).get());
////		objHolder.sponsorsLinkTextView.setText(mDataList.get(arg0).getLink());
//		return view;
//	}
//
//}
