package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ResourceManager {
	private AssetManager manager;
	private static Preferences prefs;
	
	public ResourceManager() {
//		System.out.println("Internal storage path: " + Gdx.files.getLocalStoragePath());
		manager = new AssetManager();
		prefs = Gdx.app.getPreferences("Scores");
		if (!prefs.contains("highScore")) {
		    prefs.putInteger("highScore", 0);
		} 
		prefs.putInteger("score", 0);
		prefs.flush();

		manager.load("BatMonster.png", Texture.class);
		manager.load("Pointer.png", Texture.class);
		manager.load("chaserTest.png", Texture.class);
		manager.load("stealTest.png", Texture.class);
		manager.load("pileTest.png", Texture.class);
		manager.load("pileTest2.png", Texture.class);
		manager.load("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/fastwalkdummy.png", Texture.class);
		manager.load("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/rapidfiredummy.png", Texture.class);
		manager.load("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/screencleardummy.png", Texture.class);
		manager.load("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/shielddummy.png", Texture.class);
		manager.load("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/tarbarreldummy.png", Texture.class);
		manager.load("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/tripleshotdummy.png", Texture.class);
		manager.load("C:/Users/Markus/Desktop/CandyPileDefender/core/assets/tarstain.png",Texture.class);
		
		
		
		//manager.load("", Music.class);
	}
	
	public AssetManager getManager() {
		return manager;
	}

	public void setHighScore(int value) {
	    prefs.putInteger("highScore", value);
	    prefs.flush();
	}

	public int getHighScore() {
	    return prefs.getInteger("highScore");
	}
	
	public void setScore(int value) {
	    prefs.putInteger("score", value);
	    prefs.flush();
	}

	public int getScore() {
	    return prefs.getInteger("score");
	}
}
