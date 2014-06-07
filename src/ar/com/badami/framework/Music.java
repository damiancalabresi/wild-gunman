package ar.com.badami.framework;

public interface Music {
	public void play();

	public void stop();

	public void pause();

	public void setLooping(boolean looping);

	public void setVolume(float volume);

	public boolean isPlaying();

	public boolean isStopped();

	public boolean isPaused();

	public boolean isLooping();

	public void resumeIfPaused();

	public void dispose();

}
