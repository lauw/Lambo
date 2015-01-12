package com.muller.lambo;

public interface ScreenTransition {
	//todo: how to make sure objects stored in ScreenTransition (views specifically) do not cause memory leaks and OutOfMemory exceptions?
	void enter(Screen screenFrom, Screen screenTo, Runnable complete);
	void exit(Screen screenFrom, Screen screenTo, Runnable complete);
}
