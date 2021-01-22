package world.ucode.sprites;

public class Cactus extends Obstacle {

    public enum Type {
        SMALL_ONE,
        SMALL_TWO,
        SMALL_THREE,
        BIG_ONE,
        BIG_TWO,
        BIG_THREE,
        BUSH
    }

    public Cactus(Type type) {
        String name = getFileName(type);
        addImage(name);
    }

    public String getFileName(Type type) {
        switch (type) {
            case SMALL_ONE:
                return "cactus_small_1.png";
            case SMALL_TWO:
                return "cactus_small_2.png";
            case SMALL_THREE:
                return "cactus_small_3.png";
            case BIG_ONE:
                return "cactus_big_1.png";
            case BIG_TWO:
                return "cactus_big_2.png";
            case BIG_THREE:
                return "cactus_big_3.png";
            default:
                return "cactus_bush_4.png";
        }
    }
}
