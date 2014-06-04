package ar.com.badami.framework;

import android.graphics.Typeface;

public interface Graphics {
	public static enum PixmapFormat {
		ARGB8888, ARGB4444, RGB565
	}

	public Pixmap newPixmap(String fileName, PixmapFormat format);

	public Typeface newFont(String fileName);

	public void clear(int color);

	public void drawColor(int color);

	public void drawPixel(int x, int y, int color);

	public void drawLine(int x, int y, int x2, int y2, int color);

	public void drawRect(int x, int y, int width, int height, int color);

	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);

	public void drawPixmap(Pixmap pixmap, int x, int y);

	public void drawTextLeft(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius, float shadowDiff);

	public void drawTextCenter(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff);

	public void drawTextRight(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff);

	public void drawTextLeftWithBorder(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff, int borderColor, float borderWidth);

	public void drawTextCenterWithBorder(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff, int borderColor, float borderWidth);

	public void drawTextRightWithBorder(String text, float size, float xPos, float yPos, int color, Typeface font, float shadowRadius,
			float shadowDiff, int borderColor, float borderWidth);

	public int getWidth();

	public int getHeight();

}
