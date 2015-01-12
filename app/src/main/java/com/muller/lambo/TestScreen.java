package com.muller.lambo;

import android.view.View;

import java.util.HashMap;

@Layout(R.layout.test)
public class TestScreen extends Screen {

	@Override
	public void onCreate() {
		getView().findViewById(R.id.firstTry).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Lambo.get().show(new TestScreen(), new SlideScreenTransition());
			}
		});

		getView().findViewById(R.id.secondTry).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Lambo.get().show(new TestScreen(), new ScaleFromViewTransition(v.getId()));
			}
		});

		getView().findViewById(R.id.thirdTry).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<Integer, Integer> views = new HashMap<>();

				views.put(R.id.firstTry, R.id.material_firstTry);
				views.put(R.id.secondTry, R.id.material_secondTry);
				views.put(R.id.thirdTry, R.id.material_thirdTry);

				Lambo.get().show(new HeroTestScreen(), new HeroScreenTransition(views));
			}
		});
	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onShow() {

	}

	@Override
	public void onHide() {

	}
}
