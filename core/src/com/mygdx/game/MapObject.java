package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class MapObject extends SpriteCommons {
	private long timeAlive;
	private long spawnTime;
	private Texture Graphic;
	private OBJECTTYPE type;
	private float maxRadius;
	
public MapObject(int width, int height, float x, float y, float xVel, float yVel, long timeAlive, Texture Graphic, OBJECTTYPE type) {
		super(width, height, x, y, xVel, yVel);
		this.timeAlive = timeAlive;
		this.Graphic = Graphic;
		this.type = type;
		spawnTime = 0;
		maxRadius = 0;
	}





public enum OBJECTTYPE{
	HAZARD,INTERACTABLE,EXPANDER,FOLLOWER
}





public float getMaxRadius() {
	return maxRadius;
}





public void setMaxRadius(float maxRadius) {
	this.maxRadius = maxRadius;
}





public long getTimeAlive() {
	return timeAlive;
}





public void setTimeAlive(long timeAlive) {
	this.timeAlive = timeAlive;
}





public Texture getGraphic() {
	return Graphic;
}





public void setGraphic(Texture graphic) {
	Graphic = graphic;
}





public OBJECTTYPE getType() {
	return type;
}





public void setType(OBJECTTYPE type) {
	this.type = type;
}





public long getSpawnTime() {
	return spawnTime;
}





public void setSpawnTime(long spawnTime) {
	this.spawnTime = spawnTime;
}

public float getMiddleY(){return super.getY()+super.getHeight()/2;}
public float getMiddleX(){return super.getX()+super.getWidth()/2;}



}
