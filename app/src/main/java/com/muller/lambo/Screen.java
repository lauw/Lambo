package com.muller.lambo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public abstract class Screen {
	/** Using a SoftReference instead of a weak one
	 * 	Weak references are collected eagerly (asap)
	 * 	Soft references are collected only when the available memory is experiencing pressure
	 * 	It is guaranteed that all SoftReferences will get cleared before OutOfMemoryError is thrown, so they theoretically can't cause an OOME
	 *  source: http://stackoverflow.com/questions/299659/what-is-the-difference-between-a-soft-reference-and-a-weak-reference-in-java
	 * **/
	private SoftReference<View> view = new SoftReference<>(null);
	private ScreenTransition transition;

	public Screen() {}

	public abstract void onCreate();
	public abstract void onDestroy();
	public abstract void onShow();
	public abstract void onHide();

	public final View getView() {
		return view.get();
	}

	public final View inflateView(ViewGroup container) {
		if (view.get() == null) {
			Layout layout = getClass().getAnnotation(Layout.class);

			if (layout == null) {
				throw new RuntimeException("Layout annotation not set.");
			}

			//getContext is still null here
			view = new SoftReference<>(LayoutInflater.from(container.getContext()).inflate(layout.value(), container, false));

			onCreate();
		}

		return view.get();
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
		if (view.get() != null)
			return view.get().getContext();

		return null;
	}
}
