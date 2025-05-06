package com.example.walibixgr2eq6.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private int reservationId;
    private LocalDate date;
    private LocalTime heure;
    private int userId;
    private int attractionId;
    private String attractionNom;
    private double prixTotal;
    private double prixAvecReduc;

    public Reservation(int reservationId, LocalDate date, LocalTime heure, int userId, int attractionId, String attractionNom, double prixTotal, double prixAvecReduc) {
        this.reservationId = reservationId;
        this.date = date;
        this.heure = heure;
        this.userId = userId;
        this.attractionId = attractionId;
        this.attractionNom = attractionNom;
        this.prixTotal = prixTotal;
        this.prixAvecReduc = prixAvecReduc;
    }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getAttractionId() { return attractionId; }
    public void setAttractionId(int attractionId) { this.attractionId = attractionId; }

    public String getAttractionNom() { return attractionNom; }
    public void setAttractionNom(String attractionNom) { this.attractionNom = attractionNom; }

    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }

    public double getPrixAvecReduc() { return prixAvecReduc; }
    public void setPrixAvecReduc(double prixAvecReduc) { this.prixAvecReduc = prixAvecReduc; }

    @Override
    public String toString() {
        return "Réservation #" + reservationId + " - " + date + " " + heure;
    }
}
