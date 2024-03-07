package com.example.ges_reservation.services;

import java.util.List;

public interface PService<T> {
    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(T t);
    public void supprimer(int id);
    public List<T> recupperer();
}
