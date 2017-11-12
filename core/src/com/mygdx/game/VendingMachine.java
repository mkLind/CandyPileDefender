package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class VendingMachine extends SpriteCommons {
	private Texture machine;
	private long candyDistributed;
	public VendingMachine(int width, int height, float x, float y, float xVel, float yVel, Texture machine) {
		super(width, height, x, y, xVel, yVel);
		this.machine = machine;
		candyDistributed = 0;
		
		
	}

	public Texture getGraphic() {
		return machine;
	}
	
	public int distributeCandy(Rectangle player) {
		int additionToCandy = 0;

		if(super.getHitbox().overlaps(player) && TimeUtils.timeSinceMillis(candyDistributed)>2000) {
			additionToCandy = 1;
		
			candyDistributed = TimeUtils.millis();
		}
		return additionToCandy;
		
		
		
	}
	

	
	
}
