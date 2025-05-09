package com.example.walibixgr2eq6;

public class Session {
    private static int currentUserId = -1;

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void clear() {
        currentUserId = -1;
    }

    public static boolean isLoggedIn() {
        return currentUserId != -1;
    }
}