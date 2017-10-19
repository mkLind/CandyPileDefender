package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer.Random;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.MapObject.OBJECTTYPE;
import com.mygdx.game.Player.DIRECTION;
import com.mygdx.game.Powerup.POWERUPTYPE;

/*
 * The class that actually makes the game visible
 */
public class Updater implements Screen {

	// Texture img;
	private float aspectRatio;
	private OrthographicCamera camera;
	private Stage stage;
	private ShapeRenderer r;
	private Player player;
	private float statetime;

	// Teppo test
	private Pile pile;
	private ArrayList<SpriteCommons> enemies;
	boolean pileHealth;

	private BitmapFont f;
	final Core game;
	private ArrayList<Projectile> proj;
	private int timesCalled;
	private long timeSinceWave;
	private long timeToNextPowerup;
	private long timeScore;
	private GameWorld world;
	private Random randomizer;
	private OrthogonalTiledMapRenderer mapRender;
	private Array<RectangleMapObject> borders;
	private Array<RectangleMapObject> spawnPoints;
	private Array<RectangleMapObject> monsterSpawns;
	private ArrayList<MapObject> mapObjects;

	private ArrayList<Powerup> powerups;
	private ArrayList<ParticleEffect> effects;
	private long mpObjCooldown;
	private long mpObjLastSet;

	private Label scores;
	private Skin mySkin;
	private Pixmap pixmap;
	private ProgressBar healthBar;
	private TextureRegionDrawable drawable;

	/**
	 * Initializes the entire game
	 * 
	 * @param game
	 */
	public Updater(final Core game) {
		this.game = game;
		mpObjCooldown = 200;
		mpObjLastSet = 0;
		mapObjects = new ArrayList<>();
		statetime = 0f;
		timesCalled = 0;
		randomizer = new Random();
		world = new GameWorld();
		powerups = new ArrayList<>();
		monsterSpawns = new Array<RectangleMapObject>();
		enemies = new ArrayList<SpriteCommons>();
		// When loading textures to project, the entire path to the file should
		// be included
		// be careful with that
		camera = new OrthographicCamera();
		effects = new ArrayList<>();
		aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera.setToOrtho(false, 250f * aspectRatio, 250f);
		// camera.setToOrtho(false, 700f,700f);

		mapRender = world.getMapRenderer(camera);
		borders = world.getHitboxes();
		spawnPoints = world.getSpawnPoints();
		stage = new Stage();
		r = new ShapeRenderer();
		r.setProjectionMatrix(camera.combined);

		game.batch.setProjectionMatrix(camera.combined);
		proj = new ArrayList<Projectile>();

		// Set initial coordinates from map to player and candypile
		for (int i = 0; i < spawnPoints.size; i++) {
			if (spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Player")) {
				player = new Player(32, 32, spawnPoints.get(i).getRectangle().getX(),
						spawnPoints.get(i).getRectangle().getY(), 10);
			}
			if (spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Pile")) {
				pile = new Pile(100, 100, spawnPoints.get(i).getRectangle().getX(),
						spawnPoints.get(i).getRectangle().getY(),
						game.getLoader().getManager().get("pileTest.png", Texture.class),
						game.getLoader().getManager().get("pileTest2.png", Texture.class));
			}
		}
		for (int i = 0; i < spawnPoints.size; i++) {
			if (spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Enemy")) {
				monsterSpawns.add(spawnPoints.get(i));
			}
		}

		player.setAnimations(9, 4, 0.10f, game.getLoader().getManager().get("BatMonster.png", Texture.class));
		player.setDir(DIRECTION.DOWN);
		camera.position.set(player.getX(), player.getY(), 0);
		// Teppo kokeilua
		// pile located in the center of the game

		enemies = new ArrayList<SpriteCommons>();
		spawnEnemies();
		camera.update();
		timeToNextPowerup = TimeUtils.millis();

		// For score points
		timeScore = TimeUtils.millis();
		mySkin = new Skin(Gdx.files.internal("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/skin/uiskin.json"));
		scores = new Label("Score: " + game.getLoader().getScore(), mySkin);
		scores.setPosition(Gdx.graphics.getWidth() / 1.37f, Gdx.graphics.getHeight() - 20);
		scores.setAlignment(Align.topRight);
		scores.setWidth(Gdx.graphics.getWidth() / 4);
		stage.addActor(scores);

		// Health bar
		pixmap = new Pixmap(100, 10, Format.RGBA8888);
		pixmap.setColor(Color.RED);
		pixmap.fill();
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		healthBar = new ProgressBar(0f, 1f, 0.01f, false, new ProgressBarStyle());
		healthBar.getStyle().background = drawable;

		pixmap = new Pixmap(0, 10, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		healthBar.getStyle().knob = drawable;

		pixmap = new Pixmap(100, 10, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		healthBar.getStyle().knobBefore = drawable;
		pixmap.dispose();

		healthBar.setWidth(100);
		healthBar.setHeight(10);
		// healthBar.setAnimateDuration(0.0f);
		healthBar.setValue(1f);
		// healthBar.setAnimateDuration(0.25f);
		healthBar.setPosition(10, Gdx.graphics.getHeight() - 20);
		stage.addActor(healthBar);
	}

	// Spawn enemies. Enemy count increases by one every time to make the wave
	// stronger.
	private void spawnEnemies() {
		// System.out.println("SPAWNING ENEMIES");
		timesCalled++;
		int tmp;
		int tmp2;
		for (int i = 0; i < timesCalled; i++) {
			tmp = MathUtils.random(0, monsterSpawns.size - 1);
			tmp2 = MathUtils.random(0, monsterSpawns.size - 1);
			enemies.add(new StealingEnemy(32, 32, monsterSpawns.get(tmp).getRectangle().getX(),
					monsterSpawns.get(tmp).getRectangle().getY(), 1,
					game.getLoader().getManager().get("stealTest.png", Texture.class)));

			enemies.add(new ChaserEnemy(32, 32, monsterSpawns.get(tmp2).getRectangle().getX(),
					monsterSpawns.get(tmp2).getRectangle().getY(), 2,
					game.getLoader().getManager().get("chaserTest.png", Texture.class)));

			timeSinceWave = TimeUtils.millis();
		}
		// Set The direction of stealers to pile
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) instanceof StealingEnemy) {
				double hypot = Math.hypot(pile.getX(), enemies.get(i).getX());

				enemies.get(i).setxVel(((float) (1.2f / hypot * (pile.getX() - enemies.get(i).getX()))));
				enemies.get(i).setyVel(((float) (1.2f / hypot * (pile.getY() - enemies.get(i).getY()))));
			}
		}
	}

	public void render(float delta) {

		Gdx.gl.glClearColor(100, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Move the player
		// moves hitbox first to check if collisions
		player.moveHitbox(player.getX() + player.getxVel(), player.getY() + player.getyVel());

		if (Intersector.overlaps(player.getHitbox(), pile.getHitbox())) {

			// pulls the hitbox back
			player.updateHitbox();

		} else {
			// Move the player
			player.setX(player.getX() + player.getxVel());
			player.setY(player.getY() + player.getyVel());

			if (player.getPowerupType() == POWERUPTYPE.SLOWDOWN
					&& TimeUtils.timeSinceMillis(mpObjLastSet) > mpObjCooldown) {
				MapObject obj = new MapObject(32, 32, player.getX(), player.getY(), 0, 0, 10000,
						game.getLoader().getManager().get(
								"C:/Users/Markus/Desktop/CandyPileDefender/core/assets/tarstain.png", Texture.class),
						OBJECTTYPE.HAZARD);
				obj.setSpawnTime(TimeUtils.millis());
				mapObjects.add(obj);
				mpObjLastSet = TimeUtils.millis();
				System.out.println("SPAWNING TAR");
			}

		}

		// Teppo kokeilua

		for (int i = 0; i < enemies.size(); i++) {

			if (enemies.get(i) instanceof ChaserEnemy) {
				double hypot = Math.hypot(player.getX(), enemies.get(i).getX());

				enemies.get(i).setxVel(((float) (1.2f / hypot * (player.getX() - enemies.get(i).getX()))));
				enemies.get(i).setyVel(((float) (1.2f / hypot * (player.getY() - enemies.get(i).getY()))));
			}
			if (enemies.get(i) instanceof StealingEnemy && enemies.get(i).getxVel() == 0
					&& enemies.get(i).getyVel() == 0) {
				double hypot = Math.hypot(pile.getX(), enemies.get(i).getX());

				enemies.get(i).setxVel(((float) (1.2f / hypot * (pile.getX() - enemies.get(i).getX()))));
				enemies.get(i).setyVel(((float) (1.2f / hypot * (pile.getY() - enemies.get(i).getY()))));
			}

			if (!mapObjects.isEmpty()) {
				// SLOW THE ENEMIES DOWN IF ONE OF THEM HITS A POOL OF TAR
				for (int j = 0; j < mapObjects.size(); j++) {
					if (mapObjects.get(j).getHitbox().contains(enemies.get(i).getX(), enemies.get(i).getY())) {
						System.out.println("ENEMY AT: " + i + " IS STUCK IN TAR");

						enemies.get(i).setxVel(enemies.get(i).getxVel() / 100);
						enemies.get(i).setyVel(enemies.get(i).getyVel() / 100);

					}

				}
			}

			enemies.get(i).updateHitbox();

			// move hitbox first to check if collisions
			enemies.get(i).moveHitbox(enemies.get(i).getX() + enemies.get(i).getxVel(),
					enemies.get(i).getY() + enemies.get(i).getyVel());

			if (Intersector.overlaps(enemies.get(i).getHitbox(), player.getHitbox())) {

				// pulls the hitbox back
				enemies.get(i).updateHitbox();

			} else {
				// move enemies
				enemies.get(i).setX(enemies.get(i).getX() + enemies.get(i).getxVel());
				enemies.get(i).setY(enemies.get(i).getY() + enemies.get(i).getyVel());

			}
			// steal from the pile
			for (int k = 0; k < enemies.size(); k++) {
				if (enemies.get(k) instanceof StealingEnemy) {

					if (Intersector.overlaps((enemies.get(k).getHitbox()), pile.getHitbox())) {
						enemies.remove(k);
						pileHealth = pile.reduceHealth();
					}
				}

			}

		}

		statetime += delta;
		// Movement logic template for the character

		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-(1.5f + player.getHasteVel()));
			player.setyVel(0);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.setDir(Player.DIRECTION.UP);
			player.setyVel(1.5f + player.getHasteVel());
			player.setxVel(0);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			player.setDir(Player.DIRECTION.DOWN);
			player.setyVel(-(1.5f + player.getHasteVel()));
			player.setxVel(0);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1.5f + player.getHasteVel());
			player.setyVel(0);

		}

		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A)) {
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-(1.3f + player.getHasteVel()));
			player.setyVel(1.3f + player.getHasteVel());
		}
		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)) {
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1.3f + player.getHasteVel());
			player.setyVel(1.3f + player.getHasteVel());
		}
		if (Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)) {
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1.3f + player.getHasteVel());
			player.setyVel(-(1.3f + player.getHasteVel()));
		}
		if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A)) {
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-(1.3f + player.getHasteVel()));
			player.setyVel(-(1.3f + player.getHasteVel()));
		}

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.S)
				&& !Gdx.input.isKeyPressed(Keys.D)) {
			player.setDir(player.getDir());
			player.setxVel(0);
			player.setyVel(0);
		}
		// Remove tar pools when set time has passed
		if (!mapObjects.isEmpty()) {
			for (int i = 0; i < mapObjects.size(); i++) {
				if (TimeUtils.timeSinceMillis(mapObjects.get(i).getSpawnTime()) > mapObjects.get(i).getTimeAlive()) {
					mapObjects.remove(i);
				}
			}
		}

		for (int i = 0; i < powerups.size(); i++) {
			if (Intersector.overlaps(powerups.get(i).getHitbox(), player.getHitbox())) {
				player.setPowerupType(null);
				player.setPowerupType(powerups.get(i).getType());
				player.setPowerupActiveTime(powerups.get(i).getEffectTime());
				player.setPowerupSetTime(TimeUtils.millis());
				powerups.remove(i);

				if (player.getPowerupType() == POWERUPTYPE.HASTE) {
					player.setHasteVel(1.0f);
				}
				if (player.getPowerupType() == POWERUPTYPE.SHIELD) {
					MapObject obj = new MapObject(40, 40, player.getX() - player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, 0, 0, 10000,
							game.getLoader().getManager().get(
									"C:/Users/Markus/Desktop/CandyPileDefender/core/assets/SHIELD.png", Texture.class),
							OBJECTTYPE.FOLLOWER);
					obj.setSpawnTime(TimeUtils.millis());
					mapObjects.add(obj);
				}

				if (player.getPowerupType() == POWERUPTYPE.CLEARSCREEN) {
					MapObject obj = new MapObject(32, 32, player.getX() - player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, 0, 0, 10000,
							game.getLoader().getManager().get(
									"C:/Users/Markus/Desktop/CandyPileDefender/core/assets/ScreenClear.png",
									Texture.class),
							OBJECTTYPE.EXPANDER);
					obj.setSpawnTime(TimeUtils.millis());
					mapObjects.add(obj);
					enemies.clear();
				}
				if (player.getPowerupType() == POWERUPTYPE.RAPIDFIRE) {
					player.setShootingCooldown(100);
				}

				System.out.println("New POWERUP SET. powerup TYPE: " + player.getPowerupType());
			}
		}
		if (TimeUtils.timeSinceMillis(player.getPowerupSetTime()) > player.getPowerupActiveTime()
				&& player.getPowerupType() != null) {
		
			if (player.getPowerupType() == POWERUPTYPE.HASTE) {
				player.setHasteVel(0f);
			}

			if (player.getPowerupType() == POWERUPTYPE.RAPIDFIRE) {
				player.setShootingCooldown(900);
			}
			if (player.getPowerupType() == POWERUPTYPE.SHIELD) {
				mapObjects.clear();
			}

			if (player.getPowerupType() == POWERUPTYPE.CLEARSCREEN) {
				mapObjects.clear();
			}

			player.setPowerupType(null);
			System.out.println("POWERUP EFFECT ENDED");
		}

		// This listens to mouse clicks

		if (Gdx.input.justTouched() && TimeUtils.timeSinceMillis(player.getLastShot()) > player.getShootingCooldown()) {

			// Spawn a projectile with target coordinates and set the time it is
			// visible

			// Single shot
			if (player.getPowerupType() != POWERUPTYPE.TRIPLESHOT) {
				// Convert the cursor coordinates into game world coordinates.
				// Needs to be refined
				Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				Vector3 reaCoords = camera.unproject(v);
				float bulletVel = 20f;
				float velX = (float) (reaCoords.x - (player.getX() + (player.getWidth() / 2))) / bulletVel;
				float velY = (float) (reaCoords.y - (player.getY() + (player.getHeight() / 2))) / bulletVel;

				Projectile p = new Projectile(10, 10, player.getX() + player.getWidth() / 2,
						player.getY() + player.getHeight() / 2, velX, velY,
						game.getLoader().getManager().get("Pointer.png", Texture.class));
				p.setTargetX(reaCoords.x);
				p.setTargetY(reaCoords.y);
				p.setCurrentTime(TimeUtils.millis());
				proj.add(p);
				// triple shot
			} else {
				// Convert the cursor coordinates into game world coordinates.
				// Needs to be refined
				Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

				Vector3 reaCoords = camera.unproject(v);

				float bulletVel = 20f;

				// Straight shot
				float velX = (float) (reaCoords.x - (player.getX() + (player.getWidth()) / 2)) / bulletVel;
				float velY = (float) (reaCoords.y - (player.getY() + (player.getHeight() / 2))) / bulletVel;

				float velXR = (float) (reaCoords.x - (Projectile.getSideShots(player, reaCoords)[1].x)) / bulletVel;
				float velYR = (float) (reaCoords.y - (Projectile.getSideShots(player, reaCoords)[1].y)) / bulletVel;

				float velXL = (float) (reaCoords.x - (Projectile.getSideShots(player, reaCoords)[0].x)) / bulletVel;
				float velYL = (float) (reaCoords.y - (Projectile.getSideShots(player, reaCoords)[0].y)) / bulletVel;

				Projectile p = new Projectile(10, 10, player.getX() + player.getWidth() / 2,
						player.getY() + player.getHeight() / 2, velX, velY,
						game.getLoader().getManager().get("Pointer.png", Texture.class));

				Projectile l = new Projectile(10, 10, player.getX() + player.getWidth() / 2,
						player.getY() + player.getHeight() / 2, velXR, velYR,
						game.getLoader().getManager().get("Pointer.png", Texture.class));

				Projectile r = new Projectile(10, 10, player.getX() + player.getWidth() / 2,
						player.getY() + player.getHeight() / 2, velXL, velYL,
						game.getLoader().getManager().get("Pointer.png", Texture.class));

				l.setCurrentTime(TimeUtils.millis());
				p.setCurrentTime(TimeUtils.millis());
				r.setCurrentTime(TimeUtils.millis());
				proj.add(p);
				proj.add(l);
				proj.add(r);

			}

			player.setLastShot(TimeUtils.millis());
		}
		// Powerup spawns here

		if (TimeUtils.timeSinceMillis(timeToNextPowerup) > 10000) {
			powerups.add(spawnPowerUp(world, game));
			timeToNextPowerup = TimeUtils.millis();

		}

		// If the projectile has been enough time visible on the screen, remove
		// it
		for (int i = 0; i < proj.size(); i++) {

			if (TimeUtils.timeSinceMillis(proj.get(i).getCurrentTime()) >= 4000) {
				proj.remove(i);
			} else {

				// attempt to correct the diection of each projectile
				// MOve the projectile according to its x and y velocities

				proj.get(i).setX(proj.get(i).getX() + proj.get(i).getxVel());
				proj.get(i).setY(proj.get(i).getY() + proj.get(i).getyVel());

			}
		}

		for (int i = 0; i < enemies.size(); i++) {

			for (int j = 0; j < proj.size(); j++) {

				if (Intersector.overlaps(proj.get(j).getHitbox(), enemies.get(i).getHitbox())
						&& TimeUtils.timeSinceMillis(proj.get(j).getCurrentTime()) < 4000) {
					enemies.get(i).setHP(enemies.get(i).getHP() - 1);
					proj.remove(j);
					if (enemies.get(i).getHP() <= 0 && enemies.size() > 0) {
						// Get points
						if (enemies.get(i) instanceof StealingEnemy) {
							game.getLoader().setScore(game.getLoader().getScore() + 500);
						}
						if (enemies.get(i) instanceof ChaserEnemy) {
							game.getLoader().setScore(game.getLoader().getScore() + 1000);
						}
						enemies.remove(i);
						break;
					}
				}
			}
		}
		// If enemy and player touch they both lose HP
		for (int i = 0; i < enemies.size(); i++) {
			if (Intersector.overlaps(player.getHitbox(), enemies.get(i).getHitbox()) && enemies.get(i).getHP() > 0
					&& player.getHP() > 0) {

				if (player.getPowerupType() != POWERUPTYPE.SHIELD) {
					player.setHP(player.getHP() - 1);
					// Update healthbar
					healthBar.setValue(healthBar.getValue() - 0.1f);
				}
				enemies.get(i).setHP(enemies.get(i).getHP() - 1);

				if (enemies.get(i).getHP() <= 0) {
					// Get points
					if (enemies.get(i) instanceof StealingEnemy) {
						game.getLoader().setScore(game.getLoader().getScore() + 500);
					}
					if (enemies.get(i) instanceof ChaserEnemy) {
						game.getLoader().setScore(game.getLoader().getScore() + 1000);
					}
					enemies.remove(i);
				}
				if (player.getHP() < 1) {
					System.out.println("Player HP now zero");

					game.setScreen(new LoadingScreen(game));
					this.dispose();
				}
			}
		}

		camera.position.set(
				MathUtils.clamp(player.getX(), camera.viewportWidth * .5f,
						world.mapWidth() - camera.viewportWidth * .5f),
				MathUtils.clamp(player.getY(), camera.viewportHeight * .5f,
						world.mapHeight() - camera.viewportHeight * .5f),
				0);
		// Above is for further development
		camera.update();

		// Shape renderer used for debugging

		// Render the player and projectiles

		mapRender.setView(camera);
		mapRender.render();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		// Teppo kokeilua
				if (!pileHealth) {
					game.batch.draw(pile.getPileTexture(), pile.getX(), pile.getY());
				} else {
					game.batch.draw(pile.getPileTexture2(), pile.getX(), pile.getY());
				}
				
		if (!mapObjects.isEmpty()) {
			for (int i = 0; i < mapObjects.size(); i++) {
				// Draw Follower Object
				if (mapObjects.get(i).getType() == OBJECTTYPE.FOLLOWER) {
					game.batch.draw(mapObjects.get(i).getGraphic(), player.getX(), player.getY(),
							mapObjects.get(i).getWidth(), mapObjects.get(i).getHeight());

					// DRaw expander
				} else if (mapObjects.get(i).getType() == OBJECTTYPE.EXPANDER) {
					mapObjects.get(i).setHeight(mapObjects.get(i).getHeight() + 20);
					mapObjects.get(i).setWidth(mapObjects.get(i).getWidth() + 20);
					mapObjects.get(i).setX(mapObjects.get(i).getX() - 10);
					mapObjects.get(i).setY(mapObjects.get(i).getY() - 10);
					game.batch.draw(mapObjects.get(i).getGraphic(), mapObjects.get(i).getX(), mapObjects.get(i).getY(),
							mapObjects.get(i).getWidth(), mapObjects.get(i).getHeight());
				} else {

					// IF mapObject is not expanding object, draw it normally
					game.batch.draw(mapObjects.get(i).getGraphic(), mapObjects.get(i).getX(), mapObjects.get(i).getY());
				}
			}
		}
		

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) instanceof StealingEnemy) {
				game.batch.draw(((StealingEnemy) enemies.get(i)).getTexture(), enemies.get(i).getX(),
						enemies.get(i).getY());

			} else if (enemies.get(i) instanceof ChaserEnemy) {
				game.batch.draw(((ChaserEnemy) enemies.get(i)).getTexture(), enemies.get(i).getX(),
						enemies.get(i).getY());
			}

		}
		game.batch.draw(player.getCurrentFrame(statetime), player.getX(), player.getY(), player.getWidth(),
				player.getHeight());

		for (int i = 0; i < proj.size(); i++) {
			game.batch.draw(proj.get(i).getT(), proj.get(i).getX(), proj.get(i).getY(), proj.get(i).getWidth(),
					proj.get(i).getHeight());
		}

		if (!powerups.isEmpty()) {
			for (int i = 0; i < powerups.size(); i++) {

				Powerup powerup = powerups.get(i);

				if (TimeUtils.timeSinceMillis(powerup.getTimeAlive()) < 9000) {
					game.batch.draw(powerup.getGraphic(), powerup.getX(), powerup.getY(), 16f, 16f);

				} else {

					powerups.remove(i);
				}

			}
		}
		// Update particles in the list

		if (!effects.isEmpty()) {
			System.out.println("Powerup List not empty: " + effects.size());
			for (int i = 0; i < effects.size(); i++) {

				
				System.out.println("Effect Fetched is COMPLETE:" + effects.get(i).isComplete());
				if (!effects.get(i).isComplete()) {
					effects.get(i).update(statetime);
					effects.get(i).draw(game.batch);
				} else {

					effects.remove(i);
				}
			}

		}

		// Wait 10 sec between waves.
		if (TimeUtils.timeSinceMillis(timeSinceWave) > 10000 && enemies.isEmpty() && mapObjects.isEmpty()) {

			spawnEnemies();
		}

		game.batch.end();

		// Get points for alive time
		if (TimeUtils.timeSinceMillis(timeScore) > 1000) {
			game.getLoader().setScore(game.getLoader().getScore() + 20);
			scores.setText("Score: " + game.getLoader().getScore());
			timeScore = TimeUtils.millis();
		}

		stage.act(statetime);
		stage.draw();
		// shape renderer for debugging
		/*
		 * r.setProjectionMatrix(camera.combined); r.begin(ShapeType.Line);
		 * r.setColor(Color.GREEN); Vector3 vector = new
		 * Vector3(Gdx.input.getX(), Gdx.input.getY(), 0); Vector3 reaCoordn =
		 * camera.unproject(vector); r.line(player.getX() + player.getWidth() /
		 * 2, player.getY() + player.getHeight() / 2, reaCoordn.x, reaCoordn.y);
		 * 
		 * for (int i = 0; i < proj.size(); i++) { r.setColor(Color.RED);
		 * r.line(proj.get(i).getTargetX(), proj.get(i).getTargetY(),
		 * proj.get(i).getX(), proj.get(i).getY()); r.setColor(Color.BLUE);
		 * r.line(player.getX() + player.getWidth() / 2, player.getY() +
		 * player.getHeight() / 2, proj.get(i).getX(), proj.get(i).getY()); }
		 * 
		 * r.end();
		 */
	}

	public Powerup spawnPowerUp(GameWorld world, Core game) {
		System.out.println("Spawning powerup");
		float x = MathUtils.random(world.getMinimumX(), world.getmaximumX());
		float y = MathUtils.random(world.getMinimumY(), world.getMaximumY());
		for (int i = 0; i < borders.size; i++) {
			if (borders.get(i).getRectangle().contains(x, y)) {
				boolean overlaps = true;
				while (overlaps) {
					if (borders.get(i).getRectangle().contains(x, y)) {
						x++;
						y++;
					} else {
						overlaps = false;
					}
				}

			}
		}
		for (int i = 0; i < spawnPoints.size; i++) {
			if (spawnPoints.get(i).getRectangle().contains(x, y)) {
				boolean overlaps = true;
				while (overlaps) {
					if (spawnPoints.get(i).getRectangle().contains(x, y)) {
						x++;
						y--;
					} else {
						overlaps = false;
					}
				}
			}
		}
		Powerup powerup = new Powerup(16, 16, x, y, 0f, 0f, game);

		powerup.setTypeAndGraphic(game);
		powerup.setTimeAlive(TimeUtils.millis());
		ParticleEffect effect = powerup.getSpawnEffect();
		effect.start();
		effects.add(effect);
		return powerup;

	}

	public void dispose() {
		// game.batch.dispose();
		stage.dispose();
		// mapRender.dispose();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

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
	public void hide() {
		// TODO Auto-generated method stub

	}

}