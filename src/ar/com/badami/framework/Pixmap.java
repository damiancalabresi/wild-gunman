package ar.com.badami.framework;

import ar.com.badami.framework.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();

	public int getHeight();

	public PixmapFormat getFormat();

	public void dispose();
}
