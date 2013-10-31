package com.tny.volvr.home;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.tny.volvr.base.BaseFragment;
import com.volvr.search.R;

public class ImagePagerActivity extends BaseFragment
{
	private View view;
	private ViewPager pager;
	private DisplayImageOptions options;
	int position=0;
	ArrayList<String> imageUrls=new ArrayList<String>();
	 private ImageLoader imageLoader=ImageLoader.getInstance();
	public ImagePagerActivity(ArrayList<String> url,int position) {
		this.position=position;
		imageUrls=url;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.ac_image_pager, container, false);
		
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.image_for_empty_url).cacheOnDisc()
				.imageScaleType(ImageScaleType.EXACT).build();

		pager = (ViewPager)view. findViewById(R.id.pager);

		pager.setAdapter(new ImagePagerAdapter(imageUrls));
		pager.setCurrentItem(position);
		return view;
	}

	@Override
	public void onStop() {
		imageLoader.stop();
		super.onStop();
	};

	private class ImagePagerAdapter extends PagerAdapter
	{

		private ArrayList<String> images;
		private LayoutInflater inflater;

		ImagePagerAdapter(ArrayList<String> images)
		{
			this.images = images;
			inflater = getActivity().getLayoutInflater();
		}

		@Override
		public void destroyItem(View container, int position, Object object)
		{
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container)
		{
		}

		@Override
		public int getCount()
		{
			return images.size();
		}

		@Override
		public Object instantiateItem(View view, int position)
		{
			final View imageLayout = inflater.inflate(R.layout.item_pager_image, null);
			final TouchImageView imageView = (TouchImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			imageLoader.displayImage(images.get(position), imageView, options, new ImageLoadingListener()
			{
				@Override
				public void onLoadingStarted()
				{
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(FailReason failReason)
				{
					String message = null;
					switch (failReason)
					{
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
					spinner.setVisibility(View.GONE);
					imageView.setImageResource(android.R.drawable.ic_delete);
				}

				@Override
				public void onLoadingComplete(Bitmap loadedImage)
				{
					try {
						spinner.setVisibility(View.GONE);
						Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
						imageView.setAnimation(anim);
						anim.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onLoadingCancelled()
				{
				}
			});

			((ViewPager) view).addView(imageLayout, 0);

			imageView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
				}
			});

			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader)
		{
		}

		@Override
		public Parcelable saveState()
		{
			return null;
		}

		@Override
		public void startUpdate(View container)
		{
		}
	}

}