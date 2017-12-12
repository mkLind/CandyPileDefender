package com.mygdx.game;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen implements Screen {

	final Core game;
	private UserInterface userInterface;
	private BitmapFont font;
	private Skin mySkin;
	private TextButton playButton;
	private TextButton quitButton;
	private TextButton creditsButton;
	private TextButton instructionsButton;
	private Button muteButton;
	private Stage stage;
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Label label8;
	private Label label9;
	private List list;
	private ScrollPane instructions;
	private String[] labels;

	private SimpleDateFormat format;
	private Date date;
	private String DateToStr;
	private String currentScore;
	private String tmp;
	// private String name;
	private Music ambience;
	
	private Texture backgroundTexture;
	private TextureRegion backgroundRegion;
	private Image background;
	
	private Texture instrTexture;
	private TextureRegion instrRegion;
	private Image instr;
	private FitViewport view;
	
	public MainMenuScreen(final Core game) {
		this.game = game;
		Camera cam = new OrthographicCamera(1920,1080);
		view = new FitViewport(1920, 1080, cam);
		stage = new Stage(view);
		
		Gdx.input.setInputProcessor(stage);
		userInterface = new UserInterface(game);

		this.font = game.font;
		mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		// mySkin.getFont("font-label").getData().setScale(1.5f);

		ambience = game.getLoader().getManager().get("Music/POL-horror-ambience-2-short_16bit.ogg", Music.class);
		ambience.setLooping(true);
		
		backgroundTexture = game.getLoader().getManager().get("MainMenu.jpg", Texture.class);
		backgroundRegion = new TextureRegion(backgroundTexture, 1920, 1080);
		background = new Image(backgroundRegion);
		background.setPosition(0, 0);
		stage.addActor(background);
		
		instrTexture = game.getLoader().getManager().get("Instructions2.jpg", Texture.class);
		instrRegion = new TextureRegion(instrTexture, 514, 563);
		instr = new Image(instrRegion);
		
		float row_height = cam.viewportWidth / 10f;
		float col_width = cam.viewportWidth / 12f;
		
		//Playbutton
		playButton = userInterface.newButton("Play", cam.viewportWidth / 4, cam.viewportWidth / 3,
				cam.viewportHeight / 2);
		playButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				dispose();
				if (game.getLoader().getMasterVolume()) {
					ambience.stop();
				}
				game.getLoader().setScore(0);
				game.getLoader().setPileStolen(false);
				game.getLoader().setPlayerDead(false);
				game.setScreen(new Updater(game));
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		stage.addActor(playButton);

		//Instructions button
		instructionsButton = userInterface.newButton("Instructions", cam.viewportWidth / 4, cam.viewportWidth / 3,
				cam.viewportHeight / 2 - row_height / 4);
		instructionsButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Dialog dialog = userInterface.newInstructionsDialog(instr);
				dialog.show(stage);
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		stage.addActor(instructionsButton);
		
		//Creditsbutton
		creditsButton = userInterface.newButton("Credits", cam.viewportWidth / 4, cam.viewportWidth / 3,
				cam.viewportHeight / 2 - row_height / 2);
		creditsButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Dialog dialog = userInterface.newCreditsDialog();
				dialog.show(stage);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		stage.addActor(creditsButton);

		//Quitbutton
		quitButton = userInterface.newButton("Quit", cam.viewportWidth / 4, cam.viewportWidth / 3,
				cam.viewportHeight / 2 - row_height/1.333f);
		quitButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				dispose();
				Gdx.app.exit();
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		stage.addActor(quitButton);
		
		//Mutebutton
		muteButton = userInterface.newSoundButton(row_height / 3, cam.viewportWidth - row_height / 2,
				cam.viewportHeight / 15);
		muteButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (game.getLoader().getMasterVolume()) {
					game.getLoader().setMasterVolume(false);
				} else {
					game.getLoader().setMasterVolume(true);
				}
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		stage.addActor(muteButton);

		/*
		 * game.getLoader().setScore(30);
		 * 
		 * game.getLoader().setHighScore("0     00/00/0000");
		 * game.getLoader().setSecond("0     00/00/0000");
		 * game.getLoader().setThird("0     00/00/0000");
		 * game.getLoader().setFourth("0     00/00/0000");
		 * game.getLoader().setFifth("0     00/00/0000");
		 */

		date = new Date(TimeUtils.millis());
		format = new SimpleDateFormat("dd/MM/yyyy");
		DateToStr = format.format(date);

		currentScore = Integer.toString(game.getLoader().getScore()) + "     " + DateToStr;
		tmp = checkScores();

		label1 = userInterface.newLabel("Highscores: ", cam.viewportWidth / 2,
				cam.viewportHeight - row_height * 0.8f, Align.topRight, cam.viewportWidth / 4);
		label2 = userInterface.newLabel("Your score: " + game.getLoader().getScore(), cam.viewportWidth / 4.1f,
				cam.viewportHeight - row_height * 2.4f, Align.topLeft, cam.viewportWidth / 5.5f);
		label3 = userInterface.newLabel("1. " + game.getLoader().getHighScore(), cam.viewportWidth / 1.5f,
				cam.viewportHeight - row_height * 1.1f, Align.left, cam.viewportWidth / 4);
		label4 = userInterface.newLabel("2. " + game.getLoader().getSecond(), cam.viewportWidth / 1.5f,
				cam.viewportHeight - row_height * 1.3f, Align.left, cam.viewportWidth / 4);
		label5 = userInterface.newLabel("3. " + game.getLoader().getThird(), cam.viewportWidth / 1.5f,
				cam.viewportHeight - row_height * 1.5f, Align.left, cam.viewportWidth / 4);
		label6 = userInterface.newLabel("4. " + game.getLoader().getFourth(), cam.viewportWidth / 1.5f,
				cam.viewportHeight - row_height * 1.7f, Align.left, cam.viewportWidth / 4);
		label7 = userInterface.newLabel("5. " + game.getLoader().getFifth(), cam.viewportWidth / 1.5f,
				cam.viewportHeight - row_height * 1.9f, Align.left, cam.viewportWidth / 4);
		label8 = userInterface.newLabel("Yo", cam.viewportWidth / 4.1f, cam.viewportHeight - row_height * 2.1f,
				Align.topLeft, cam.viewportWidth / 5.5f);
		label9 = userInterface.newLabel("Jo", cam.viewportWidth, cam.viewportHeight,
				Align.topLeft, cam.viewportWidth / 4);

		stage.addActor(label2); //current score
		stage.addActor(label8); //end result of the game
		label2.setAlignment(Align.center);
		label8.setAlignment(Align.center);
		
		if(game.getLoader().getPileStolen()) {
			label8.setText("The whole pile was stolen!");
		}
		if(game.getLoader().getPlayerDead()) {
			label8.setText("You died!");
		}
		
		if (game.getLoader().getScore() == 0) {
			label2.setText("");
			label2.setVisible(false);
			label8.setVisible(false);
		} else {
			label2.setVisible(true);
			label8.setVisible(true);
		}
		if (Integer.parseInt(game.getLoader().getHighScore().split(" ")[0]) == 0) {
			label3.setText("1. ");
		}
		if (Integer.parseInt(game.getLoader().getSecond().split(" ")[0]) == 0) {
			label4.setText("2. ");
		}
		if (Integer.parseInt(game.getLoader().getThird().split(" ")[0]) == 0) {
			label5.setText("3. ");
		}
		if (Integer.parseInt(game.getLoader().getFourth().split(" ")[0]) == 0) {
			label6.setText("4. ");
		}
		if (Integer.parseInt(game.getLoader().getFifth().split(" ")[0]) == 0) {
			label7.setText("5. ");
		}
		if (game.getLoader().getScore() != 0) {
			if (tmp == "first") {
				label2.setText("New highscore!: " + game.getLoader().getScore());
				label3.setText("1. " + game.getLoader().getHighScore());
				if (Integer.parseInt(game.getLoader().getSecond().split(" ")[0]) != 0) {
					label4.setText("2. " + game.getLoader().getSecond());
				}
				if (Integer.parseInt(game.getLoader().getThird().split(" ")[0]) != 0) {
					label5.setText("3. " + game.getLoader().getThird());
				}
				if (Integer.parseInt(game.getLoader().getFourth().split(" ")[0]) != 0) {
					label6.setText("4. " + game.getLoader().getFourth());
				}
				if (Integer.parseInt(game.getLoader().getFifth().split(" ")[0]) != 0) {
					label7.setText("5. " + game.getLoader().getFifth());
				}
			} else if (tmp == "second") {
				label2.setText("Your score: " + game.getLoader().getScore());
				label4.setText("2. " + game.getLoader().getSecond());
				if (Integer.parseInt(game.getLoader().getThird().split(" ")[0]) != 0) {
					label5.setText("3. " + game.getLoader().getThird());
				}
				if (Integer.parseInt(game.getLoader().getFourth().split(" ")[0]) != 0) {
					label6.setText("4. " + game.getLoader().getFourth());
				}
				if (Integer.parseInt(game.getLoader().getFifth().split(" ")[0]) != 0) {
					label7.setText("5. " + game.getLoader().getFifth());
				}
			} else if (tmp == "third") {
				label2.setText("Your score: " + game.getLoader().getScore());
				label5.setText("3. " + game.getLoader().getThird());
				if (Integer.parseInt(game.getLoader().getFourth().split(" ")[0]) != 0) {
					label6.setText("4. " + game.getLoader().getFourth());
				}
				if (Integer.parseInt(game.getLoader().getFifth().split(" ")[0]) != 0) {
					label7.setText("5. " + game.getLoader().getFifth());
				}
			} else if (tmp == "fourth") {
				label2.setText("Your score: " + game.getLoader().getScore());
				label6.setText("4. " + game.getLoader().getFourth());
				if (Integer.parseInt(game.getLoader().getFifth().split(" ")[0]) != 0) {
					label7.setText("5. " + game.getLoader().getFifth());
				}
			} else if (tmp == "fifth") {
				label2.setText("Your score: " + game.getLoader().getScore());
				label7.setText("5. " + game.getLoader().getFifth());
			} else {
				label2.setText("Your score: " + game.getLoader().getScore());
			}
		}

		labels = new String[7];
		labels[0] = label1.getText().toString();
		labels[1] = "";
		labels[2] = label3.getText().toString();
		labels[3] = label4.getText().toString();
		labels[4] = label5.getText().toString();
		labels[5] = label6.getText().toString();
		labels[6] = label7.getText().toString();

		mySkin.getFont("font-label").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		mySkin.getFont("font-label").getData().setScale(1.7f);
		list = new List(mySkin, "dimmed");
		list.setSize(cam.viewportWidth / 4, cam.viewportWidth / 7);
		list.setPosition(cam.viewportWidth / 1.35f, cam.viewportHeight - row_height * 1.75f);
		list.setItems(labels);

		if (game.getLoader().getScore() != 0) {
			if (tmp == "first") {
				list.setSelectedIndex(2);
			} else if (tmp == "second") {
				list.setSelectedIndex(3);
			} else if (tmp == "third") {
				list.setSelectedIndex(4);
			} else if (tmp == "fourth") {
				list.setSelectedIndex(5);
			} else if (tmp == "fifth") {
				list.setSelectedIndex(6);
			} else {
				list.setSelectedIndex(0);
			}
		} else {
			list.setSelectedIndex(0);
		}
		stage.addActor(list);
	
		/*
		instructions = new ScrollPane(label9, mySkin);
		instructions.setSize(cam.viewportWidth / 3.50f, cam.viewportHeight / 5.5f);
		instructions.setPosition(cam.viewportWidth /  5.25f, cam.viewportHeight - row_height * 4.85f);
		stage.addActor(instructions);*/

	}

	public String checkScores() {

		if (game.getLoader().getScore() > Integer.parseInt(game.getLoader().getHighScore().split(" ")[0])) {
			game.getLoader().setFifth(game.getLoader().getFourth());
			game.getLoader().setFourth(game.getLoader().getThird());
			game.getLoader().setThird(game.getLoader().getSecond());
			game.getLoader().setSecond(game.getLoader().getHighScore());
			game.getLoader().setHighScore(currentScore);
			return "first";
		} else if (game.getLoader().getScore() <= Integer.parseInt(game.getLoader().getHighScore().split(" ")[0])
				&& game.getLoader().getScore() > Integer.parseInt(game.getLoader().getSecond().split(" ")[0])) {
			game.getLoader().setFifth(game.getLoader().getFourth());
			game.getLoader().setFourth(game.getLoader().getThird());
			game.getLoader().setThird(game.getLoader().getSecond());
			game.getLoader().setSecond(currentScore);
			return "second";

		} else if (game.getLoader().getScore() <= Integer.parseInt(game.getLoader().getSecond().split(" ")[0])
				&& game.getLoader().getScore() > Integer.parseInt(game.getLoader().getThird().split(" ")[0])) {
			game.getLoader().setFifth(game.getLoader().getFourth());
			game.getLoader().setFourth(game.getLoader().getThird());
			game.getLoader().setThird(currentScore);
			return "third";

		} else if (game.getLoader().getScore() <= Integer.parseInt(game.getLoader().getThird().split(" ")[0])
				&& game.getLoader().getScore() > Integer.parseInt(game.getLoader().getFourth().split(" ")[0])) {
			game.getLoader().setFifth(game.getLoader().getFourth());
			game.getLoader().setFourth(currentScore);
			return "fourth";

		} else if (game.getLoader().getScore() <= Integer.parseInt(game.getLoader().getFourth().split(" ")[0])
				&& game.getLoader().getScore() > Integer.parseInt(game.getLoader().getFifth().split(" ")[0])) {
			game.getLoader().setFifth(currentScore);
			return "fifth";

		} else
			return "";
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		if (game.getLoader().getMasterVolume()) {
			ambience.play();
		} else {
			ambience.pause();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height);
		stage.getViewport().update(width, height, true);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();

	}

}
