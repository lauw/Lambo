package com.muller.lambo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;

public class ScaleFromViewTransition implements ScreenTransition {
	private int duration = 160;

	private int leftDelta;
	private int topDelta;
	private float widthScale;
	private float heightScale;
	private View view;

	public ScaleFromViewTransition(View v) {
		this.view = v;
	}

	@Override
	public void enter(Screen screenFrom, final Screen screenTo, final Runnable complete) {
		View thumbnail = view;

		final int[] thumbnailLocation = new int[2];
		thumbnail.getLocationOnScreen(thumbnailLocation);

		// Figure out where the thumbnail and full size versions are, relative
		// to the screen and each other
		int[] screenLocation = new int[2];
		screenTo.getView().getLocationOnScreen(screenLocation);

		leftDelta = thumbnailLocation[0] - screenLocation[0];
		topDelta = thumbnailLocation[1] - screenLocation[1];

		// Scale factors to make the large version the same size as the thumbnail
		widthScale = (float)thumbnail.getWidth() / screenTo.getView().getWidth();
		heightScale = (float)thumbnail.getHeight() / screenTo.getView().getHeight();

		screenTo.getView().setPivotX(0);
		screenTo.getView().setPivotY(0);
		screenTo.getView().setTranslationX(leftDelta);
		screenTo.getView().setTranslationY(topDelta);
		screenTo.getView().setScaleX(widthScale);
		screenTo.getView().setScaleY(heightScale);

		view = null;
		thumbnail = null;

		screenTo.getView().animate().translationX(0).translationY(0).scaleX(1).scaleY(1).setDuration(duration).setInterpolator(new DecelerateInterpolator(1.5f)).withEndAction(complete);
	}

	@Override
	public void exit(Screen screenFrom, Screen screenTo, Runnable complete) {
		screenFrom.getView().animate().setDuration(duration)
				.scaleX(widthScale).scaleY(heightScale)
				.translationX(leftDelta).translationY(topDelta)
				.withEndAction(complete);
	}
}
