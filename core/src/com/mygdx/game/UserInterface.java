package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class UserInterface {
	
	private Skin mySkin;
	private AssetManager manager;
	private Label label;
	
	private ProgressBar healthBar;
	private TextureRegionDrawable drawable;
	
	private Texture warningTexture;
	private TextureRegion region;
	private Image warning;
	private TextButton playButton;
	
	public UserInterface(final Core game) {
		manager = game.getLoader().getManager();
		mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	}
	
	public Label newLabel(String name, float x, float y, int align, float width) {
		label = new Label(name, mySkin);
		label.setPosition(x, y);
		label.setAlignment(align);
		label.setWidth(width);
		return label;
		
	}
	
	public ProgressBar newHealthBar(float min, float max, float stepSize, boolean vertical, ProgressBarStyle style, float x, float y) {
		//The bar background
		drawable = new TextureRegionDrawable(new TextureRegion(manager.get("HealthbarBackGround.png", Texture.class)));
		healthBar = new ProgressBar(min, max, stepSize, vertical, style);
		healthBar.getStyle().background = drawable;
		
		//The bar knob
		Pixmap pixmap = new Pixmap(0, 19, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		healthBar.getStyle().knob = drawable;
		pixmap.dispose();
		
		//The bar foreground
		drawable = new TextureRegionDrawable(
				new TextureRegion(manager.get("Healthbar.png", Texture.class)));
		healthBar.getStyle().knobBefore = drawable;

		healthBar.setWidth(160);
//		healthBar.setHeight(19);
		healthBar.getStyle().background.setMinHeight(19);
		healthBar.getStyle().knobBefore.setMinHeight(19);
		healthBar.getStyle().knob.setMinHeight(19);
//		healthBar.setAnimateDuration(1f);
		healthBar.setValue(1f);
		healthBar.setPosition(x, y);
		return healthBar;
	}
	
	public Image newWarning(float x, float y) {
		warningTexture = manager.get("warning2.png", Texture.class);
		region = new TextureRegion(warningTexture, 7, 36);
		warning = new Image(region);
		warning.setPosition(x, y);
		warning.setVisible(false);
		return warning;
		
	}
	
	public TextButton newPlayButton(String name, float width, float x, float y) {
		playButton = new TextButton(name, mySkin);
		playButton.setWidth(width);
        playButton.setPosition(x - playButton.getWidth()/2, y  - playButton.getHeight()/3);
        return playButton;
	}
}
