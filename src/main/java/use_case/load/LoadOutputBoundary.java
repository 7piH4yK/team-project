package use_case.load;

public interface LoadOutputBoundary {
    void switchToLoadView(LoadOutputData outputData);

    void displayError(String message);
}
