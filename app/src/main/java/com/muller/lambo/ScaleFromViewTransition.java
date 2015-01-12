package com.muller.lambo;

import android.animation.AnimatorSet;

public class ScaleFromViewTransition implements ScreenTransition {
	private int duration = 160;

	private int viewId;

	//todo: make sure you don't leak the view here (which does not happen here, because view is set to null after it is used, how to fix generic?)
	//todo: fixed by replacing View variable, by viewId (int), how to avoid this from happening to anyone creating a transition?

	public ScaleFromViewTransition(int viewId) {
		this.viewId = viewId;
	}

	@Override
	public void enter(Screen screenFrom, final Screen screenTo, final Runnable complete) {
		HeroTransition.prepareAnimation(screenFrom.getView().findViewById(viewId), screenTo.getView());

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(HeroTransition.getEnterAnimation(screenTo.getView()));
		animatorSet.setDuration(duration);
		animatorSet.addListener(new AnimatorListener() {
			@Override
			public void onAnimatorEnd() {
				complete.run();
			}
		});
		animatorSet.start();
	}

	@Override
	public void exit(Screen screenFrom, Screen screenTo, final Runnable complete) {
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(HeroTransition.getExitAnimation(screenFrom.getView()));
		animatorSet.setDuration(duration);
		animatorSet.addListener(new AnimatorListener() {
			@Override
			public void onAnimatorEnd() {
				complete.run();
			}
		});
		animatorSet.start();
	}
}
