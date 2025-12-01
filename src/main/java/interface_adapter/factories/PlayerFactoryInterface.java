package interface_adapter.factories;

import java.util.List;

import entity.Collectibles;
import entity.Player;

/**
 * Factory interface for creating Player objects.
 */
public interface PlayerFactoryInterface {

    /**
     * Creates a new Player with an empty inventory.
     *
     * @return a new Player instance
     */
    Player create();

    /**
     * Creates a new Player with a pre-defined inventory.
     *
     * @param inventory the list of Collectibles for the Player
     * @return a new Player instance with the specified inventory
     */
    Player create(List<Collectibles> inventory);
}
