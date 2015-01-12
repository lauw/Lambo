package com.muller.lambo;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.util.Stack;

import timber.log.Timber;

public class Lambo extends FrameLayout {
	private Stack<Screen> stack = new Stack<>();
	private static Lambo instance;

	public static Lambo get() {
		return instance;
	}

	public Lambo(Context context, AttributeSet attrs) {
		super(context, attrs);

		instance = this;
	}

	public void show(final Screen screen, ScreenTransition transition) {
		screen.setTransition(transition);
		show(screen, false);
	}

	private void show(final Screen screen, boolean back) {
		show(stack.isEmpty() ? null : stack.peek(), screen, back);
	}

	private void show(final Screen from, final Screen to, final boolean back) {
		ScreenTransition tempTransition = to.getTransition();

		//if back, then use transition from previous screen, else from new screen
		if (from != null && back) {
			tempTransition = from.getTransition();
		}

		final ScreenTransition transition = tempTransition;

		if (to.getView() == null) {
			Timber.i("inflating View " + to.getClass().getSimpleName());
			to.inflateView(this);
		}

		final int newViewIndex = back ? getChildCount() - 1 : getChildCount();
		Timber.i("adding View");
		addView(to.getView(), newViewIndex);
		Timber.i("view Added");

		Timber.i("containerChildCount", "" + getChildCount());

		//if we have transition, execute it, else show screen right away
		if (transition != null) {
			if (from != null)
				Timber.i("showing from: " + from.getClass().getSimpleName() + ", to: " + to.getClass().getSimpleName());

			to.getView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
				@Override
				public boolean onPreDraw() {
					to.getView().getViewTreeObserver().removeOnPreDrawListener(this);
					Timber.i("playing view animation");

					Runnable listener = new Runnable() {
						@Override
						public void run() {
							removeView(from.getView());
							Timber.i("animation done " + to.getClass().getSimpleName());
							to.entered();
						}
					};

					if (back) {
						transition.exit(from, to, listener);
					} else {
						transition.enter(from, to, listener);
					}

					return true;
				}
			});
		} else {
			if (from != null)
				removeView(from.getView());
		}

		if (back)
			stack.pop();
		else
			stack.push(to);
	}

	public boolean back() {
		if (stack.isEmpty())
			return false;

		if (stack.size() < 2)
			return false;

		Timber.i("back > showing: " + stack.get(stack.size() - 2).getClass().getSimpleName());
		show(stack.get(stack.size() - 2), true);

		return true;
	}
}
