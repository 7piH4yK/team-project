package interface_adapter;

import dataaccess.InMemoryGameDataAccessObject;

// AppContext.java
public class AppContext {
    private static InMemoryGameDataAccessObject gameDAO;

    public static InMemoryGameDataAccessObject getGameDAO() {
        return gameDAO;
    }

    public static void setGameDAO(InMemoryGameDataAccessObject dao) {
        gameDAO = dao;
    }
}
