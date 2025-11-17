package use_case.game;

public enum ClickActionType {
    NONE,
    CHANGE_SCENE,              // switch to a target scene
    COLLECT,                   // remove clicked object from current scene
    COLLECT_AND_CHANGE_SCENE,  // collect, then change scene
    MESSAGE_ONLY               // show message only
}
