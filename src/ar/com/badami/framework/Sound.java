package ar.com.badami.framework;

public interface Sound {
	public void play(float volume);

	public void pause();

	public void resume();

	public void stop();

	public void dispose();

}
