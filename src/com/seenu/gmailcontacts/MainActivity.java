package com.seenu.gmailcontacts;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

public class MainActivity extends ActionBarActivity {

	private String[] avail_accounts;
	private AccountManager mAccountManager;
	private ListView list;
	private ArrayAdapter<String> adapter;
	private SharedPreferences pref;
	private Button select, api_call;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		select = (Button) findViewById(R.id.select_button);
		api_call = (Button) findViewById(R.id.apicall_button);

		avail_accounts = getAccountNames();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, avail_accounts);
		pref = getSharedPreferences("AppPref", MODE_PRIVATE);

		select.setOnClickListener(new View.OnClickListener() {
			Dialog accountDialog;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (avail_accounts.length != 0) {
					accountDialog = new Dialog(MainActivity.this);
					accountDialog.setContentView(R.layout.accounts_dialog);
					accountDialog.setTitle("Select Google Account");
					list = (ListView) accountDialog.findViewById(R.id.list);
					list.setAdapter(adapter);
					list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							SharedPreferences.Editor edit = pref.edit();
							// Storing Data using SharedPreferences
							edit.putString("Email", avail_accounts[position]);
							edit.commit();
							new Authenticate().execute();
							accountDialog.cancel();
						}
					});
					accountDialog.show();
				} else {
					Toast.makeText(getApplicationContext(),
							"No accounts found, Add a Account and Continue.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String[] getAccountNames() {
		// TODO Auto-generated method stub
		mAccountManager = AccountManager.get(this);
		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		return names;
	}

	private class Authenticate extends AsyncTask<String, String, String> {
		ProgressDialog pDialog;
		String mEmail;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Authenticating....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			mEmail = pref.getString("Email", "");
			pDialog.show();
		}

		@Override
		protected void onPostExecute(String token) {
			pDialog.dismiss();
			if (token != null) {
				SharedPreferences.Editor edit = pref.edit();
				// Storing Access Token using SharedPreferences
				edit.putString("Access Token", token);
				edit.commit();
				Log.i("Token", "Access Token retrieved:" + token);
				Toast.makeText(getApplicationContext(),
						"Access Token is " + token, Toast.LENGTH_SHORT).show();
				select.setText(pref.getString("Email", "")
						+ " is Authenticated");
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String token = null;
			try {
				token = GoogleAuthUtil.getToken(MainActivity.this, mEmail,
						"oauth2:https://www.googleapis.com/auth/urlshortener");
			} catch (IOException transientEx) {
				// Network or server error, try later
				Log.e("IOException", transientEx.toString());
			} catch (UserRecoverableAuthException e) {
				// Recover (with e.getIntent())
				startActivityForResult(e.getIntent(), 1001);
				Log.e("AuthException", e.toString());
			} catch (GoogleAuthException authEx) {
				// The call is not ever expected to succeed
				// assuming you have already verified that
				// Google Play services is installed.
				Log.e("GoogleAuthException", authEx.toString());
			}
			return token;
		}

	}

}
