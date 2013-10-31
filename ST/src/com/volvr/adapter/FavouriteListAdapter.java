package com.volvr.adapter;
//package com.strippers.adapter;
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
//public class FavouriteListAdapter extends BaseAdapter
//{
//	LayoutInflater nflater;
//	Context mCntxt;
//
//	String[] data_text,data_comment;
//	int[] data_image;
//
//	public FavouriteListAdapter(Context cntext,String[] text,int[] image,String[] comment) 
//	{
//		mCntxt = cntext;
//
//		data_text = new String[text.length];
//		data_image = new int[image.length];
//		data_comment= new String[comment.length];
//
//		data_text = text;
//		data_image = image;
//		data_comment = comment;
//		nflater = (LayoutInflater) mCntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	@Override
//	public int getCount() 
//	{
//		return data_text.length;
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
//		        objHolder.reviewimage.setImageResource(data_image[position]);
//				objHolder.sponsorsNameTextView.setText(data_text[position]);
//				objHolder.sponsorsLinkTextView.setText(data_comment[position]);
//		return view;
//	}
//
//}
