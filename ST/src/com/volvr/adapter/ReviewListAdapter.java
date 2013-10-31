package com.volvr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class ReviewListAdapter extends BaseAdapter
{
	LayoutInflater nflater;
	Context mCntxt;
	public ReviewListAdapter(Context cntext) 
	{
		mCntxt = cntext;
		nflater = (LayoutInflater) mCntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() 
	{
		return StripperInfo.ALStripperReviews.size();
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
		com.tny.utils.MyTextView sponsorsNameTextView, sponsorsLinkTextView;
		ImageView reviewimage;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) 
	{
		ViewHolder objHolder;
		View view;	
		objHolder = new ViewHolder();
		nflater = (LayoutInflater)mCntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = nflater.inflate(R.layout.view_review_row_item, null);
		objHolder.sponsorsNameTextView = (com.tny.utils.MyTextView) view.findViewById(R.id.view_review_row_name);
		objHolder.sponsorsLinkTextView = (com.tny.utils.MyTextView) view.findViewById(R.id.view_review_row_review);
		objHolder.reviewimage=(ImageView)view.findViewById(R.id.view_review_row_image);
		try {
			objHolder.sponsorsNameTextView.setText(StripperInfo.ALStripperReviews.get(arg0).getReview_provider_name());
			objHolder.sponsorsLinkTextView.setText(StripperInfo.ALStripperReviews.get(arg0).getReview_comments());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
}
