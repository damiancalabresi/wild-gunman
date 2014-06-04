package ar.com.badami.wildgunman;

import ar.com.badami.framework.Screen;
import ar.com.badami.framework.impl.AndroidGame;
import ar.com.badami.wildgunman.screen.LoadingScreen;

public class MainGame extends AndroidGame {
	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}
