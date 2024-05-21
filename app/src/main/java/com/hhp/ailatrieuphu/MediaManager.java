package com.hhp.ailatrieuphu;

import android.media.MediaPlayer;

public class MediaManager {
    private static MediaManager instance;
    private MediaPlayer mainPlayer;
    private MediaPlayer mcPlayer;

    private MediaManager() {
    }

    public static MediaManager getInstance() {
        if (instance == null) {
            instance = new MediaManager();
        }
        return instance;
    }

    private void playSong(int song, boolean isLooping) {
        if (mainPlayer != null) {
            mainPlayer.reset();
        }
        mainPlayer = MediaPlayer.create(App.getInstance(), song);
        mainPlayer.setLooping(isLooping);
        mainPlayer.start();
    }

    public void playIntro() {
        playSong(R.raw.song_intro, true);
    }

    public void playRule() {
        playSong(R.raw.rule, false);
    }

    public void playReady() {
        playSong(R.raw.ready, false);
    }

    public void playGame() {
        playSong(R.raw.song_play, true);
    }
    public void playRanked(){
        playSong(R.raw.song_playranked_1, true);
    }


    public void playMC(int song, MediaPlayer.OnCompletionListener event) {
        if (mcPlayer != null) {
            mcPlayer.reset();
        }
        mcPlayer = MediaPlayer.create(App.getInstance(), song);
        mcPlayer.setOnCompletionListener(event);
        mcPlayer.start();
    }


    public void pauseSong() {
        if (mainPlayer != null && mainPlayer.isPlaying()) {
            mainPlayer.pause();
        }
    }

}
