package com.jencys.pricetest.infrastructure.secondary.repository.dao;

import com.jencys.pricetest.infrastructure.secondary.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PriceDAO extends JpaRepository<PriceEntity, Long> {
    @Query("SELECT p FROM PriceEntity p INNER JOIN BrandEntity b ON p.brandEntity.id = b.id WHERE ?1 BETWEEN p.startDate AND p.endDate AND p.productId = ?2 AND b.name = ?3 ORDER BY p.priority DESC")
    List<PriceEntity> getPrice(Timestamp date, Long id, String brand);
}
