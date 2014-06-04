package ar.com.badami.wildgunman.screen;

import android.graphics.Color;
import ar.com.badami.framework.Game;
import ar.com.badami.framework.Graphics;
import ar.com.badami.framework.Screen;
import ar.com.badami.wildgunman.Assets;
import ar.com.badami.wildgunman.Settings;

public class PlayerPrepareScreen extends Screen {

	private boolean playMusic = true;
	private static float WAIT_TIME_IN_SEC = 5.0f;
	private float remainingTime;
	private String remainingSeconds;

	public PlayerPrepareScreen(Game game) {
		super(game);
		remainingTime = WAIT_TIME_IN_SEC;
	}

	@Override
	public void update(float deltaTime) {
		game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if (playMusic) {
			Assets.prepareToPlay.play();
			playMusic = false;
		}

		remainingTime -= deltaTime;
		remainingSeconds = String.valueOf((int) remainingTime);

		if (remainingTime <= 0.0f) {
			Assets.prepareToPlay.dispose();
			game.setScreen(new GameScreen(game));
		}

	}

	@Override
	public void present(float deltaTime) {
		Graphics graphics = game.getGraphics();
		graphics.drawPixmap(Assets.background, 0, 0);
		// Lo multiplica con la parte decimal del tiempo restante, de esta forma
		// la letra se achica y se agranda cuando esta mas cerca de un segundo
		// concreto
		graphics.drawTextCenterWithBorder(remainingSeconds, 180 * (1 - (remainingTime % 1)), 400, 240, 0xff946800, Assets.homestead, 3.0f,
				2.0f, Color.WHITE, 4.0f);
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
