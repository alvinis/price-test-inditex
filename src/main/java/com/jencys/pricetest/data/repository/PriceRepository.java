package com.jencys.pricetest.data.repository;

import com.jencys.pricetest.data.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT p FROM Price p INNER JOIN Brand b ON p.brand.id = b.id WHERE ?1 BETWEEN p.startDate AND p.endDate AND p.productId = ?2 AND b.name = ?3 ORDER BY p.priority DESC")
    List<Price> xd(Timestamp date, Long id, String brand);
}
