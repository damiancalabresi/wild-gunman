package ar.com.badami.framework.impl;

import android.media.SoundPool;
import ar.com.badami.framework.Sound;

public class AndroidSound implements Sound {
	int soundId;
	SoundPool soundPool;

	public AndroidSound(SoundPool soundPool, int soundId) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	@Override
	public int play(float volume) {
		return soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() {
		soundPool.unload(soundId);
	}
}
