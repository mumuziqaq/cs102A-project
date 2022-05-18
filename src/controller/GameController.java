package controller;

import view.ChessGameFrame;
import view.Chessboard;
import view.FunctionFrame;

import java.io.FileFilter;
import java.util.HashMap;
//todo:输赢棋增加减少，排名
public class GameController {
    private static Chessboard chessboard;
    private static UserController userController;
    private static String user1;
    private static String user2="AI";
    private static ChessGameFrame chessGameFrame;
    private static int error;
    private static FunctionFrame functionFrame;
    private static int functionFrameExist=0;

    public static int getFunctionFrameExist() {
        return functionFrameExist;
    }

    public static void setFunctionFrameExist(int functionFrameExist) {
        GameController.functionFrameExist = functionFrameExist;
    }

    public static FunctionFrame getFunctionFrame() {
        return functionFrame;
    }

    public static void setFunctionFrame(FunctionFrame functionFrame) {
        GameController.functionFrame = functionFrame;
    }

    public static int getError() {
        return error;
    }
    public static void setError(int error) {
        GameController.error = error;
    }
    public static ChessGameFrame getChessGameFrame() {
        return chessGameFrame;
    }
    public static void setChessGameFrame(ChessGameFrame chessGameFrame) {
        GameController.chessGameFrame = chessGameFrame;
    }
    public static Chessboard getChessboard() {
        return chessboard;
    }
    public static void setChessboard(Chessboard cb) {
        chessboard = cb;
    }
    public static UserController getUserController() {
        return userController;
    }
    public static void setUserController(UserController userController) {
        GameController.userController = userController;
    }
    public static String getUser1() {
        return user1;
    }
    public static void setUser1(String user1) {
        GameController.user1 = user1;
    }
    public static String getUser2() {
        return user2;
    }
    public static void setUser2(String user2) {
        GameController.user2 = user2;
    }
}
