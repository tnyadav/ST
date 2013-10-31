package com.tny.volvr.more;

import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tny.utils.Constant;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.volvr.search.R;

public class More_Contactus extends BaseFragment{
private View view;
Button sendbtn,backbtn;
EditText namebox,messagebox,emailbox;
String emailformat="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view       =   inflater.inflate(R.layout.contact_us, container, false);
	        setContent();
	       
	        return view;
	}
	
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
				case R.id.contact_us_back:
				getActivity().onBackPressed();	
				break;
				case R.id.sendbtn:
					if(chkValidation())
					sendEmailForContact();
					break;
			default:
				break;
			}

		}

	};
	
	
	private boolean chkValidation() 
	{
		if(!Util1.isFieldEmpty(namebox.getText().toString()))
		{
			UIUtils.showMessage(getActivity(), "message", "Enter name");
			return false;
		}else if (!Util1.isFieldEmpty(messagebox.getText().toString())) {
			UIUtils.showMessage(getActivity(), "message", "Enter message");
			return false;
		}else if (!Util1.isFieldEmpty(emailbox.getText().toString())) {
			UIUtils.showMessage(getActivity(), "message", "Enter email");
			return false;
		}else if(!Pattern.compile(emailformat).matcher(emailbox.getText().toString()).matches())
		{
			UIUtils.showMessage(getActivity(), "Message", "Please enter valid email id");
			return false;
		}
		return true;
	}

	
	private void sendEmailForContact() {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		String[] recipients = new String[]{Constant.emailIdForContact};
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Contact Details");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Dear,"+"\n\nIwant to be in contact with you " 
		+".Please send me proper feedback on my email id."+"\n\nBest Regards \n"+namebox.getText().toString()+"\n"+"Email-"+emailbox.getText().toString());
		emailIntent.setType("text/plain");
		startActivity(Intent.createChooser(emailIntent, "Select Mail Client :"));
	}
	
private void setContent() {
	
	namebox=(EditText)view.findViewById(R.id.nameedittext);
	Util1.textFormatterCapitalizeFirstLetter(namebox);
	emailbox=(EditText)view.findViewById(R.id.emailedittext);
	messagebox=(EditText)view.findViewById(R.id.messageedittext);
	
	sendbtn=(Button)view.findViewById(R.id.sendbtn);
	sendbtn.setOnClickListener(listener);
	
	backbtn=(Button)view.findViewById(R.id.contact_us_back);
	backbtn.setOnClickListener(listener);
}
}
