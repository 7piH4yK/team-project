package interface_adapter;

import data_access.InMemoryGameDataAccessObject;

// AppContext.java
public class AppContext {
    private static InMemoryGameDataAccessObject gameDAO;
    public static void setGameDAO(InMemoryGameDataAccessObject dao) { gameDAO = dao; }
    public static InMemoryGameDataAccessObject getGameDAO() { return gameDAO; }
}
