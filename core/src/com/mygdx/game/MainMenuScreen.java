package com.mygdx.game;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

	final Core game;
	private UserInterface userInterface;
	private BitmapFont font;
	private Skin mySkin;
	private TextButton playButton;
	private Stage stage;
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;

	private SimpleDateFormat format;
	private Date date;
	private String DateToStr;
	private String currentScore;
	private String tmp;
	// private String name;
	private Music ambience;

	public MainMenuScreen(final Core game) {
		this.game = game;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		userInterface = new UserInterface(game);

		this.font = game.font;
		mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		// mySkin.getFont("font-label").getData().setScale(1.5f);

		ambience = game.getLoader().getManager().get("Music/POL-horror-ambience-2-short_16bit.wav", Music.class);
		ambience.setLooping(true);
		ambience.play();

		playButton = userInterface.newPlayButton("Play", Gdx.graphics.getWidth() / 3, Gdx.graphics.getWidth() / 3,
				Gdx.graphics.getHeight() / 2);
		playButton.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				dispose();
				ambience.stop();
				game.getLoader().setScore(0);
				game.setScreen(new Updater(game));
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		stage.addActor(playButton);

		// game.getLoader().setScore(30);

		/*
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

		/*
		 * name = Base64Coder.encodeString("Tommi"); try { sendScores();
		 * }catch(Exception e) { System.out.println(e.getMessage()); }
		 */

		int row_height = Gdx.graphics.getWidth() / 10;
		int col_width = Gdx.graphics.getWidth() / 12;

		label1 = userInterface.newLabel("Highscores: ", Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() - row_height * 0.8f, Align.topRight, Gdx.graphics.getWidth() / 4);
		label2 = userInterface.newLabel("Your score: " + game.getLoader().getScore(), Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 9, Align.topLeft, Gdx.graphics.getWidth() / 4);
		label3 = userInterface.newLabel("1. " + game.getLoader().getHighScore(), Gdx.graphics.getWidth() / 1.5f,
				Gdx.graphics.getHeight() - row_height * 1.1f, Align.left, Gdx.graphics.getWidth() / 4);
		label4 = userInterface.newLabel("2. " + game.getLoader().getSecond(), Gdx.graphics.getWidth() / 1.5f,
				Gdx.graphics.getHeight() - row_height * 1.3f, Align.left, Gdx.graphics.getWidth() / 4);
		label5 = userInterface.newLabel("3. " + game.getLoader().getThird(), Gdx.graphics.getWidth() / 1.5f,
				Gdx.graphics.getHeight() - row_height * 1.5f, Align.left, Gdx.graphics.getWidth() / 4);
		label6 = userInterface.newLabel("4. " + game.getLoader().getFourth(), Gdx.graphics.getWidth() / 1.5f,
				Gdx.graphics.getHeight() - row_height * 1.7f, Align.left, Gdx.graphics.getWidth() / 4);
		label7 = userInterface.newLabel("5. " + game.getLoader().getFifth(), Gdx.graphics.getWidth() / 1.5f,
				Gdx.graphics.getHeight() - row_height * 1.9f, Align.left, Gdx.graphics.getWidth() / 4);
		
		stage.addActor(label1);
		stage.addActor(label2);
		stage.addActor(label3);
		stage.addActor(label4);
		stage.addActor(label5);
		stage.addActor(label6);
		stage.addActor(label7);

		if (game.getLoader().getScore() == 0) {
			label2.setText("");
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
				label3.setText("1. " + game.getLoader().getHighScore() + "  <-");
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
				label4.setText("2. " + game.getLoader().getSecond() + "  <-");
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
				label5.setText("3. " + game.getLoader().getThird() + "  <-");
				if (Integer.parseInt(game.getLoader().getFourth().split(" ")[0]) != 0) {
					label6.setText("4. " + game.getLoader().getFourth());
				}
				if (Integer.parseInt(game.getLoader().getFifth().split(" ")[0]) != 0) {
					label7.setText("5. " + game.getLoader().getFifth());
				}
			} else if (tmp == "fourth") {
				label2.setText("Your score: " + game.getLoader().getScore());
				label6.setText("4. " + game.getLoader().getFourth() + "  <-");
				if (Integer.parseInt(game.getLoader().getFifth().split(" ")[0]) != 0) {
					label7.setText("5. " + game.getLoader().getFifth());
				}
			} else if (tmp == "fifth") {
				label2.setText("Your score: " + game.getLoader().getScore());
				label7.setText("5. " + game.getLoader().getFifth() + "  <-");
			} else {
				label2.setText("Your score: " + game.getLoader().getScore());
			}
		}

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
	/*
	 * public void sendScores() { HttpRequestBuilder requestBuilder = new
	 * HttpRequestBuilder(); HttpRequest httpRequest =
	 * requestBuilder.newRequest().method(HttpMethods.POST).url(
	 * "http://ftp.thilanne.altervista.org/CandyPileDefender/addscore.php").content(
	 * "name="+name+"&score="+(Integer.toString(game.getLoader().getScore()))+
	 * "&hash=mkGZgaG0Gl").build(); HttpResponseListener httpResponseListener = new
	 * HttpResponseListener() {
	 * 
	 * @Override public void handleHttpResponse(HttpResponse httpResponse) {
	 * System.out.println(httpResponse.getHeaders());
	 * 
	 * }
	 * 
	 * @Override public void failed(Throwable t) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * @Override public void cancelled() { // TODO Auto-generated method stub
	 * 
	 * } }; Gdx.net.sendHttpRequest(httpRequest, httpResponseListener); } public
	 * void getScores() { HttpRequestBuilder requestBuilder = new
	 * HttpRequestBuilder(); HttpRequest httpRequest =
	 * requestBuilder.newRequest().method(HttpMethods.GET).url(
	 * "http://ftp.thilanne.altervista.org/CandyPileDefender/addscore.php").content(
	 * "name="+name+"&score="+(Integer.toString(game.getLoader().getScore()))+
	 * "&hash=mkGZgaG0Gl").build(); HttpResponseListener httpResponseListener = new
	 * HttpResponseListener() {
	 * 
	 * @Override public void handleHttpResponse(HttpResponse httpResponse) { String
	 * answer = httpResponse.getResultAsString();
	 * 
	 * }
	 * 
	 * @Override public void failed(Throwable t) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * @Override public void cancelled() { // TODO Auto-generated method stub
	 * 
	 * } }; Gdx.net.sendHttpRequest(httpRequest, httpResponseListener); }
	 *
	 * 
	 * 
	 * }else return ""; }
	 */
	/*
	 * public void sendScores() { HttpRequestBuilder requestBuilder = new
	 * HttpRequestBuilder(); HttpRequest httpRequest =
	 * requestBuilder.newRequest().method(HttpMethods.POST).url(
	 * "http://ftp.thilanne.altervista.org/CandyPileDefender/addscore.php").content(
	 * "name="+name+"&score="+(Integer.toString(game.getLoader().getScore()))+
	 * "&hash=mkGZgaG0Gl").build(); HttpResponseListener httpResponseListener = new
	 * HttpResponseListener() {
	 * 
	 * @Override public void handleHttpResponse(HttpResponse httpResponse) {
	 * System.out.println(httpResponse.getHeaders());
	 * System.out.println("name="+name+"&score="+(Integer.toString(game.getLoader().
	 * getScore()))+"&hash=mkGZgaG0Gl");
	 * 
	 * }
	 * 
	 * @Override public void failed(Throwable t) {
	 * System.out.println(t.getLocalizedMessage());
	 * 
	 * }
	 * 
	 * @Override public void cancelled() { System.out.println("cancelled");
	 * 
	 * } }; Gdx.net.sendHttpRequest(httpRequest, httpResponseListener); } public
	 * void getScores() { HttpRequestBuilder requestBuilder = new
	 * HttpRequestBuilder(); HttpRequest httpRequest =
	 * requestBuilder.newRequest().method(HttpMethods.GET).url(
	 * "http://ftp.thilanne.altervista.org/CandyPileDefender/addscore.php").content(
	 * "name="+name+"&no_lines="+(Integer.toString(5))+"&hash=mkGZgaG0Gl").build();
	 * HttpResponseListener httpResponseListener = new HttpResponseListener() {
	 * 
	 * @Override public void handleHttpResponse(HttpResponse httpResponse) { String
	 * answer = httpResponse.getResultAsString();
	 * 
	 * }
	 * 
	 * @Override public void failed(Throwable t) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * @Override public void cancelled() { // TODO Auto-generated method stub
	 * 
	 * } }; Gdx.net.sendHttpRequest(httpRequest, httpResponseListener); }
	 */

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);

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
