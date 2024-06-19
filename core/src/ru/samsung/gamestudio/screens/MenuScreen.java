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
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.TextView;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    MovingBackgroundView backgroundView;
    TextView titleView;
    TextView difficulty;
    ButtonView level1;
    ButtonView level2;
    ButtonView level3;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleView = new TextView(myGdxGame.largeWhiteFont, 180, 960, "Space Cleaner");
        difficulty = new TextView(myGdxGame.commonWhiteFont, 220, 870, "Choose the difficulty");
        level1 = new ButtonView(200, 770, 70, 70, GameResources.BUTTON_LEVEL1_CHECKED);
        level2 = new ButtonView(320, 770, 70, 70, GameResources.BUTTON_LEVEL2);
        level3 = new ButtonView(440, 770, 70, 70, GameResources.BUTTON_LEVEL3);
        startButtonView = new ButtonView(140, 646, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "settings");
        exitButtonView = new ButtonView(140, 456, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "exit");
    }

    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        titleView.draw(myGdxGame.batch);
        difficulty.draw(myGdxGame.batch);
        level1.draw(myGdxGame.batch);
        level2.draw(myGdxGame.batch);
        level3.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);
        settingsButtonView.draw(myGdxGame.batch);
        startButtonView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (level1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                if (GameSettings.LEVEL != 1){
                    //Меняем текстуру кнопки
                    level1.setTexture(GameResources.BUTTON_LEVEL1_CHECKED);
                    level2.setTexture(GameResources.BUTTON_LEVEL2);
                    level3.setTexture(GameResources.BUTTON_LEVEL3);
                    //Меняем настройки игры
                    GameSettings.LEVEL = 1;
                }
            }
            if (level2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                if (GameSettings.LEVEL != 2){
                    //Меняем текстуру кнопки
                    level1.setTexture(GameResources.BUTTON_LEVEL1);
                    level2.setTexture(GameResources.BUTTON_LEVEL2_CHECKED);
                    level3.setTexture(GameResources.BUTTON_LEVEL3);
                    //Меняем настройки игры
                    GameSettings.LEVEL = 2;
                    GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN = 1500;
                }
            }
            if (level3.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                if (GameSettings.LEVEL != 3){
                    //Меняем текстуру кнопки
                    level1.setTexture(GameResources.BUTTON_LEVEL1);
                    level2.setTexture(GameResources.BUTTON_LEVEL2);
                    level3.setTexture(GameResources.BUTTON_LEVEL3_CHECKED);
                    //Меняем настройки игры
                    GameSettings.LEVEL = 3;
                    GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN = 1000;
                    GameSettings.STARTING_KIT_APPEARANCE_COOL_DOWN = 10000;
                }
            }

            if (startButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.settingsScreen);
            }
        }
    }
}
