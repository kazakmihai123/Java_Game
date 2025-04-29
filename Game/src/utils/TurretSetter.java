package utils;

import entities.Turret;
import graphics.GamePanel;

import java.util.ArrayList;

public class TurretSetter {

    GamePanel gp;
    ArrayList<Turret> turrets = new ArrayList<>();

    public TurretSetter(GamePanel gp)
    {
        this.gp = gp;
    }

    public void load(int currLevel) {
        turrets.clear();
        switch (currLevel)
        {
            case 1:
                turrets.add(new Turret(40, 17, Constants.ShootDirection.LEFT, 1000));
                turrets.add(new Turret(40, 18, Constants.ShootDirection.LEFT, 2700));
                turrets.add(new Turret(11, 26, Constants.ShootDirection.RIGHT, 1500));
                turrets.add(new Turret(11, 42, Constants.ShootDirection.UP, 3600));
                turrets.add(new Turret(12, 39, Constants.ShootDirection.UP, 4000));
                turrets.add(new Turret(13, 38, Constants.ShootDirection.UP, 1000));
                break;
            case 2:
                turrets.add(new Turret(48, 27, Constants.ShootDirection.DOWN, 1000));
                turrets.add(new Turret(46, 27, Constants.ShootDirection.DOWN, 2000));
                turrets.add(new Turret(47, 27, Constants.ShootDirection.DOWN, 3000));
                break;
        }
    }

    public void update() {
        for (Turret t : turrets)
            t.update(gp);
    }

}
