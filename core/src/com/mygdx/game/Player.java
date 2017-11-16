package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Powerup.POWERUPTYPE;

 // sprite.setRegion(animation.getKeyFrame(time));
/**
 * Class for representing the player in the game. extends SpriteCommons
 * @author Markus
 *
 */
public class Player extends SpriteCommons {

	private DIRECTION dir; 
	private Animator animations;
	private POWERUPTYPE powerupType;
	private long powerupActiveTime;
	private long powerupSetTime;
	private long ShootingCooldown;
	private long LastShot;
	private long attackAnimStart;
	
	private float hasteVel;
	private boolean isAttacking;
	private ArrayList<Float> previousX;
	private ArrayList<Float> previousY;
	private long lastPreviousSet;
	private int collectedCandy;
	private Texture candyIndicator;
	private HashMap<Integer, ArrayList<Vector2>> goAround;
	
	
/**
 * Initializes the player
 * @param width
 * @param height
 * @param x
 * @param y
 */
	
	public Player(int width, int height, float x, float y, int HP){
		super(width, height, x, y, 0f, 0f, HP);
		dir = DIRECTION.DOWN;
		powerupActiveTime = 0;
		powerupSetTime = 0;
		powerupType = null;
		hasteVel = 0f;
		ShootingCooldown =  500;
		isAttacking = false;
		attackAnimStart = 0;
		previousX = new ArrayList<Float>();
		previousY = new ArrayList<Float>();
		lastPreviousSet = 0;
		setPreviousX(super.getX());
		setPreviousY(super.getY());
		collectedCandy = 0;
		candyIndicator = null; 
		goAround = new HashMap<Integer, ArrayList<Vector2>>();
		
	}
	public enum DIRECTION{
		UP,DOWN,LEFT,RIGHT
	}
	// Sets all the animations for the player
	public void setAnimations(int row, int column, float frametime, Texture spritesheet){
		animations = new Animator(row, column, frametime, spritesheet);
		
	}
	
	/*
	 * Method for changing the frame of current animation. 
	 * @param row = which row from the spritesheet is used
	 * @param time = used to determine which frame of the animation to use
	 */
	
	
	public long getLastPreviousSet() {
		return lastPreviousSet;
	}

	public Texture getCandyIndicator() {
		return candyIndicator;
	}




	public void setCandyIndicator(Texture candyIndicator) {
		this.candyIndicator = candyIndicator;
	}

	public void setGoAround(int enemyId, Vector2 path) {
		if(goAround.containsKey(enemyId)) {
			goAround.get(enemyId).add(path);
			
			if(goAround.get(enemyId).size()>5) {
				goAround.get(enemyId).remove(0);
			}
			
		}else {
	     
		 ArrayList<Vector2> coords = new ArrayList<Vector2>();
	     coords.add(path);
		 goAround.put(enemyId, coords );
		
		}
	}
	
	




	public int getCollectedCandy() {
		return collectedCandy;
	}




	public void setCollectedCandy(int collectedCandy) {
		this.collectedCandy = collectedCandy;
	}

	public void setLastPreviousSet(long lastPreviousSet) {
		this.lastPreviousSet = lastPreviousSet;
	}




	public TextureRegion getCurrentFrame( float time){
		
				
		if(this.dir == DIRECTION.UP){
			if(super.getxVel()== 0 && super.getyVel() == 0){
				return (TextureRegion)animations.getAnimation(2).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(2).getKeyFrame(time,true);
			}
		}
		if(this.dir == DIRECTION.DOWN){
			if(super.getxVel()== 0 && super.getyVel() == 0){
				return (TextureRegion)animations.getAnimation(0).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(0).getKeyFrame(time,true);
			}
				}
		if(this.dir == DIRECTION.LEFT){
			if(super.getxVel()== 0 && super.getyVel() == 0){
				return (TextureRegion)animations.getAnimation(6).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(6).getKeyFrame(time,true);
			}
		}
		if(this.dir == DIRECTION.RIGHT){
			if(super.getxVel()== 0 && super.getyVel() == 0){
				return (TextureRegion)animations.getAnimation(4).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(4).getKeyFrame(time,true);
			}	
		}
		return null;
		
		
	}
	
	public TextureRegion getCurrentAttackFrame( float time){
		
		if(this.dir == DIRECTION.UP){
			
				return (TextureRegion)animations.getAnimation(3,0.40f).getKeyFrame(time,true);	
			  
		}
		if(this.dir == DIRECTION.DOWN){
			
				return (TextureRegion)animations.getAnimation(1,0.40f).getKeyFrame(time,true);	
			
				}
		if(this.dir == DIRECTION.LEFT){
	
				return (TextureRegion)animations.getAnimation(7,0.40f).getKeyFrame(time,true);	
			
		}
		if(this.dir == DIRECTION.RIGHT){
			
				return (TextureRegion)animations.getAnimation(5,0.40f).getKeyFrame(time,true);	
		
		}
		return null;
		
	}

	
public boolean hasAnimationFinished( float time){
		
		if(this.dir == DIRECTION.UP){
			
				return (boolean)animations.getAnimation(3,0.40f).isAnimationFinished(time);	
			  
		}
		if(this.dir == DIRECTION.DOWN){
			
				return (boolean)animations.getAnimation(1,0.40f).isAnimationFinished(time);	
			
				}
		if(this.dir == DIRECTION.LEFT){
	
				return (boolean)animations.getAnimation(7,0.40f).isAnimationFinished(time);	
			
		}
		if(this.dir == DIRECTION.RIGHT){
			
				return (boolean)animations.getAnimation(5,0.40f).isAnimationFinished(time);	
		
		}
		return false;
		
	}

public float AttackDuration( float time){
	
	if(this.dir == DIRECTION.UP){
		
			return animations.getAnimation(3,0.40f).getAnimationDuration();	
		  
	}
	if(this.dir == DIRECTION.DOWN){
		
			return animations.getAnimation(1,0.40f).getAnimationDuration();	
		
			}
	if(this.dir == DIRECTION.LEFT){

			return animations.getAnimation(7,0.40f).getAnimationDuration();	
		
	}
	if(this.dir == DIRECTION.RIGHT){
		
			return animations.getAnimation(5,0.40f).getAnimationDuration();	
	
	}
	return 0f;
	
}

	public long getAttackAnimStart() {
	return attackAnimStart;
}


public void setAttackAnimStart(long attackAnimStart) {
	this.attackAnimStart = attackAnimStart;
}


	public boolean isAttacking() {
		return isAttacking;
	}
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
	public DIRECTION getDir() {
		return dir;
	}
	public void setDir(DIRECTION dir) {
		this.dir = dir;
	}
	public Animator getAnimations() {
		return animations;
	}
	public void setAnimations(Animator animations) {
		this.animations = animations;
	}
	public POWERUPTYPE getPowerupType() {
		return powerupType;
	}
	public void setPowerupType(POWERUPTYPE powerupType) {
		this.powerupType = powerupType;
	}
	public long getPowerupActiveTime() {
		return powerupActiveTime;
	}
	public void setPowerupActiveTime(long powerupActiveTime) {
		this.powerupActiveTime = powerupActiveTime;
	}
	public long getPowerupSetTime() {
		return powerupSetTime;
	}
	public void setPowerupSetTime(long powerupSetTime) {
		this.powerupSetTime = powerupSetTime;
	}
	public float getHasteVel() {
		return hasteVel;
	}
	public void setHasteVel(float hasteVel) {
		this.hasteVel = hasteVel;
	}
	public long getShootingCooldown() {
		return ShootingCooldown;
	}
	public void setShootingCooldown(long shootingCooldown) {
		ShootingCooldown = shootingCooldown;
	}
	public long getLastShot() {
		return LastShot;
	}
	public void setLastShot(long lastShot) {
		LastShot = lastShot;
	}
	
	public float getMiddleY(){return super.getY()+super.getHeight()/2;}
	public float getMiddleX(){return super.getX()+super.getWidth()/2;}


public void setPreviousX(float x) {
	if(previousX.size()<5 && !previousX.contains(x)) {
		previousX.add(x);
	}else {
		previousX.remove(0);
		previousX.add(x);
	}
}
public void setPreviousY(float y) {
	if(previousY.size()<5 && !previousY.contains(y)) {
		previousY.add(y);
	}else {
		previousY.remove(0);
		previousY.add(y);
	}
}
	
public float getPreviousX() {

	float x = 0f;
	
	x = previousX.get(0);	

	return x;
}

public float getPreviousY() {

	float y = 0f;
	  
		    y = previousY.get(0);
	
	return y;
}

}
