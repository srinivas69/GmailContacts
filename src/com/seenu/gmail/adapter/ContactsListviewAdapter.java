package com.seenu.gmail.adapter;

import java.util.ArrayList;
import com.seenu.gmail.pojo.ContactsFeed.Feed.Entry;
import com.seenu.gmail.pojo.ContactsFeed.Feed.Entry.Email;
import com.seenu.gmailcontacts.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactsListviewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Entry> entry;
	private boolean mobNumAvail;

	public ContactsListviewAdapter(Context context, ArrayList<Entry> entry,
			boolean mobNumAvail) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entry = entry;
		this.mobNumAvail = mobNumAvail;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entry.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entry.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (view == null) {
			view = inflater.inflate(R.layout.contacts_list_item, null);
			holder = new ViewHolder();

			holder.titleAdpTv = (TextView) view.findViewById(R.id.textView1);
			holder.phnEmailTv = (TextView) view.findViewById(R.id.textView2);
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();

		String name = entry.get(position).getTitle().get$t();
		String mobEmail = "no email available";

		if (mobNumAvail)
			mobEmail = entry.get(position).getGd$phoneNumber().get(0).get$t();
		else {
			ArrayList<Email> email = entry.get(position).getGd$email();
			if (email.size() != 0)
				mobEmail = email.get(0).getAddress();
		}

		holder.titleAdpTv.setText(name);
		holder.phnEmailTv.setText(mobEmail);

		return view;
	}

	private static class ViewHolder {

		private TextView titleAdpTv;
		private TextView phnEmailTv;
	}

}
