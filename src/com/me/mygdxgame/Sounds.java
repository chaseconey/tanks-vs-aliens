package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {  
    
    public static Sound shoot;
    public static Sound hit;
    public static Sound music;
      
    public static void load () {  
          
        shoot = loadSound("shoot.wav");
        hit = loadSound("hit.mp3");
        music = loadSound("epic.mp3");
    }  
      
    private static Sound loadSound (String filename) {  
        return Gdx.audio.newSound(Gdx.files.internal("data/sounds/" + filename));  
    }  
      
    public static void play (Sound sound) {  
        sound.play(1);  
    }
    
    public static void setLooping (Sound sound) {  
        sound.setLooping(1, true);
    }
    
    public static void dispose() {
    	shoot.dispose();
    	hit.dispose();
    	music.dispose();
    }
}  