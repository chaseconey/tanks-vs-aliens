package com.me.mygdxgame.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.Images;
import com.me.mygdxgame.MyGame;
import com.me.mygdxgame.Sounds;
import com.me.mygdxgame.View.World;
import com.me.mygdxgame.View.WorldRenderer;

public class GameScreen implements Screen {

	private MyGame game;
	private WorldRenderer render;
	private World world;
	
	public GameScreen(MyGame game) {
		this.game = game;
		world = new World(game);
		render = new WorldRenderer(world);
	}

	public void render(float delta) {
		world.update();
		render.render();
	}

	public void create() {
	}

	@Override
	public void dispose() {
		world.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
