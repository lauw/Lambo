package com.muller.lambo;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BackStack {
	public Stack<Screen> stack = new Stack<Screen>();

	/**
	 * Removes the screen you were previously on from the backstack
	 * @return The screen you were previously on
	 */
	public Screen pop() {
		if (stack.isEmpty())
			return null;

		return stack.pop();
	}

	/** Clears the stack */
	public void clear() {
		stack.clear();
	}

	/** Add a screen to the backstack */
	public void add(Screen screen) {
		stack.push(screen);
	}

	public Screen peek() {
		if (stack.isEmpty())
			return null;

		return stack.peek();
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}
}
