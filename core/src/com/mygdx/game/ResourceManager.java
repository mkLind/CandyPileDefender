package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ResourceManager {
	private AssetManager manager;
	
	public ResourceManager() {
		System.out.println("Ineranl storage path: " + Gdx.files.getLocalStoragePath());
		manager = new AssetManager();
		
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
		
		
		//manager.load("", Music.class);
	}
	
	public AssetManager getManager() {
		return manager;
	}
}
