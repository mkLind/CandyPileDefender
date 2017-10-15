package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Powerup extends SpriteCommons {
	
	private TextureRegion graphic;
	private ResourceManager manager;
	private POWERUPTYPE type;
	
	
	public Powerup(int width, int height, float x, float y, float xVel, float yVel, ResourceManager manager) {
		super(width, height, x, y, xVel, yVel);
		this.manager = manager;
	}

	
	
	public void setTypeAndGraphic(POWERUPTYPE type){
		this.type = type;
		if(type == POWERUPTYPE.CLEARSCREEN){
			 graphic = manager.getManager().get("SCREENCLEARDUMMY.png");
		}
		if(type == POWERUPTYPE.HASTE){
			graphic = manager.getManager().get("FASTWALKDUMMY.png");
		}
		if(type == POWERUPTYPE.RAPIDFIRE){
			graphic = manager.getManager().get("RAPIDFIREDUMMY.png");
		}
		if(type == POWERUPTYPE.SLOWDOWN){
			graphic = manager.getManager().get("TARBARRELDUMMY.png");
		}
		if(type == POWERUPTYPE.TRIPLESHOT){
			graphic = manager.getManager().get("TRIPLESHOTDUMMY.png");
		}
		if(type == POWERUPTYPE.SHIELD){
			graphic = manager.getManager().get("SHIELDDUMMY.png");
		}
		
		
		
	}
	
public enum POWERUPTYPE{
		SHIELD,TRIPLESHOT,HASTE,RAPIDFIRE,CLEARSCREEN,SLOWDOWN
	}
	
	

}
