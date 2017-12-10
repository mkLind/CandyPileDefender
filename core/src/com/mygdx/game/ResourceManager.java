package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
/*
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
*/
public class ResourceManager {
	private AssetManager manager;
	private static Preferences prefs;
	private boolean masterVolume;
	private boolean playerDead;
	private boolean pileStolen;
	private boolean gamePaused;

	public ResourceManager() {
		// System.out.println("Internal storage path: " +
		// Gdx.files.getLocalStoragePath());
		masterVolume = true;
		playerDead = false;
		pileStolen = false;
		gamePaused = false;
		manager = new AssetManager();
		prefs = Gdx.app.getPreferences("Scores");
		if (!prefs.contains("highScore")) {
			prefs.putString("highScore", "0 00/00/0000");
		}
		if (!prefs.contains("2")) {
			prefs.putString("2", "0 00/00/0000");
		}
		if (!prefs.contains("3")) {
			prefs.putString("3", "0 00/00/0000");
		}
		if (!prefs.contains("4")) {
			prefs.putString("4", "0 00/00/0000");
		}
		if (!prefs.contains("5")) {
			prefs.putString("5", "0 00/00/0000");
		}
		prefs.putString("score", "0");
		prefs.flush();




		try {
			manager.load("Pointer.png", Texture.class);
		
	
			manager.load("pileTest.png", Texture.class);
			manager.load("pileTest2.png", Texture.class);
			manager.load("VendingMachine.png",Texture.class);
			manager.load("candyindicator.png",Texture.class);
		
			manager.load("MainMenu.jpg",Texture.class);
			manager.load("Instructions.jpg",Texture.class);
			
			manager.load("WalkingSpeedUp.png", Texture.class);
			manager.load("FireRateUp.png", Texture.class);
			manager.load("Explosion.png", Texture.class);
			manager.load("ShieldCrate.png", Texture.class);
			manager.load("Tar.png", Texture.class);
			
			manager.load("Times3.png", Texture.class);
			manager.load("tarstain.png",Texture.class);
			manager.load("SHIELD.png",Texture.class);
			manager.load("ScreenClear.png",Texture.class);
			manager.load("PirateTileset.png",Texture.class);
			
			manager.load("SkeletonTileset.png", Texture.class);
			manager.load("PumpkinTileset.png",Texture.class);
			manager.load("WitchTileset.png",Texture.class);
			manager.load("thirdEnemyTest.png", Texture.class);
			// Each candy pile phase is a different .png
			manager.load("CPBigCrop.png", Texture.class);
			manager.load("CPMedCrop.png", Texture.class);
			manager.load("CPSmallCrop.png", Texture.class);
			manager.load("CPTinyCrop.png", Texture.class);
			
			// Ammunition
			manager.load("Carrot.png",Texture.class);
			manager.load("Tomato.png",Texture.class);
			manager.load("Broccoli.png",Texture.class);
			
			manager.load("Eggplant.png",Texture.class);
			// Music and sounds
			
			manager.load("Music/POL-horror-ambience-1-short_16bit.ogg", Music.class);
			manager.load("Music/POL-horror-ambience-2-short_16bit.ogg", Music.class);
			
			manager.load("Sounds/game_over/NFF-death-bell.wav", Sound.class);
			manager.load("Sounds/game_over/NFF-zomboid.wav", Sound.class);
			manager.load("Sounds/NFF-coin-03.wav", Sound.class);
		
			
			manager.load("Sounds/hit/NFF-dusty-hit.wav", Sound.class);
			manager.load("Sounds/hit/NFF-explode.wav", Sound.class);
			manager.load("Sounds/hit/NFF-kid-hurt.wav", Sound.class);
			manager.load("Sounds/hit/NFF-slap-02.wav", Sound.class);
			manager.load("Sounds/hit/NFF-springy-hit.wav", Sound.class);
			
			manager.load("Sounds/shooting/NFF-gun-miss.wav",Sound.class);
			manager.load("Sounds/shooting/NFF-toy-gun.wav", Sound.class);
			
			manager.load("Sounds/walking/grass1.wav", Sound.class);
			manager.load("Sounds/walking/gravel1.wav", Sound.class);
			
			manager.load("Healthbar.png", Texture.class);
			manager.load("HealthbarBackGround.png", Texture.class);
			manager.load("HealthbarKnob.png", Texture.class);
			manager.load("warning.png", Texture.class);
			/*
			FileHandleResolver resolver = new InternalFileHandleResolver();
			manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
			manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
			
			FreeTypeFontLoaderParameter smallFont = new FreeTypeFontLoaderParameter();
			smallFont.fontFileName = "arial1.ttf";
			smallFont.fontParameters.size = 16;
			manager.load("arial1.ttf", BitmapFont.class, smallFont);
			
			FreeTypeFontLoaderParameter bigFont = new FreeTypeFontLoaderParameter();
			bigFont.fontFileName = "arial2.ttf";
			bigFont.fontParameters.size = 28;
			manager.load("arial2.ttf", BitmapFont.class, bigFont);*/

		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		

	}

	public AssetManager getManager() {
		return manager;
	}

	public void setHighScore(String value) {

		prefs.putString("highScore", value);
		prefs.flush();
	}

	public String getHighScore() {
		return prefs.getString("highScore");
	}

	public void setScore(int value) {
		prefs.putString("score", Integer.toString(value));
		prefs.flush();
	}

	public int getScore() {
		int value = Integer.parseInt(prefs.getString("score"));
		return value;
	}

	public void setSecond(String value) {
		prefs.putString("2", value);
		prefs.flush();
	}

	public String getSecond() {
		return prefs.getString("2");
	}

	public void setThird(String value) {
		prefs.putString("3", value);
		prefs.flush();
	}

	public String getThird() {
		return prefs.getString("3");
	}

	public void setFourth(String value) {
		prefs.putString("4", value);
		prefs.flush();
	}

	public String getFourth() {
		return prefs.getString("4");
	}

	public void setFifth(String value) {
		prefs.putString("5", value);
		prefs.flush();
	}

	public String getFifth() {
		return prefs.getString("5");
	}
	public void setMasterVolume(boolean value) {
		masterVolume = value;
	}
	public boolean getMasterVolume() {
		return masterVolume;
	}
	public void setPlayerDead(boolean value) {
		playerDead = value;
	}
	public boolean getPlayerDead() {
		return playerDead;
	}
	public void setPileStolen(boolean value) {
		pileStolen = value;
	}
	public boolean getPileStolen() {
		return pileStolen;
	}
	public void setGamePaused(boolean value) {
		gamePaused = value;
	}
	public boolean getGamePaused() {
		return gamePaused;
	}
}
