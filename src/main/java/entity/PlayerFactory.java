package entity;

import interface_adapter.factories.PlayerFactoryInterface;

import java.util.List;

/**
 * Factory for creating Player objects.
 */
public class PlayerFactory implements PlayerFactoryInterface {

    public Player create() {
        return new Player();
    }

    public Player create(List<Collectibles> inventory) {
        return new Player(inventory);
    }
}
