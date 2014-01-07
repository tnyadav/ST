package com.tny.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.tnutil.Util;

public class UIUtils {
	static float density = 1;
	private static ProgressDialog mProgressDialog;
	
	public static int getScreenWidth(Context context){
	    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = windowManager.getDefaultDisplay();
	    return display.getWidth();
	}
	
	public static void showSimpleSpinProgressDialog(Context context, String message) {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	public static void removeSimpleSpinProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.cancel();
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	public static void showNetworkErrorMessage(final Context context){
		/*Builder dlg = new AlertDialog.Builder(context);
		dlg.setCancelable(false);
		dlg.setTitle("Error");
		dlg.setMessage("Network error has occured. Please check the network status of your phone and retry");
		dlg.setPositiveButton("Retry", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,	int which) {
				
			}
		});
		dlg.setNegativeButton("Close", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int which) {
				((Activity)context).finish();
				System.exit(0);
			}
		});
		dlg.show();*/
		Util.showCustomDialog(context, "Error", "Network error has occured. Please check the network status of your phone and retry");
	}
	
	public static void showMessage(Context context, String strTitle, String strMsg)
	{
		/*Builder dlg = new AlertDialog.Builder(context);
		dlg.setCancelable(false);
		dlg.setTitle(strTitle);
		dlg.setMessage(strMsg);
		dlg.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,	int which) {
				
			}
		});
		dlg.show();*/
		Util.showCustomDialog(context, strTitle, strMsg);
	}
	
	public static float getDisplayMetricsDensity(Context context) {
		density = context.getResources().getDisplayMetrics().density;
		
		return density;
	}
	
	public static int getPixel(Context context, int p) {
		if (density != 1) {
			return (int)(p*density + 0.5);
		}
		return p;
	}
	
	public static Animation FadeAnimation(float nFromFade, float nToFade) {
		Animation fadeAnimation = new AlphaAnimation(nToFade, nToFade);
		
		return fadeAnimation;
	}
	
	public static Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
		);
		
		return inFromRight;
	}

	public static Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
		);
		
		return inFromLeft;
	}

	public static Animation inFromBottomAnimation() {
		Animation inFromBottom = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
			Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f
		);
		
		return inFromBottom;
	}

	public static Animation outToLeftAnimation() {
		Animation outToLeft = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
		);
		
		return outToLeft;
	}

	public static Animation outToRightAnimation() {
		Animation outToRight = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
		);
		
		return outToRight;
	}

	public static Animation outToBottomAnimation() {
		Animation outToBottom = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f
		);
		
		return outToBottom;
	}
	
	/*public static String create_imgage_view(String imageURL, int width, int height) {
		StringBuffer html = new StringBuffer("<html lang='en'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head>");
		html.append("<body style='width:"+width+"; height:"+height+"; padding:0; margin:0;'>");
		
		html.append("<div style='width:"+width+"; height:"+height+"; overflow:hidden;'>");
		html.append("<img src='"+imageURL+"' style='width:"+width+";' border=0 />");
		html.append("</div>");
		
		html.append("</body>");
		html.append("</html>");
		
		return html.toString();
	}*/
	public static String checkBlanck(String string,boolean isLast) {
		
		if (string==null||string.equals("")) {
			return "";
		}else {
			if (isLast) {
				return string;
			}else {
				return string+",";
			}
		}
	}
}

