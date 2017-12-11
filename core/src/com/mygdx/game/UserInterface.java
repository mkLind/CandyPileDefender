package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
	private TextButton button;
	private Button musicButton;
	private BitmapFont font;
	final Core game;

	public UserInterface(final Core game) {
		this.game = game;
		this.font = game.font;
		manager = game.getLoader().getManager();
		mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	}

	public Label newLabel(String name, float x, float y, int align, float width) {
		Label.LabelStyle style = new LabelStyle();
		style.font = mySkin.getFont("font-title");
		if (name.substring(0, 2).equals("Yo")) {
			label = new Label(name, mySkin, "title");
		} else if (name.substring(0, 2).equals("Jo")) {
			label = new Label(name, mySkin, "title");
			label.setText(
					"Protect your candy pile from the other kids who want to steal your candy! Move around with A, S, D, W keys and shoot with the mouse.");
		} else {
			label = new Label(name, style);
		}
		label.setWrap(true);
		label.setPosition(x, y);
		label.setAlignment(align);
		label.setWidth(width);
		return label;

	}

	public ProgressBar newHealthBar(float min, float max, float stepSize, boolean vertical, ProgressBarStyle style,
			float x, float y) {
		// The bar background
		drawable = new TextureRegionDrawable(new TextureRegion(manager.get("HealthbarBackGround.png", Texture.class)));
		healthBar = new ProgressBar(min, max, stepSize, vertical, style);
		healthBar.getStyle().background = drawable;

		// The bar knob
		Pixmap pixmap = new Pixmap(0, 35, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		healthBar.getStyle().knob = drawable;
		pixmap.dispose();

		// The bar foreground
		drawable = new TextureRegionDrawable(new TextureRegion(manager.get("Healthbar.png", Texture.class)));
		healthBar.getStyle().knobBefore = drawable;

		healthBar.setWidth(Gdx.graphics.getWidth() / 5);
		//healthBar.setHeight(19);
		healthBar.getStyle().background.setMinHeight(Gdx.graphics.getHeight() / 25);
		healthBar.getStyle().knobBefore.setMinHeight(Gdx.graphics.getHeight() / 25);
		healthBar.getStyle().knob.setMinHeight(Gdx.graphics.getHeight() / 25);
		//healthBar.setAnimateDuration(0.5f);
		healthBar.setValue(1f);
		healthBar.setPosition(x, y);
		return healthBar;
	}

	public Image newWarning(float x, float y) {
		warningTexture = manager.get("warning.png", Texture.class);
		region = new TextureRegion(warningTexture, 11, 57);
		warning = new Image(region);
		warning.setPosition(x, y);
		warning.setVisible(false);
		return warning;

	}

	public TextButton newButton(String name, float width, float x, float y) {
		button = new TextButton(name, mySkin, "round");
		button.setWidth(width);
		button.setPosition(x - button.getWidth() / 2, y - button.getHeight() / 2);

		return button;
	}

	public Button newSoundButton(float width, float x, float y) {
		//ButtonStyle buttonStyle = mySkin.get("sound", ButtonStyle.class);
		musicButton = new Button(mySkin, "sound");
		musicButton.setChecked(game.getLoader().getMasterVolume());
		musicButton.setWidth(width);
		musicButton.setHeight(width);
		musicButton.setPosition(x - musicButton.getWidth() / 2, y - musicButton.getHeight() / 2);
		return musicButton;
	}

	public Dialog newCreditsDialog() {
		Dialog dialog = new Dialog("", mySkin);
		dialog.getBackground().setMinWidth(500f);
		dialog.getBackground().setMinHeight(500f);
		Label text = new Label("Shade UI Skin by Raymond 'Raeleus' Buckley / CC BY 4.0\nBackground Music by PlayOnLoop.com " + "\n" +
						"Licensed under Creative Commons By Attribution 4.0\nSound Effects by NoiseForFun.com" + " \n"+ 
				"Licensed under the CC-BY-ND 3.0 Attribution license " + "\n" + "Programming: Tommi Hilanne, Teppo Huhtala, Markus Lindberg" + "\n" + "Graphics: Sanne Jelink" 
						+ "\n" + "Sounds: Maria Kejonen", mySkin);
		text.setWrap(true);
		text.setAlignment(Align.center);
		dialog.text(text);
		
		//dialog.align(Align.top);
		dialog.button("Close", true); // sends "true" as the result
		dialog.key(Keys.ENTER, true); // sends "true" when the ENTER key is pressed
		return dialog;
	}

	public Dialog newPauseDialog() {
		Dialog dialog = new Dialog("", mySkin) {
			protected void result(Object object) {
				if (object.equals(true)) {
					Gdx.app.exit();
				} else {
					Gdx.graphics.setContinuousRendering(true);
					game.getLoader().setGamePaused(false);
				}
			}
		};
		dialog.getBackground().setMinWidth(400f);
		dialog.getBackground().setMinHeight(300f);
		Label text = new Label("Are you sure you want to quit the game?", mySkin);
		text.setWrap(true);
		text.setAlignment(Align.center);
		dialog.text(text);
		dialog.button("Continue", false); // sends "false" as the result
		dialog.button("Quit", true);
		dialog.key(Keys.ENTER, false);
		return dialog;
	}
	
	public Dialog newInstructionsDialog(Image instr) {
		Dialog dialog = new Dialog("", mySkin);
		dialog.getBackground().setMinWidth(600f);
		dialog.getBackground().setMinHeight(600f);
		dialog.getContentTable().add(instr);
		dialog.button("Close", true); 
		dialog.key(Keys.ENTER, true); 
		return dialog;
	}
}
