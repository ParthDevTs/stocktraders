package com.stocktraders.service;

import com.stocktraders.domain.Stocks;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Stocks}.
 */
public interface StocksService {
    /**
     * Save a stocks.
     *
     * @param stocks the entity to save.
     * @return the persisted entity.
     */
    Stocks save(Stocks stocks);

    /**
     * Updates a stocks.
     *
     * @param stocks the entity to update.
     * @return the persisted entity.
     */
    Stocks update(Stocks stocks);

    /**
     * Partially updates a stocks.
     *
     * @param stocks the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Stocks> partialUpdate(Stocks stocks);

    /**
     * Get all the stocks.
     *
     * @return the list of entities.
     */
    List<Stocks> findAll();

    /**
     * Get the "id" stocks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Stocks> findOne(Long id);

    /**
     * Delete the "id" stocks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
