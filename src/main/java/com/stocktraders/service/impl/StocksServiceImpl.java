package com.stocktraders.service.impl;

import com.stocktraders.domain.Stocks;
import com.stocktraders.repository.StocksRepository;
import com.stocktraders.service.StocksService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Stocks}.
 */
@Service
@Transactional
public class StocksServiceImpl implements StocksService {

    private final Logger log = LoggerFactory.getLogger(StocksServiceImpl.class);

    private final StocksRepository stocksRepository;

    public StocksServiceImpl(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public Stocks save(Stocks stocks) {
        log.debug("Request to save Stocks : {}", stocks);
        return stocksRepository.save(stocks);
    }

    @Override
    public Stocks update(Stocks stocks) {
        log.debug("Request to save Stocks : {}", stocks);
        return stocksRepository.save(stocks);
    }

    @Override
    public Optional<Stocks> partialUpdate(Stocks stocks) {
        log.debug("Request to partially update Stocks : {}", stocks);

        return stocksRepository
            .findById(stocks.getId())
            .map(existingStocks -> {
                if (stocks.getStockname() != null) {
                    existingStocks.setStockname(stocks.getStockname());
                }
                if (stocks.getMarket() != null) {
                    existingStocks.setMarket(stocks.getMarket());
                }
                if (stocks.getHighPrice() != null) {
                    existingStocks.setHighPrice(stocks.getHighPrice());
                }
                if (stocks.getLowPrice() != null) {
                    existingStocks.setLowPrice(stocks.getLowPrice());
                }
                if (stocks.getBuyPrice() != null) {
                    existingStocks.setBuyPrice(stocks.getBuyPrice());
                }

                return existingStocks;
            })
            .map(stocksRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stocks> findAll() {
        log.debug("Request to get all Stocks");
        return stocksRepository.findByUserIsCurrentUser();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Stocks> findOne(Long id) {
        log.debug("Request to get Stocks : {}", id);
        return stocksRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stocks : {}", id);
        stocksRepository.deleteById(id);
    }
}
