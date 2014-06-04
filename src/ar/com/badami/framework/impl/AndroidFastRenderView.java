package ar.com.badami.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
	AndroidGame game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;

	public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
		super(game);
		this.game = game;
		// El frameBuffer que tiene androidGraphics y sobre el que dibuja
		this.framebuffer = framebuffer;
		this.holder = getHolder();
	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}

	@Override
	public void run() {
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while (running) {
			if (!holder.getSurface().isValid())
				continue;

			// Le avisa al game cuanto tiempo paso desde el último dibujado
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();

			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);

			Canvas canvas = holder.lockCanvas();
			// Devuelve el tamaño de la pantalla
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(framebuffer, null, dstRect, null);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {
		running = false;
		while (true) {
			try {
				// Espera hasta que el thread de render termine para salir del
				// metodo pause
				renderThread.join();
				return;
			} catch (InterruptedException e) {
				// retry
			}
		}
	}
}
