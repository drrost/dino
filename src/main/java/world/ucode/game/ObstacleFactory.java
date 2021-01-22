package world.ucode.game;

import world.ucode.sprites.Cactus;
import world.ucode.utils.Utils;

public class ObstacleFactory {

    public Cactus randomCactus() {
        int len = Cactus.Type.values().length - 1;
        int index = Utils.getRandomNumber(0, len);
        Cactus.Type type = Cactus.Type.values()[index];
        return new Cactus(type);
    }
}
