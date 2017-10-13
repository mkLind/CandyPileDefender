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
		
		//manager.load("", Music.class);
	}
	
	public AssetManager getManager() {
		return manager;
	}
}
