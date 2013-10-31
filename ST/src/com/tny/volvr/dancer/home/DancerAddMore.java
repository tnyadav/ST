package com.tny.volvr.dancer.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.JsonResponse;
import com.tny.utils.MoreImageUploadingAsyncTask;
import com.tny.utils.UIUtils;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.home.ImagePagerActivity;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class DancerAddMore extends BaseFragment {
	private  String t = "StripperViewReview";
	private View view;
	private Button back, addmore;
	public static ArrayList<String> imageUrls = new ArrayList<String>();
	ArrayList<String> uplaodingImageUrls = new ArrayList<String>();
	private DisplayImageOptions options;
	GridView gridView;
	private Uri capturedImageURI;
	boolean editFlag = false,photoTaken=false;
	Handler handler,handler1;
	private boolean editMode=false;
    private boolean  showedit=true;
    private ImageLoader imageLoader=ImageLoader.getInstance();
	public DancerAddMore(boolean showEdit) {
		showedit=showEdit;
	}
	public DancerAddMore() {
	
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.ac_image_grid, container, false);
		setContent();
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d(t, JsonResponse.JsonResponse);
				try {
					JSONObject jsonResponse=new JSONObject(JsonResponse.JsonResponse);
					if(!jsonResponse.optString("status").equalsIgnoreCase("success"))
					{
						UIUtils.showMessage(getActivity(), "Message", "Uploading failed");
					}
					imageUrls.clear();
					getActivity().onBackPressed();
				} catch (Exception e) {
					e.printStackTrace();
					imageUrls.clear();
					getActivity().onBackPressed();
				}
			}
		};

		handler1=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d(t, JsonResponse.JsonResponse);
			}
		};


		return view;
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.stripper_traval_back:
				if (editFlag) {
					editFlag = false;
					addmore.setText("Edit");
					back.setBackgroundResource(R.drawable.btn_done);
					gridView.setAdapter(new ImageAdapter());
				} else if(editMode){
					Log.e("Image size=", ""+uplaodingImageUrls.size());
					if(uplaodingImageUrls.size()>0)
						new MoreImageUploadingAsyncTask(getActivity(), handler, "uploadavatar", "Uploading...",uplaodingImageUrls).execute("");
					else{
						imageUrls.clear();
						getActivity().onBackPressed();
					}
				}
				else{
					imageUrls.clear();
					getActivity().onBackPressed();
				}
				break;
			case R.id.stripper_traval_add_more:
				if (addmore.getText().toString().equalsIgnoreCase("edit")) {
					editFlag = true;
					editMode=true;
					addmore.setText("Upload");
					back.setBackgroundResource(R.drawable.btn_done);
					gridView.setAdapter(new ImageAdapter());
				} else {
					if (imageUrls.size() >= 10) {
						UIUtils.showMessage(getActivity(), "Sorry!",
								"You can't upload more than 10 photos.");
					} else {
						showTakeImageDialog();
					}
				}
				break;
			default:
				break;
			}

		}
	};
	private TextView uploadimage;

	// Method for choosing images from Gallery or Camera....
	void showTakeImageDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Choose Image");
		builder.setMessage("Pick Image From ?");

		builder.setPositiveButton("Camera",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ContentValues values = new ContentValues();
				String fileName = System.currentTimeMillis() + ".png";
				values.put(MediaStore.Images.Media.TITLE, fileName);
				try {
					capturedImageURI = getActivity()
							.getContentResolver()
							.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									values);
					Intent intent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
							capturedImageURI);
					getActivity().startActivityForResult(intent, 1);
				} catch (Exception e) {
					Toast.makeText(getActivity(),
							"SD Card Not Available", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("Gallery",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					Intent picImage = new Intent();
					picImage.setAction(Intent.ACTION_PICK);
					picImage.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					getActivity().startActivityForResult(picImage, 2);
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(),
							"SD Card Not Available", Toast.LENGTH_LONG)
							.show();
				}
				dialog.dismiss();
			}
		});
		builder.create();
		builder.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("resultCodeafter picking image", "" + requestCode + "krishna"
				+ String.valueOf(resultCode));
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 1:
				onPhotoTaken();
				break;
			case 2:
				onPhotoTakenFromgallery(data);
				break;
			default:
				break;
			}
		}
	}

	// Method for photos taken from camera...
	private void onPhotoTaken() {
		Log.i("onphototaken", "krishna");
		try {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().managedQuery(capturedImageURI,
					projection, null, null, null);
			int column_index_data = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			getActivity().startManagingCursor(cursor);
			cursor.moveToFirst();
			// mCount = cursor.getCount();
			String imagePath = cursor.getString(column_index_data);
			Log.i("imagepath", "camera"+imagePath);
			imageUrls.add("file://"+imagePath);
			uplaodingImageUrls.add("file://"+imagePath);
			photoTaken=true;
			gridView.setAdapter(new ImageAdapter());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("imagePath", "onactivity result");
		}
	}

	// Method for photos taken from phone's gallery...
	protected void onPhotoTakenFromgallery(Intent data) {
		Log.i("onphototakenfromgallery", "krishna");
		try {
			Uri _uri = data.getData();
			if (_uri != null) {
				Cursor cursor = getActivity()
						.getContentResolver()
						.query(_uri,
								new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
								null, null, null);
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				getActivity().startManagingCursor(cursor);
				cursor.moveToFirst();
				// mCount = cursor.getCount();
			    String imagePath = cursor.getString(column_index);
				Log.i("imagepath", "camera"+imagePath);
				imageUrls.add("file://"+imagePath);
				uplaodingImageUrls.add("file://"+imagePath);
//				InitialImageUrls.remove(InitialImageUrls.size()-1);
//				Log.e("InitialImageUrls=",""+ InitialImageUrls.length);
				Log.e("imageUrls=",""+ imageUrls.size());
				photoTaken=true;
				gridView.setAdapter(new ImageAdapter());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("imagePath", "onactivity result");
		}
	}

	@Override
	public void onStop() {
		imageLoader.stop();
		super.onStop();
	};

	private void setContent() {
		back = (Button) view.findViewById(R.id.stripper_traval_back);
		back.setOnClickListener(listener);
		addmore = (Button) view.findViewById(R.id.stripper_traval_add_more);
		addmore.setOnClickListener(listener);
		uploadimage = (TextView) view.findViewById(R.id.stripper_add_more_hint);
		if (!showedit) {
			addmore.setVisibility(View.GONE);
		}
		if (imageUrls.size()==0)
		{
			UIUtils.showMessage(getActivity(), "Message","No image avalable");
		}

//		if(UserInfo.USERTYPE.equalsIgnoreCase("club") || UserInfo.USERTYPE.equalsIgnoreCase("fan"))
//		{
//			addmore.setVisibility(View.GONE);
//			uploadimage.setVisibility(View.GONE);
//		}

		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.frame)
		.showImageForEmptyUri(R.drawable.image_for_empty_url)
		.cacheInMemory().cacheOnDisc().build();

		gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter());
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (editFlag) {

				} else {
					mActivity.pushFragments(AppConstants.TAB_HOME,
							new ImagePagerActivity(imageUrls, position), true,
							true);
				}
			}
		});
	}

	public class ImageAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return imageUrls.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final RelativeLayout imageView;
			ImageView image = null;
			TextView cancelImageView = null;
			imageView = (RelativeLayout) getActivity().getLayoutInflater()
					.inflate(R.layout.item_grid_image, parent, false);
			image = (ImageView) imageView.findViewById(R.id.image);
			cancelImageView = (TextView) imageView.findViewById(R.id.imgQueueMultiSelected);
			if (editFlag) 
			{
				cancelImageView.setVisibility(View.VISIBLE);
			}
			Log.e("imagepath", ""+imageUrls.get(position));
			imageLoader.displayImage(imageUrls.get(position), image, options,
					new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(Bitmap loadedImage) {
					try {
						Animation anim = AnimationUtils.loadAnimation(
								getActivity(), R.anim.fade_in);
						imageView.setAnimation(anim);
						anim.start();
					} catch (Exception e) {
					}
				}
			});

			cancelImageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","deletestripimage"));
					entity.add(new BasicNameValuePair("user_id",StripperInfo.user_id));
					entity.add(new BasicNameValuePair("img_url",""+imageUrls.get(position)));
					new AsyncWebServiceProcessingTask(getActivity(), handler1, entity, "Please wait....").execute(""); 
					imageUrls.remove(position);
					setContent();
//					notifyDataSetChanged();
				}
			});

			return imageView;
		}
	}
}
