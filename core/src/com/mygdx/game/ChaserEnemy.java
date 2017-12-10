package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.StealingEnemy.DIRECTION;

public class ChaserEnemy extends SpriteCommons {
	

	private Animator animations;
	private DIRECTION dir;

	public ChaserEnemy(int width, int height, float x, float y, int HP) {
		super(width, height, x, y, 0f, 0f, HP);
	
		setDir(super.getxVel(), super.getyVel());
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
		System.out.println("Setting animations for chasers");
		System.out.println("ROWS: " + row  + " COLUMN: " + column + " FRAMETIME: " + frametime );
		animations = new Animator(row, column, frametime, spritesheet);
		
	}
     /*
	 * Method for changing the frame of current animation. 
	 * @param row = which row from the spritesheet is used
	 * @param time = used to determine which frame of the animation to use
	 */

	public TextureRegion getCurrentFrame( float time){
		
				System.out.println("@ getCurrentFrame()");
				
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
	
	public void setDir(Rectangle target) {
		if((Math.abs(target.getX() - super.getX())<target.getWidth() && target.getY()>super.getY() )) {
			dir = DIRECTION.UP;
		}
		 else if((Math.abs(target.getX() - super.getX())<target.getWidth() && target.getY()<super.getY() )) {
		    	dir = DIRECTION.DOWN;
			}
				else if(target.getX()>super.getX() && target.getY()>super.getY()) {
			dir = DIRECTION.LEFT;
		}
		else if(target.getX()<super.getX() && target.getY()>super.getY()) {
			dir = DIRECTION.RIGHT;
		}
		else if(target.getX()>super.getX() && target.getY()<super.getY()) {
			dir = DIRECTION.LEFT;
	}
		else if(target.getX()<super.getX() && target.getY()<super.getY()) {
			dir = DIRECTION.RIGHT;
	}
   
	}
	
	public void setDirc(Player target) {
		
	if(target.getPreviousX()>super.getX()) {
		dir = DIRECTION.LEFT;
		if(target.getPreviousY() - super.getY()>target.getHitbox().getHeight()) {
			dir = DIRECTION.UP;
		}else if(target.getPreviousY() - super.getY()<target.getHitbox().getHeight()) {
			dir = DIRECTION.DOWN;
		}
		
		
	}else {
		dir = DIRECTION.RIGHT;
		if(target.getPreviousY() - super.getY()>target.getHitbox().getHeight()) {
			dir = DIRECTION.UP;
		}else if(target.getPreviousY() - super.getY()<target.getHitbox().getHeight()) {
			dir = DIRECTION.DOWN;
		}
	}
		
		}
	public void setDir() {
		float xv = super.getxVel();
		float yv = super.getyVel();
		if(xv > 0 && yv > 0) {
			if(Math.abs(xv)>Math.abs(yv)) {
				dir = DIRECTION.LEFT;
			}else {
				dir = DIRECTION.UP;
			}
			
		}
		if(xv < 0 && yv > 0) {
			
			if(Math.abs(xv)>Math.abs(yv)) {
				dir = DIRECTION.RIGHT;
			}else {
				dir = DIRECTION.UP;
			}
		}
		if(xv > 0 && yv < 0) {
			if(Math.abs(xv)>Math.abs(yv)) {
				dir = DIRECTION.LEFT;
			}else {
				dir = DIRECTION.DOWN;
			}
		}
		if(xv < 0 && yv < 0) {
			if(Math.abs(xv)>Math.abs(yv)) {
				dir = DIRECTION.RIGHT;
			}else {
				dir = DIRECTION.DOWN;
			}
}
	}
	
	public void setDir(float xVel, float yVel) {
		float yplus = 0.5f;
		float yminus = -0.5f;
		
		float xplus = 0.5f;
		float xminus = -0.5f;
		
		if(yVel>=yplus && xVel<xplus) {
			dir = DIRECTION.UP;
		}
		else if(yVel>=yplus && xVel>=xplus) {
			dir = DIRECTION.RIGHT;
		}
		else if(yVel<yminus && xVel<xplus) {
			dir = DIRECTION.DOWN;
		}
		else if(yVel<yminus && xVel>=xplus) {
			dir = DIRECTION.RIGHT;
			
		}else if(yVel>=yplus && xVel<xminus) {
			dir = DIRECTION.LEFT;
		}
		else if(yVel>=yplus && xVel>xminus && xVel < xplus) {
			dir = DIRECTION.UP;
		}
		else if(yVel<yminus && xVel<xminus) {
			dir = DIRECTION.LEFT;
		}
		else if(yVel<yminus && xVel>xminus && xVel < xplus) {
			dir = DIRECTION.DOWN;
		}else if(yVel ==0 && xVel==0) {
			dir = DIRECTION.LEFT;
		}
		
		}
}

