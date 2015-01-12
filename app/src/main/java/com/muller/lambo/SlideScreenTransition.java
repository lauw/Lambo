package com.muller.lambo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

public class SlideScreenTransition implements ScreenTransition {
	int duration = 500;

	@Override
	public void enter(Screen screenFrom, Screen screenTo, final Runnable complete) {
		int width = screenFrom.getView().getWidth();

		screenTo.getView().setTranslationX(-width);

		AnimatorSet animatorSet = new AnimatorSet();

		animatorSet.playTogether(ObjectAnimator.ofFloat(screenFrom.getView(), "translationX", width), ObjectAnimator.ofFloat(screenTo.getView(), "translationX", 0));

		animatorSet.addListener(new AnimatorListener() {
			@Override
			public void onAnimatorEnd() {
				complete.run();
			}
		});
		animatorSet.setDuration(duration);
		animatorSet.start();
	}

	@Override
	public void exit(Screen screenFrom, Screen screenTo, final Runnable complete) {
		int width = screenFrom.getView().getWidth();

		screenTo.getView().setTranslationX(width);

		AnimatorSet animatorSet = new AnimatorSet();

		animatorSet.playTogether(ObjectAnimator.ofFloat(screenFrom.getView(), "translationX", -width), ObjectAnimator.ofFloat(screenTo.getView(), "translationX", 0));

		animatorSet.addListener(new AnimatorListener() {
			@Override
			public void onAnimatorEnd() {
				complete.run();
			}
		});
		animatorSet.setDuration(duration);
		animatorSet.start();
	}
}
