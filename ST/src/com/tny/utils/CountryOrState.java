package com.tny.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.volvr.beans.Country;
import com.volvr.beans.WorkingAt;

public class CountryOrState extends ListActivity {
	Intent i;
	String whichList;
	String countryCode;
	Context context;
	ArrayList<Country> ALcountry;
	ArrayList<WorkingAt> ALclub;
	ArrayList<String> ALgender;
	Handler mHandler = new Handler();
	  public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	    context=this;
	    i=getIntent();
	    whichList=i.getStringExtra("whichList");
	    countryCode=i.getStringExtra("countryCode");
         ListView listView = getListView();
	 
		listView.setTextFilterEnabled(true);
	    if (whichList.equals("COUNTRY")) {
			setCountry();
		}else if (whichList.equals("WORKINGAT")) {
			setClub();
		}
	    else if(whichList.equals("STATE")){
			setState();
		}
	    else
	    {
	    	setGender();
	    }
	    
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			  
			i.putExtra("selectedValue",((TextView) view).getText());
			
		    if(whichList.equals("STATE")){
		    String s=	ALcountry.get(position).getCode();
				i.putExtra("steteCode",ALcountry.get(position).getCode());
				i.putExtra("countryCode",ALcountry.get(position).getCountry_code());
				}
			
            setResult(RESULT_OK, i);
            finish();
			
			}
		});
 
	   /* ListView lv=getListView();
	    for (int i = 0; i < lv.getCount(); i++) {
	    	if (lv.getItemAtPosition(i).equals("")) {
				lv.setSelection(i);
				break;
			}
			
		}*/
	  }
	private void setGender() {
		ALgender=new ArrayList<String>();	
		ALgender.add("Male");
		ALgender.add("Female");
		ArrayAdapter<String> workingitAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, ALgender);
		workingitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 setListAdapter(workingitAdapter);
	}
	private void setClub() {
		if (Extra.casheWorkingAt.size()>0) {
			ArrayAdapter<WorkingAt> workingitAdapter = new ArrayAdapter<WorkingAt>(context,
					android.R.layout.simple_list_item_1, Extra.casheWorkingAt);
			workingitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 setListAdapter(workingitAdapter);
		} else {
			UIUtils.showSimpleSpinProgressDialog(context, "Loading Clubs...");
			Thread thread = new Thread(new Runnable() {
				public void run() {
					final String response;
					try{
						List<NameValuePair> paramList = new ArrayList<NameValuePair>();
						paramList.add(new BasicNameValuePair("action", "getclublist"));
						response=HTTPUtils.executeHttpGet(paramList,Constant.SERVER_URL);
						UIUtils.removeSimpleSpinProgressDialog();
						mHandler.post(new Runnable() {
							public void run() {
								if(response == null || response.equals(""))
								{
									System.out.println("(HomeStripperSignup2)response is null ");
								}else
								{
									try{
										JSONObject obj = new JSONObject(response);
										JSONArray jArray = obj.getJSONArray("clubs");
										ALclub=new ArrayList<WorkingAt>();
										for (int i=0; i < jArray.length(); i++)
										{
											WorkingAt c=new WorkingAt();

											JSONObject oneObject = jArray.getJSONObject(i);
											String id = oneObject.getString("club_id");
											c.setClub_id(id);
											String zipcode = oneObject.getString("club_zip_code");
											c.setClub_zip_code(zipcode);
											String city = oneObject.getString("club_city");
											c.setClub_city(city);
											String contactnumber = oneObject.getString("club_contact_number");
											c.setClub_contact_number(contactnumber);
											String country = oneObject.getString("club_country");
											c.setClub_country(country);
											String address = oneObject.getString("club_address");
											c.setClub_address(address);
											String state = oneObject.getString("club_state");
											c.setClub_state(state);
											String name = oneObject.getString("club_name");
											c.setClub_name(name);



											ALclub.add(c);

										}
										WorkingAt c1=new WorkingAt();
										c1.setClub_name("Other");
										ALclub.add(c1);
										ALclub.toString();

										ArrayAdapter<WorkingAt> workingitAdapter = new ArrayAdapter<WorkingAt>(context,
												android.R.layout.simple_list_item_1, ALclub);
										workingitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										 setListAdapter(workingitAdapter);
										
										//workingat.setAdapter(workingitAdapter);
									}catch(JSONException e)
									{
										UIUtils.removeSimpleSpinProgressDialog();
										ArrayList<String> ALcountry=new ArrayList<String>();
										ALcountry.add("Other");
										ArrayAdapter<String> workingitAdapter = new ArrayAdapter<String>(context,
												android.R.layout.simple_list_item_1, ALcountry);
										workingitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										 setListAdapter(workingitAdapter);
										//workingat.setAdapter(countryAdapter);
									}

								}
							}
						});
					}
					catch(Exception e)
					{
						UIUtils.removeSimpleSpinProgressDialog();
					}

				}

			});
			thread.start();
		}
		
	}
	private void setState() {
		if (countryCode.equals("US")&&Extra.casheStateList.size()>0) {
			  ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(context,
				        android.R.layout.simple_list_item_1, Extra.casheStateList);
				    setListAdapter(adapter);
		} else {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("action", "getstates"));
			paramList.add(new BasicNameValuePair("country_code",countryCode));
			new AsyncWebServiceProcessingTask(context, paramList, "Getting list...", new Callback() {
				
				@Override
				public void run(String response) {
					// TODO Auto-generated method stub
                     try {
						
						if(new JSONObject(response).optString("status").equalsIgnoreCase("error"))
						{
						//	Util1.showMessage(context, "Message",new JSONObject(response).optString("msg"));
						    ALcountry=new ArrayList<Country>();
							Country c=new Country();
							c.setId("");
						    c.setCode("");
							c.setName("No state");
							c.setCountry_code("");
							ALcountry.add(c);
							 ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(context,
								        android.R.layout.simple_list_item_1, ALcountry);
								    setListAdapter(adapter);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

						}
						else
						{
							JSONObject obj = new JSONObject(response);
							JSONArray jArray = obj.getJSONArray("states");
							ALcountry=new ArrayList<Country>();
							for (int i=0; i < jArray.length(); i++)
							{
								Country c=new Country();
								JSONObject oneObject = jArray.getJSONObject(i);
								String id = oneObject.getString("id");
								c.setId(id);
								String code = oneObject.getString("code");
								c.setCode(code);
								String name = oneObject.getString("name");
								c.setName(name);
								String country_code=oneObject.getString("country_code");
								c.setCountry_code(country_code);
								ALcountry.add(c);
							}
							
							  ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(context,
								        android.R.layout.simple_list_item_1, ALcountry);
							  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								    setListAdapter(adapter);
								    Extra.casheStateList=ALcountry;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).execute("");
			
			/*UIUtils.showSimpleSpinProgressDialog(context, "Loading States...");
			Thread thread = new Thread(new Runnable() {
				public void run() {
					final String response;
					try{
						List<NameValuePair> paramList = new ArrayList<NameValuePair>();
						paramList.add(new BasicNameValuePair("action", "getstates"));
						paramList.add(new BasicNameValuePair("country_code",countryCode));
						response=HTTPUtils.executeHttpGet(paramList,Constant.SERVER_URL);

						UIUtils.removeSimpleSpinProgressDialog();
						mHandler.post(new Runnable() {
							public void run() {
								if(response == null || response.equals(""))
								{
									System.out.println("(HomeStripperSignup2)response is null ");
								}elseif()
								{
									try{
										
									}catch(JSONException e)
									{
										UIUtils.removeSimpleSpinProgressDialog();
																				
									}

								}
							}
						});
					}
					catch(Exception e)
					{
						UIUtils.removeSimpleSpinProgressDialog();
					}

				}

			});
			thread.start();*/
		}
	
	}
	private void setCountry() {
		ArrayList<String> countryList=new ArrayList<String>();
//		countryList.add("US");
//		countryList.add("other");
		try
		{
			InputStream in = getAssets().open("countries_json.txt");
			String json = getJsonString(in);
			JSONArray countries = new JSONObject(json).getJSONArray("Countries");
			for (int i = 0; i < countries.length(); i++)
			{
				String name = countries.getJSONObject(i).getString("name");
//				String isd = countries.getJSONObject(i).getString("isd");
				countryList.add(name);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(context ,
			        android.R.layout.simple_list_item_1, countryList);
		  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    setListAdapter(adapter);
	}
	
	private String getJsonString(InputStream in) throws IOException
	{
		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		StringBuffer sb = new StringBuffer("");
		String line = "";
		String NL = System.getProperty("line.separator");
		while ((line = bin.readLine()) != null)
		{
			sb.append(line + NL);
		}
		in.close();
		return sb.toString();
	}
	
}