package springbook.user.domain;

/**
 * Created by Wayne on 2015. 10. 1..
 */
public enum Level {
	GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

	private final int value;
	private final Level next;

	Level(int value, Level next) {
		this.value = value;
		this.next = next;
	}

	public int intValue() {
		return this.value;
	}

	public Level nextLevel() {
		return next;
	}

	public static Level valueOf(int value) {
		switch (value) {
			case 1: return BASIC;
			case 2: return SILVER;
			case 3: return GOLD;
			default: throw new AssertionError("Unknown value : " + value);
		}
	}
}
