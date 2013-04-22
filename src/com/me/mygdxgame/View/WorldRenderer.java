package com.me.mygdxgame.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.me.mygdxgame.Images;
import com.me.mygdxgame.MyGame;
import com.me.mygdxgame.Sounds;

public class WorldRenderer {

	private World world;
	private OrthographicCamera camera;
	private String status;
	private BitmapFont font;
	private Array<Rectangle> projectiles;
	private SpriteBatch batch;
	private Rectangle tank;
	private Array<Rectangle> aliens;

	public WorldRenderer(World world) {
		Gdx.app.log(MyGame.LOG, "WE MADE IT TO THE RENDERER!");
		this.world = world;
		
		camera = world.getCamera();
		
		// load textures
		Images.load();

		// load sounds
		Sounds.load();
		
		// start the playback of the background music immediately
		Sounds.setLooping(Sounds.music);
		Sounds.play(Sounds.music);

		// Create enemy
		world.spawnEnemy();
		
		font = new BitmapFont();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
	}
	
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tank = world.getTank();
		aliens = world.getAliens();
		projectiles = world.getProjectiles();
		status = world.getStatus();
		
		batch.begin();
		font.draw(batch, status, 400, 20);
		batch.draw(Images.tank, tank.x, tank.y);
		for (Rectangle alien : aliens) {
			batch.draw(Images.enemy, alien.x, alien.y);
		}
		for (Rectangle projectile : projectiles) {
			batch.draw(Images.projectileImage, projectile.x, projectile.y);
		}
		batch.end();
		
	}
}
