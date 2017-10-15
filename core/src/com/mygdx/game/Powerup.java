package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer.Random;
import com.badlogic.gdx.math.MathUtils;

public class Powerup extends SpriteCommons {
	
	private TextureRegion graphic;
	private Core game;
	private POWERUPTYPE type;
	private ParticleEffect spawnEffect;
	private Long timeAlive;
	private Long effectTime;
	
	public Powerup(int width, int height, float x, float y, float xVel, float yVel, Core game) {
		super(width, height, x, y, xVel, yVel);
		this.game = game;
		spawnEffect = new ParticleEffect();
		
		spawnEffect.load(Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\PowerupSpawn"), Gdx.files.internal("C:\\Users\\Markus\\Desktop\\CandyPileDefender\\core\\assets\\POWERUPAPPEARPARTICLE.png"));
		spawnEffect.getEmitters().first().setPosition(super.getX(), super.getY());
		spawnEffect.scaleEffect(20f);
		spawnEffect.start();
	
		effectTime = (long) 5000;
		
	}

	
	
	public Long getEffectTime() {
		return effectTime;
	}



	public void setEffectTime(Long effectTime) {
		this.effectTime = effectTime;
	}



	public void setTypeAndGraphic(){
		
		this.type = POWERUPTYPE.values()[MathUtils.random(0, POWERUPTYPE.values().length)];
		
		if(type == POWERUPTYPE.CLEARSCREEN){
			 graphic = game.getLoader().getManager().get("SCREENCLEARDUMMY.png");
		}
		if(type == POWERUPTYPE.HASTE){
			graphic = game.getLoader().getManager().get("FASTWALKDUMMY.png");
		}
		if(type == POWERUPTYPE.RAPIDFIRE){
			graphic = game.getLoader().getManager().get("RAPIDFIREDUMMY.png");
		}
		if(type == POWERUPTYPE.SLOWDOWN){
			graphic = game.getLoader().getManager().get("TARBARRELDUMMY.png");
		}
		if(type == POWERUPTYPE.TRIPLESHOT){
			graphic = game.getLoader().getManager().get("TRIPLESHOTDUMMY.png");
		}
		if(type == POWERUPTYPE.SHIELD){
			graphic = game.getLoader().getManager().get("SHIELDDUMMY.png");
		}
		
		
		
	}
	
public enum POWERUPTYPE{
		SHIELD,TRIPLESHOT,HASTE,RAPIDFIRE,CLEARSCREEN,SLOWDOWN
	}

public TextureRegion getGraphic() {
	return graphic;
}



public void setGraphic(TextureRegion graphic) {
	this.graphic = graphic;
}







public POWERUPTYPE getType() {
	return type;
}



public void setType(POWERUPTYPE type) {
	this.type = type;
}



public ParticleEffect getSpawnEffect() {
	return spawnEffect;
}



public void setSpawnEffect(ParticleEffect spawnEffect) {
	this.spawnEffect = spawnEffect;
}



public Long getTimeAlive() {
	return timeAlive;
}



public void setTimeAlive(Long timeAlive) {
	this.timeAlive = timeAlive;
}
	
	

}
