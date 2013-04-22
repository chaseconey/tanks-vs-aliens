package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Images {

    public static Texture projectileImage;
    public static Texture tank;
    public static Texture enemy;
      
    public static void load () {  
          
    	projectileImage = loadTexture("bullet.png");
    	tank = loadTexture("tank.png");
        enemy = loadTexture("enemy.png");
    }  
      
    private static Texture loadTexture (String filename) {  
        return new Texture(Gdx.files.internal("data/images/" + filename)); 
    }
    
    public static void dispose() {
    	projectileImage.dispose();
    	tank.dispose();
        enemy.dispose();
    }
}
