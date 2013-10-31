package com.tny.volvr.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fedorvlasov.lazylist.ImageLoader;


public class BaseFragment extends Fragment {
	public MainTabActivity mActivity;
//	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected ImageLoader imageLoader=new ImageLoader(mActivity);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivity		=	(MainTabActivity) this.getActivity();
	}
	
	public boolean onBackPressed(){
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data){
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//Toast.makeText(mActivity, "base fregament distroy", 3).show();
		
	}
	
	
}
