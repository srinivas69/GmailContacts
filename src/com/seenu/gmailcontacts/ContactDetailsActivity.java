package com.seenu.gmailcontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class ContactDetailsActivity extends ActionBarActivity {

	private final String TAG = getClass().getSimpleName();

	private ImageView logIv;
	private TextView nameTv;
	private TextView mobNumTv;
	private Button shareBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_details);

		logIv = (ImageView) findViewById(R.id.imageView1);
		nameTv = (TextView) findViewById(R.id.textView1);
		mobNumTv = (TextView) findViewById(R.id.textView2);
		shareBt = (Button) findViewById(R.id.button1);

		Bundle b = getIntent().getExtras();

		final String name = b.getString("NAME");
		final String mobNum = b.getString("MOB_NUM");
		String imgUrl = b.getString("IMG_URL");

		nameTv.setText("Name: " + name);
		mobNumTv.setText("Mobile Num: " + mobNum);

		UrlImageViewHelper
				.setUrlDrawable(logIv, imgUrl, R.drawable.ic_launcher);

		Log.i(TAG, name);
		Log.i(TAG, mobNum);
		Log.i(TAG, imgUrl);

		shareBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, name);
				shareIntent.putExtra(Intent.EXTRA_TEXT, mobNum);
				startActivity(Intent.createChooser(shareIntent,
						"Share Via"));
			}
		});

	}
}
