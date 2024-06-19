package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ImageView;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.TextView;

public class ChooseTextureScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;

    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ImageView image;
    ButtonView skin1;
    ButtonView skin2;
    ButtonView skin3;
    ButtonView skin4;
    ButtonView skin5;
    ButtonView skin6;
    ButtonView returnButton;

    public ChooseTextureScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleTextView = new TextView(myGdxGame.largeWhiteFont, 200, 1120, "Choose skin");

        image = new ImageView(280, 900, GameResources.SHIP_IMG_PATH);
        skin1 = new ButtonView(80, 650, 150, 150, GameResources.SHIP1_BACKGROUND_IMG_PATH);
        skin2 = new ButtonView(280, 650, 150, 150, GameResources.SHIP2_BACKGROUND_IMG_PATH);
        skin3 = new ButtonView(480, 650, 150, 150, GameResources.SHIP3_BACKGROUND_IMG_PATH);
        skin4 = new ButtonView(80, 440, 150, 150, GameResources.SHIP4_BACKGROUND_IMG_PATH);
        skin5 = new ButtonView(280, 440, 150, 150, GameResources.SHIP5_BACKGROUND_IMG_PATH);
        skin6 = new ButtonView(480, 440, 150, 150, GameResources.SHIP6_BACKGROUND_IMG_PATH);

        returnButton = new ButtonView(
                280, 250,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "return"
        );
    }

    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        titleTextView.draw(myGdxGame.batch);

        image.draw(myGdxGame.batch);
        skin1.draw(myGdxGame.batch);
        skin2.draw(myGdxGame.batch);
        skin3.draw(myGdxGame.batch);
        skin4.draw(myGdxGame.batch);
        skin5.draw(myGdxGame.batch);
        skin6.draw(myGdxGame.batch);

        returnButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (skin1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                GameResources.SHIP_IMG_PATH = GameResources.SHIP1_IMG_PATH;
                image.setTexture(GameResources.SHIP1_IMG_PATH);
            }
            if (skin2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                GameResources.SHIP_IMG_PATH = GameResources.SHIP2_IMG_PATH;
                image.setTexture(GameResources.SHIP2_IMG_PATH);
            }
            if (skin3.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                GameResources.SHIP_IMG_PATH = GameResources.SHIP3_IMG_PATH;
                image.setTexture(GameResources.SHIP3_IMG_PATH);
            }
            if (skin4.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                GameResources.SHIP_IMG_PATH = GameResources.SHIP4_IMG_PATH;
                image.setTexture(GameResources.SHIP4_IMG_PATH);
            }
            if (skin5.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                GameResources.SHIP_IMG_PATH = GameResources.SHIP5_IMG_PATH;
                image.setTexture(GameResources.SHIP5_IMG_PATH);
            }
            if (skin6.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                GameResources.SHIP_IMG_PATH = GameResources.SHIP6_IMG_PATH;
                image.setTexture(GameResources.SHIP6_IMG_PATH);
            }
        }
    }
}
