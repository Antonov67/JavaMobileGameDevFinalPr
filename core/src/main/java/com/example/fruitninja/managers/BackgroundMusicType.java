package com.example.fruitninja.managers;

public enum BackgroundMusicType {

    MUSIC_TYPE_1("sounds/background_music1.mp3"),
    MUSIC_TYPE_2("sounds/background_music2.mp3");

    public final String musicPath;
    BackgroundMusicType(String musicPath) {
        this.musicPath = musicPath;
    }
}
