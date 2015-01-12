package com.muller.lambo;


import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.util.Stack;

import timber.log.Timber;

public class Lambo extends FrameLayout {
	private Stack<Screen> stack = new Stack<>();
	private static Lambo instance;
	private boolean _animating;

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
		if (_animating)
			return;

		//if back, then use transition from previous screen, else from new screen
		final ScreenTransition transition = (from != null && back) ? from.getTransition() : to.getTransition();

		to.inflateView(this);

		final int newViewIndex = back ? getChildCount() - 1 : getChildCount();
		addView(to.getView(), newViewIndex);

		final Runnable onScreenChangedListener = new Runnable() {
			@Override
			public void run() {
				//remove the old view
				if (from != null) {
					from.onHide();
					removeView(from.getView());
				}

				//new view is shown
				to.onShow();
				_animating = false;
			}
		};

		//if we have transition, execute it, else show screen right away
		if (transition != null) {
			_animating = true;

			to.getView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
				@Override
				public boolean onPreDraw() {
					to.getView().getViewTreeObserver().removeOnPreDrawListener(this);

					if (back) {
						transition.exit(from, to, onScreenChangedListener);
					} else {
						transition.enter(from, to, onScreenChangedListener);
					}

					return true;
				}
			});
		} else { //call runnable without transition
			onScreenChangedListener.run();
		}

		if (back)
			stack.pop().onDestroy(); //todo: check if this is correct? call onDestroy when removing from stack? (perhaps after animation?)
		else
			stack.push(to);

		//todo: memory allows at least 200 TestScreens (no data of their own) to stay in stack without throwing away View's softReferences, should there be a limit to the stack?
		Timber.i("screenStackCount: " + stack.size());
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
