package com.stocktraders.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.stocktraders.IntegrationTest;
import com.stocktraders.domain.Stocks;
import com.stocktraders.domain.enumeration.Market;
import com.stocktraders.repository.StocksRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StocksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StocksResourceIT {

    private static final String DEFAULT_STOCKNAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCKNAME = "BBBBBBBBBB";

    private static final Market DEFAULT_MARKET = Market.NSE;
    private static final Market UPDATED_MARKET = Market.BSE;

    private static final Float DEFAULT_HIGH_PRICE = 1F;
    private static final Float UPDATED_HIGH_PRICE = 2F;

    private static final Float DEFAULT_LOW_PRICE = 1F;
    private static final Float UPDATED_LOW_PRICE = 2F;

    private static final Float DEFAULT_BUY_PRICE = 1F;
    private static final Float UPDATED_BUY_PRICE = 2F;

    private static final String ENTITY_API_URL = "/api/stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStocksMockMvc;

    private Stocks stocks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stocks createEntity(EntityManager em) {
        Stocks stocks = new Stocks()
            .stockname(DEFAULT_STOCKNAME)
            .market(DEFAULT_MARKET)
            .highPrice(DEFAULT_HIGH_PRICE)
            .lowPrice(DEFAULT_LOW_PRICE)
            .buyPrice(DEFAULT_BUY_PRICE);
        return stocks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stocks createUpdatedEntity(EntityManager em) {
        Stocks stocks = new Stocks()
            .stockname(UPDATED_STOCKNAME)
            .market(UPDATED_MARKET)
            .highPrice(UPDATED_HIGH_PRICE)
            .lowPrice(UPDATED_LOW_PRICE)
            .buyPrice(UPDATED_BUY_PRICE);
        return stocks;
    }

    @BeforeEach
    public void initTest() {
        stocks = createEntity(em);
    }

    @Test
    @Transactional
    void createStocks() throws Exception {
        int databaseSizeBeforeCreate = stocksRepository.findAll().size();
        // Create the Stocks
        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isCreated());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeCreate + 1);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getStockname()).isEqualTo(DEFAULT_STOCKNAME);
        assertThat(testStocks.getMarket()).isEqualTo(DEFAULT_MARKET);
        assertThat(testStocks.getHighPrice()).isEqualTo(DEFAULT_HIGH_PRICE);
        assertThat(testStocks.getLowPrice()).isEqualTo(DEFAULT_LOW_PRICE);
        assertThat(testStocks.getBuyPrice()).isEqualTo(DEFAULT_BUY_PRICE);
    }

    @Test
    @Transactional
    void createStocksWithExistingId() throws Exception {
        // Create the Stocks with an existing ID
        stocks.setId(1L);

        int databaseSizeBeforeCreate = stocksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStocknameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stocksRepository.findAll().size();
        // set the field null
        stocks.setStockname(null);

        // Create the Stocks, which fails.

        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarketIsRequired() throws Exception {
        int databaseSizeBeforeTest = stocksRepository.findAll().size();
        // set the field null
        stocks.setMarket(null);

        // Create the Stocks, which fails.

        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHighPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stocksRepository.findAll().size();
        // set the field null
        stocks.setHighPrice(null);

        // Create the Stocks, which fails.

        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLowPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stocksRepository.findAll().size();
        // set the field null
        stocks.setLowPrice(null);

        // Create the Stocks, which fails.

        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBuyPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stocksRepository.findAll().size();
        // set the field null
        stocks.setBuyPrice(null);

        // Create the Stocks, which fails.

        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStocks() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList
        restStocksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stocks.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockname").value(hasItem(DEFAULT_STOCKNAME)))
            .andExpect(jsonPath("$.[*].market").value(hasItem(DEFAULT_MARKET.toString())))
            .andExpect(jsonPath("$.[*].highPrice").value(hasItem(DEFAULT_HIGH_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].lowPrice").value(hasItem(DEFAULT_LOW_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].buyPrice").value(hasItem(DEFAULT_BUY_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getStocks() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get the stocks
        restStocksMockMvc
            .perform(get(ENTITY_API_URL_ID, stocks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stocks.getId().intValue()))
            .andExpect(jsonPath("$.stockname").value(DEFAULT_STOCKNAME))
            .andExpect(jsonPath("$.market").value(DEFAULT_MARKET.toString()))
            .andExpect(jsonPath("$.highPrice").value(DEFAULT_HIGH_PRICE.doubleValue()))
            .andExpect(jsonPath("$.lowPrice").value(DEFAULT_LOW_PRICE.doubleValue()))
            .andExpect(jsonPath("$.buyPrice").value(DEFAULT_BUY_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingStocks() throws Exception {
        // Get the stocks
        restStocksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStocks() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks
        Stocks updatedStocks = stocksRepository.findById(stocks.getId()).get();
        // Disconnect from session so that the updates on updatedStocks are not directly saved in db
        em.detach(updatedStocks);
        updatedStocks
            .stockname(UPDATED_STOCKNAME)
            .market(UPDATED_MARKET)
            .highPrice(UPDATED_HIGH_PRICE)
            .lowPrice(UPDATED_LOW_PRICE)
            .buyPrice(UPDATED_BUY_PRICE);

        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStocks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getStockname()).isEqualTo(UPDATED_STOCKNAME);
        assertThat(testStocks.getMarket()).isEqualTo(UPDATED_MARKET);
        assertThat(testStocks.getHighPrice()).isEqualTo(UPDATED_HIGH_PRICE);
        assertThat(testStocks.getLowPrice()).isEqualTo(UPDATED_LOW_PRICE);
        assertThat(testStocks.getBuyPrice()).isEqualTo(UPDATED_BUY_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stocks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStocksWithPatch() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks using partial update
        Stocks partialUpdatedStocks = new Stocks();
        partialUpdatedStocks.setId(stocks.getId());

        partialUpdatedStocks.stockname(UPDATED_STOCKNAME).lowPrice(UPDATED_LOW_PRICE).buyPrice(UPDATED_BUY_PRICE);

        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStocks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getStockname()).isEqualTo(UPDATED_STOCKNAME);
        assertThat(testStocks.getMarket()).isEqualTo(DEFAULT_MARKET);
        assertThat(testStocks.getHighPrice()).isEqualTo(DEFAULT_HIGH_PRICE);
        assertThat(testStocks.getLowPrice()).isEqualTo(UPDATED_LOW_PRICE);
        assertThat(testStocks.getBuyPrice()).isEqualTo(UPDATED_BUY_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateStocksWithPatch() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks using partial update
        Stocks partialUpdatedStocks = new Stocks();
        partialUpdatedStocks.setId(stocks.getId());

        partialUpdatedStocks
            .stockname(UPDATED_STOCKNAME)
            .market(UPDATED_MARKET)
            .highPrice(UPDATED_HIGH_PRICE)
            .lowPrice(UPDATED_LOW_PRICE)
            .buyPrice(UPDATED_BUY_PRICE);

        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStocks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getStockname()).isEqualTo(UPDATED_STOCKNAME);
        assertThat(testStocks.getMarket()).isEqualTo(UPDATED_MARKET);
        assertThat(testStocks.getHighPrice()).isEqualTo(UPDATED_HIGH_PRICE);
        assertThat(testStocks.getLowPrice()).isEqualTo(UPDATED_LOW_PRICE);
        assertThat(testStocks.getBuyPrice()).isEqualTo(UPDATED_BUY_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stocks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStocks() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeDelete = stocksRepository.findAll().size();

        // Delete the stocks
        restStocksMockMvc
            .perform(delete(ENTITY_API_URL_ID, stocks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
