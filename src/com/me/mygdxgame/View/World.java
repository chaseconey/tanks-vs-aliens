package com.me.mygdxgame.View;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.me.mygdxgame.Images;
import com.me.mygdxgame.MyGame;
import com.me.mygdxgame.Sounds;
import com.me.mygdxgame.Screens.GameOverScreen;

public class World {

	MyGame game;
	Rectangle tank;
	private String status;
	private int score;
	private int lives;
	private long lastShootTime;
	private long lastDropTime;
	private OrthographicCamera camera;
	
	public Array<Rectangle> aliens;
	public Array<Rectangle> projectiles;
	
	public World(MyGame game) {
		this.game = game;
		
		// place tank
		tank = new Rectangle();
		tank.x = 800 / 2 - 32 / 2;
		tank.y = 20;
		tank.width = 32;
		tank.height = 32;
		
		status = "Score: 0  Lives: 0";
		score = 0;
		lives = 5;
		
		aliens = new Array<Rectangle>();
		projectiles = new Array<Rectangle>();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		camera.update();
	}
	
	public Rectangle getTank() {
		return this.tank;
	}
	
	public Array<Rectangle> getAliens() {
		return this.aliens;
	}
	
	public Array<Rectangle> getProjectiles() {
		return this.projectiles;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public void update() {
		
		// Is the game over?
		if (this.lives < 1) {
			this.dispose();
			game.setScreen(new GameOverScreen(game, score));
		}

		// Update status
		this.status = "Score: " + this.score + "   Lives: " + this.lives;

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			tank.x = touchPos.x - 32 / 2;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT))
			this.tank.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			tank.x += 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.SPACE)
				&& TimeUtils.nanoTime() - this.lastShootTime > 500000000)
			this.spawnProjectile();

		if (tank.x < 0)
			tank.x = 0;
		if (tank.x > 800 - 32)
			tank.x = 800 - 32;

		// Time to spawn a new enemy?
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			this.spawnEnemy();

		// Move the enemys
		Iterator<Rectangle> rainIter = aliens.iterator();
		while (rainIter.hasNext()) {
			Rectangle enemy = rainIter.next();
			enemy.y -= 100 * Gdx.graphics.getDeltaTime();

			if (enemy.y + 32 < 0) {
				rainIter.remove();
				this.removeLife();
			}

			// Did it hit us?
			if (enemy.overlaps(tank)) {
				Sounds.play(Sounds.hit);
				rainIter.remove();
				// decrease lives
				this.removeLife();
			}
		}

		// Move the projectiles
		Iterator<Rectangle> projectileIter = projectiles.iterator();
		while (projectileIter.hasNext()) {
			Rectangle projectile = projectileIter.next();
			projectile.y += 200 * Gdx.graphics.getDeltaTime();
			if (projectile.y - 16 > 480)
				projectileIter.remove();

			// Did we shoot something?
			// Increase score
			Iterator<Rectangle> rainIter1 = aliens.iterator();
			while (rainIter1.hasNext()) {
				Rectangle enemy = rainIter1.next();

				if (enemy.overlaps(projectile)) {
					Sounds.play(Sounds.hit);
					rainIter1.remove();
					this.increaseScore();
				}
			}
		}
	}
	
	public void dispose() {
		Images.dispose();
		Sounds.dispose();
	}

	public void spawnProjectile() {
		Rectangle projectile = new Rectangle();

		projectile.width = 16;
		projectile.height = 16;
		projectile.y = 30;
		projectile.x = this.tank.x
				+ ((this.tank.width / 2) - (projectile.width / 2));

		projectiles.add(projectile);
		Sounds.play(Sounds.shoot);
		this.lastShootTime = TimeUtils.nanoTime();
	}
	
	public void spawnEnemy() {
		Rectangle enemy = new Rectangle();
		enemy.x = MathUtils.random(0, 800 - 32);
		enemy.y = 480;
		enemy.width = 32;
		enemy.height = 32;
		aliens.add(enemy);
		lastDropTime = TimeUtils.nanoTime();
	}

	private void removeLife() {
		this.lives -= 1;
	}

	private void increaseScore() {
		this.score += 10;
	}
}
