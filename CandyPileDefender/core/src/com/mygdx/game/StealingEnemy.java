package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class StealingEnemy extends SpriteCommons {
	
	private DIRECTION dir; 
	// private Animator animations;
	private Texture stealerTexture;
	
	public StealingEnemy(int width, int height, float x, float y,float xVel, float yVel) {
		super(width, height, x, y, xVel, yVel);
		
		stealerTexture = new Texture(Gdx.files.internal("C:\\CandyPile\\CandyPileDefender\\core\\assets\\stealTest.png"));

		// Looks the side of the spawn DOESN'T WORK
		if(x == 0 ) {
			
			dir = DIRECTION.RIGHT;			
			
		}else if(y == 0 ) {
			
			dir = DIRECTION.UP;
			
		}else if(y == (Gdx.graphics.getWidth() - width)) {
			
			dir = DIRECTION.LEFT;
			
		}else if(y == (Gdx.graphics.getHeight() - height)) {
			
			dir = DIRECTION.DOWN;		
		}
		
		// replace with pathfinding or smth, maybe
		
		//double hypot = Math.hypot(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//setxVel((float) (1.5f / hypot  * (Gdx.graphics.getWidth() - x)));
		//setyVel((float) (1.5f / hypot * (Gdx.graphics.getHeight() - y)));
		
	}
	
	public Texture getTexture() {
		return stealerTexture;
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
	
	
	/*
	// Sets all the animations
	public void setAnimations(int row, int column, float frametime, Texture spritesheet){
		animations = new Animator(row, column, frametime, spritesheet);
		
	}

	 * Method for changing the frame of current animation. 
	 * @param row = which row from the spritesheet is used
	 * @param time = used to determine which frame of the animation to use

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
		
		
	} */
}