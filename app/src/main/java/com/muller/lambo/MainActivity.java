package com.muller.lambo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import timber.log.Timber;


public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Timber.plant(new Timber.DebugTree()); //todo: remove Timber throughout

		setContentView(R.layout.activity_main);
		Lambo.get().show(new TestScreen(), null);
		initView();
	}

	@Override
	public void onBackPressed() {
		if (!Lambo.get().back())
			super.onBackPressed();
	}

	private void initView() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//
//		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			if (test)
//				lambo.show(new Test2Screen(), new SlideScreenTransition());
//			else {
//				lambo.show(new Test3Screen(), new ScaleViewTransition());
//				initView();
//			}
//
//			test = !test;
//			return true;
//		}
//
//		return super.onOptionsItemSelected(item);
//	}
}
