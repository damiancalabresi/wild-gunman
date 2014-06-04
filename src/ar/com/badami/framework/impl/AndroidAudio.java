package ar.com.badami.framework.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import ar.com.badami.framework.Audio;
import ar.com.badami.framework.Music;
import ar.com.badami.framework.Sound;

public class AndroidAudio implements Audio {
	AssetManager assets;
	SoundPool soundPool;

	// No se hace un release del soundPool debido a que el juego va a tener una
	// sola Activity con un solo SoundPool hasta que termine la activity

	public AndroidAudio(Activity activity) {
		// La activity es necesaria para definir el stream que controla el
		// volumen y para obtener el assetManager
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}

	@Override
	public Music newMusic(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			return new AndroidMusic(assetDescriptor);
		} catch (IOException e) {
			// Lanza una runtime exception ya que si falla es que no hay un
			// archivo de sonido o esta corrupto
			throw new RuntimeException("Couldn't load music '" + filename + "'");
		}
	}

	@Override
	public Sound newSound(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			// Lanza una runtime exception ya que si falla es que no hay un
			// archivo de sonido o esta corrupto
			throw new RuntimeException("Couldn't load sound '" + filename + "'");
		}
	}
}