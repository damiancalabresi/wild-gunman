package ar.com.badami.wildgunman.screen;

import java.util.List;

import android.graphics.Color;
import ar.com.badami.framework.Game;
import ar.com.badami.framework.Graphics;
import ar.com.badami.framework.Input.TouchEvent;
import ar.com.badami.framework.Screen;
import ar.com.badami.wildgunman.Assets;
import ar.com.badami.wildgunman.Settings;

public class MenuScreen extends Screen {

	private boolean arcadeButtonPressed = false;
	private boolean adventureButtonPressed = false;

	public MenuScreen(Game game) {
		super(game);
		Assets.titleMusic.play();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN || event.type == TouchEvent.TOUCH_DRAGGED) {
				arcadeButtonPressed = isInArcadeButton(event.x, event.y) ? true : false;
				adventureButtonPressed = isInAdventureButton(event.x, event.y) ? true : false;
			}
			if (event.type == TouchEvent.TOUCH_UP) {
				arcadeButtonPressed = adventureButtonPressed = false;
				if (isInArcadeButton(event.x, event.y)) {
					game.setScreen(new GameScreen(game));
				}
			}
		}
	}

	private boolean isInArcadeButton(int x, int y) {
		return (x > 90 && x < 490 && y > 250 && y < 300);
	}

	private boolean isInAdventureButton(int x, int y) {
		return (x > 90 && x < 490 && y > 310 && y < 360);
	}

	@Override
	public void present(float deltaTime) {
		Graphics graphics = game.getGraphics();
		graphics.clear(Color.BLACK);
		graphics.drawPixmap(Assets.mainBackground, 0, 0);
		graphics.drawTextLeft("WILD", 75, 100, 100, 0xff0f0f0f, Assets.homesteadInline, 1.0f, 3.0f);
		graphics.drawTextLeft("GUNMAN", 75, 100, 175, 0xff0f0f0f, Assets.homesteadInline, 1.0f, 3.0f);
		graphics.drawRect(90, 250, 400, 50, 0x80000000);
		graphics.drawRect(90, 310, 400, 50, 0x80000000);
		if (arcadeButtonPressed) {
			graphics.drawTextLeft("Arcade mode", 40, 100, 290, 0xff505050, Assets.homesteadInline, 1.0f, 0.0f);
		} else {
			graphics.drawTextLeft("Arcade mode", 40, 100, 290, 0xffd0d0d0, Assets.homesteadInline, 1.0f, 1.0f);
		}
		if (adventureButtonPressed) {
			graphics.drawTextLeft("Adventure mode", 40, 100, 350, 0xff505050, Assets.homesteadInline, 1.0f, 0.0f);
		} else {
			graphics.drawTextLeft("Adventure mode", 40, 100, 350, 0xffd0d0d0, Assets.homesteadInline, 1.0f, 1.0f);
		}
	}

	@Override
	public void pause() {
		Assets.titleMusic.pause();
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
		Assets.titleMusic.resumeIfPaused();
	}

	@Override
	public void dispose() {
		Assets.titleMusic.stop();
	}
}
