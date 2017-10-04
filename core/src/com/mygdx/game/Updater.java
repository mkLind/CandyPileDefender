package com.mygdx.game;

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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Player.DIRECTION;

public class Updater implements Screen {
	
	Texture img;
	private float aspectRatio; 
	private OrthographicCamera camera;
	private Stage stage;
	
	private Player player;
	private float statetime;
	
	private BitmapFont f;
	final Core game;
	
	public Updater(final Core game) {
		this.game = game;
		statetime = 0f;
		f = new BitmapFont();
		
		// When loading textures to project, the entire path to the file should be included
		// be careful with that
		img = new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\1Currency.png")); 
		camera = new OrthographicCamera();
	
		aspectRatio =  (float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight();
		//camera.setToOrtho(false, 120f*aspectRatio, 120f);
		camera.setToOrtho(false, 700f,700f);
		camera.update();
		stage = new Stage();
		game.batch.setProjectionMatrix(camera.combined);

		/*
		Cursor cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\Pointer.png")),0,0);
		Gdx.graphics.setCursor(cursor);
		*/
		
		player = new Player(32,32,50,50);	
		player.setAnimations(9, 4, 0.25f, new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\BatMonster.png")));
		player.setDir(DIRECTION.DOWN);
		
	}
	
	

	
	public void render (float delta) {
		
		Gdx.gl.glClearColor(100, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Movement logic template for the character
	
		if(Gdx.input.isKeyPressed(Keys.W)){
			player.setDir(Player.DIRECTION.UP);
			player.setyVel(1);
		
		}else if(Gdx.input.isKeyPressed(Keys.A)){
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-1);
			
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			player.setDir(Player.DIRECTION.DOWN);
			player.setyVel(-1);
			
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1);
			
		}else{
			player.setDir(player.getDir());
			player.setxVel(0);
			player.setyVel(0);
			
			
		}
		player.setX(player.getX() + player.getxVel());
		player.setY(player.getY() + player.getyVel());
		statetime += delta;
		
	//	camera.position.set(MathUtils.clamp(character.getX(), camera.viewportWidth * .5f, level.mapWidth() - camera.viewportWidth * .5f), MathUtils.clamp(character.getY(), camera.viewportHeight * .5f, level.mapHeight() - camera.viewportHeight * .5f), 0);
// Above is for further development
		

		camera.update();
		
		
		
		game.batch.begin();
		//batch.draw(img, 50, 50);
		game.batch.draw(player.getCurrentFrame(statetime), player.getX(), player.getY(), player.getWidth(), player.getHeight());
		f.draw(game.batch, "Player status information Player position: x " + player.getX() + " y: " + player.getY() , 150f, 150f);
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
