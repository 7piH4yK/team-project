package use_case.game;

import use_case.collect_item.ClickRule;

import java.util.*;

public class GameManager {

    private final Map<String, ClickRule> rulesByObjectName;

    public GameManager(Map<String, ClickRule> rulesByObjectName) {
        this.rulesByObjectName = new HashMap<>(rulesByObjectName);
    }


}
