package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Updater extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private float aspectRatio; 
	private OrthographicCamera camera;
	private Stage stage;
	private Vector3 mousePos;
	private Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// When loading textures to project, the entire path to the file should be included
		// be careful with that
		img = new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\badlogic.jpg")); 
		camera = new OrthographicCamera();
		aspectRatio =  (float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight();
		camera.setToOrtho(false, 220f*aspectRatio, 220f);
		camera.update();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch.setProjectionMatrix(camera.combined);
		mousePos = new Vector3(0,0,0);
		Cursor cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\Pointer.png")),0,0);
		Gdx.graphics.setCursor(cursor);
		player = new Player(new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\badlogic.jpg")),0,0,32,32,100,100);	
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Movement logic template for the character
		if(Gdx.input.isKeyPressed(Keys.W)){
			player.setDir(Player.DIRECTION.UP);
			player.setyVel(-1);
		}else if(Gdx.input.isKeyPressed(Keys.A)){
			player.setDir(Player.DIRECTION.LEFT);
			player.setxVel(-1);
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			player.setDir(Player.DIRECTION.DOWN);
			player.setyVel(1);
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			player.setDir(Player.DIRECTION.RIGHT);
			player.setxVel(1);
		}else{
			player.setxVel(0);
			player.setyVel(0);
		}
		
		camera.update();
		
		
		batch.begin();
		batch.draw(img, 50, 50);
		batch.end();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
