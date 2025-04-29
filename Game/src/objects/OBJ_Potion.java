package objects;

import utils.ResourceLoader;

public class OBJ_Potion extends SuperObject {

    private final int healAmount = 20;

    public OBJ_Potion() {
        name = "potion";

        ResourceLoader res = new ResourceLoader("/tiles/map2/Dungeon_Tileset.png", 10, 10);
        image = res.getFrame(8, 9);

        collision = true; // Se declanșează la contact
    }

    public int getHealAmount() {
        return healAmount;
    }
}
