package com.stocktraders.repository;

import com.stocktraders.domain.Stocks;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Stocks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {
    @Query("select stocks from Stocks stocks where stocks.user.login = ?#{principal.username}")
    List<Stocks> findByUserIsCurrentUser();
}
