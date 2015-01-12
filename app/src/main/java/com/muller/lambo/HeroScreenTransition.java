package com.muller.lambo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroScreenTransition implements ScreenTransition {
	private Map<Integer, Integer> transitioningViews;
	private final int duration = 200;

	/**
	 * @param transitioningViews first integer represents the id of the view in the old screen (ex: R.id.screen1_image)
	 *                           second integer represents the id of the view in the new screen (ex: R.id.screen2_image)
	 */
	public HeroScreenTransition(HashMap<Integer, Integer> transitioningViews) {
		this.transitioningViews = transitioningViews;
	}

	//todo: make this nicer

	@Override
	public void enter(Screen screenFrom, Screen screenTo, final Runnable complete) {
		List<Animator> viewAnimations = new ArrayList<>();

		//fetch enter animations and set initial values (views from the new screen get set to the positions equal to the old one, the animate back to their original positions)
		for (Map.Entry<Integer, Integer> entry : transitioningViews.entrySet()) {
			View from = screenFrom.getView().findViewById(entry.getKey());
			View to = screenTo.getView().findViewById(entry.getValue());

			HeroTransition.prepareAnimation(from, to); //prepares the views for animation (sets to initial position)
			viewAnimations.addAll(HeroTransition.getEnterAnimation(to));
		}

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(viewAnimations);
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
		List<Animator> viewAnimations = new ArrayList<>();

		//fetch the exit animations (views from the old screen (screenFrom) animate back to the old position before getting replaced)
		for (Integer viewId : transitioningViews.values()) {
			View view = screenFrom.getView().findViewById(viewId);
			viewAnimations.addAll(HeroTransition.getExitAnimation(view));
		}

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(viewAnimations);
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
