package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Projectile extends SpriteCommons {
	private Texture t;
	private long TimeAlive;
	private long currentTime;
	private float angle;
	private float targetX;
	private float targetY;
	

	public Projectile(int width, int height, float x, float y, float xVel, float yVel, Texture t) {
		super(width, height, x, y, xVel, yVel);
		this.t = t;
		currentTime = 0;
		TimeAlive = 0;
		angle = 0f;
		targetX = 0;
		targetY = 0;
		
		// TODO Auto-generated constructor stub
	}

	public float getTargetX() {
		return targetX;
	}

	public void setTargetX(float targetX) {
		this.targetX = targetX;
	}

	public float getTargetY() {
		return targetY;
	}

	public void setTargetY(float targetY) {
		this.targetY = targetY;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Texture getT() {
		return t;
	}

	public void setT(Texture t) {
		this.t = t;
	}

	public long getTimeAlive() {
		return TimeAlive;
	}

	public void setTimeAlive(long timeAlive) {
		TimeAlive = timeAlive;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long l) {
		this.currentTime = l;
	}
	
	

}
