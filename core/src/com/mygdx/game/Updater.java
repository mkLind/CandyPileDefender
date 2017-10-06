package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.Player.DIRECTION;
/*
 * The class that actually makes the game visible
 */
public class Updater implements Screen {
	
	Texture img;
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
	
	//For testing wave system for enemies with player instances
	// private ArrayList<Player> enemies;
	private float timeSinceWave;
	private int timesCalled;
	
	
	/**
	 * Initializes the entire game
	 * @param game
	 */
	public Updater(final Core game) {
		this.game = game;
		statetime = 0f;
		timesCalled = 0;
		f = new BitmapFont();
		enemies = new ArrayList<SpriteCommons>();
		// When loading textures to project, the entire path to the file should be included
		// be careful with that
		camera = new OrthographicCamera();
	
		aspectRatio =  (float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight();
		camera.setToOrtho(false,220f*aspectRatio, 220f);
	//	camera.setToOrtho(false, 700f,700f);
		camera.update();
		stage = new Stage();
		r = new ShapeRenderer();
		r.setProjectionMatrix(camera.combined);
		
		game.batch.setProjectionMatrix(camera.combined);
		proj = new ArrayList<Projectile>();
		

		
		//enemies = new ArrayList<Player>();
		spawnEnemies();

		player = new Player(32,32,50,50);	
		player.setAnimations(9, 4, 0.10f, new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\BatMonster.png")));
		player.setDir(DIRECTION.DOWN);
		
		
		//Teppo kokeilua
		// pile located in the center of the game
		pile = new Pile(100, 100, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 50);
		
	}
		

		
	
	
	//Spawn enemies. Enemy count increases by one every time to make the wave stronger.
	//Fixed spawn points that will be changed when the map is ready. Tested with simple
	//player instances since enemies not ready yet.
	private void spawnEnemies() {
		timesCalled++;
		int tmp;
		for(int i = 0; i < timesCalled; i++){
			tmp = MathUtils.random(0, 4);
			System.out.println(tmp);
			if(tmp > 3) {
				/*
			
				player = new Player(32,32,MathUtils.random(10, 30),MathUtils.random(10, 30));
				player.setAnimations(9, 4, 0.10f, new Texture(Gdx.files.internal("BatMonster.png")));
				player.setDir(DIRECTION.DOWN);
			*/
				
				enemies.add(new StealingEnemy (20,20,0, 50));
				enemies.add(new ChaserEnemy (20, 20, 0, 0));
				
				
			}else if (tmp > 2) {
				enemies.add(new StealingEnemy (20,20,0, 50));
				enemies.add(new ChaserEnemy (20, 20, 0, 0));
		
			}else if (tmp > 1) {
				enemies.add(new StealingEnemy (20,20,0, 50));
				enemies.add(new ChaserEnemy (20, 20, 0, 0));
				
				
			}else 
				enemies.add(new StealingEnemy (20,20,0, 50));
				enemies.add(new ChaserEnemy (20, 20, 0, 0));
				
				
			}
	timeSinceWave = 0;
		}
	

	
	public void render (float delta) {
		
		Gdx.gl.glClearColor(100, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Move the player
		player.setX(player.getX() + player.getxVel());
		player.setY(player.getY() + player.getyVel());
				
		
		//Teppo kokeilua
		
		for (int i = 0; i < enemies.size(); i++) {
		 
		//chase
		if(enemies.get(i) instanceof ChaserEnemy) {	
			double hypot = Math.hypot(player.getX(), enemies.get(i).getX());
			
			enemies.get(i).setxVel(((float) (1.2f / hypot  * (player.getX() - enemies.get(i).getX())))); 
			enemies.get(i).setyVel(((float) (1.2f / hypot  * (player.getY() - enemies.get(i).getY())))); 
		}
		
		// move enemies
		enemies.get(i).setX(enemies.get(i).getX() + enemies.get(i).getxVel());
		enemies.get(i).setY(enemies.get(i).getY() + enemies.get(i).getyVel());
		
		enemies.get(i).updateHitbox();
		
		
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
			
			float diffX = reaCoords.x - player.getX()+ player.getWidth()/2;
			float diffY = reaCoords.y - player.getY() + player.getHeight()/2;
			float directionLength =(float) Math.sqrt(diffX*diffX + diffY*diffY);
		float velX = 0;
		float velY = 0;

			if(!(diffX == 0)){
				 velX = diffX/100;
		}else{
			velX = 0;

		if(!(diffY == 0)){
			velY = diffY/100;	
		}else{
			velY = 0;
		}
		}
			// Spawn a projectile with target coordinates and set the time it is visible

		   
		    Projectile p = new Projectile(10, 10,player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2,velX ,velY, new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\Pointer.png")));

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
		
		
	//	camera.position.set(MathUtils.clamp(character.getX(), camera.viewportWidth * .5f, level.mapWidth() - camera.viewportWidth * .5f), MathUtils.clamp(character.getY(), camera.viewportHeight * .5f, level.mapHeight() - camera.viewportHeight * .5f), 0);
// Above is for further development
		

		camera.update();
		
//		Shape renderer used for debugging
		r.begin(ShapeType.Line);
		Vector3 vector = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
		Vector3 reaCoordn = camera.unproject(vector);
		r.line(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, reaCoordn.x, reaCoordn.y);
		r.end();
		
		// Render the player and projectiles

//		Matrix Transform = Matrix.CreateTranslation(offsetX, offsetY, 0);
//
//		SpriteBatch.Begin(...,...,...., Transform);

		game.batch.begin();
		//Not working properly. If one spawn point has multiple enemies they are drawn to the same spot
		//at the same time
		

		
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
		timeSinceWave += delta;
		if(timeSinceWave > 10.0f) spawnEnemies();
		
		//Move the enemies (here still players) for testing 
	   /* Iterator<Player> iter = enemies.iterator();
	    while(iter.hasNext()) {
	    	Player player = iter.next();
	    	player.setX(player.getX() + 30 * Gdx.graphics.getDeltaTime());
	    }
		*/
		game.batch.end();
		stage.act(statetime);
		stage.draw();
		}
		
	   
	

	@Override
	public void dispose () {
		game.batch.dispose();
		img.dispose();
		stage.dispose();
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

