package ar.com.badami.wildgunman.screen;

import java.util.List;

import android.util.Log;
import ar.com.badami.framework.Game;
import ar.com.badami.framework.Graphics;
import ar.com.badami.framework.Input.TouchEvent;
import ar.com.badami.framework.Screen;
import ar.com.badami.wildgunman.Assets;
import ar.com.badami.wildgunman.Settings;
import ar.com.badami.wildgunman.model.World;
import ar.com.badami.wildgunman.model.World.RoundStatus;
import ar.com.badami.wildgunman.view.WorldView;

public class GameScreen extends Screen {
	enum GameState {
		// Muestra el cartel para preguntar si esta listo
		Ready, Running,
		// Muestra la posibilidad de resumir o salir
		Paused,
		// Da la posibilidad de volver al menu principal
		GameOver
	}

	GameState state = GameState.Ready;
	World world;

	// Variables para el estado Ready
	private static float READY_WAIT_TIME_IN_SEC = 5.0f;
	private float remainingTimeToReady = READY_WAIT_TIME_IN_SEC;
	private String remainingSecondsToReady;

	// Properties para la vista
	private final WorldView worldView;
	private int pixelsWidth;
	private int pixelsHeight;

	public GameScreen(Game game) {
		super(game);
		world = new World();
		worldView = new WorldView(world);
		Assets.prepareToPlay.play();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if (state == GameState.Ready)
			updateReady(deltaTime);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(float deltaTime) {
		remainingTimeToReady -= deltaTime;
		remainingSecondsToReady = String.valueOf((int) remainingTimeToReady);
		if (remainingTimeToReady <= 0.0f) {
			Assets.prepareToPlay.dispose();
			state = GameState.Running;
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		world.update(deltaTime);
		Log.d("damian", world.status.toString());
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				Assets.shoot.play(1.0f);
				updateSizeInPixels();
				float xCoord = (event.x * World.WORLD_WIDTH) / pixelsWidth;
				float yCoord = (event.y * World.WORLD_HEIGHT) / pixelsHeight;
				world.shoot(xCoord, yCoord);
			}
		}
		resetWorldIfFinished();
	}

	public void resetWorldIfFinished() {
		if (world.status == RoundStatus.finished) {
			world.init();
			worldView.init(world);
		}
	}

	private void updateSizeInPixels() {
		pixelsWidth = game.getGraphics().getWidth();
		pixelsHeight = game.getGraphics().getHeight();
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		// Verifica si tocó resume o quit
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.background, 0, 0);
		worldView.present(g);
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
	}

	private void drawReadyUI() {
		Graphics graphics = game.getGraphics();
		// Lo multiplica con la parte decimal del tiempo restante, de esta forma
		// la letra se achica y se agranda cuando esta mas cerca de un segundo
		// concreto
		graphics.drawTextCenter(remainingSecondsToReady, 180 * (1 - (remainingTimeToReady % 1)), graphics.getWidth() / 2,
				graphics.getHeight() / 2, 0xff946800, Assets.homestead, 3.0f, 2.0f);
		// graphics.drawTextCenterWithBorder(remainingSecondsToReady, 180 * (1 -
		// (remainingTimeToReady % 1)), graphics.getWidth() / 2,
		// graphics.getHeight() / 2, 0xff946800, Assets.homestead, 3.0f, 2.0f,
		// Color.WHITE, 4.0f);
	}

	private void drawRunningUI() {

	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
	}

	@Override
	public void pause() {
		// Es el metodo al que se llama si se pausa la activity
		if (state == GameState.Running)
			state = GameState.Paused;
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
