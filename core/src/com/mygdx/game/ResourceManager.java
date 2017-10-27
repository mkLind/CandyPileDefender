package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ResourceManager {
	private AssetManager manager;
	private static Preferences prefs;

	public ResourceManager() {
		// System.out.println("Internal storage path: " +
		// Gdx.files.getLocalStoragePath());
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

		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Pointer.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/chaserTest.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/stealTest.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/pileTest.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/pileTest2.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/WalkingSpeedUp.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/FireRateUp.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Explosion.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/ShieldCrate.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Tar.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Times3.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/tarstain.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/SHIELD.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/ScreenClear.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/PirateTileset.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/CPBigCrop.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/CPMedCrop.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/CPSmallCrop.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/CPTinyCrop.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/SkeletonTileset.png", Texture.class);

		// Ammunition
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Carrot.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Tomato.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Broccoli.png", Texture.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/EggPlant.png", Texture.class);
		// Music and sounds

		manager.load(
				"C:/Users/marku/Documents/CandyPileDefender/core/assets/Music/POL-horror-ambience-1-short_16bit.wav",
				Music.class);
		manager.load(
				"C:/Users/marku/Documents/CandyPileDefender/core/assets/Music/POL-horror-ambience-2-short_16bit.wav",
				Music.class);

		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/game_over/NFF-death-bell.wav",
				Sound.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/game_over/NFF-zomboid.wav",
				Sound.class);

		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/hit/NFF-dusty-hit.wav",
				Sound.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/hit/NFF-explode.wav", Sound.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/hit/NFF-kid-hurt.wav", Sound.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/hit/NFF-slap-02.wav", Sound.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/hit/NFF-springy-hit.wav",
				Sound.class);

		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/shooting/NFF-gun-miss.wav",
				Sound.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/shooting/NFF-toy-gun.wav",
				Sound.class);

		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/walking/grass1.wav", Sound.class);
		manager.load("C:/Users/marku/Documents/CandyPileDefender/core/assets/Sounds/walking/gravel1.wav", Sound.class);

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
}
