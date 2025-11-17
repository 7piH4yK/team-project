package use_case.game;

import java.util.Optional;

public class ClickRule {
    private final ClickActionType type;
    private final String targetScene; // optional
    private final String message;     // optional
    private final boolean removeOnCollect;

    private ClickRule(Builder b) {
        this.type = b.type;
        this.targetScene = b.targetScene;
        this.message = b.message;
        this.removeOnCollect = b.removeOnCollect;
    }

    public ClickActionType getType() { return type; }
    public Optional<String> getTargetScene() { return Optional.ofNullable(targetScene); }
    public Optional<String> getMessage() { return Optional.ofNullable(message); }
    public boolean removeOnCollect() { return removeOnCollect; }

    public static class Builder {
        private ClickActionType type = ClickActionType.NONE;
        private String targetScene;
        private String message;
        private boolean removeOnCollect = false;

        public Builder type(ClickActionType t) { this.type = t; return this; }
        public Builder targetScene(String s) { this.targetScene = s; return this; }
        public Builder message(String m) { this.message = m; return this; }
        public Builder removeOnCollect(boolean r) { this.removeOnCollect = r; return this; }
        public ClickRule build() { return new ClickRule(this); }
    }
}
