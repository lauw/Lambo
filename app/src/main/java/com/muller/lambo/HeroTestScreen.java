package com.muller.lambo;

import android.view.View;

import java.util.HashMap;

@Layout(R.layout.test_material)
public class HeroTestScreen extends Screen {

	@Override
	public void onCreate() {
		getView().findViewById(R.id.material_firstTry).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Lambo.get().show(new TestScreen(), new SlideScreenTransition());
			}
		});

		getView().findViewById(R.id.material_secondTry).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Lambo.get().show(new TestScreen(), new ScaleFromViewTransition(v.getId()));
			}
		});

		getView().findViewById(R.id.material_thirdTry).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<Integer, Integer> views = new HashMap<>();

				views.put(R.id.material_firstTry, R.id.firstTry);
				views.put(R.id.material_secondTry, R.id.secondTry);
				views.put(R.id.material_thirdTry, R.id.thirdTry);
				Lambo.get().show(new TestScreen(), new HeroScreenTransition(views));
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
