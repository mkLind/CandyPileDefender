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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.Player.DIRECTION;
/*
 * The class that actually makes the game visible
 */
public class Updater implements Screen {
	
	//Texture img;
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
	private GameWorld world;
	private OrthogonalTiledMapRenderer mapRender;
	private Array<RectangleMapObject> borders;
	private Array<RectangleMapObject> spawnPoints;
	private Array<RectangleMapObject> monsterSpawns;

	
	/**
	 * Initializes the entire game
	 * @param game
	 */
	public Updater(final Core game) {
		this.game = game;
		statetime = 0f;
		timesCalled = 0;
		world = new GameWorld();
		monsterSpawns = new Array<RectangleMapObject>();
		enemies = new ArrayList<SpriteCommons>();
		// When loading textures to project, the entire path to the file should be included
		// be careful with that
		camera = new OrthographicCamera();
	
		aspectRatio =  (float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight();
		camera.setToOrtho(false,250f*aspectRatio, 250f);
	//	camera.setToOrtho(false, 700f,700f);
		
		mapRender = world.getMapRenderer(camera);
		borders = world.getHitboxes();
		spawnPoints = world.getSpawnPoints();
		stage = new Stage();
		r = new ShapeRenderer();
		r.setProjectionMatrix(camera.combined);
		
		game.batch.setProjectionMatrix(camera.combined);
		proj = new ArrayList<Projectile>();
		


		// Set initial coordinates from map to player and candypile
		for(int i = 0; i < spawnPoints.size;i++){
			if(spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Player")){
				player = new Player(32, 32, spawnPoints.get(i).getRectangle().getX(), spawnPoints.get(i).getRectangle().getY(), 5);
			}
			if(spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Pile")){
				pile = new Pile(100, 100, spawnPoints.get(i).getRectangle().getX(), spawnPoints.get(i).getRectangle().getY(), game.getLoader().getManager().get("pileTest.png", Texture.class),  game.getLoader().getManager().get("pileTest2.png", Texture.class));
			}
		}
		for(int i = 0; i<spawnPoints.size;i++){
			if(spawnPoints.get(i).getProperties().get("Spawnpoint").toString().equals("Enemy")){
				monsterSpawns.add(spawnPoints.get(i));
			}
		}
				
		
		player.setAnimations(9, 4, 0.10f, game.getLoader().getManager().get("BatMonster.png", Texture.class));
		player.setDir(DIRECTION.DOWN);
		camera.position.set(player.getX(), player.getY(), 0);
		//Teppo kokeilua
		// pile located in the center of the game
	
		


		enemies = new ArrayList<SpriteCommons>();
		spawnEnemies();
		camera.update();
	}

		


		
		
	
	//Spawn enemies. Enemy count increases by one every time to make the wave stronger.
	private void spawnEnemies() {
		//System.out.println("SPAWNING ENEMIES");
		timesCalled++;
		int tmp;
		int tmp2;
		for(int i = 0; i < timesCalled; i++){
			tmp = MathUtils.random(0, monsterSpawns.size-1);
			tmp2 = MathUtils.random(0, monsterSpawns.size-1);
			enemies.add(new StealingEnemy (32,32,monsterSpawns.get(tmp).getRectangle().getX(), monsterSpawns.get(tmp).getRectangle().getY(), 1, game.getLoader().getManager().get("stealTest.png", Texture.class)));
			enemies.add(new ChaserEnemy (32,32,monsterSpawns.get(tmp2).getRectangle().getX(), monsterSpawns.get(tmp2).getRectangle().getY(), 2, game.getLoader().getManager().get("chaserTest.png", Texture.class)));
			timeSinceWave = TimeUtils.millis();
		}
	}


	public void render (float delta) {
		
		Gdx.gl.glClearColor(100, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Move the player
		//moves hitbox first to check if collisions
				player.moveHitbox(player.getX() + player.getxVel(), player.getY() + player.getyVel());
				
				if(Intersector.overlaps(player.getHitbox(), pile.getHitbox())) {
					
					//pulls the hitbox back
					player.updateHitbox();	
					
				}else {
					// Move the player
					player.setX(player.getX() + player.getxVel());
					player.setY(player.getY() + player.getyVel());

				}
				
		
		//Teppo kokeilua
		
		for (int i = 0; i < enemies.size(); i++) {
		 
		//chase
		if(enemies.get(i) instanceof ChaserEnemy) {	
			double hypot = Math.hypot(player.getX(), enemies.get(i).getX());
			
			enemies.get(i).setxVel(((float) (1.2f / hypot  * (player.getX() - enemies.get(i).getX())))); 
			enemies.get(i).setyVel(((float) (1.2f / hypot  * (player.getY() - enemies.get(i).getY())))); 
		}
		
	
		
		enemies.get(i).updateHitbox();
		
		//move hitbox first to check if collisions
		enemies.get(i).moveHitbox(enemies.get(i).getX() + enemies.get(i).getxVel(), enemies.get(i).getY() + enemies.get(i).getyVel());
		
		if(Intersector.overlaps(enemies.get(i).getHitbox(), player.getHitbox())) {
			
			//pulls the hitbox back
			enemies.get(i).updateHitbox();

		}else {
			// move enemies
			enemies.get(i).setX(enemies.get(i).getX() + enemies.get(i).getxVel());
			enemies.get(i).setY(enemies.get(i).getY() + enemies.get(i).getyVel());
		}
		// steal from the pile
    	if(enemies.get(i) instanceof StealingEnemy) {
        	if(Intersector.overlaps((enemies.get(i).getHitbox()), pile.getHitbox())){
        		enemies.remove(i);
        		pileHealth = pile.reduceHealth();
    		}
    	}
		}
        	
        
	    
		
		statetime += delta;
		// Movement logic template for the character
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-1.5f);
			player.setyVel(0);
		}
		if(Gdx.input.isKeyPressed(Keys.W)){
			player.setDir(Player.DIRECTION.UP);
			player.setyVel(1.5f);
			player.setxVel(0);
		}  if(Gdx.input.isKeyPressed(Keys.S)){
			player.setDir(Player.DIRECTION.DOWN);
			player.setyVel(-1.5f);
			player.setxVel(0);	
		} if(Gdx.input.isKeyPressed(Keys.D)){
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1.5f);
			player.setyVel(0);
			
		} 
		
		if(Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A)){
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-1.3f);
			player.setyVel(1.3f);
		} if(Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)){
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1.3f);
			player.setyVel(1.3f);
		} if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)){
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1.3f);
			player.setyVel(-1.3f);
		}if(Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A)){
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-1.3f);
			player.setyVel(-1.3f);
		}
		
		if(!Gdx.input.isKeyPressed(Keys.W)  && !Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.S) && !Gdx.input.isKeyPressed(Keys.D)){
			player.setDir(player.getDir());
			player.setxVel(0);
			player.setyVel(0);
		}
		
		// This listens to mouse clicks
		
		if(Gdx.input.justTouched()){
			// Convert the cursor coordinates into game world coordinates. Needs to be refined
			Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			Vector3 reaCoords = camera.unproject(v);
			float bulletVel = 20f;
			
			float velX = (float)(reaCoords.x - (player.getX()+ (player.getWidth()/2)))/bulletVel;
			float velY = (float)(reaCoords.y - (player.getY() + (player.getHeight()/2)))/bulletVel;
			//float directionLength =(float) Math.sqrt(diffX*diffX + diffY*diffY);

			// Spawn a projectile with target coordinates and set the time it is visible

		   
		    Projectile p = new Projectile(10, 10,player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2,velX ,velY, game.getLoader().getManager().get("Pointer.png", Texture.class));
		    p.setTargetX(reaCoords.x);
			p.setTargetY(reaCoords.y);
			
		    
			
		    p.setCurrentTime(TimeUtils.millis());
			
			proj.add(p);
		
		}
		
		
//		 If the projectile has been enough time visible on the screen, remove it
		for(int i = 0; i<proj.size();i++){
			
			if(TimeUtils.timeSinceMillis(proj.get(i).getCurrentTime())>= 4000){
				proj.remove(i);
			}else{
				
				// attempt to correct the diection of each projectile
				// MOve the projectile according to its x and y velocities
				
				
				proj.get(i).setX(proj.get(i).getX() + proj.get(i).getxVel());
				proj.get(i).setY(proj.get(i).getY() + proj.get(i).getyVel());
				
			}
		}
		
		
	
		
		for(int i = 0; i<enemies.size();i++){
			
			for(int j = 0; j<proj.size();j++){
			
				if(Intersector.overlaps(proj.get(j).getHitbox(), enemies.get(i).getHitbox()) && TimeUtils.timeSinceMillis(proj.get(j).getCurrentTime())<4000){
					enemies.get(i).setHP(enemies.get(i).getHP() - 1);
					proj.remove(j);
					if(enemies.get(i).getHP() <= 0 && enemies.size() > 0){
						enemies.remove(i);
						break;
					}
				}
			}
		}
		//If enemy and player touch they both lose HP
		for(int i = 0; i<enemies.size();i++){	
			if(Intersector.overlaps(player.getHitbox(), enemies.get(i).getHitbox()) && enemies.get(i).getHP() > 0 && player.getHP() > 0 ){
				player.setHP(player.getHP() - 1);
				enemies.get(i).setHP(enemies.get(i).getHP() - 1);

				
				if(enemies.get(i).getHP() <= 0) {
					enemies.remove(i);
				}
				if(player.getHP() < 1) {
					dispose();
					game.setScreen(new LoadingScreen(game));
				}
			}
		}

		
		camera.position.set(MathUtils.clamp(player.getX(), camera.viewportWidth * .5f, world.mapWidth() - camera.viewportWidth * .5f), MathUtils.clamp(player.getY(), camera.viewportHeight * .5f, world.mapHeight() - camera.viewportHeight * .5f), 0);
		// Above is for further development
		camera.update();
		
//		Shape renderer used for debugging
		
		
		
		
		
		
		
		// Render the player and projectiles

		mapRender.setView(camera);
		mapRender.render();
	
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();


		// Teppo kokeilua
		if(!pileHealth) {
			game.batch.draw(pile.getPileTexture(), pile.getX(), pile.getY());
		}else {
			game.batch.draw(pile.getPileTexture2(), pile.getX(), pile.getY());
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i) instanceof StealingEnemy) {
				game.batch.draw(((StealingEnemy) enemies.get(i)).getTexture(), enemies.get(i).getX(), enemies.get(i).getY());
			
			}else if (enemies.get(i) instanceof ChaserEnemy){
				game.batch.draw(((ChaserEnemy) enemies.get(i)).getTexture(), enemies.get(i).getX(), enemies.get(i).getY());
			}
			
			
		}
		game.batch.draw(player.getCurrentFrame(statetime), player.getX(), player.getY(), player.getWidth(), player.getHeight());

		for(int i = 0; i<proj.size();i++){
			game.batch.draw(proj.get(i).getT(), proj.get(i).getX(), proj.get(i).getY(), proj.get(i).getWidth(), proj.get(i).getHeight());
		}


		//Wait 10 sec between waves.		
		if(TimeUtils.timeSinceMillis(timeSinceWave)> 10000){ 
			spawnEnemies();
		}
		game.batch.end();  
		
		stage.act(statetime);
		stage.draw();
		// shape renderer for debugging
		/*
		r.setProjectionMatrix(camera.combined);
		r.begin(ShapeType.Line);
		r.setColor(Color.GREEN);
		Vector3 vector = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
		Vector3 reaCoordn = camera.unproject(vector);
		r.line(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, reaCoordn.x, reaCoordn.y);
		
		for(int i = 0; i<proj.size();i++){
			r.setColor(Color.RED);
		r.line(proj.get(i).getTargetX(),proj.get(i).getTargetY(),proj.get(i).getX(),proj.get(i).getY());
		r.setColor(Color.BLUE);
		r.line(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, proj.get(i).getX(), proj.get(i).getY());
		}
		
		r.end();
	*/
		}
		
	   
	


	public void dispose () {
		//game.batch.dispose();
		stage.dispose();
		//img.dispose();
		
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