package com.example.tnutil;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Util {
	public static void showCustomDialog( final Context activity,final String strTitle,final String strMsg,final String ok,final String cancel,final Callback callback) {
			final Dialog dialog = new Dialog(activity,
				android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
		dialog.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog);

		TextView title = (TextView) dialog.findViewById(R.id.title);
		title.setText(strTitle);
		TextView msg = (TextView) dialog.findViewById(R.id.msg);
		msg.setText(strMsg);
		Button btnSearch = (Button) dialog.findViewById(R.id.btnsearch);
		btnSearch.setText(ok);
		Button btnCancel = (Button) dialog.findViewById(R.id.btncancel);
        btnCancel.setText(cancel);
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callback.ok();
				dialog.dismiss();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callback.cancel();
				dialog.dismiss();
				
			}
		});

		dialog.show();
	
	
		
	}
	public static void showCustomDialog( final Context activity,final String strTitle,final String strMsg,final Callback callback) {
		final Dialog dialog = new Dialog(activity,
			android.R.style.Theme_Translucent);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	
	dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
	dialog.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
	dialog.setCancelable(true);
	dialog.setContentView(R.layout.dialog);

	TextView title = (TextView) dialog.findViewById(R.id.title);
	title.setText(strTitle);
	TextView msg = (TextView) dialog.findViewById(R.id.msg);
	msg.setText(strMsg);
	Button btnSearch = (Button) dialog.findViewById(R.id.btnsearch);
	btnSearch.setText(R.string.ok);
	Button btnCancel = (Button) dialog.findViewById(R.id.btncancel);
    btnCancel.setText(R.string.cancel);
	btnSearch.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			callback.ok();
			dialog.dismiss();
		}
	});
	btnCancel.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			callback.cancel();
			dialog.dismiss();
			
		}
	});

	dialog.show();


	
}
	public static void showCustomDialog( final Context activity,final String strTitle,final String strMsg,final Callback1 callback) {
		final Dialog dialog = new Dialog(activity,
			android.R.style.Theme_Translucent);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	
	dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
	dialog.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
	dialog.setCancelable(true);
	dialog.setContentView(R.layout.dialog1);

	TextView title = (TextView) dialog.findViewById(R.id.title);
	title.setText(strTitle);
	TextView msg = (TextView) dialog.findViewById(R.id.msg);
	msg.setText(strMsg);
	Button btnOk = (Button) dialog.findViewById(R.id.btnsearch);
	btnOk.setText(R.string.ok);
    btnOk.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			callback.ok();
			dialog.dismiss();
		}
	});
	

	dialog.show();


	
}
	public static void showCustomDialog( final Context activity,final String strTitle,final String strMsg) {
		final Dialog dialog = new Dialog(activity,
			android.R.style.Theme_Translucent);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	
	dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
	dialog.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
	dialog.setCancelable(true);
	dialog.setContentView(R.layout.dialog1);

	TextView title = (TextView) dialog.findViewById(R.id.title);
	title.setText(strTitle);
	TextView msg = (TextView) dialog.findViewById(R.id.msg);
	msg.setText(strMsg);
	Button btnOk = (Button) dialog.findViewById(R.id.btnsearch);
	btnOk.setText(R.string.ok);
    btnOk.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			dialog.dismiss();
		}
	});
	

	dialog.show();


	
}
	public static void showCustomDialog( final Context activity,final String strMsg) {
		final Dialog dialog = new Dialog(activity,
			android.R.style.Theme_Translucent);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	
	dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
	dialog.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
	dialog.setCancelable(true);
	dialog.setContentView(R.layout.dialog1);

	TextView title = (TextView) dialog.findViewById(R.id.title);

	TextView msg = (TextView) dialog.findViewById(R.id.msg);
	msg.setText(strMsg);
	Button btnOk = (Button) dialog.findViewById(R.id.btnsearch);
	btnOk.setText(R.string.ok);
    btnOk.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			dialog.dismiss();
		}
	});
	

	dialog.show();


	
}
	public static void toast(Context context,String msg) {
		LayoutInflater inflater =  (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View layout = inflater.inflate(R.layout.toast,null);
			TextView text = (TextView) layout.findViewById(R.id.title);
		text.setText(msg);

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
	
}
