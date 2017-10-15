package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {
	private AssetManager manager;
	
	public ResourceManager() {
		manager = new AssetManager();
		manager.load("BatMonster.png", Texture.class);
		manager.load("Pointer.png", Texture.class);
		manager.load("chaserTest.png", Texture.class);
		manager.load("stealTest.png", Texture.class);
		manager.load("pileTest.png", Texture.class);
		manager.load("pileTest2.png", Texture.class);
		manager.load("FASTWALKDUMMY.png", Texture.class);
		manager.load("RAPIDFIREDUMMY.png", Texture.class);
		manager.load("SCREENCLEARDUMMY.png", Texture.class);
		manager.load("SHIELDDUMMY.png", Texture.class);
		manager.load("TARBARRELDUMMY.png", Texture.class);
		manager.load("TRIPLESHOTDUMMY.png", Texture.class);
		//manager.load("", Music.class);
	}
	
	public AssetManager getManager() {
		return manager;
	}
}
