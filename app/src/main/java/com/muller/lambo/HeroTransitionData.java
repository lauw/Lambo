package com.muller.lambo;

public class HeroTransitionData {
	private int leftDelta;
	private int topDelta;
	private float widthScale;
	private float heightScale;

	public HeroTransitionData(int leftDelta, int topDelta, float widthScale, float heightScale) {
		this.leftDelta = leftDelta;
		this.topDelta = topDelta;
		this.widthScale = widthScale;
		this.heightScale = heightScale;
	}

	public int getLeftDelta() {
		return leftDelta;
	}

	public int getTopDelta() {
		return topDelta;
	}

	public float getWidthScale() {
		return widthScale;
	}

	public float getHeightScale() {
		return heightScale;
	}
}
