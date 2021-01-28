package com.rdruzhchenko.board.game;

import com.rdruzhchenko.board.sprites.Cactus;
import com.rdruzhchenko.utils.Utils;

public class ObstacleFactory {

    public Cactus randomCactus() {
        int len = Cactus.Type.values().length - 1;
        int index = Utils.getRandomNumber(0, len);
        Cactus.Type type = Cactus.Type.values()[index];
        return new Cactus(type);
    }
}
