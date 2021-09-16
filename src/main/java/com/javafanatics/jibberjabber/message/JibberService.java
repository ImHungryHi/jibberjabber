package com.javafanatics.jibberjabber.message;
import java.util.List;

public interface JibberService {
    void save(Jibber jibber);
    Jibber getById(int id);
    List<Jibber> getAll();
    List<Jibber> getJibbersByUserHandle(String handle);
    List<Jibber> getJibberHomeByHandle(String handle);
}
