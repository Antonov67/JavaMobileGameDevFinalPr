package com.example.fruitninja.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.example.fruitninja.GameResources;


public class AudioManager {

    public boolean isSoundOn;
    public boolean isMusicOn;

    public Music backgroundMusic;
    public Sound bladeSound;
    public Sound bangSound;

    public AudioManager() {
        BackgroundMusicType[] types = BackgroundMusicType.values();
        BackgroundMusicType type = types[(int) (Math.random() * types.length)];

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(type.musicPath));
        bladeSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.BLADE_SOUND_PATH));
        bangSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.BANG_SOUND_PATH));

        backgroundMusic.setVolume(0.7f);
        backgroundMusic.setLooping(true);



        updateSoundFlag();
        updateMusicFlag();
    }

    public void updateSoundFlag() {
        isSoundOn = MemoryManager.loadIsSoundOn();
    }

    public void updateMusicFlag() {
        isMusicOn = MemoryManager.loadIsMusicOn();

        if (isMusicOn) backgroundMusic.play();
        else backgroundMusic.stop();
    }

}
