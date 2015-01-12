package com.muller.lambo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;

import java.util.Map;

public class ScaleFromViewTransition implements ScreenTransition {
	private int duration = 160;

	private int leftDelta;
	private int topDelta;
	private float widthScale;
	private float heightScale;
	private int viewId;

	//todo: make sure you don't leak the view here (which does not happen here, because view is set to null after it is used, how to fix generic?)
	//todo: fixed by replacing View variable, by viewId (int), how to avoid this from happening to anyone creating a transition?

	public ScaleFromViewTransition(int viewId) {
		this.viewId = viewId;
	}

	@Override
	public void enter(Screen screenFrom, final Screen screenTo, final Runnable complete) {
		ViewTransition.prepareAnimation(screenFrom.getView().findViewById(viewId), screenTo.getView());

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(ViewTransition.getEnterAnimation(screenTo.getView()));
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
		animatorSet.playTogether(ViewTransition.getExitAnimation(screenTo.getView()));
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
