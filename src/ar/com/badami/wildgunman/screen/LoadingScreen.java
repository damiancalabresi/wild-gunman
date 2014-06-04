package ar.com.badami.wildgunman.screen;

import ar.com.badami.framework.Audio;
import ar.com.badami.framework.Game;
import ar.com.badami.framework.Graphics;
import ar.com.badami.framework.Graphics.PixmapFormat;
import ar.com.badami.framework.Screen;
import ar.com.badami.wildgunman.Assets;
import ar.com.badami.wildgunman.Settings;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		// Carga las imagenes y sonidos
		// El UI Thread sigue bloqueado
		// Se podría hacer esto en otro thread y en el metodo present dibujar
		// algo

		Graphics graphics = game.getGraphics();
		Assets.homestead = graphics.newFont("font/homestead.ttf");
		Assets.homesteadInline = graphics.newFont("font/homestead-inline.ttf");
		Assets.mainBackground = graphics.newPixmap("picture/main-background.png", PixmapFormat.RGB565);
		Assets.background = graphics.newPixmap("picture/background-dark.png", PixmapFormat.RGB565);
		Assets.eye = graphics.newPixmap("picture/eye.png", PixmapFormat.RGB565);
		Assets.fireLeft = graphics.newPixmap("picture/fire-left.png", PixmapFormat.RGB565);
		Assets.fireRight = graphics.newPixmap("picture/fire-right.png", PixmapFormat.RGB565);
		Assets.title = graphics.newPixmap("picture/title.png", PixmapFormat.RGB565);
		Assets.outlawBillyWalkA = graphics.newPixmap("picture/outlaw/outlaw-billy-walk-a.png", PixmapFormat.RGB565);
		Assets.outlawBillyWalkB = graphics.newPixmap("picture/outlaw/outlaw-billy-walk-b.png", PixmapFormat.RGB565);
		Assets.outlawBillyStand = graphics.newPixmap("picture/outlaw/outlaw-billy-stand.png", PixmapFormat.RGB565);
		Assets.outlawBillyUnholster = graphics.newPixmap("picture/outlaw/outlaw-billy-unholster.png", PixmapFormat.RGB565);
		Assets.outlawBillyAim = graphics.newPixmap("picture/outlaw/outlaw-billy-aim.png", PixmapFormat.RGB565);
		Assets.outlawBillyAimNotShine = graphics.newPixmap("picture/outlaw/outlaw-billy-aim-eyes-not-shine.png", PixmapFormat.RGB565);
		Assets.outlawBillyAimWin = graphics.newPixmap("picture/outlaw/outlaw-billy-aim-win.png", PixmapFormat.RGB565);
		Assets.outlawBillyFall = graphics.newPixmap("picture/outlaw/outlaw-billy-fall.png", PixmapFormat.RGB565);
		Assets.outlawBillyHat = graphics.newPixmap("picture/outlaw/outlaw-billy-hat.png", PixmapFormat.RGB565);
		Assets.outlawBobWalkA = graphics.newPixmap("picture/outlaw/outlaw-bob-walk-a.png", PixmapFormat.RGB565);
		Assets.outlawBobWalkB = graphics.newPixmap("picture/outlaw/outlaw-bob-walk-b.png", PixmapFormat.RGB565);
		Assets.outlawBobStand = graphics.newPixmap("picture/outlaw/outlaw-bob-stand.png", PixmapFormat.RGB565);
		Assets.outlawBobUnholster = graphics.newPixmap("picture/outlaw/outlaw-bob-unholster.png", PixmapFormat.RGB565);
		Assets.outlawBobAim = graphics.newPixmap("picture/outlaw/outlaw-bob-aim.png", PixmapFormat.RGB565);
		Assets.outlawBobAimNotShine = graphics.newPixmap("picture/outlaw/outlaw-bob-aim-eyes-not-shine.png", PixmapFormat.RGB565);
		Assets.outlawBobAimWin = graphics.newPixmap("picture/outlaw/outlaw-bob-aim-win.png", PixmapFormat.RGB565);
		Assets.outlawBobFall = graphics.newPixmap("picture/outlaw/outlaw-bob-fall.png", PixmapFormat.RGB565);
		Assets.outlawMarkWalkA = graphics.newPixmap("picture/outlaw/outlaw-mark-walk-a.png", PixmapFormat.RGB565);
		Assets.outlawMarkWalkB = graphics.newPixmap("picture/outlaw/outlaw-mark-walk-b.png", PixmapFormat.RGB565);
		Assets.outlawMarkStand = graphics.newPixmap("picture/outlaw/outlaw-mark-stand.png", PixmapFormat.RGB565);
		Assets.outlawMarkUnholster = graphics.newPixmap("picture/outlaw/outlaw-mark-unholster.png", PixmapFormat.RGB565);
		Assets.outlawMarkAim = graphics.newPixmap("picture/outlaw/outlaw-mark-aim.png", PixmapFormat.RGB565);
		Assets.outlawMarkAimNotShine = graphics.newPixmap("picture/outlaw/outlaw-mark-aim-eyes-not-shine.png", PixmapFormat.RGB565);
		Assets.outlawMarkAimWin = graphics.newPixmap("picture/outlaw/outlaw-mark-aim-win.png", PixmapFormat.RGB565);
		Assets.outlawMarkFall = graphics.newPixmap("picture/outlaw/outlaw-mark-fall.png", PixmapFormat.RGB565);
		Assets.outlawMarkHat = graphics.newPixmap("picture/outlaw/outlaw-mark-hat.png", PixmapFormat.RGB565);
		Assets.outlawShefferWalkA = graphics.newPixmap("picture/outlaw/outlaw-sheffer-walk-a.png", PixmapFormat.RGB565);
		Assets.outlawShefferWalkB = graphics.newPixmap("picture/outlaw/outlaw-sheffer-walk-b.png", PixmapFormat.RGB565);
		Assets.outlawShefferStand = graphics.newPixmap("picture/outlaw/outlaw-sheffer-stand.png", PixmapFormat.RGB565);
		Assets.outlawShefferUnholster = graphics.newPixmap("picture/outlaw/outlaw-sheffer-unholster.png", PixmapFormat.RGB565);
		Assets.outlawShefferAim = graphics.newPixmap("picture/outlaw/outlaw-sheffer-aim.png", PixmapFormat.RGB565);
		Assets.outlawShefferAimNotShine = graphics.newPixmap("picture/outlaw/outlaw-sheffer-aim-eyes-not-shine.png", PixmapFormat.RGB565);
		Assets.outlawShefferAimWin = graphics.newPixmap("picture/outlaw/outlaw-sheffer-aim-win.png", PixmapFormat.RGB565);
		Assets.outlawShefferFall = graphics.newPixmap("picture/outlaw/outlaw-sheffer-fall.png", PixmapFormat.RGB565);
		Assets.outlawShefferHat = graphics.newPixmap("picture/outlaw/outlaw-sheffer-hat.png", PixmapFormat.RGB565);
		Assets.outlawRandyWalkA = graphics.newPixmap("picture/outlaw/outlaw-randy-walk-a.png", PixmapFormat.RGB565);
		Assets.outlawRandyWalkB = graphics.newPixmap("picture/outlaw/outlaw-randy-walk-b.png", PixmapFormat.RGB565);
		Assets.outlawRandyStand = graphics.newPixmap("picture/outlaw/outlaw-randy-stand.png", PixmapFormat.RGB565);
		Assets.outlawRandyUnholster = graphics.newPixmap("picture/outlaw/outlaw-randy-unholster.png", PixmapFormat.RGB565);
		Assets.outlawRandyAim = graphics.newPixmap("picture/outlaw/outlaw-randy-aim.png", PixmapFormat.RGB565);
		Assets.outlawRandyAimNotShine = graphics.newPixmap("picture/outlaw/outlaw-randy-aim-eyes-not-shine.png", PixmapFormat.RGB565);
		Assets.outlawRandyAimWin = graphics.newPixmap("picture/outlaw/outlaw-randy-aim-win.png", PixmapFormat.RGB565);
		Assets.outlawRandyFall = graphics.newPixmap("picture/outlaw/outlaw-randy-fall.png", PixmapFormat.RGB565);
		Assets.outlawRandyHat = graphics.newPixmap("picture/outlaw/outlaw-randy-hat.png", PixmapFormat.RGB565);
		Audio audio = game.getAudio();
		Assets.bonusPoint = audio.newSound("sound/bonus-point.mp3");
		Assets.doorOpening = audio.newSound("sound/door-opening.mp3");
		Assets.fire = audio.newSound("sound/fire.mp3");
		Assets.foul = audio.newSound("sound/foul.mp3");
		Assets.gameOver = audio.newSound("sound/game-over.mp3");
		Assets.guyFalling = audio.newSound("sound/guy-falling.mp3");
		Assets.guyHitFloor = audio.newSound("sound/guy-hit-floor.mp3");
		Assets.oneOutlawIntro = audio.newSound("sound/one-outlaw-intro.mp3");
		Assets.prepareToShoot = audio.newSound("sound/prepare-to-shoot.mp3");
		Assets.shoot = audio.newSound("sound/shoot.mp3");
		Assets.twoOutlawsIntro = audio.newSound("sound/two-outlaws-intro.mp3");
		Assets.youLost = audio.newSound("sound/you-lost.mp3");
		Assets.youWon = audio.newSound("sound/you-won.mp3");
		Assets.titleMusic = audio.newMusic("sound/title.mp3");
		Assets.prepareToPlay = audio.newMusic("sound/prepare-to-play.mp3");

		// Carga las settings
		Settings.load(game.getFileIO());
		game.setScreen(new MenuScreen(game));
	}

	@Override
	public void present(float deltaTime) {
		// Mientras se van cargando las imagenes se podría mostrar algo
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
