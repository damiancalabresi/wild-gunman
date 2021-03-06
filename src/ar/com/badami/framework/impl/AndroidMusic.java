package ar.com.badami.framework.impl;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import ar.com.badami.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener {
	MediaPlayer mediaPlayer;
	// Se implementa onCompletionListener para setear isPrepared en false cuando
	// se termine de reproducir
	boolean isPrepared = false;
	boolean isPaused = false;

	public AndroidMusic(AssetFileDescriptor assetDescriptor) {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(), assetDescriptor.getStartOffset(), assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException("Couldn't load music");
		}
	}

	@Override
	public void dispose() {
		if (mediaPlayer.isPlaying())
			this.stop();
		// Si no se hace stop antes del release falla
		mediaPlayer.release();
	}

	@Override
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped() {
		return !isPrepared;
	}

	@Override
	public boolean isPaused() {
		return isPaused;
	}

	@Override
	public void pause() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			synchronized (this) {
				isPaused = true;
			}
		}
	}

	@Override
	public void play() {
		if (mediaPlayer.isPlaying())
			return;
		try {
			synchronized (this) {
				if (!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
				isPaused = false;
			}
		} catch (IllegalStateException | IOException e) {
			// Imprime la excepcion pero la aplicacion sigue
			e.printStackTrace();
		}
	}

	@Override
	public void resumeIfPaused() {
		if (isPaused)
			this.play();
	}

	@Override
	public void setLooping(boolean isLooping) {
		mediaPlayer.setLooping(isLooping);
	}

	@Override
	public void setVolume(float volume) {
		mediaPlayer.setVolume(volume, volume);
	}

	@Override
	public void stop() {
		mediaPlayer.stop();
		synchronized (this) {
			isPrepared = false;
			isPaused = false;
		}
	}

	@Override
	public void onCompletion(MediaPlayer player) {
		synchronized (this) {
			isPrepared = false;
			isPaused = false;
		}
	}
}
