package world.ucode;

public interface Constants {

    int BOARD_WIDTH = 1000;
    int BOARD_HEIGHT = 350;

    int CHARACTER_X_POSITION = 30;
    int CHARACTER_Y_OFFSET = 50;

    int DELAY = 10;

    float GAME_SPEED_INITIAL = 3.0f;
    float GAME_SPEED_MAX = 10.0f;
    float GAME_SPEED_INCREMENT = 1.0f;
    int GAME_SPEED_GAP = 20;
    float CHARACTER_SPEED_FACTOR = 2.0f;

    int JUMP_WINDOW_WIDTH = 15;
    float JUMP_WIDTH_FACTOR = 0.9f;
    int JUMP_TOP_FACTOR = 15;
    float JUMP_STEP = 0.5f;

    int COUNT_TO_NEXT_INITIAL = 100;
    int COUNT_TO_NEXT_MIN = 300;
    int COUNT_TO_NEXT_MAX = 500;

    int COUNT_TO_NEXT_CLOUD_INITIAL = 50;
    int COUNT_TO_NEXT_CLOUD_MIN = 200;
    int COUNT_TO_NEXT_CLOUD_MAX = 300;
    int CLOUD_Y_MIN = 120;
    int CLOUD_Y_MAX = 220;
}
