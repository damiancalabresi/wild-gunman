package ar.com.badami.framework.handler;

import java.util.List;

import android.view.View.OnTouchListener;
import ar.com.badami.framework.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener {
	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	public List<TouchEvent> getTouchEvents();
}
