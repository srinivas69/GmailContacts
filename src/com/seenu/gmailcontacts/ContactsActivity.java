package com.seenu.gmailcontacts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.seenu.gmail.adapter.ContactsListviewAdapter;
import com.seenu.gmail.pojo.ContactsFeed;
import com.seenu.gmail.pojo.ContactsFeed.Feed.Entry;
import com.seenu.gmail.pojo.ContactsFeed.Feed.Entry.PhoneNumber;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsActivity extends ActionBarActivity {
	private String token;
	private String email;

	private String url = "https://www.google.com/m8/feeds/contacts/";
	private String gps_url = "https://www.google.com/m8/feeds/groups/";

	private ContactsFeed obj;
	private final String TAG = getClass().getSimpleName();

	private ContactsListviewAdapter adapter;

	private ListView lv;
	private TextView emptyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_activity);

		lv = (ListView) findViewById(R.id.listView1);
		emptyView = (TextView) findViewById(R.id.textView1);

		Bundle b = getIntent().getExtras();
		token = b.getString("TOKEN");
		email = b.getString("EMAIL");

		url = url + email + "/full?alt=json&max-results=9999&access_token="
				+ token;
		gps_url = gps_url + email + "/full/?alt=json&access_token=" + token;

		new FetchContacts().execute(url);

	}

	private class FetchContacts extends AsyncTask<String, Void, ContactsFeed> {

		private ProgressDialog pDialog;
		private String Token;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			pDialog = new ProgressDialog(ContactsActivity.this);
			pDialog.setMessage("Contacting Google Servers ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected ContactsFeed doInBackground(String... params) {
			// TODO Auto-generated method stub

			String result = null;

			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(params[0]);
			Log.d("URL", params[0]);

			try {
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity httpEntity = response.getEntity();
				InputStream is = httpEntity.getContent();
				result = convertStreamToString(is);
				Log.d("result", result.toString());
				is.close();

				Gson gson = new Gson();
				obj = gson.fromJson(result, ContactsFeed.class);

				Log.i(TAG, String.valueOf(obj.getFeed().getEntry().size()));
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj;
		}

		@Override
		protected void onPostExecute(ContactsFeed result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();

			ArrayList<Entry> entryList = result.getFeed().getEntry();
			ArrayList<Entry> mobNums = new ArrayList<Entry>();

			// adding objects that have mobile number to mobNums ArrayList
			for (Entry entry2 : entryList) {
				ArrayList<PhoneNumber> phoneNumber = entry2.getGd$phoneNumber();
				if (phoneNumber.size() != 0) {
					mobNums.add(entry2);
				} else
					continue;
			}

			// sorting the mobNums ArrayList alphabetically
			Collections.sort(mobNums, new Comparator<Entry>() {

				@Override
				public int compare(Entry lhs, Entry rhs) {
					// TODO Auto-generated method stub
					return lhs.getTitle().get$t()
							.compareTo(rhs.getTitle().get$t());
				}

			});

			adapter = new ContactsListviewAdapter(ContactsActivity.this,
					mobNums);
			lv.setAdapter(adapter);
			lv.setEmptyView(emptyView);
		}
	}

	public String convertStreamToString(InputStream is) {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
