package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	private ArrayList<SpriteCommons> enemyAdd;

	boolean noEnemies;

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
	private Label.LabelStyle labelStyle;
	private Skin mySkin;
	private Pixmap pixmap;
	private ProgressBar healthBar;
	private TextureRegionDrawable drawable;
	private Music ambience;
	private Sound shot;
	private Sound hit;
	private Sound Explosion;
	private Sound GameOver;
	private Sound walk1;
	private Sound walk2;
	private long walkSet;

	private Texture warningTexture;
	private TextureRegion region;
	
	private Image leftCenter;
	private Image rightCenter;
	private Image topCenter;
	private Image bottomCenter;
	
	private Image topLeftCorner;
	private Image topRightCorner;
	private Image bottomLeftCorner;
	private Image bottomRightCorner;

	/**
	 * Initializes the entire game
	 * 
	 * @param game
	 */
	public Updater(final Core game) {
		this.game = game;
		mpObjCooldown = 200;
		mpObjLastSet = 0;
		mapObjects = new ArrayList<MapObject>();
		statetime = 0f;
		walkSet = TimeUtils.millis();
		timesCalled = 0;
		randomizer = new Random();
		world = new GameWorld();
		powerups = new ArrayList<Powerup>();
		monsterSpawns = new Array<RectangleMapObject>();
		enemies = new ArrayList<SpriteCommons>();
		enemyAdd = new ArrayList<SpriteCommons>();
		// When loading textures to project, the entire path to the file should
		// be included
		// be careful with that
		camera = new OrthographicCamera();
		effects = new ArrayList<ParticleEffect>();
		aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera.setToOrtho(false, 250f * aspectRatio, 250f);
		// camera.setToOrtho(false, 700f,700f);

		mapRender = world.getMapRenderer(camera);
		borders = world.getHitboxes();
		spawnPoints = world.getSpawnPoints();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		r = new ShapeRenderer();
		r.setProjectionMatrix(camera.combined);

		game.batch.setProjectionMatrix(camera.combined);
		proj = new ArrayList<Projectile>();

		ambience = game.getLoader().getManager().get("Music/POL-horror-ambience-1-short_16bit.wav", Music.class);
		ambience.setLooping(true);
		ambience.play();

		
		shot = game.getLoader().getManager().get("Sounds/shooting/NFF-gun-miss.wav",Sound.class);
		hit = game.getLoader().getManager().get("Sounds/hit/NFF-slap-02.wav",Sound.class);
		Explosion = game.getLoader().getManager().get("Sounds/hit/NFF-explode.wav",Sound.class);
		GameOver = game.getLoader().getManager().get("Sounds/game_over/NFF-death-bell.wav",Sound.class);
		walk1 = game.getLoader().getManager().get("Sounds/walking/grass1.wav",Sound.class);
		walk2 = game.getLoader().getManager().get("Sounds/walking/gravel1.wav",Sound.class);
		


		// Set initial coordinates from map to player and candypile
		for (int i = 0; i < spawnPoints.size; i++) {
			if (spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Player")) {

				player = new Player(30, 40, spawnPoints.get(i).getRectangle().getX(),

						spawnPoints.get(i).getRectangle().getY(), 10);
			}
			if (spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Pile")) {
				pile = new Pile(87, 62, spawnPoints.get(i).getRectangle().getX(),
						spawnPoints.get(i).getRectangle().getY(),

						game.getLoader().getManager().get("CPBigCrop.png", Texture.class),
						game.getLoader().getManager().get("CPMedCrop.png", Texture.class),
						game.getLoader().getManager().get("CPSmallCrop.png", Texture.class),
						game.getLoader().getManager().get("CPTinyCrop.png", Texture.class));

			}
		}
		for (int i = 0; i < spawnPoints.size; i++) {
			if (spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Enemy")) {
				monsterSpawns.add(spawnPoints.get(i));
			}
		}


		player.setAnimations(8, 3, 0.10f, game.getLoader().getManager().get("PirateTileset.png", Texture.class));

		player.setDir(DIRECTION.DOWN);
		
	
		
		camera.position.set(player.getX(), player.getY(), 0);

		enemies = new ArrayList<SpriteCommons>();
		// spawnEnemies();
		camera.update();
		timeToNextPowerup = TimeUtils.millis();

		// For score points
		timeScore = TimeUtils.millis();


		mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));


		scores = new Label("Score: " + game.getLoader().getScore(), mySkin);
		scores.setPosition(Gdx.graphics.getWidth() / 1.37f, Gdx.graphics.getHeight() - 20);

		scores.setAlignment(Align.topRight);
		scores.setWidth(Gdx.graphics.getWidth() / 4);
		stage.addActor(scores);
		

		// Health bar
		drawable = new TextureRegionDrawable(
				new TextureRegion(game.getLoader().getManager().get("HealthBarBackGround.png", Texture.class)));
		healthBar = new ProgressBar(0f, 1f, 0.01f, false, new ProgressBarStyle());
		healthBar.getStyle().background = drawable;

		pixmap = new Pixmap(0, 12, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		healthBar.getStyle().knob = drawable;
		pixmap.dispose();

		drawable = new TextureRegionDrawable(
				new TextureRegion(game.getLoader().getManager().get("HealthBar.png", Texture.class)));
		healthBar.getStyle().knobBefore = drawable;

		healthBar.setWidth(120);
		healthBar.setHeight(12);
		// healthBar.setAnimateDuration(1f);
		healthBar.setValue(1f);
		// healthBar.setAnimateDuration(0.25f);
		healthBar.setPosition(Gdx.graphics.getWidth() / 100f, Gdx.graphics.getHeight() - 20);

		stage.addActor(healthBar);

		// No enemies overlap player hitbox
		noEnemies = true;
		timeSinceWave = TimeUtils.millis() - 8000;

		warningTexture = game.getLoader().getManager().get("warning2.png", Texture.class);
		region = new TextureRegion(warningTexture, 7, 36);
		
		leftCenter = new Image(region);
		leftCenter.setPosition(Gdx.graphics.getWidth()/100f,Gdx.graphics.getHeight()/2);
		leftCenter.setVisible(false);
		stage.addActor(leftCenter);

		rightCenter = new Image(region);
		rightCenter.setPosition(Gdx.graphics.getWidth()/1.02f,Gdx.graphics.getHeight()/2);
		rightCenter.setVisible(false);
		stage.addActor(rightCenter);
		
		topCenter = new Image(region);
		topCenter.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/1.11f);
		topCenter.setVisible(false);
		stage.addActor(topCenter);
		
		bottomCenter = new Image(region);
		bottomCenter.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/100f);
		bottomCenter.setVisible(false);
		stage.addActor(bottomCenter);
		
		topLeftCorner = new Image(region);
		topLeftCorner.setPosition(Gdx.graphics.getWidth()/100f,Gdx.graphics.getHeight()/1.19f);
		topLeftCorner.setVisible(false);
		stage.addActor(topLeftCorner);
		
		topRightCorner = new Image(region);
		topRightCorner.setPosition(Gdx.graphics.getWidth()/1.02f,Gdx.graphics.getHeight()/1.11f);
		topRightCorner.setVisible(false);
		stage.addActor(topRightCorner);
		
		bottomLeftCorner = new Image(region);
		bottomLeftCorner.setPosition(Gdx.graphics.getWidth()/100f,Gdx.graphics.getHeight()/100f);
		bottomLeftCorner.setVisible(false);
		stage.addActor(bottomLeftCorner);
		
		bottomRightCorner = new Image(region);
		bottomRightCorner.setPosition(Gdx.graphics.getWidth()/1.02f,Gdx.graphics.getHeight()/100f);
		bottomRightCorner.setVisible(false);
		stage.addActor(bottomRightCorner);
		
		
	}

	// Spawn enemies. Enemy count increases by one every time to make the wave
	// stronger.
	private void spawnEnemies() {
		// System.out.println("SPAWNING ENEMIES");
		timesCalled++;

		int tmp;
		int tmp2;
		if (enemies.size() == 0) {
			for (int i = 0; i < timesCalled; i++) {
				tmp = MathUtils.random(0, monsterSpawns.size - 1);
				tmp2 = MathUtils.random(0, monsterSpawns.size - 1);
				// if(tmp3 == 1) {

				/*
				 * enemyAdd.add(new StealingEnemy(32, 32,
				 * monsterSpawns.get(tmp).getRectangle().getX(),
				 * monsterSpawns.get(tmp).getRectangle().getY(), 1,
				 * game.getLoader().getManager().get( "stealTest.png", Texture.class)));
				 */

				// new stealer
				StealingEnemy tmpSE = new StealingEnemy(30, 40, monsterSpawns.get(tmp).getRectangle().getX(),
						monsterSpawns.get(tmp).getRectangle().getY(), 1);

				enemyAdd.add(tmpSE);


				tmpSE.setAnimations(4, 3, 0.10f, game.getLoader().getManager()
						.get("SkeletonTileset.png", Texture.class));


				// }else {

				enemyAdd.add(new ChaserEnemy(21, 32, monsterSpawns.get(tmp2).getRectangle().getX(),
	monsterSpawns.get(tmp2).getRectangle().getY(), 2, game.getLoader().getManager()
								.get("chaserTest.png", Texture.class)));


				// }

			}
		}

		// Check if spawn in same coordinates, if yes then timeout
		for (int j = 0; j < enemyAdd.size(); j++) {
			for (int k = j + 1; k < enemyAdd.size(); k++) {
				if ((enemyAdd.get(j).getX() == enemyAdd.get(k).getX())
						&& (enemyAdd.get(j).getY() == enemyAdd.get(k).getY())) {
					tmp = MathUtils.random(30, 100);
					enemyAdd.get(j).setTimeoutTimer(tmp);
					if (j >= 1) {
						while (((enemyAdd.get(j).getTimeoutTimer() - enemyAdd.get(k).getTimeoutTimer()) >= -20)
								&& ((enemyAdd.get(j).getTimeoutTimer() - enemyAdd.get(k).getTimeoutTimer()) <= 20)) {
							tmp = MathUtils.random(30, 100);
							enemyAdd.get(k).setTimeoutTimer(tmp);
						}
					}
				}
			}
		}

		// If timeout same or close to the other enemy in same coordinates, pick a new
		// timeout
		// for (int j = 0; j < enemyAdd.size(); j++) {
		// for (int k = j + 1; k < enemyAdd.size(); k++) {
		// if((enemyAdd.get(j).getX() == enemyAdd.get(k).getX()) &&
		// (enemyAdd.get(j).getY() == enemyAdd.get(k).getY())) {
		// while(((enemyAdd.get(j).getTimeoutTimer() -
		// enemyAdd.get(k).getTimeoutTimer()) >= -20)
		// && ((enemyAdd.get(j).getTimeoutTimer() - enemyAdd.get(k).getTimeoutTimer())
		// <= 20)) {
		// tmp = MathUtils.random(30, 100);
		// enemyAdd.get(k).setTimeoutTimer(tmp);
		//
		// }
		// }
		// }
		// }

		for (int i = 0; i < enemyAdd.size(); i++) {
			enemies.add(enemyAdd.get(i));
		}
		enemyAdd.clear();
		// System.out.println(enemies.size());

		// Set The direction of stealers to pile
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) instanceof StealingEnemy) {
				double hypot = Math.hypot(enemies.get(i).getX() - pile.getX() + (pile.getWidth() / 2),
						enemies.get(i).getY() - pile.getY() + (pile.getHeight() / 2));
				// TEST SPEED 3! was 1.2
				enemies.get(i).setxVel(
						((float) (1.2f / hypot * (pile.getX() + (pile.getWidth() / 2) - enemies.get(i).getX()))));
				enemies.get(i).setyVel(
						((float) (1.2f / hypot * (pile.getY() + (pile.getHeight() / 2) - enemies.get(i).getY()))));
			}
		}
	}

	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// walking sounds

		if (TimeUtils.timeSinceMillis(walkSet) >250 && player.getxVel() > 0 && player.getyVel() > 0) {
			int random = MathUtils.random(0, 1);
			if (random == 0) {
				walk1.play(1);
			} else {
				walk2.play(1);
			}
			walkSet = TimeUtils.millis();
		}
		
		if( TimeUtils.timeSinceMillis(player.getLastPreviousSet())>200) {
		
			player.setPreviousX(player.getX());
			player.setPreviousY(player.getY());
			player.setLastPreviousSet(TimeUtils.millis());	
		}

		// Move the player
		// moves hitbox first to check if collision

		player.moveHitbox(player.getX() + player.getxVel(), player.getY() + player.getyVel());

		// checks if there is a enemy where the player is moving
		for (int j = 0; j < enemies.size(); j++) {

			if (Intersector.overlaps(player.getHitbox(), enemies.get(j).getHitbox())) {

				noEnemies = false;
				break;
			}

		}

		// Player Collisions with borders
		for (int i = 0; i < borders.size; i++) {
			if (Intersector.overlaps(borders.get(i).getRectangle(), player.getHitbox())) {

				player.setxVel(0);
				player.setyVel(0);
				player.updateHitbox();
			}
		}

		if (Intersector.overlaps(player.getHitbox(), pile.getHitbox()) || noEnemies == false) {

			// pulls the hitbox back
			player.updateHitbox();

		} else {
			// Move the player
			player.setX(player.getX() + player.getxVel());
			player.setY(player.getY() + player.getyVel());

			// Tar trail behind the player
			if (player.getPowerupType() == POWERUPTYPE.SLOWDOWN
					&& TimeUtils.timeSinceMillis(mpObjLastSet) > mpObjCooldown) {

				MapObject obj = new MapObject(32, 32, player.getX(), player.getY(), 0, 0, 10000,

						game.getLoader().getManager().get(
								"tarstain.png", Texture.class),

						OBJECTTYPE.HAZARD);

				obj.setSpawnTime(TimeUtils.millis());
				mapObjects.add(obj);
				mpObjLastSet = TimeUtils.millis();
				System.out.println("SPAWNING TAR");
			}

		}

		noEnemies = true;

		// ALL ENEMY STUFF
		for (int i = 0; i < enemies.size(); i++) {
			double hypot = Math.hypot(enemies.get(i).getX() - player.getPreviousX(), enemies.get(i).getY() - player.getPreviousY());
			//topleftcorner
			if (hypot > Gdx.graphics.getWidth()/3 && enemies.get(i).getX() < player.getPreviousX() && enemies.get(i).getY() > player.getPreviousY()) {
				topLeftCorner.setVisible(true);
				if(enemies.get(i).getY()-player.getY() < Gdx.graphics.getWidth()/6) {
					topLeftCorner.setVisible(false);
					
					leftCenter.setVisible(true);
				}
				if(player.getPreviousX() - enemies.get(i).getX() < Gdx.graphics.getWidth()/6) {
					topLeftCorner.setVisible(false);
					
					topCenter.setVisible(true);
				}
			} else {
				topLeftCorner.setVisible(false);
				leftCenter.setVisible(false);
				topCenter.setVisible(false);
			}
			//topRightCorner
			if (hypot > Gdx.graphics.getWidth()/3 && enemies.get(i).getX() > player.getPreviousX() && enemies.get(i).getY() > player.getPreviousY()) {
				topRightCorner.setVisible(true);
				if(enemies.get(i).getY()-player.getPreviousY() < Gdx.graphics.getWidth()/6) {
					topRightCorner.setVisible(false);
					
					rightCenter.setVisible(true);
				}
				if(enemies.get(i).getX() - player.getPreviousX() < Gdx.graphics.getWidth()/6) {
					topRightCorner.setVisible(false);
					
					topCenter.setVisible(true);
				}
				
			} else {
				topRightCorner.setVisible(false);
				rightCenter.setVisible(false);
				topCenter.setVisible(false);
			}
			//bottomLeftCorner
			if (hypot > Gdx.graphics.getWidth()/3 && enemies.get(i).getX() < player.getPreviousX() && enemies.get(i).getY() < player.getPreviousY()) {
				bottomLeftCorner.setVisible(true);
				if(player.getY() - enemies.get(i).getY() < Gdx.graphics.getWidth()/6) {
					bottomLeftCorner.setVisible(false);
					leftCenter.setVisible(true);
				}
			} else {
				bottomLeftCorner.setVisible(false);
				leftCenter.setVisible(false);
			}
			//bottomRightCorner
			if (hypot > Gdx.graphics.getWidth()/3 && enemies.get(i).getX() > player.getPreviousX() && enemies.get(i).getY() < player.getPreviousY()) {
				bottomRightCorner.setVisible(true);
				if(player.getY() - enemies.get(i).getY() < Gdx.graphics.getWidth()/6) {
					bottomRightCorner.setVisible(false);
					rightCenter.setVisible(true);
				}
			} else {
				bottomRightCorner.setVisible(false);
				rightCenter.setVisible(false);
			}
//			if(hypot > Gdx.graphics.getWidth()/4) {
//				if(enemies.get(i).getY() - player.getY() > Gdx.graphics.getWidth()/4 && enemies.get(i).getX() - player.getX() < Gdx.graphics.getWidth()/4) {
//					topCenter.setVisible(true);
//				}else {
//					topCenter.setVisible(false);
//				}
//			}
			// checks if the enemy is on timeout and does nothing if is
			if (enemies.get(i).getTimeoutTimer() == 0) {

				// Calculates enemy velocities
				if (enemies.get(i) instanceof ChaserEnemy) {
					hypot = Math.hypot(enemies.get(i).getX() - player.getPreviousX(), enemies.get(i).getY() - player.getPreviousY());

					enemies.get(i).setxVel(((float) (1.5f / hypot * (player.getPreviousX() - enemies.get(i).getX()))));
					enemies.get(i).setyVel(((float) (1.5f / hypot * (player.getPreviousY() - enemies.get(i).getY()))));
				}

				if (enemies.get(i) instanceof StealingEnemy) {

					hypot = Math.hypot(enemies.get(i).getX() - pile.getX() + (pile.getWidth() / 2),
							enemies.get(i).getX() - pile.getY() + (pile.getHeight() / 2));
					// TEST SPEED 3! was 1.2
					enemies.get(i).setxVel(
							((float) (1.2f / hypot * (pile.getX() + (pile.getWidth() / 2) - enemies.get(i).getX()))));
					enemies.get(i).setyVel(
							((float) (1.2f / hypot * (pile.getY() + (pile.getHeight() / 2) - enemies.get(i).getY()))));
					// Maby correct direction to stealer?

				}

				// SLOW THE ENEMIES DOWN IF ONE OF THEM HITS A POOL OF TAR
				if (!mapObjects.isEmpty()) {

					for (int j = 0; j < mapObjects.size(); j++) {
						if (mapObjects.get(j).getHitbox().contains(enemies.get(i).getX(), enemies.get(i).getY())) {
							System.out.println("ENEMY AT: " + i + " IS STUCK IN TAR");

							enemies.get(i).setxVel(enemies.get(i).getxVel() / 100);
							enemies.get(i).setyVel(enemies.get(i).getyVel() / 100);

						}

					}
				}

				// enemies.get(i).updateHitbox();

				// move hitbox first to check if collisions
				enemies.get(i).moveHitbox(enemies.get(i).getX() + enemies.get(i).getxVel(),
						enemies.get(i).getY() + enemies.get(i).getyVel());

				// Enemy collisions with each other
				for (int k = i + 1; k < enemies.size(); k++) {
					if (Intersector.overlaps(enemies.get(i).getHitbox(), enemies.get(k).getHitbox())) {
						int tmp = MathUtils.random(30, 100);
						enemies.get(i).setTimeoutTimer(tmp);
						enemies.get(k).setTimeoutTimer(0);
					}
				}

				if (Intersector.overlaps(enemies.get(i).getHitbox(), player.getHitbox())) {

					// pulls the hitbox back
					// enemies.get(i).updateHitbox();

					// damages player if no shield
					if (player.getPowerupType() != POWERUPTYPE.SHIELD) {
						player.setHP(player.getHP() - 1);
						// Update healthbar
						healthBar.setValue(healthBar.getValue() - 0.1f);
					}

					// enemy is on a timeout after attacking, randomly selected 100 frames
					// timeoutTimer atm
					enemies.get(i).setTimeoutTimer(100);

					/*
					 * timeoutTimer can replace this
					 * 
					 * enemies.get(i).setHP(enemies.get(i).getHP() - 1);
					 * 
					 * if (enemies.get(i).getHP() <= 0) { // Get points if (enemies.get(i)
					 * instanceof StealingEnemy) {
					 * game.getLoader().setScore(game.getLoader().getScore() + 500); } if
					 * (enemies.get(i) instanceof ChaserEnemy) {
					 * game.getLoader().setScore(game.getLoader().getScore() + 1000); }
					 * enemies.remove(i); }
					 */

					if (player.getHP() < 1) {
						System.out.println("Player HP now zero");
						GameOver.play();
						ambience.stop();
						game.setScreen(new LoadingScreen(game));
						this.dispose();
					}

					// checks if collides with the candy pile
				} else if (Intersector.overlaps(enemies.get(i).getHitbox(), pile.getHitbox())) {

					// steal from the pile
					if (enemies.get(i) instanceof StealingEnemy) {

						if (Intersector.overlaps((enemies.get(i).getHitbox()), pile.getHitbox())) {

							pile.reduceHealth();
							enemies.remove(i);

						}

					} else {

						// "pathfinding" around the pile (probably badly optimized)

						if ((player.getPreviousY() - enemies.get(i).getY() > 0)) { // player is up

							enemies.get(i).moveHitbox(enemies.get(i).getX(), enemies.get(i).getY() + 1.2f);

							// check if up is clear
							if (!(Intersector.overlaps(enemies.get(i).getHitbox(), pile.getHitbox()))) {

								// move enemy up
								enemies.get(i).setY(enemies.get(i).getY() + 1.2f);

							} else { // up is blocked -> right or left

								if (player.getPreviousX() - enemies.get(i).getX() > 0) { // player is right and up

									// move enemy right
									enemies.get(i).setX(enemies.get(i).getX() + 1.2f);

								} else { // player is left and up

									// move enemy left
									enemies.get(i).setX(enemies.get(i).getX() - 1.2f);

								}

							}

						} else { // player is down

							enemies.get(i).moveHitbox(enemies.get(i).getX(), enemies.get(i).getY() - 1.2f);

							// check if down is clear
							if (!(Intersector.overlaps(enemies.get(i).getHitbox(), pile.getHitbox()))) {

								// move enemy down
								enemies.get(i).setY(enemies.get(i).getY() - 1.2f);

							} else { // down is blocked -> right or left

								if (player.getPreviousX() - enemies.get(i).getX() > 0) { // player is right and down

									// move enemy right
									enemies.get(i).setX(enemies.get(i).getX() + 1.2f);

								} else { // player is left and down

									// move enemy left
									enemies.get(i).setX(enemies.get(i).getX() - 1.2f);

								}

							}

						}

					}

					// enemies.get(i).updateHitbox();

				} else {
					// Enemy collisions with borders


				
					
						for(int k = 0; k<borders.size;k++){
				
							
							if(Intersector.overlaps(borders.get(k).getRectangle(), enemies.get(i).getHitbox())) {
								
								//enemies.get(i).setxVel(0);
								//enemies.get(i).setyVel(0);
								/*
								
							if(borders.get(k).getRectangle().getX() + borders.get(k).getRectangle().getWidth() < enemies.get(i).getX()) {
								
								
								enemies.get(i).setX(borders.get(k).getRectangle().getX() + borders.get(k).getRectangle().getWidth() + 0.5f);
								
								
								if(enemies.get(i).getY() + enemies.get(i).getHeight()< borders.get(k).getRectangle().getY() ) { // Down and Right
									enemies.get(i).setY(borders.get(k).getRectangle().getY() - enemies.get(k).getHeight() - 0.5f);
									
									
								}else if(enemies.get(i).getY()> borders.get(k).getRectangle().getY() + borders.get(k).getRectangle().getHeight()) { // up and right 
									enemies.get(i).setY(borders.get(k).getRectangle().getY() + borders.get(k).getRectangle().getHeight() +  0.5f);
									
									
								}
								
								
								
							}
							if(borders.get(k).getRectangle().getX()>enemies.get(i).getX() + enemies.get(i).getWidth()) { // The enemy is left
							
								enemies.get(i).setX(borders.get(k).getRectangle().getX() - enemies.get(i).getWidth() - 0.5f);
								
								if(enemies.get(i).getY() + enemies.get(i).getHeight()< borders.get(k).getRectangle().getY() ) { //Down and left
									enemies.get(i).setY(borders.get(k).getRectangle().getY() - enemies.get(i).getHeight() -  0.5f);
									
									
								}else if(enemies.get(i).getY()> borders.get(k).getRectangle().getY() + borders.get(k).getRectangle().getHeight()) { // up and left
									
									enemies.get(i).setY(borders.get(k).getRectangle().getY() + borders.get(k).getRectangle().getHeight() +  0.5f);
									
								}
								
							}
							if(borders.get(k).getRectangle().getY() + borders.get(k).getRectangle().getHeight() < enemies.get(i).getY()) {	// The enemy is up
								System.out.println("ENEMY COLLIDING UP");
								
								enemies.get(i).setY(borders.get(k).getRectangle().getY() + borders.get(k).getRectangle().getHeight() +  0.5f);
								
								if(enemies.get(i).getX() + enemies.get(i).getWidth()< borders.get(k).getRectangle().getX() ) { // up and left
									enemies.get(i).setX(borders.get(k).getRectangle().getX() - enemies.get(i).getWidth() - 0.5f);
								}else if(enemies.get(i).getX()> borders.get(k).getRectangle().getX() + borders.get(k).getRectangle().getWidth()) { // up and right 
									enemies.get(i).setX(borders.get(k).getRectangle().getX() + borders.get(k).getRectangle().getWidth() + 0.5f);
								}
								
							}
							if(borders.get(k).getRectangle().getY()>enemies.get(i).getY() + enemies.get(i).getHeight()) {// The enemy is down
								System.out.println("ENEMY COLLIDING DOWN");
								enemies.get(i).setY(borders.get(k).getRectangle().getY() - enemies.get(i).getHeight() -  0.5f);
								
								if(enemies.get(i).getX() + enemies.get(i).getWidth()< borders.get(k).getRectangle().getX() ) { // Down and left
									enemies.get(i).setX(borders.get(k).getRectangle().getX() - enemies.get(i).getWidth() - 0.5f);
								}else if(enemies.get(i).getX()> borders.get(k).getRectangle().getX() + borders.get(k).getRectangle().getWidth()) { // Down and right 
									enemies.get(i).setX(borders.get(k).getRectangle().getX() + borders.get(k).getRectangle().getWidth() + 0.5f);
								}
								
							
								
							}
							
							
							*/
								
								if(enemies.get(i) instanceof ChaserEnemy ) {
									// "pathfinding" around the pile (probably badly optimized)

									if ((player.getPreviousY() - enemies.get(i).getY() > 0)) { // player is up

										enemies.get(i).moveHitbox(enemies.get(i).getX(), enemies.get(i).getY() + 1.2f);

										// check if up is clear
										if (!(Intersector.overlaps(enemies.get(i).getHitbox(), borders.get(k).getRectangle()))) {

											// move enemy up
											enemies.get(i).setY(enemies.get(i).getY() + 1.2f);

										} else { // up is blocked -> right or left

											if (player.getPreviousX() - enemies.get(i).getX() > 0) { // player is right and up

												// move enemy right
												enemies.get(i).setX(enemies.get(i).getX() + 1.2f);

											} else { // player is left and up

												// move enemy left
												enemies.get(i).setX(enemies.get(i).getX() - 1.2f);

											}

										}

									} else { // player is down

										enemies.get(i).moveHitbox(enemies.get(i).getX(), enemies.get(i).getY() - 1.2f);

										// check if down is clear
										if (!(Intersector.overlaps(enemies.get(i).getHitbox(), borders.get(k).getRectangle()))) {

											// move enemy down
											enemies.get(i).setY(enemies.get(i).getY() - 1.2f);

										} else { // down is blocked -> right or left

											if (player.getPreviousX() - enemies.get(i).getX() > 0) { // player is right and down

												// move enemy right
												enemies.get(i).setX(enemies.get(i).getX() + 1.2f);

											} else { // player is left and down

												// move enemy left
												enemies.get(i).setX(enemies.get(i).getX() - 1.2f);

											}

										}

									}
										
									
								}
								if(enemies.get(i) instanceof StealingEnemy ) {
									// "pathfinding" around the pile (probably badly optimized)

									if ((pile.getY() - enemies.get(i).getY() > 0)) { // player is up

										enemies.get(i).moveHitbox(enemies.get(i).getX(), enemies.get(i).getY() + 1.2f);

										// check if up is clear
										if (!(Intersector.overlaps(enemies.get(i).getHitbox(), borders.get(k).getRectangle()))) {

											// move enemy up
											enemies.get(i).setY(enemies.get(i).getY() + 1.2f);

										} else { // up is blocked -> right or left

											if (pile.getX() - enemies.get(i).getX() > 0) { // player is right and up

												// move enemy right
												enemies.get(i).setX(enemies.get(i).getX() + 1.2f);

											} else { // player is left and up

												// move enemy left
												enemies.get(i).setX(enemies.get(i).getX() - 1.2f);

											}

										}

									} else { // player is down

										enemies.get(i).moveHitbox(enemies.get(i).getX(), enemies.get(i).getY() - 1.2f);

										// check if down is clear
										if (!(Intersector.overlaps(enemies.get(i).getHitbox(), borders.get(k).getRectangle()))) {

											// move enemy down
											enemies.get(i).setY(enemies.get(i).getY() - 1.2f);

										} else { // down is blocked -> right or left

											if (pile.getX() - enemies.get(i).getX() > 0) { // player is right and down

												// move enemy right
												enemies.get(i).setX(enemies.get(i).getX() + 1.2f);

											} else { // player is left and down

												// move enemy left
												enemies.get(i).setX(enemies.get(i).getX() - 1.2f);

											}

										}

									}
								}
							
							
						
							
							
							
							}
							

				}
					
						// move enemies
						enemies.get(i).setX(enemies.get(i).getX() + enemies.get(i).getxVel());
						enemies.get(i).setY(enemies.get(i).getY() + enemies.get(i).getyVel());
						
						
				}
			} else {

				// reduce the enemy timeoutTimer
				enemies.get(i).setTimeoutTimer(enemies.get(i).getTimeoutTimer() - 1);

			}

			// one hitbox update should be enough (maybe)

			if (!enemies.isEmpty()) {
				if (enemies.size() == i) {
					enemies.get(enemies.size() - 1).updateHitbox();
				} else {
					enemies.get(i).updateHitbox();
				}
			}
		}

		// PLAYER INPUT STUFF
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

		// POWER-UP STUFF
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
									"SHIELD.png", Texture.class),

							OBJECTTYPE.FOLLOWER);

					obj.setSpawnTime(TimeUtils.millis());
					mapObjects.add(obj);
				}

				if (player.getPowerupType() == POWERUPTYPE.CLEARSCREEN) {
					MapObject obj = new MapObject(32, 32, player.getX() - player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, 0, 0, 10000,


							game.getLoader().getManager().get(
									"ScreenClear.png",
									Texture.class),

							OBJECTTYPE.EXPANDER);

					obj.setSpawnTime(TimeUtils.millis());
					mapObjects.add(obj);
					Explosion.play();
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
				player.setShootingCooldown(500);
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
		// Gdx.input.isTouched would be holding down to shoot
		if (Gdx.input.isTouched() && TimeUtils.timeSinceMillis(player.getLastShot()) > player.getShootingCooldown()) {

			player.setAttacking(true);
			player.setAttackAnimStart(TimeUtils.millis());
			// Spawn a projectile with target coordinates and set the time it is
			// visible

			// Single shot
			if (player.getPowerupType() != POWERUPTYPE.TRIPLESHOT) {
				// Convert the cursor coordinates into game world coordinates.
				// Needs to be refined
				Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				Vector3 reaCoords = camera.unproject(v);
				float bulletVel = 5f;
				double hypot = Math.hypot((reaCoords.x - player.getMiddleX()), (reaCoords.y - player.getMiddleY()));

				/*
				 * original attempt float velX = (float) (reaCoords.x - (player.getX() +
				 * (player.getWidth() / 2))) / bulletVel; float velY = (float) (reaCoords.y -
				 * (player.getY() + (player.getHeight() / 2))) / bulletVel;
				 */

				float velX = (float) (bulletVel / hypot * (reaCoords.x - player.getMiddleX()));
				float velY = (float) (bulletVel / hypot * (reaCoords.y - player.getMiddleY()));

				int tmp = 0;
				tmp = MathUtils.random(0, 3);
				shot.play(0.5f);
				if (tmp == 0) {

					Projectile p = new Projectile(15, 15, player.getX() + player.getWidth() / 2,

							player.getY() + player.getHeight() / 2, velX, velY,
							game.getLoader().getManager().get("Carrot.png", Texture.class));

					p.setTargetX(reaCoords.x);
					p.setTargetY(reaCoords.y);
					p.setCurrentTime(TimeUtils.millis());
					proj.add(p);
				}
				if (tmp == 1) {

					Projectile p = new Projectile(15, 15, player.getX() + player.getWidth() / 2,

							player.getY() + player.getHeight() / 2, velX, velY,
							game.getLoader().getManager().get("Tomato.png", Texture.class));

					p.setTargetX(reaCoords.x);
					p.setTargetY(reaCoords.y);
					p.setCurrentTime(TimeUtils.millis());
					proj.add(p);
				}
				if (tmp == 2) {

					Projectile p = new Projectile(15, 15, player.getX() + player.getWidth() / 2,

							player.getY() + player.getHeight() / 2, velX, velY,
							game.getLoader().getManager().get("Broccoli.png", Texture.class));

					p.setTargetX(reaCoords.x);
					p.setTargetY(reaCoords.y);
					p.setCurrentTime(TimeUtils.millis());
					proj.add(p);
				}
				if (tmp == 3) {

					Projectile p = new Projectile(15, 15, player.getX() + player.getWidth() / 2,

							player.getY() + player.getHeight() / 2, velX, velY,

							game.getLoader().getManager().get("Carrot.png", Texture.class)); //Eggplant replacement



					p.setTargetX(reaCoords.x);
					p.setTargetY(reaCoords.y);
					p.setCurrentTime(TimeUtils.millis());
					proj.add(p);
				}

				// triple shot
			} else {
				// Convert the cursor coordinates into game world coordinates.
				// Needs to be refined
				Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

				Vector3 reaCoords = camera.unproject(v);

				float bulletVel = 5f;
				double hypot = Math.hypot((reaCoords.x - player.getMiddleX()), (reaCoords.y - player.getMiddleY()));
				// Straight shot
				/*
				 * original attempt float velX = (float) (reaCoords.x - (player.getX() +
				 * (player.getWidth()) / 2)) / bulletVel; float velY = (float) (reaCoords.y -
				 * (player.getY() + (player.getHeight() / 2))) / bulletVel;
				 */

				float velX = (float) (bulletVel / hypot * (reaCoords.x - player.getMiddleX()));
				float velY = (float) (bulletVel / hypot * (reaCoords.y - player.getMiddleY()));

				float velXR = (float) (bulletVel / hypot
						* (reaCoords.x - (Projectile.getSideShots(player, reaCoords)[1].x)));
				float velYR = (float) (bulletVel / hypot
						* (reaCoords.y - (Projectile.getSideShots(player, reaCoords)[1].y)));

				float velXL = (float) (bulletVel / hypot
						* (reaCoords.x - (Projectile.getSideShots(player, reaCoords)[0].x)));
				float velYL = (float) (bulletVel / hypot
						* (reaCoords.y - (Projectile.getSideShots(player, reaCoords)[0].y)));

				// Randomly select the ammo for triple shot
				int tmp = 0;
				tmp = MathUtils.random(0, 3);

				if (tmp == 0) {

					Projectile p = new Projectile(15, 15, player.getX() + player.getWidth() / 2,

							player.getY() + player.getHeight() / 2, velX, velY,
							game.getLoader().getManager().get("Carrot.png", Texture.class)); // Eggplant replacement

					Projectile l = new Projectile(15, 15, player.getX() + player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, velXR, velYR,
							game.getLoader().getManager().get("Carrot.png", Texture.class)); // Eggplant replacement

					Projectile r = new Projectile(15, 15, player.getX() + player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, velXL, velYL,
							game.getLoader().getManager().get("Carrot.png", Texture.class)); // Eggplant replacement


					l.setCurrentTime(TimeUtils.millis());
					p.setCurrentTime(TimeUtils.millis());
					r.setCurrentTime(TimeUtils.millis());

					proj.add(p);
					proj.add(l);
					proj.add(r);
				}
				if (tmp == 1) {

					Projectile p = new Projectile(15, 15, player.getX() + player.getWidth() / 2,

							player.getY() + player.getHeight() / 2, velX, velY,
							game.getLoader().getManager().get("Carrot.png", Texture.class));

					Projectile l = new Projectile(15, 15, player.getX() + player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, velXR, velYR,
							game.getLoader().getManager().get("Carrot.png", Texture.class));

					Projectile r = new Projectile(15, 15, player.getX() + player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, velXL, velYL,
							game.getLoader().getManager().get("Carrot.png", Texture.class));


					l.setCurrentTime(TimeUtils.millis());
					p.setCurrentTime(TimeUtils.millis());
					r.setCurrentTime(TimeUtils.millis());

					proj.add(p);
					proj.add(l);
					proj.add(r);
				}
				if (tmp == 2) {

					Projectile p = new Projectile(15, 15, player.getX() + player.getWidth() / 2,

							player.getY() + player.getHeight() / 2, velX, velY,
							game.getLoader().getManager().get("Tomato.png", Texture.class));

					Projectile l = new Projectile(15, 15, player.getX() + player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, velXR, velYR,
							game.getLoader().getManager().get("Tomato.png", Texture.class));

					Projectile r = new Projectile(15, 15, player.getX() + player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, velXL, velYL,
							game.getLoader().getManager().get("Tomato.png", Texture.class));


					l.setCurrentTime(TimeUtils.millis());
					p.setCurrentTime(TimeUtils.millis());
					r.setCurrentTime(TimeUtils.millis());

					proj.add(p);
					proj.add(l);
					proj.add(r);
				}
				if (tmp == 3) {
					Projectile p = new Projectile(15, 15, player.getX() + player.getWidth() / 2,

							player.getY() + player.getHeight() / 2, velX, velY,
							game.getLoader().getManager().get("Broccoli.png", Texture.class));

					Projectile l = new Projectile(15, 15, player.getX() + player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, velXR, velYR,
							game.getLoader().getManager().get("Broccoli.png", Texture.class));

					Projectile r = new Projectile(15, 15, player.getX() + player.getWidth() / 2,
							player.getY() + player.getHeight() / 2, velXL, velYL,
							game.getLoader().getManager().get("Broccoli.png", Texture.class));

					l.setCurrentTime(TimeUtils.millis());
					p.setCurrentTime(TimeUtils.millis());
					r.setCurrentTime(TimeUtils.millis());

					proj.add(p);
					proj.add(l);
					proj.add(r);
				}
				shot.play(0.5f);

			}

			player.setLastShot(TimeUtils.millis());
		}

		if (TimeUtils.timeSinceMillis(player.getAttackAnimStart()) > 500) {

			player.setAttacking(false);
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

				// attempt to correct the direction of each projectile
				// Move the projectile according to its x and y velocities

				proj.get(i).setX(proj.get(i).getX() + proj.get(i).getxVel());
				proj.get(i).setY(proj.get(i).getY() + proj.get(i).getyVel());

			}
		}

		for (int i = 0; i < enemies.size(); i++) {

			for (int j = 0; j < proj.size(); j++) {

				if (Intersector.overlaps(proj.get(j).getHitbox(), enemies.get(i).getHitbox())
						&& TimeUtils.timeSinceMillis(proj.get(j).getCurrentTime()) < 4000) {
					hit.play();
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
				}else if(Intersector.overlaps(proj.get(j).getHitbox(), pile.getHitbox())) {
					proj.remove(j);
				}
			}
		}
		// If enemy and player touch they both lose HP
		// Teppo moved this elsewhere
		/*
		 * for (int i = 0; i < enemies.size(); i++) { if
		 * (Intersector.overlaps(player.getHitbox(), enemies.get(i).getHitbox()) &&
		 * enemies.get(i).getHP() > 0 && player.getHP() > 0) {
		 * 
		 * if (player.getPowerupType() != POWERUPTYPE.SHIELD) {
		 * player.setHP(player.getHP() - 1); // Update healthbar
		 * healthBar.setValue(healthBar.getValue() - 0.1f); }
		 * enemies.get(i).setHP(enemies.get(i).getHP() - 1);
		 * 
		 * if (enemies.get(i).getHP() <= 0) { // Get points if (enemies.get(i)
		 * instanceof StealingEnemy) {
		 * game.getLoader().setScore(game.getLoader().getScore() + 500); } if
		 * (enemies.get(i) instanceof ChaserEnemy) {
		 * game.getLoader().setScore(game.getLoader().getScore() + 1000); }
		 * enemies.remove(i); } if (player.getHP() < 1) {
		 * System.out.println("Player HP now zero");
		 * 
		 * game.setScreen(new LoadingScreen(game)); this.dispose(); } } }
		 */

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
		if(!effects.isEmpty()) {
			for(int i = 0; i<effects.size();i++) {
				effects.get(i).update(statetime);
			}
		}

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		//Draw powerups
		
		
		

		if (!powerups.isEmpty()) {
			for (int i = 0; i < powerups.size(); i++) {

				Powerup powerup = powerups.get(i);

				if (TimeUtils.timeSinceMillis(powerup.getTimeAlive()) < 9000) {
					game.batch.draw(powerup.getGraphic(), powerup.getX(), powerup.getY(), powerup.getWidth(),
							powerup.getHeight());

				} else {

					powerups.remove(i);
				}

			}
		}
		// Draw pile; four health states
		if (pile.getHealth() > 6) {

			game.batch.draw(pile.getPileTexture(), pile.getX(), pile.getY());
			pile.setHeight(49);

		} else if (pile.getHealth() > 4) {

			game.batch.draw(pile.getPileTexture2(), pile.getX(), pile.getY());
			pile.setHeight(38);

		} else if (pile.getHealth() > 2) {

			game.batch.draw(pile.getPileTexture3(), pile.getX(), pile.getY());
			pile.setHeight(27);

		} else if (pile.getHealth() > 0) {

			game.batch.draw(pile.getPileTexture3(), pile.getX(), pile.getY());
			pile.setHeight(27);

		} else if (pile.getHealth() == 0) {

			System.out.println("The whole pile was stolen");
			GameOver.play();
			ambience.stop();
			game.setScreen(new LoadingScreen(game));
			this.dispose();

		}
		if (!mapObjects.isEmpty()) {
			for (int i = 0; i < mapObjects.size(); i++) {
				// Draw Follower Object
				if (mapObjects.get(i).getType() == OBJECTTYPE.FOLLOWER) {
					game.batch.draw(mapObjects.get(i).getGraphic(), player.getX(), player.getY(),
							mapObjects.get(i).getWidth(), mapObjects.get(i).getHeight());

					// Draw expander
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
		// DRAW ENEMIES
		for (int i = 0; i < enemies.size(); i++) {

			// STEALER

			if (enemies.get(i) instanceof StealingEnemy) {

				// game.batch.draw(((StealingEnemy) enemies.get(i)).getTexture(),
				// enemies.get(i).getX(),enemies.get(i).getY());

				game.batch.draw(enemies.get(i).getCurrentFrame(statetime), enemies.get(i).getX(), enemies.get(i).getY(),
						enemies.get(i).getWidth(), enemies.get(i).getHeight());

				// CHASER
			} else if (enemies.get(i) instanceof ChaserEnemy) {
				game.batch.draw(((ChaserEnemy) enemies.get(i)).getTexture(), enemies.get(i).getX(),
						enemies.get(i).getY(), enemies.get(i).getWidth(), enemies.get(i).getHeight());
			}

		}

		if (player.isAttacking()) {

			game.batch.draw(player.getCurrentAttackFrame(statetime), player.getX(), player.getY(), player.getWidth(),
					player.getHeight());

		} else {
			game.batch.draw(player.getCurrentFrame(statetime), player.getX(), player.getY(), player.getWidth(),
					player.getHeight());
		}
		for (int i = 0; i < proj.size(); i++) {
			game.batch.draw(proj.get(i).getT(), proj.get(i).getX(), proj.get(i).getY(), proj.get(i).getWidth(),
					proj.get(i).getHeight());
		}
		
	
		


		// Test 1 sec
		if (TimeUtils.timeSinceMillis(timeSinceWave) > 10000 && enemies.isEmpty()) {

			spawnEnemies();
			timeSinceWave = TimeUtils.millis();
		}
		
		
		
		// Update particles in the list FURTHER WORK REQUIRED
		if (!effects.isEmpty()) {
System.out.println("AMOUNT OF EFFECTS CURRENTLY RENDERING: " + effects.size());
			for (int i = 0; i < effects.size(); i++) {

				if (!effects.get(i).isComplete()) {

					
					effects.get(i).draw(game.batch);
				} else {
					effects.get(i).dispose();
					effects.remove(i);
					
					
					
				}
			}

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
		  r.setProjectionMatrix(camera.combined);
		  r.begin(ShapeType.Line);
		  r.setColor(Color.RED); 
		
		  
		  for(int i = 0; i<enemies.size();i++) {
			  r.point(enemies.get(i).getUpProbes().get(0)[0], enemies.get(i).getUpProbes().get(0)[1], 0);
			  r.point(enemies.get(i).getUpProbes().get(1)[0], enemies.get(i).getUpProbes().get(1)[1], 0);
			  r.point(enemies.get(i).getUpProbes().get(2)[0], enemies.get(i).getUpProbes().get(2)[1], 0);
			  
			  r.point(enemies.get(i).getDownProbes().get(0)[0], enemies.get(i).getDownProbes().get(0)[1], 0);
			  r.point(enemies.get(i).getDownProbes().get(1)[0], enemies.get(i).getDownProbes().get(1)[1], 0);
			  r.point(enemies.get(i).getDownProbes().get(2)[0], enemies.get(i).getDownProbes().get(2)[1], 0);

			  r.point(enemies.get(i).getLeftProbes().get(0)[0], enemies.get(i).getLeftProbes().get(0)[1], 0);
			  r.point(enemies.get(i).getLeftProbes().get(1)[0], enemies.get(i).getLeftProbes().get(1)[1], 0);
			  r.point(enemies.get(i).getLeftProbes().get(2)[0], enemies.get(i).getLeftProbes().get(2)[1], 0);
			  
			  r.point(enemies.get(i).getRightProbes().get(0)[0], enemies.get(i).getRightProbes().get(0)[1], 0);
			  r.point(enemies.get(i).getRightProbes().get(1)[0], enemies.get(i).getRightProbes().get(1)[1], 0);
			  r.point(enemies.get(i).getRightProbes().get(2)[0], enemies.get(i).getRightProbes().get(2)[1], 0);
			  
			 
			  
			  
		  }
		r.setColor(Color.GREEN);
		for(int i = 0; i<borders.size;i++) {
			r.rect(borders.get(i).getRectangle().getX(), borders.get(i).getRectangle().getY(), borders.get(i).getRectangle().getWidth(), borders.get(i).getRectangle().getHeight());
			
		}
		  
		  r.end();
		 */
	}

	public Powerup spawnPowerUp(GameWorld world, Core game) {
System.out.println("SPAWNING POWERUP");
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
		// Instatiating powerup

		Powerup powerup = new Powerup(32, 32, x, y, 0f, 0f, game);

		powerup.setTypeAndGraphic(game);
		powerup.setTimeAlive(TimeUtils.millis());
		ParticleEffect effect = powerup.getSpawnEffect();

		effect.start();
		effects.add(effect);
		System.out.println("Returning a new powerup");
		return powerup;

	}
	
	public void dispose() {
		// game.batch.dispose();
		// stage.dispose();
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