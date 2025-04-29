package objects;

import utils.ResourceLoader;

public class OBJ_Door extends SuperObject {

    public OBJ_Door() {
        name = "tiles/map1/door";

        ResourceLoader res = new ResourceLoader("/tiles/map1/door/castle_door.png", 1, 6);
        image = res.getFrame(0, 1);

        // System.out.println(image);

        collision = true; // Blochează trecerea până la coliziune + interacțiune
    }
}
