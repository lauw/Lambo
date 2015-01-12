package com.muller.lambo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class Screen {
	private View view;
	private ScreenTransition transition;

	public Screen() {}

	public abstract void created();
	public abstract void entered();

	public final View getView() {
		return view;
	}

	public final View inflateView(ViewGroup container) {
		if (view == null) {
			Layout layout = getClass().getAnnotation(Layout.class);

			if (layout == null) {
				throw new IllegalArgumentException("Layout annotation not set");
			}

			//getContext is still null here
			view = LayoutInflater.from(container.getContext()).inflate(layout.value(), container, false);
		}

		created();

		return view;
	}


	final boolean hasTransition() {
		return transition != null;
	}

	final ScreenTransition getTransition() {
		return transition;
	}

	final void setTransition(ScreenTransition transition) {
		this.transition = transition;
	}

	public final Context getContext() {
		if (view != null)
			return view.getContext();

		return null;
	}
}
