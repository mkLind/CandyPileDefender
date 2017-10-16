package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private float hasteVel;
	
	
	
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
	public TextureRegion getCurrentFrame( float time){
		
				
		if(this.dir == DIRECTION.UP){
			if(super.getxVel()== 0 && super.getyVel() == 0){
				return (TextureRegion)animations.getAnimation(1).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(1).getKeyFrame(time,true);
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
				return (TextureRegion)animations.getAnimation(3).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(3).getKeyFrame(time,true);
			}
		}
		if(this.dir == DIRECTION.RIGHT){
			if(super.getxVel()== 0 && super.getyVel() == 0){
				return (TextureRegion)animations.getAnimation(2).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(2).getKeyFrame(time,true);
			}	
		}
		return null;
		
		
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
	
	

}
