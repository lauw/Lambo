package com.muller.lambo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class HeroTransition {
	private HeroTransition() {}

	public static void prepareAnimation(View from, View to) {
		// Figure out where the views are located, relative to the screen and each other
		final int[] oldLocation = new int[2];
		from.getLocationOnScreen(oldLocation);

		int[] newLocation = new int[2];
		to.getLocationOnScreen(newLocation);

		int leftDelta = oldLocation[0] - newLocation[0];
		int topDelta = oldLocation[1] - newLocation[1];

		// Scale factors to make the new view the same size as the old view
		float widthScale = (float)from.getWidth() / to.getWidth();
		float heightScale = (float)from.getHeight() / to.getHeight();

		to.setPivotX(0);
		to.setPivotY(0);
		to.setTranslationX(leftDelta);
		to.setTranslationY(topDelta);
		to.setScaleX(widthScale);
		to.setScaleY(heightScale);

		to.setTag(R.id.VIEW_TRANSITION_DATA, new HeroTransitionData(leftDelta, topDelta, widthScale, heightScale));
	}

	public static List<Animator> getEnterAnimation(View view) {
		if (view.getTag(R.id.VIEW_TRANSITION_DATA) == null)
			throw new RuntimeException("View is not prepared for animation. Call prepareAnimation(view, view) first."); //runTimeException does not require catching

		List<Animator> animations = new ArrayList<>();

		animations.add(ObjectAnimator.ofFloat(view, "translationX", 0));
		animations.add(ObjectAnimator.ofFloat(view, "translationY", 0));
		animations.add(ObjectAnimator.ofFloat(view, "scaleX", 1));
		animations.add(ObjectAnimator.ofFloat(view, "scaleY", 1));

		return animations;
	}

	public static List<Animator> getExitAnimation(View view) {
		if (view.getTag(R.id.VIEW_TRANSITION_DATA) == null)
			throw new RuntimeException("View is not prepared for animation. Call prepareAnimation(view, view) first."); //runTimeException does not require catching

		HeroTransitionData oldState = (HeroTransitionData)view.getTag(R.id.VIEW_TRANSITION_DATA);

		List<Animator> animations = new ArrayList<>();

		animations.add(ObjectAnimator.ofFloat(view, "translationX", oldState.getLeftDelta()));
		animations.add(ObjectAnimator.ofFloat(view, "translationY", oldState.getTopDelta()));
		animations.add(ObjectAnimator.ofFloat(view, "scaleX", oldState.getWidthScale()));
		animations.add(ObjectAnimator.ofFloat(view, "scaleY", oldState.getHeightScale()));

		return animations;
	}
}
