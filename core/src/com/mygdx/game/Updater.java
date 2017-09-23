package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Updater extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// When loading textures to project, the entire classpath should be included
		// be careful with that
		img = new Texture(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\1Currency.png")); 
	
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Movement logic template for the character
		if(Gdx.input.isKeyPressed(Keys.W)){
			
		}else if(Gdx.input.isKeyPressed(Keys.A)){
			
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			
		}
		
		
		
		batch.begin();
		batch.draw(img, Gdx.input.getX(), Gdx.input.getY());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
