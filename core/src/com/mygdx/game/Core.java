package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*
 * Wrapper class for bitmap font and spritebatch. Extends libgdx Game.
 */
public class Core extends Game{
public SpriteBatch batch;
public BitmapFont font;
private ResourceManager assetLoader;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.GREEN);
		assetLoader = new ResourceManager();
		this.setScreen(new LoadingScreen(this));
	}
	public void render(){
		super.render();
	}
	public void dispose(){
		batch.dispose();
	}
	public ResourceManager getLoader() {
		return assetLoader;
	}

}
