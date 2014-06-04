package ar.com.badami.wildgunman.model;

import ar.com.badami.framework.utils.Random;
import ar.com.badami.wildgunman.model.Outlaw.State;
import ar.com.badami.wildgunman.model.Outlaw.Type;

public class World {

	public static float WORLD_WIDTH = 10.0f;
	public static float WORLD_HEIGHT = 5.0f;
	public static float Y_POSITION = 4.0f;

	public static float FAULT_TIME_IN_SEC = 4.0f;
	public static float FALLEN_TIME_IN_SEC = 8.0f;
	public static float WIN_TIME_IN_SEC = 4.0f;

	public enum RoundStatus {
		running, playerFault, playerWin, playerFallen, finished;
	}

	public Outlaw[] outlaws;
	public boolean hasTwoOutlaws;
	public boolean firstComesFromLeft;
	public boolean playerHasShot;

	public RoundStatus status;

	private float acumulatedTimeInScene;

	public World() {
		super();
		init();
	}

	public void init() {
		playerHasShot = false;
		acumulatedTimeInScene = 0.0f;
		status = RoundStatus.running;
		createOutlaws();
	}

	private void createOutlaws() {
		hasTwoOutlaws = Random.INSTANCE.nextBoolean();
		firstComesFromLeft = Random.INSTANCE.nextBoolean();
		int outlawCant = hasTwoOutlaws ? 2 : 1;
		outlaws = new Outlaw[outlawCant];
		outlaws[0] = createOneOutlaw(null, firstComesFromLeft);
		if (hasTwoOutlaws)
			outlaws[1] = createOneOutlaw(outlaws[0].type, !firstComesFromLeft);
	}

	private float getXPosToStop(boolean comesFromLeft) {
		float xPosToStop;
		if (!hasTwoOutlaws) {
			// Si es el unico tiene entre 1/4 y 3/4 de la pantalla
			xPosToStop = Random.INSTANCE.nextFloat(WORLD_WIDTH / 4, (WORLD_WIDTH * 3) / 4);
		} else {
			// Si hay dos tiene 1/4 de la pantalla
			if (comesFromLeft) {
				// Si viene de la izquierda tendra el segundo cuarto
				xPosToStop = Random.INSTANCE.nextFloat(WORLD_WIDTH / 4, WORLD_WIDTH / 2);
			} else {
				// Si viene de la derecha tendra el tercer cuarto
				xPosToStop = Random.INSTANCE.nextFloat(WORLD_WIDTH / 2, (WORLD_WIDTH * 3) / 4);
			}
		}
		return xPosToStop;
	}

	private Outlaw createOneOutlaw(Outlaw.Type typeAlreadyUsed, boolean comesFromLeft) {
		Outlaw.Type randomType = decideTypeExceptOne(typeAlreadyUsed);
		float xPosToStop = getXPosToStop(comesFromLeft);
		return new Outlaw(randomType, comesFromLeft, xPosToStop, Y_POSITION, WORLD_WIDTH);
	}

	private Outlaw.Type decideTypeExceptOne(Outlaw.Type typeAlreadyUsed) {
		Outlaw.Type randomType = decideType();
		// Al usar == es null safe
		while (randomType == typeAlreadyUsed) {
			randomType = decideType();
		}
		return randomType;
	}

	private Type decideType() {
		return Outlaw.Type.values()[Random.INSTANCE.nextInt(5)];
	}

	public void update(float deltaTime) {
		switch (status) {
		case running:
			updateRunning(deltaTime);
			break;
		case playerFault:
			updateFault(deltaTime);
			break;
		case playerWin:
			updateWin(deltaTime);
			break;
		case playerFallen:
			updateFallen(deltaTime);
			break;
		default:
			break;
		}
	}

	private void updateRunning(float deltaTime) {
		for (Outlaw outlaw : outlaws) {
			outlaw.update(deltaTime);
			if (outlaw.hasShoot && status == RoundStatus.running)
				status = RoundStatus.playerFallen;
		}
		verifyIfPlayerWin();
	}

	private void updateFault(float deltaTime) {
		finishAfterSomeTime(deltaTime, FAULT_TIME_IN_SEC);
	}

	private void updateWin(float deltaTime) {
		finishAfterSomeTime(deltaTime, WIN_TIME_IN_SEC);
		// Los jugadores se siguen moviendo luego de caer
		for (Outlaw outlaw : outlaws) {
			outlaw.update(deltaTime);
		}
	}

	private void updateFallen(float deltaTime) {
		finishAfterSomeTime(deltaTime, FALLEN_TIME_IN_SEC);
		// Los jugadores se siguen moviendo luego de ganar
		for (Outlaw outlaw : outlaws) {
			outlaw.update(deltaTime);
		}
	}

	private void finishAfterSomeTime(float deltaTime, float timeToWait) {
		acumulatedTimeInScene += deltaTime;
		if (acumulatedTimeInScene > timeToWait) {
			acumulatedTimeInScene = 0.0f;
			status = RoundStatus.finished;
		}
	}

	private void verifyIfPlayerWin() {
		boolean areAllOutlawsFallen = true;
		for (Outlaw outlaw : outlaws) {
			if (outlaw.state != State.fallen)
				areAllOutlawsFallen = false;
		}
		if (areAllOutlawsFallen)
			status = RoundStatus.playerWin;
	}

	public Outlaw[] getOutlaws() {
		return outlaws;
	}

	public void shoot(float xCoord, float yCoord) {
		playerHasShot = true;
		if (status == RoundStatus.running) {
			// Si ninguno estaba preparado hubo falta
			boolean hasSomeoneSaidFire = false;
			for (Outlaw outlaw : outlaws) {
				if (outlaw.hasSaidFire)
					hasSomeoneSaidFire = true;
			}
			if (!hasSomeoneSaidFire) {
				playerFault();
				return;
			}
			// Si alguno disparo hubo falta si alguno no estaba preparado y
			// recibio el disparo
			for (Outlaw outlaw : outlaws) {
				if (outlaw.shootReturnIfFault(xCoord, yCoord))
					playerFault();
			}
		}
	}

	public void playerFault() {
		status = RoundStatus.playerFault;
	}

}
