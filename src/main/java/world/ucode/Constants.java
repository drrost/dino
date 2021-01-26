package world.ucode;

public interface Constants {

    int BOARD_WIDTH = 1000;
    int BOARD_HEIGHT = 350;

    int CHARACTER_X_POSITION = 30;
    int CHARACTER_Y_OFFSET = 50;

    int DELAY = 10;

    float GAME_SPEED_INITIAL = 3.0f;
    float GAME_SPEED_INCREMENT = 1.0f;
    float CHARACTER_SPEED_FACTOR = 2.0f;

    int JUMP_WINDOW_WIDTH = 15;
    float JUMP_WIDTH_FACTOR = 0.9f;
    int JUMP_TOP_FACTOR = 15;
    float JUMP_STEP = 0.5f;

    int COUNT_TO_NEXT_INITIAL = 100;
    int COUNT_TO_NEXT_MIN = 300;
    int COUNT_TO_NEXT_MAX = 500;
}
