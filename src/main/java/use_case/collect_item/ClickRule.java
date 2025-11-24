package use_case.collect_item;

import java.util.Optional;

public class ClickRule {
    private final ClickActionType type;
    private final String targetScene;
    private final String message;
    private final boolean removeOnCollect;

    private ClickRule(Builder builder) {
        this.type = builder.type;
        this.targetScene = builder.targetScene;
        this.message = builder.message;
        this.removeOnCollect = builder.removeOnCollect;
    }

    public ClickActionType getType() {
        return type;
    }

    public Optional<String> getTargetScene() {
        return Optional.ofNullable(targetScene);
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

    public boolean removeOnCollect() {
        return removeOnCollect;
    }

    public static class Builder {
        private ClickActionType type = ClickActionType.NONE;
        private String targetScene;
        private String message;
        private boolean removeOnCollect = false;

        public Builder type(ClickActionType actionType) {
            this.type = actionType;
            return this;
        }

        public Builder targetScene(String scene) {
            this.targetScene = scene;
            return this;
        }


        public Builder message(String builderMessage) {
            this.message = builderMessage;
            return this;
        }

        public Builder removeOnCollect(boolean remove) {
            this.removeOnCollect = remove;
            return this;
        }

        public ClickRule build() {
            return new ClickRule(this);
        }
    }
}
