package ar.com.badami.wildgunman.view;

import android.graphics.Color;
import ar.com.badami.framework.Graphics;
import ar.com.badami.wildgunman.Assets;
import ar.com.badami.wildgunman.model.World;

public class WorldView {

	private World world;

	private OutlawView[] outlaws;

	public WorldView(World world) {
		super();
		init(world);
	}

	public void init(World world) {
		this.world = world;
		outlaws = new OutlawView[world.outlaws.length];
		for (int i = 0; i < world.outlaws.length; i++) {
			outlaws[i] = new OutlawView(world.outlaws[i]);
		}
	}

	public void present(Graphics graphics) {
		graphics.drawPixmap(Assets.background, 0, 0);
		presentDependsOnStatus(graphics);
		for (OutlawView outlawView : outlaws) {
			outlawView.present(graphics);
		}
		if (world.playerHasShot) {
			world.playerHasShot = false;
			presentShoot(graphics);
		}
	}

	private void presentDependsOnStatus(Graphics graphics) {
		switch (world.status) {
		case playerFault:
			graphics.drawColor(0xa0000080);
			graphics.drawTextCenter("Foul!!", 100, graphics.getWidth() / 2, graphics.getHeight() / 3, 0xff946800, Assets.homestead, 3.0f,
					2.0f);
			break;
		case playerWin:
			graphics.drawTextCenter("You win!", 100, graphics.getWidth() / 2, graphics.getHeight() / 3, 0xff946800, Assets.homestead, 3.0f,
					2.0f);
			break;
		case playerFallen:
			graphics.drawColor(0xa0a00000);
			graphics.drawTextCenter("You lose!", 100, graphics.getWidth() / 2, graphics.getHeight() / 3, 0xff946800, Assets.homestead,
					3.0f, 3.0f);
			break;
		default:
			break;
		}
	}

	private void presentShoot(Graphics graphics) {
		graphics.clear(Color.BLACK);
		for (OutlawView outlawView : outlaws) {
			outlawView.presentShootFrame(graphics);
		}
	}

}
