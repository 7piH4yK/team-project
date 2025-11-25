package use_case.collect_item;

public interface CollectItemOutputBoundary {
    void prepareSuccessView(CollectItemOutputData outputData);
    void prepareFailView(String errorMessage);
}
