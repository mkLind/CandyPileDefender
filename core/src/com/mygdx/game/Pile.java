package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Pile extends SpriteCommons {
	
	private int health;
	
	private Texture pileTexture;
	private Texture pileTexture2;
	
	public Pile(int width, int height, float x, float y, Texture pileTexture, Texture pileTexture2) {
		
		super(width, height, x, y, 0f, 0f);
		
		this.pileTexture = pileTexture;
		this.pileTexture2 = pileTexture2;

		
		health = 2;
		
		
	}

	
	public boolean reduceHealth() {
		health--;
		if(health < 2) {
			return true;
		}else { 
			return false;		
		}
	}




	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}


	public Texture getPileTexture() {
		return pileTexture;
	}

	public Texture getPileTexture2() {
		return pileTexture2;
	}



	public void setPileTexture(Texture pileTexture) {
		this.pileTexture = pileTexture;
	}

}
