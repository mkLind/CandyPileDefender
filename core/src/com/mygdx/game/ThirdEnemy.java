package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.StealingEnemy.DIRECTION;

public class ThirdEnemy extends SpriteCommons {
	
	private DIRECTION dir; 
	private Animator animations;
	
	/*
	public ThirdEnemy(int width, int height, float x, float y, float xVel, float yVel, int HP) {
		super(width, height, x, y, xVel, yVel, HP);
		// TODO Auto-generated constructor stub
	}
	*/

	public ThirdEnemy(int width, int height, float x, float y, int HP) {
		super(width, height, x, y, 0, 0, HP);
		
		if (y < 10) {
			dir = DIRECTION.UP;
		} else if (x < 10) {
			dir = DIRECTION.RIGHT;			
		} else if (x > 100) {
			dir = DIRECTION.LEFT;	
		}else {
			dir = DIRECTION.DOWN;
		}
		
	}

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
