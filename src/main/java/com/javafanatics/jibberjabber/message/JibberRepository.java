package com.javafanatics.jibberjabber.message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JibberRepository extends JpaRepository<Jibber, Integer> {
    @Query("FROM Jibber j LEFT JOIN FETCH j.user WHERE j.user.handle = :handle " +
            "ORDER BY j.createdDate DESC")
    List<Jibber> findByHandle(@Param("handle") String handle);

    @Query("FROM Jibber j, IN (j.user.followedBy)followedBy LEFT JOIN FETCH j.user LEFT JOIN FETCH j.user.followedBy " +
            "WHERE j.user.handle = :handle OR followedBy.handle = :handle " +
            "GROUP BY j.id " +
            "ORDER BY j.createdDate DESC")
    List<Jibber> findHomeByHandle(@Param("handle") String handle);
}
