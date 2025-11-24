package use_case.collect_item;

import entity.Collectibles;
import entity.Scene;
import entity.Player;

public interface CollectItemDataAccessInterface {

    Scene getScene(String name);
    Player getPlayer();
    public void savePlayer(Player p);

    public void saveScene(Scene s);

}

