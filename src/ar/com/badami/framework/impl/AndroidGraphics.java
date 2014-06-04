package ar.com.badami.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import ar.com.badami.framework.Graphics;
import ar.com.badami.framework.Pixmap;

public class AndroidGraphics implements Graphics {
	// Para cargar las imagenes
	AssetManager assets;
	// El bitmap que vamos a usar de frameBuffer
	Bitmap frameBuffer;
	// El canvas y el paint que usamos para dibujar
	Canvas canvas;
	Paint paint;
	Paint paintText;
	// Los dos rectangulos son necesarios para las imagenes
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();

	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
		this.assets = assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
		this.paintText = new Paint();
	}

	@Override
	public Pixmap newPixmap(String fileName, PixmapFormat format) {
		Config config = null;
		if (format == PixmapFormat.RGB565)
			config = Config.RGB_565;
		else if (format == PixmapFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;

		Options options = new Options();
		// Va a tratar de usar esta configuracion si es posible
		options.inPreferredConfig = config;

		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = assets.open(fileName);
			bitmap = BitmapFactory.decodeStream(in);
			if (bitmap == null)
				throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		// Actualiza el formato con el que cargo finalmente
		if (bitmap.getConfig() == Config.RGB_565)
			format = PixmapFormat.RGB565;
		else if (bitmap.getConfig() == Config.ARGB_4444)
			format = PixmapFormat.ARGB4444;
		else
			format = PixmapFormat.ARGB8888;

		return new AndroidPixmap(bitmap, format);
	}

	@Override
	public Typeface newFont(String fileName) {
		return Typeface.createFromAsset(assets, fileName);
	}

	@Override
	public void clear(int color) {
		// Saca las componentes R,G, B y las convierte a ints que luego le pasa
		// a drawRGB
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
	}

	@Override
	public void drawColor(int color) {
		canvas.drawColor(color);
	}

	@Override
	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2, int color) {
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}

	@Override
	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		// Se resta uno porque el pixel x e y es inclusivo, se le suma lo que
		// falta para llegar al width o el height
		canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
		// Sirve para dibujar una parte del bitmap
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth - 1;
		srcRect.bottom = srcY + srcHeight - 1;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth - 1;
		dstRect.bottom = y + srcHeight - 1;

		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y) {
		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, null);
	}

	@Override
	public void drawTextLeft(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius, float shadowDiff) {
		drawText(text, size, xPos, yPos, color, font, Paint.Align.LEFT, shadowRadius, shadowDiff);
	}

	@Override
	public void drawTextCenter(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff) {
		drawText(text, size, xPos, yPos, color, font, Paint.Align.CENTER, shadowRadius, shadowDiff);
	}

	@Override
	public void drawTextRight(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff) {
		drawText(text, size, xPos, yPos, color, font, Paint.Align.RIGHT, shadowRadius, shadowDiff);
	}

	@Override
	public void drawTextLeftWithBorder(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff, int borderColor, float borderWidth) {
		drawTextWithBorder(text, size, xPos, yPos, color, font, Paint.Align.LEFT, shadowRadius, shadowDiff, borderColor, borderWidth);
	}

	@Override
	public void drawTextCenterWithBorder(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff, int borderColor, float borderWidth) {
		drawTextWithBorder(text, size, xPos, yPos, color, font, Paint.Align.CENTER, shadowRadius, shadowDiff, borderColor, borderWidth);
	}

	@Override
	public void drawTextRightWithBorder(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff, int borderColor, float borderWidth) {
		drawTextWithBorder(text, size, xPos, yPos, color, font, Paint.Align.RIGHT, shadowRadius, shadowDiff, borderColor, borderWidth);
	}

	private void drawText(String text, float size, float xPos, float yPos, int color, Typeface font, Paint.Align align, float shadowRadius,
			float shadowDiff) {
		paintText.setTypeface(font);
		paintText.setTextSize(size);
		paintText.setTextAlign(align);
		paintText.setStyle(Style.FILL);
		paintText.setColor(color);
		paintText.setShadowLayer(shadowRadius, shadowDiff, shadowDiff, color);
		canvas.drawText(text, xPos, yPos, paintText);
	}

	private void drawTextWithBorder(String text, float size, float xPos, float yPos, int color, Typeface font, Paint.Align align,
			float shadowRadius, float shadowDiff, int borderColor, float borderWidth) {
		drawText(text, size, xPos, yPos, color, font, align, shadowRadius, shadowDiff);
		paintText.setStyle(Style.STROKE);
		paintText.setStrokeWidth(borderWidth);
		paintText.setColor(borderColor);
		canvas.drawText(text, xPos, yPos, paintText);
	}

	@Override
	public int getWidth() {
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		return frameBuffer.getHeight();
	}
}
