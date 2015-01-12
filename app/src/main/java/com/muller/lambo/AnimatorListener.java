package com.muller.lambo;

import android.animation.Animator;

public abstract class AnimatorListener implements Animator.AnimatorListener {
	@Override
	public final void onAnimationStart(Animator animation) {

	}

	@Override
	public final void onAnimationEnd(Animator animation) {
		onAnimatorEnd();
	}

	@Override
	public final void onAnimationCancel(Animator animation) {

	}

	@Override
	public final void onAnimationRepeat(Animator animation) {

	}

	public abstract void onAnimatorEnd();
}
