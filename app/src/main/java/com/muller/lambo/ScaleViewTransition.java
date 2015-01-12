package com.muller.lambo;

public class ScaleViewTransition implements ScreenTransition {
	int duration = 200;

	@Override
	public void enter(final Screen screenFrom, Screen screenTo, final Runnable complete) {
		screenTo.getView().setScaleX(0);
		screenTo.getView().setScaleY(0);

		screenTo.getView().animate().scaleX(1).scaleY(1).setDuration(duration).withEndAction(complete);
	}

	@Override
	public void exit(final Screen screenFrom, final Screen screenTo, final Runnable complete) {
		screenFrom.getView().animate().scaleX(0).scaleY(0).setDuration(duration).withEndAction(complete);
	}
}
