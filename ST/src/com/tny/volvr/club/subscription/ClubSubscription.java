package com.tny.volvr.club.subscription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.search.R;

public class ClubSubscription extends BaseFragment {
    TextView msubscription11,msubscription22,msubscription33;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.club_suscription, container, false);
            setContent();
            
            setContent();
        return view;
    }

    private void setContent() {
    	msubscription11=(TextView)view.findViewById(R.id.club_subscription1);
    	msubscription22=(TextView)view.findViewById(R.id.club_subscription2);
    	msubscription33=(TextView)view.findViewById(R.id.club_subscription3);
    	
    	msubscription11.setOnClickListener(listener);
		msubscription22.setOnClickListener(listener);
		msubscription33.setOnClickListener(listener);
	}
    
    private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.club_subscription1:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new Subscription_details("40"),true,true);
				break;

			case R.id.club_subscription2:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new Subscription_details("85"),true,true);
				break;

			case R.id.club_subscription3:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new Subscription_details("150"),true,true);
				break;
			default:
				break;
			}

		}
	};
}
