package com.mygdx.game;

import java.util.ArrayList;

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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
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
	
	private BitmapFont f;
	final Core game;
	private ArrayList<Projectile> proj;
	/**
	 * Initializes the entire game
	 * @param game
	 */
	public Updater(final Core game) {
		this.game = game;
		statetime = 0f;
		f = new BitmapFont();
		
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
		/*
		Cursor cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\Pointer.png")),0,0);
		Gdx.graphics.setCursor(cursor);
		*/
		
		player = new Player(32,32,50,50);	
		player.setAnimations(9, 4, 0.10f, new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\BatMonster.png")));
		player.setDir(DIRECTION.DOWN);
		
	}
	
	

	
	public void render (float delta) {
		
		Gdx.gl.glClearColor(100, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Move the player
		player.setX(player.getX() + player.getxVel());
		player.setY(player.getY() + player.getyVel());
		
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
			player.setxVel(-1.5f);
			player.setyVel(1.5f);
		} if(Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)){
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1.5f);
			player.setyVel(1.5f);
		} if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)){
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1.5f);
			player.setyVel(-1.5f);
		}if(Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A)){
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-1.5f);
			player.setyVel(-1.5f);
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
				 velX = diffX/directionLength;
		}
		if(!(diffY == 0)){
			velY = diffY/directionLength;	
		}
			
		 		
					
			// Spawn a projectile with target coordinates and set the time it is visible
		    Projectile p = new Projectile(10, 10,player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2,velX ,velY, new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\Pointer.png")));
			p.setTargetX(reaCoords.x);
			p.setTargetY(reaCoords.y);
		    p.setCurrentTime(TimeUtils.millis());
			
			proj.add(p);
		
		}
		// If the projectile has been enough time visible on the screen, remove it
		for(int i = 0; i<proj.size();i++){
			if(TimeUtils.timeSinceMillis(proj.get(i).getCurrentTime())>= 4000){
				proj.remove(i);
			}else{
				
				// attempt to correct the diection of each projectile
				
				if(proj.get(i).getX() - player.getX() < 1|| proj.get(i).getY() - player.getY()<1 ){
				float diffX = proj.get(i).getTargetX() - proj.get(i).getX();
				float diffY = proj.get(i).getTargetY() - proj.get(i).getY();
				float directionLength =(float) Math.sqrt(diffX*diffX + diffY*diffY);
				
				proj.get(i).setxVel(diffX/directionLength);
				
				proj.get(i).setyVel(diffY/directionLength);
				
				}
				// MOve the projectile according to its x and y velocities
				proj.get(i).setX(proj.get(i).getX() + proj.get(i).getxVel());
				proj.get(i).setY(proj.get(i).getY() + proj.get(i).getyVel());
				
			}
		}
		
		
	//	camera.position.set(MathUtils.clamp(character.getX(), camera.viewportWidth * .5f, level.mapWidth() - camera.viewportWidth * .5f), MathUtils.clamp(character.getY(), camera.viewportHeight * .5f, level.mapHeight() - camera.viewportHeight * .5f), 0);
// Above is for further development
		

		camera.update();
		// Shape renderer used for debugging
		r.begin(ShapeType.Line);
		Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
		Vector3 reaCoords = camera.unproject(v);
		r.line(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, reaCoords.x, reaCoords.y);
		r.end();
		
		// Render the player and projectiles
		game.batch.begin();
		
		

		game.batch.draw(player.getCurrentFrame(statetime), player.getX(), player.getY(), player.getWidth(), player.getHeight());
		
		for(int i = 0; i<proj.size();i++){
			game.batch.draw(proj.get(i).getT(), proj.get(i).getX(), proj.get(i).getY(), proj.get(i).getWidth(), proj.get(i).getHeight());
		}
		
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