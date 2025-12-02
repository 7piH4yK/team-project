package use_case.question;

import data_access.api.Question;
import data_access.api.TriviaResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionInteractorTest {
    /**
     * Simple stub DAO that returns whatever TriviaResponse
     * we configure in the tests.
     */
    static class StubDAO implements QuestionDataAccessInterface {
        TriviaResponse responseToReturn;

        @Override
        public TriviaResponse fetchRiddles() {
            return responseToReturn;
        }
    }

    /**
     * Simple presenter to capture what the interactor sends.
     */
    static class StubPresenter implements QuestionOutputBoundary {
        QuestionOutputData outputData;
        String errorMessage;

        @Override
        public void presentRiddles(QuestionOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void displayError(String message) {
            this.errorMessage = message;
        }
    }

    // ----------------------------------------------------------
    // Helper: build a TriviaResponse using reflection
    // ----------------------------------------------------------
    private TriviaResponse makeResponse(int code, List<Question> questions) {
        try {
            TriviaResponse response = new TriviaResponse();

            Field codeField = TriviaResponse.class.getDeclaredField("responseCode");
            codeField.setAccessible(true);
            codeField.setInt(response, code);

            Field resultsField = TriviaResponse.class.getDeclaredField("results");
            resultsField.setAccessible(true);
            resultsField.set(response, questions);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Adjust makeResponse(...) to match your TriviaResponse implementation", e);
        }
    }

    // ----------------------------------------------------------
    // SUCCESS: valid response, non-empty questions
    // ----------------------------------------------------------
    @Test
    void testExecute_successfulFetch() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        QuestionInteractor interactor = new QuestionInteractor(dao, presenter);

        // Dummy question list (we don't care about fields)
        Question q1 = new Question(); // adjust ctor if needed
        List<Question> questions = List.of(q1);

        dao.responseToReturn = makeResponse(0, questions);

        interactor.execute();

        assertNull(presenter.errorMessage, "No error should be shown on success");
        assertNotNull(presenter.outputData, "Output data should be passed to presenter");

        assertEquals(0, presenter.outputData.getResponseCode());
        assertEquals(questions, presenter.outputData.getQuestions());
    }

    // ----------------------------------------------------------
    // FAIL: DAO returns null response
    // ----------------------------------------------------------
    @Test
    void testExecute_nullResponse_showsError() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        QuestionInteractor interactor = new QuestionInteractor(dao, presenter);

        dao.responseToReturn = null;

        interactor.execute();

        assertNull(presenter.outputData, "No riddles should be presented on null response");
        assertEquals("Failed to load riddles: no response from API.", presenter.errorMessage);
    }

    // ----------------------------------------------------------
    // FAIL: non-zero responseCode
    // (Requires early return in interactor after displayError)
    // ----------------------------------------------------------
    @Test
    void testExecute_nonZeroResponseCode_showsError() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        QuestionInteractor interactor = new QuestionInteractor(dao, presenter);

        Question q1 = new Question();
        dao.responseToReturn = makeResponse(1, List.of(q1));

        interactor.execute();

        assertNull(presenter.outputData, "No riddles should be presented on error response code");
        assertEquals("Riddle service returned error code: 1", presenter.errorMessage);
    }

    // ----------------------------------------------------------
    // FAIL: responseCode = 0 but no questions
    // ----------------------------------------------------------
    @Test
    void testExecute_emptyQuestions_showsError() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        QuestionInteractor interactor = new QuestionInteractor(dao, presenter);

        dao.responseToReturn = makeResponse(0, Collections.emptyList());

        interactor.execute();

        assertNull(presenter.outputData, "No riddles should be presented when list is empty");
        assertEquals("No riddles available.", presenter.errorMessage);
    }

    // ----------------------------------------------------------
    // FAIL: responseCode = 0 but results == null
    // ----------------------------------------------------------
    @Test
    void testExecute_nullQuestions_showsError() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        QuestionInteractor interactor = new QuestionInteractor(dao, presenter);

        dao.responseToReturn = makeResponse(0, null);

        interactor.execute();

        assertNull(presenter.outputData);
        assertEquals("No riddles available.", presenter.errorMessage);
    }
}
