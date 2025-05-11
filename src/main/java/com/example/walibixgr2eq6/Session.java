package com.example.walibixgr2eq6;

/**
 * gère la session de l'user courant
 * permet de stocker et consulter l'identifiant de l'user connecté
 * permet de réinitialisé la session
 */
public class Session {
    private static int currentUserId = -1;

    /**
     * définit l'id de l'user connecté
     * @param userId
     */
    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    /**
     * retourne l'id de l'user connecte
     * @return
     */
    public static int getCurrentUserId() {
        return currentUserId;
    }

    /**
     * reinitialise la session en deconnectant l'user
     * remet l'id à -1 -> aucun user de connecté
     */
    public static void clear() {
        currentUserId = -1;
    }

    /**
     * indique si un user est connecte -> true si oui, false sinon
     * @return
     */
    public static boolean isLoggedIn() {
        return currentUserId != -1;
    }
}