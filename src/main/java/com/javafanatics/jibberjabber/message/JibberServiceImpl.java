package com.javafanatics.jibberjabber.message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JibberServiceImpl implements JibberService {
    private JibberRepository jibberRepository;

    @Autowired
    public void setJibberRepository(JibberRepository jibberRepository) {
        this.jibberRepository = jibberRepository;
    }

    @Override
    public void save(Jibber jibber) {
        jibberRepository.save(jibber);
    }

    @Override
    public List<Jibber> getJibbersByUserHandle(String handle) {
        return jibberRepository.findByHandle(handle);
    }

    @Override
    public List<Jibber> getJibberHomeByHandle(String handle) {
        return jibberRepository.findHomeByHandle(handle);
    }
}
