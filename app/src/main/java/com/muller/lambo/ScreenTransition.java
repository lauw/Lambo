package com.muller.lambo;

public interface ScreenTransition {
	void enter(Screen screenFrom, Screen screenTo, Runnable complete);
	void exit(Screen screenFrom, Screen screenTo, Runnable complete);
}
