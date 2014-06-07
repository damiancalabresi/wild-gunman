package ar.com.badami.framework.impl;

import android.media.SoundPool;
import ar.com.badami.framework.Sound;

public class AndroidSound implements Sound {
	int soundId;
	int streamId;
	SoundPool soundPool;

	public AndroidSound(SoundPool soundPool, int soundId) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	@Override
	public void play(float volume) {
		streamId = soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	@Override
	public void pause() {
		if (streamId != 0) {
			soundPool.pause(streamId);
		}
	}

	@Override
	public void resume() {
		if (streamId != 0) {
			soundPool.resume(streamId);
		}
	}

	@Override
	public void stop() {
		if (streamId != 0) {
			soundPool.stop(streamId);
		}
	}

	@Override
	public void dispose() {
		soundPool.unload(soundId);
	}
}
