package com.tny.volvr.base;

import java.util.HashMap;
import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.tnutil.Callback;
import com.example.tnutil.Util;
import com.tny.volvr.club.home.ClubMyProfile;
import com.tny.volvr.dancer.calendar.PaymentCalendar;
import com.tny.volvr.dancer.home.DancerMyProfile;
import com.tny.volvr.dancer.message.InboxActivity;
import com.tny.volvr.fan.home.FanMyProfile;
import com.tny.volvr.more.More_Search;
import com.tny.volvr.more.TabMore;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class MainTabActivity extends FragmentActivity {
    /* Your Tab host */
    private TabHost mTabHost;

    /* A HashMap of stacks, where we use tab identifier as keys..*/
    public HashMap<String, Stack<Fragment>> mStacks;

    /*Save current tabs identifier in this..*/
    public String mCurrentTab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.app_main_tab_fragment_layout);

        /*  
         *  Navigation stacks for each tab gets created.. 
         *  tab identifier is used as key to get respective stack for each tab
         */
        mStacks=new HashMap<String, Stack<Fragment>>();
        mStacks.put(AppConstants.TAB_HOME, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_MESSAGES, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_SUBSCRIPTION, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_CALENDAR, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_MORE, new Stack<Fragment>());

        mTabHost                =   (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();
        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); */
        initializeTabs();
    }


    private View createTabView(final int id) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
        ImageView imageView =   (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));
        return view;
    }

    public void initializeTabs(){
        TabHost.TabSpec spec    =   mTabHost.newTabSpec(AppConstants.TAB_HOME);
        mTabHost.setCurrentTab(-3);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.tab_home_state_btn));
        mTabHost.addTab(spec);


        spec                    =   mTabHost.newTabSpec(AppConstants.TAB_MESSAGES);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.tab_messages_state_btn));
        mTabHost.addTab(spec);
        
        spec                    =   mTabHost.newTabSpec(AppConstants.TAB_SUBSCRIPTION);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.tab_subscription_state_btn));
        mTabHost.addTab(spec);
        
        spec                    =   mTabHost.newTabSpec(AppConstants.TAB_CALENDAR);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.tab_calendar_state_btn));
        mTabHost.addTab(spec);
        spec                    =   mTabHost.newTabSpec(AppConstants.TAB_MORE);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.tab_more_state_btn));
        mTabHost.addTab(spec);
    }


    /*Comes here when user switch tab, or we do programmatically*/
    TabHost.OnTabChangeListener listener    =   new TabHost.OnTabChangeListener() {
      public void onTabChanged(String tabId) {
        /*Set current tab..*/
        mCurrentTab                     =   tabId;

        if(mStacks.get(tabId).size() == 0){
          if(tabId.equals(AppConstants.TAB_HOME)){
        	if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
            pushFragments(tabId, new DancerMyProfile(), false,true);
        	if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
                pushFragments(tabId, new ClubMyProfile(), false,true);
        	if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
                pushFragments(tabId, new FanMyProfile(), false,true);
            
          }else if(tabId.equals(AppConstants.TAB_MESSAGES)){
            pushFragments(tabId, new InboxActivity(), false,true);
          
          }else if(tabId.equals(AppConstants.TAB_SUBSCRIPTION)){
        	  pushFragments(tabId, new More_Search(), false,true);
        	/*  if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
        		  pushFragments(tabId, new DancerSubscription(), false,true);
              	if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
                      pushFragments(tabId, new ClubSubscription(), false,true);
              	if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
                      pushFragments(tabId, new FanSubscription(), false,true);
          */
          }else if(tabId.equals(AppConstants.TAB_CALENDAR)){
            pushFragments(tabId, new PaymentCalendar(0), false,true);
          
          }else if(tabId.equals(AppConstants.TAB_MORE)){
            pushFragments(tabId, new TabMore(), false,true);
         }
        }else {
          pushFragments(tabId, mStacks.get(tabId).lastElement(), false,false);
        }
      }
    };


    /* Might be useful if we want to switch tab programmatically, from inside any of the fragment.*/
    public void setCurrentTab(int val){
          mTabHost.setCurrentTab(val);
    }


    /* 
     *      To add fragment to a tab. 
     *  tag             ->  Tab identifier
     *  fragment        ->  Fragment to show, in tab identified by tag
     *  shouldAnimate   ->  should animate transaction. false when we switch tabs, or adding first fragment to a tab
     *                      true when when we are pushing more fragment into navigation stack. 
     *  shouldAdd       ->  Should add to fragment navigation stack (mStacks.get(tag)). false when we are switching tabs (except for the first time)
     *                      true in all other cases.
     */
    public void pushFragments(String tag, Fragment fragment,boolean shouldAnimate, boolean shouldAdd){
      if(shouldAdd)
          mStacks.get(tag).push(fragment);
      FragmentManager   manager         =   getSupportFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      if(shouldAnimate)
          ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
      ft.replace(R.id.realtabcontent, fragment);
      ft.commit();
    }


    public void popFragments(){
      /*    
       *    Select the second last fragment in current tab's stack.. 
       *    which will be shown after the fragment transaction given below 
       */
      Fragment fragment             =   mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

      /*pop current fragment from stack.. */
      mStacks.get(mCurrentTab).pop();

      /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
      FragmentManager   manager         =   getSupportFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
      ft.replace(R.id.realtabcontent, fragment);
      ft.commit();
    }   


    @Override
    public void onBackPressed() {
       	if(((BaseFragment)mStacks.get(mCurrentTab).lastElement()).onBackPressed() == false){
       		if(mStacks.get(mCurrentTab).size() == 1){
       		//	showNetworkErrorMessage();
       			Util.showCustomDialog(MainTabActivity.this, "Message", "Are you sure you want to exit Voiur?", "Yes", "No", new Callback() {
					
					@Override
					public void ok() {
						// TODO Auto-generated method stub
						System.exit(0);
					}
					
					@Override
					public void cancel() {
						// TODO Auto-generated method stub
						
					}
				});
       		}else{
       			popFragments();
       		}
       	}else{
       	}
    }


    /*
     *   Imagine if you wanted to get an image selected using ImagePicker intent to the fragment. Ofcourse I could have created a public function
     *  in that fragment, and called it from the activity. But couldn't resist myself.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mStacks.get(mCurrentTab).size() == 0){
            return;
        }

        /*Now current fragment on screen gets onActivityResult callback..*/
        mStacks.get(mCurrentTab).lastElement().onActivityResult(requestCode, resultCode, data);
    }
    
/*   private void showNetworkErrorMessage() {
		Builder dlg = new AlertDialog.Builder(MainTabActivity.this);
		dlg.setCancelable(false);
		dlg.setTitle("Message");
		dlg.setMessage("Are you sure you want to exit Voiur?");
		dlg.setPositiveButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dlg.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				System.exit(0);
			}
		});
		dlg.show();
	}*/
   
}
