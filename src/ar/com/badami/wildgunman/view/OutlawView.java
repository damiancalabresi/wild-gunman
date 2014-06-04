package ar.com.badami.wildgunman.view;

import ar.com.badami.framework.Graphics;
import ar.com.badami.framework.Pixmap;
import ar.com.badami.wildgunman.Assets;
import ar.com.badami.wildgunman.model.Outlaw;
import ar.com.badami.wildgunman.model.World;

public class OutlawView {

	private static float TIME_ALTERNATE_LAUGH = 0.5f;
	private static float DISTANCE_ALTERNATE_WALK = 0.3f;
	private static float TIME_FALLING = 2.0f;

	private final Outlaw outlaw;

	private Pixmap outlawPixmap;
	private Pixmap hatPixmap;
	private float pixmapSlice;
	private float hatPixmapSlice;

	public OutlawView(Outlaw outlaw) {
		super();
		this.outlaw = outlaw;
	}

	public void present(Graphics g) {
		pixmapSlice = 0.0f;
		hatPixmapSlice = 0.0f;
		hatPixmap = null;

		switch (outlaw.type) {
		case Billy:
			decidePixMap(Assets.outlawBillyWalkA, Assets.outlawBillyWalkB, Assets.outlawBillyStand, Assets.outlawBillyUnholster,
					Assets.outlawBillyAim, Assets.outlawBillyAimNotShine, Assets.outlawBillyAimWin, Assets.outlawBillyFall,
					Assets.outlawBillyHat, 1.0f);
			break;
		case Bob:
			decidePixMap(Assets.outlawBobWalkA, Assets.outlawBobWalkB, Assets.outlawBobStand, Assets.outlawBobUnholster,
					Assets.outlawBobAim, Assets.outlawBobAimNotShine, Assets.outlawBobAimWin, Assets.outlawBobFall, null, 0.0f);
			break;
		case Sheffer:
			decidePixMap(Assets.outlawShefferWalkA, Assets.outlawShefferWalkB, Assets.outlawShefferStand, Assets.outlawShefferUnholster,
					Assets.outlawShefferAim, Assets.outlawShefferAimNotShine, Assets.outlawShefferAimWin, Assets.outlawShefferFall,
					Assets.outlawShefferHat, 0.0f);
			break;
		case Mark:
			decidePixMap(Assets.outlawMarkWalkA, Assets.outlawMarkWalkB, Assets.outlawMarkStand, Assets.outlawMarkUnholster,
					Assets.outlawMarkAim, Assets.outlawMarkAimNotShine, Assets.outlawMarkAimWin, Assets.outlawMarkFall,
					Assets.outlawMarkHat, 1.0f);
			break;
		case Randy:
			decidePixMap(Assets.outlawRandyWalkA, Assets.outlawRandyWalkB, Assets.outlawRandyStand, Assets.outlawRandyUnholster,
					Assets.outlawRandyAim, Assets.outlawRandyAimNotShine, Assets.outlawRandyAimWin, Assets.outlawRandyFall,
					Assets.outlawRandyHat, 1.0f);
			break;
		default:
			break;
		}

		float sliceFixedYPosition = outlaw.yPosition - this.pixmapSlice;
		int xPosInPixels = (int) ((outlaw.xPosition / World.WORLD_WIDTH) * g.getWidth());
		int yPosInPixels = (int) ((sliceFixedYPosition / World.WORLD_HEIGHT) * g.getHeight());
		int topLeftY = yPosInPixels - outlawPixmap.getHeight();
		int topLeftX = xPosInPixels - (outlawPixmap.getWidth() / 2);
		g.drawPixmap(outlawPixmap, topLeftX, topLeftY);

		if (hatPixmap != null) {
			sliceFixedYPosition = outlaw.yPosition - this.hatPixmapSlice;
			yPosInPixels = (int) ((sliceFixedYPosition / World.WORLD_HEIGHT) * g.getHeight());
			topLeftY = yPosInPixels - outlawPixmap.getHeight();
			g.drawPixmap(hatPixmap, topLeftX + outlawPixmap.getWidth() / 2 - hatPixmap.getWidth() / 2, topLeftY - hatPixmap.getHeight());
		}

		if (outlaw.hasSaidFire && (!outlaw.hasShoot)) {
			if (outlaw.startedOnLeft) {
				Pixmap fireLeft = Assets.fireLeft;
				g.drawPixmap(fireLeft, topLeftX - fireLeft.getWidth(), topLeftY - fireLeft.getHeight());
			} else {
				Pixmap fireRight = Assets.fireRight;
				g.drawPixmap(fireRight, topLeftX + outlawPixmap.getWidth(), topLeftY - fireRight.getHeight());
			}
		}
	}

	private void decidePixMap(Pixmap walkA, Pixmap walkB, Pixmap stand, Pixmap unholster, Pixmap aim, Pixmap aimNotShine, Pixmap aimWin,
			Pixmap fall, Pixmap hat, float slicePixmapFallen) {
		switch (outlaw.state) {
		case walkLeft:
			int walkCycles = (int) (outlaw.xPosition / DISTANCE_ALTERNATE_WALK);
			if (walkCycles % 2 == 0) {
				outlawPixmap = walkA;
			} else {
				outlawPixmap = walkB;
			}
			break;
		case walkRight:
			walkCycles = (int) (outlaw.xPosition / DISTANCE_ALTERNATE_WALK);
			if (walkCycles % 2 == 0) {
				outlawPixmap = walkA;
			} else {
				outlawPixmap = walkB;
			}
			break;
		case stand:
			outlawPixmap = stand;
			break;
		case unholster:
			outlawPixmap = unholster;
			break;
		case aim:
			outlawPixmap = aim;
			break;
		case aimWin:
			int laughCycles = (int) (outlaw.acumulatedTimeInState / TIME_ALTERNATE_LAUGH);
			if (laughCycles % 2 == 0) {
				outlawPixmap = aimWin;
			} else {
				outlawPixmap = aimNotShine;
			}
			break;
		case fallen:
			outlawPixmap = fall;
			this.pixmapSlice = returnSliceBounded(slicePixmapFallen, 0.0f);
			hatPixmap = hat;
			this.hatPixmapSlice = returnSliceBounded(slicePixmapFallen, -0.30f);
			break;
		default:
			break;
		}
	}

	public float returnSliceBounded(float slicePixmapFallen, float boundSlice) {
		float acumTimeFalling = outlaw.acumulatedTimeInState + TIME_FALLING / 4;
		// Es una funcion cuadratica donde el tiempo acumulado es la x
		// f= (-x) * (x-b), el punto maximo es b^2/4
		float cuadraticFunction = (-acumTimeFalling) * (acumTimeFalling - TIME_FALLING);
		float normalizedCuadrFunc = cuadraticFunction / ((TIME_FALLING * TIME_FALLING) / 4);
		float slice = slicePixmapFallen * normalizedCuadrFunc;
		if (slice < boundSlice) {
			return boundSlice;
		} else {
			return slice;
		}
	}

	public void presentShootFrame(Graphics g) {
		float xTopLeft = outlaw.xPosition - outlaw.type.xRadius;
		float yTopLeft = outlaw.yPosition - outlaw.type.height;

		int xPosInPixels = xWorldCoordToPixel(g, xTopLeft);
		int yPosInPixels = yWorldCoordToPixel(g, yTopLeft);
		int widthInPixels = xWorldCoordToPixel(g, outlaw.type.xRadius * 2);
		int heightInPixels = yWorldCoordToPixel(g, outlaw.type.height);
		g.drawRect(xPosInPixels, yPosInPixels, widthInPixels, heightInPixels, 0x80ffffff);
	}

	private int xWorldCoordToPixel(Graphics g, float xCoord) {
		return (int) ((xCoord / World.WORLD_WIDTH) * g.getWidth());
	}

	private int yWorldCoordToPixel(Graphics g, float yCoord) {
		return (int) ((yCoord / World.WORLD_HEIGHT) * g.getHeight());
	}
}
