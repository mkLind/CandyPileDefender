package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer.Random;
import com.badlogic.gdx.math.MathUtils;

public class Powerup extends SpriteCommons {
	
	private Texture graphic;
	private Core game;
	private POWERUPTYPE type;
	private ParticleEffect spawnEffect;
	private Long timeAlive;
	private Long effectTime;
	
	public Powerup(int width, int height, float x, float y, float xVel, float yVel, Core game) {
		super(width, height, x, y, xVel, yVel);
		this.game = game;
		spawnEffect = new ParticleEffect();
		


		spawnEffect.load(Gdx.files.internal("C:/Users/marku/Documents/CandyPileDefender/core/assets/spawnparticle2.p"), Gdx.files.internal("C:/Users/marku/Documents/CandyPileDefender/core/assets/"));


		
		spawnEffect.getEmitters().first().setPosition(super.getX(), super.getY());
		spawnEffect.scaleEffect(1000f);
		spawnEffect.start();
	
		effectTime = (long) 10000;
		
	}

	
	
	public Long getEffectTime() {
		return effectTime;
	}



	public void setEffectTime(Long effectTime) {
		this.effectTime = effectTime;
	}



	public void setTypeAndGraphic(Core game){
		
		this.type = POWERUPTYPE.values()[MathUtils.random(0, POWERUPTYPE.values().length-1)];
		//this.type = POWERUPTYPE.CLEARSCREEN;
		if(type == POWERUPTYPE.CLEARSCREEN){

			 graphic = game.getLoader().getManager().get("C:/Users/marku/Documents/CandyPileDefender/core/assets/Explosion.png",Texture.class);
			 effectTime = (long) 1000;
		}
		if(type == POWERUPTYPE.HASTE){
			graphic = game.getLoader().getManager().get("C:/Users/marku/Documents/CandyPileDefender/core/assets/WalkingSpeedUp.png",Texture.class);
		}
		if(type == POWERUPTYPE.RAPIDFIRE){
			graphic = game.getLoader().getManager().get("C:/Users/marku/Documents/CandyPileDefender/core/assets/FireRateUp.png",Texture.class);
		}
		if(type == POWERUPTYPE.SLOWDOWN){
			graphic = game.getLoader().getManager().get("C:/Users/marku/Documents/CandyPileDefender/core/assets/Tar.png",Texture.class);
		}
		if(type == POWERUPTYPE.TRIPLESHOT){
			graphic = game.getLoader().getManager().get("C:/Users/marku/Documents/CandyPileDefender/core/assets/Times3.png",Texture.class);
		}
		if(type == POWERUPTYPE.SHIELD){
			graphic = game.getLoader().getManager().get("C:/Users/marku/Documents/CandyPileDefender/core/assets/ShieldCrate.png", Texture.class);

			 
		}
		if(type == POWERUPTYPE.HASTE){
			graphic = game.getLoader().getManager().get("C:/CandyPile/CandyPileDefender/core/assets/WalkingSpeedUp.png",Texture.class);
		}
		if(type == POWERUPTYPE.RAPIDFIRE){
			graphic = game.getLoader().getManager().get("C:/CandyPile/CandyPileDefender/core/assets/FireRateUp.png",Texture.class);
		}
		if(type == POWERUPTYPE.SLOWDOWN){
			graphic = game.getLoader().getManager().get("C:/CandyPile/CandyPileDefender/core/assets/Tar.png",Texture.class);
		}
		if(type == POWERUPTYPE.TRIPLESHOT){
			graphic = game.getLoader().getManager().get("C:/CandyPile/CandyPileDefender/core/assets/Times3.png",Texture.class);
		}
		if(type == POWERUPTYPE.SHIELD){
			graphic = game.getLoader().getManager().get("C:/CandyPile/CandyPileDefender/core/assets/ShieldCrate.png", Texture.class);

		}
		
		
		
	}
	
public enum POWERUPTYPE{
		SHIELD,TRIPLESHOT,HASTE,RAPIDFIRE,CLEARSCREEN,SLOWDOWN
	}

public Texture getGraphic() {
	return graphic;
}



public void setGraphic(Texture graphic) {
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
