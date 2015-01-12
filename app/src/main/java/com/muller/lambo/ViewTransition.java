package com.muller.lambo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class ViewTransition {
	private int leftDelta;
	private int topDelta;
	private float widthScale;
	private float heightScale;

	private View to;

	public ViewTransition(View from, View to) {
		this.to = to;
		// Figure out where the thumbnail and full size versions are, relative
		// to the screen and each other
		final int[] oldLocation = new int[2];
		from.getLocationOnScreen(oldLocation);

		int[] newLocation = new int[2];
		to.getLocationOnScreen(newLocation);

		leftDelta = oldLocation[0] - newLocation[0];
		topDelta = oldLocation[1] - newLocation[1];

		// Scale factors to make the large version the same size as the thumbnail
		widthScale = (float)from.getWidth() / to.getWidth();
		heightScale = (float)from.getHeight() / to.getHeight();

		to.setPivotX(0);
		to.setPivotY(0);
		to.setTranslationX(leftDelta);
		to.setTranslationY(topDelta);
		to.setScaleX(widthScale);
		to.setScaleY(heightScale);
	}

	public List<Animator> getEnterAnimation() {
		List<Animator> animations = new ArrayList<>();

		animations.add(ObjectAnimator.ofFloat(to, "translationX", 0));
		animations.add(ObjectAnimator.ofFloat(to, "translationY", 0));
		animations.add(ObjectAnimator.ofFloat(to, "scaleX", 1));
		animations.add(ObjectAnimator.ofFloat(to, "scaleY", 1));

		return animations;
	}

	public List<Animator> getExitAnimation() {
		List<Animator> animations = new ArrayList<>();

		animations.add(ObjectAnimator.ofFloat(to, "translationX", leftDelta));
		animations.add(ObjectAnimator.ofFloat(to, "translationY", topDelta));
		animations.add(ObjectAnimator.ofFloat(to, "scaleX", widthScale));
		animations.add(ObjectAnimator.ofFloat(to, "scaleY", heightScale));

		return animations;
	}
}
