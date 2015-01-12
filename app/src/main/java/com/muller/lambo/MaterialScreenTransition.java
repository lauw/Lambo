package com.muller.lambo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class MaterialScreenTransition implements ScreenTransition {
	private Map<Integer, Integer> transitioningViews;
	private List<ViewTransition> transitions;
	private final int duration = 200;

	/**
	 * @param transitioningViews first integer represents the id of the view in the old screen (ex: R.id.screen1_image)
	 *                           second integer represents the id of the view in the new screen (ex: R.id.screen2_image)
	 */
	public MaterialScreenTransition(HashMap<Integer, Integer> transitioningViews) {
		this.transitioningViews = transitioningViews;
		transitions = new ArrayList<>();
	}

	@Override
	public void enter(Screen screenFrom, Screen screenTo, final Runnable complete) {
		createTransitions(screenFrom, screenTo);

		List<Animator> viewAnimations = new ArrayList<>();

		for (ViewTransition transition : transitions) {
			viewAnimations.addAll(transition.getEnterAnimation());
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

		for (ViewTransition transition : transitions) {
			viewAnimations.addAll(transition.getExitAnimation());
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

	private void createTransitions(Screen screenFrom, Screen screenTo) {
		if (!transitions.isEmpty())
			return;

		for (Map.Entry<Integer, Integer> entry : transitioningViews.entrySet()) {
			View from = screenFrom.getView().findViewById(entry.getKey());
			View to = screenTo.getView().findViewById(entry.getValue());
			transitions.add(new ViewTransition(from, to));
		}
	}
}
