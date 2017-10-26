package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class StealingEnemy extends SpriteCommons {
	
	private DIRECTION dir; 
	private Animator animations;
	// private Texture stealerTexture;
	
	public StealingEnemy(int width, int height, float x, float y, int HP) {
		super(width, height, x, y, 0f, 0f, HP);
		dir = DIRECTION.DOWN;
	
	}
	
	/*
	public Texture getTexture() {
		return stealerTexture;
	}
	*/
	
	public enum DIRECTION{
		UP,DOWN,LEFT,RIGHT
	}

	public DIRECTION getDir() {
		return dir;
	}
	public void setDir(DIRECTION dir) {
		this.dir = dir;
	}

	
	
	// Sets all the animations
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
				return (TextureRegion)animations.getAnimation(2).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(2).getKeyFrame(time,true);
			}
		}
		if(this.dir == DIRECTION.RIGHT){
			if(super.getxVel()== 0 && super.getyVel() == 0){
				return (TextureRegion)animations.getAnimation(3).getKeyFrame(0,true);	
			}else{
			return (TextureRegion)animations.getAnimation(3).getKeyFrame(time,true);
			}	
		}
		return null;
		
		
	} 
}
