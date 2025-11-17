package use_case.game;

import entity.ClickableObject;
import entity.Scene;

import java.util.*;

public class GameManager {

    private final Map<String, ClickRule> rulesByObjectName;

    public GameManager(Map<String, ClickRule> rulesByObjectName) {
        this.rulesByObjectName = new HashMap<>(rulesByObjectName);
    }

    /** Fallback to NONE if an object has no rule. */
    public ClickRule ruleFor(ClickableObject obj) {
        return rulesByObjectName.getOrDefault(
                obj.getName(),
                new ClickRule.Builder().type(ClickActionType.NONE).build()
        );
    }

    /**
     * Apply collection/removal to current scene based on the rule.
     * Returns a NEW Scene (your Scene is immutable).
     */
    public Scene applyToCurrentScene(ClickRule rule, Scene currentScene, ClickableObject clicked) {
        List<ClickableObject> updated = new ArrayList<>(currentScene.getObjects());

        switch (rule.getType()) {
            case COLLECT:
            case COLLECT_AND_CHANGE_SCENE:
                if (rule.removeOnCollect()) {
                    updated.removeIf(o -> o.getName().equals(clicked.getName()));
                }
                break;
            default:
                // no mutation to objects
        }

        return new Scene(currentScene.getName(), updated, currentScene.getImage());
    }
}
