package com.javafanatics.jibberjabber.message;
import java.util.List;

public interface JibberService {
    void save(Jibber jibber);
    List<Jibber> getJibbersByUserHandle(String handle);
}
