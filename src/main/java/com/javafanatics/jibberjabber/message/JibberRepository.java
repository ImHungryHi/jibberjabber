package com.javafanatics.jibberjabber.message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JibberRepository extends JpaRepository<Jibber, Integer> {
    @Query("FROM Jibber j LEFT JOIN FETCH j.user WHERE j.user.handle = :handle")
    List<Jibber> findByHandle(@Param("handle") String handle);
}
