package ar.com.badami.framework.utils;

public enum Random {
	INSTANCE;

	private java.util.Random random;

	private Random() {
		random = new java.util.Random();
	}

	public int nextInt(int n) {
		return random.nextInt(n);
	}

	public boolean nextBoolean() {
		return random.nextBoolean();
	}

	public float nextFloat() {
		return random.nextFloat();
	}

	public float nextFloat(float min, float max) {
		return random.nextFloat() * (max - min) + min;
	}

}
