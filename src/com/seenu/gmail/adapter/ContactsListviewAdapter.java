package com.seenu.gmail.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.seenu.gmail.pojo.ContactsFeed.Feed.Entry;
import com.seenu.gmail.pojo.ContactsFeed.Feed.Entry.PhoneNumber;
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
	private ArrayList<Entry> mobNums;

	public ContactsListviewAdapter(Context context, ArrayList<Entry> entry) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entry = entry;

		mobNums = new ArrayList<Entry>();

		for (Entry entry2 : entry) {
			ArrayList<PhoneNumber> phoneNumber = entry2.getGd$phoneNumber();
			if (phoneNumber.size() != 0) {
				mobNums.add(entry2);
			} else
				continue;
		}

		Collections.sort(mobNums, new Comparator<Entry>() {

			@Override
			public int compare(Entry lhs, Entry rhs) {
				// TODO Auto-generated method stub
				return lhs.getTitle().get$t().compareTo(rhs.getTitle().get$t());
			}

		});
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mobNums.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mobNums.get(position);
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

		String name = mobNums.get(position).getTitle().get$t();
		String mobNum = mobNums.get(position).getGd$phoneNumber().get(0)
				.get$t();

		holder.titleAdpTv.setText(name);
		holder.phnEmailTv.setText(mobNum);

		/*
		 * ArrayList<PhoneNumber> phoneNumber = entry.get(position)
		 * .getGd$phoneNumber();
		 * 
		 * if (phoneNumber.size() != 0) { String mobNum =
		 * phoneNumber.get(0).get$t(); holder.titleAdpTv.setText(name);
		 * holder.phnEmailTv.setText(mobNum); //
		 * System.out.println(phoneNumber.size()); }
		 */

		return view;
	}

	private static class ViewHolder {

		private TextView titleAdpTv;
		private TextView phnEmailTv;
	}

}
