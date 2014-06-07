package ar.com.badami.wildgunman.model;

import ar.com.badami.framework.utils.Random;
import ar.com.badami.wildgunman.Assets;

public class Outlaw {

	private static float SPEED_PER_SEC = 1.5f;
	public static float MIN_TIME_STANDING = 0.15f;
	public static float MAX_TIME_STANDING = 1.0f;
	public static float MIN_TIME_UNHOLSTER = 0.15f;
	public static float MAX_TIME_UNHOLSTER = 0.5f;
	public static float MIN_TIME_SHOOT = 0.2f;
	public static float MAX_TIME_SHOOT = 0.5f;
	public static float TIME_LAUGHING = 2.0f;

	public enum Type {
		Billy(1.4f, 0.5f), Bob(1.6f, 0.5f), Sheffer(1.7f, 0.5f), Mark(1.4f, 0.5f), Randy(1.6f, 0.5f);

		private Type(float height, float xRadius) {
			this.height = height;
			this.xRadius = xRadius;
		}

		public float height;
		public float xRadius;
	}

	public enum State {
		walkLeft, walkRight, stand, unholster, aim, aimWin, fallen
	}

	public final Type type;
	public State state;

	public final float xPosToStop;
	public final float timeStanding;
	public final float timeToUnholster;
	public final float timeToShoot;

	public float xPosition;
	public float yPosition;
	public boolean hasSaidFire = false;
	public final boolean startedOnLeft;
	public float acumulatedTimeInState = 0.0f;

	public boolean hasShoot = false;

	public Outlaw(Type type, boolean comesFromLeft, float xPosToStop, float initYPosition, float worldWidth) {
		super();
		this.type = type;
		this.startedOnLeft = comesFromLeft;
		this.state = comesFromLeft ? State.walkRight : State.walkLeft;
		this.xPosToStop = xPosToStop;
		this.yPosition = initYPosition;
		this.xPosition = comesFromLeft ? -worldWidth * 0.2f : worldWidth * 1.2f;
		this.timeStanding = getTimeStanding();
		this.timeToUnholster = getTimeToUnholster();
		this.timeToShoot = getTimeToShoot();
	}

	private float getTimeStanding() {
		return Random.INSTANCE.nextFloat(MIN_TIME_STANDING, MAX_TIME_STANDING);
	}

	private float getTimeToUnholster() {
		return Random.INSTANCE.nextFloat(MIN_TIME_UNHOLSTER, MAX_TIME_UNHOLSTER);
	}

	private float getTimeToShoot() {
		return Random.INSTANCE.nextFloat(MIN_TIME_SHOOT, MAX_TIME_SHOOT);
	}

	public boolean shootReturnIfFault(float xCoord, float yCoord) {
		boolean shooted = ((xCoord < xPosition + type.xRadius && xCoord > xPosition - type.xRadius) && (yCoord < yPosition && yCoord > yPosition
				- type.height));
		if (shooted) {
			if (!hasSaidFire)
				return true;
			Assets.guyFalling.play(1.0f);
			state = State.fallen;
			acumulatedTimeInState = 0.0f;
		}
		return false;
	}

	public void update(float deltaTime) {
		if (state == State.walkLeft) {
			xPosition -= deltaTime * SPEED_PER_SEC;
			if ((!hasSaidFire) && xPosition <= xPosToStop) {
				state = State.stand;
				acumulatedTimeInState = 0.0f;
			}
		} else if (state == State.walkRight) {
			xPosition += deltaTime * SPEED_PER_SEC;
			if ((!hasSaidFire) && xPosition >= xPosToStop) {
				state = State.stand;
				acumulatedTimeInState = 0.0f;
			}
		} else if (state == State.stand) {
			acumulatedTimeInState += deltaTime;
			if (acumulatedTimeInState > this.timeStanding) {
				Assets.oneOutlawIntro.stop();
				Assets.twoOutlawsIntro.stop();
				Assets.fire.play(1.0f);
				hasSaidFire = true;
				state = State.unholster;
				acumulatedTimeInState = 0.0f;
			}
		} else if (state == State.unholster) {
			acumulatedTimeInState += deltaTime;
			if (acumulatedTimeInState > this.timeToUnholster) {
				acumulatedTimeInState = 0.0f;
				state = State.aim;
			}
		} else if (state == State.aim) {
			acumulatedTimeInState += deltaTime;
			if (acumulatedTimeInState > this.timeToShoot) {
				acumulatedTimeInState = 0.0f;
				state = State.aimWin;
				hasShoot = true;
			}
		} else if (state == State.aimWin) {
			acumulatedTimeInState += deltaTime;
			if (acumulatedTimeInState > TIME_LAUGHING) {
				acumulatedTimeInState = 0.0f;
				this.state = startedOnLeft ? State.walkLeft : State.walkRight;
			}
		} else if (state == State.fallen) {
			acumulatedTimeInState += deltaTime;
		}
	}
}
