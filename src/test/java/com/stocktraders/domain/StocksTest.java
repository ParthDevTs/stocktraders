package com.stocktraders.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.stocktraders.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StocksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stocks.class);
        Stocks stocks1 = new Stocks();
        stocks1.setId(1L);
        Stocks stocks2 = new Stocks();
        stocks2.setId(stocks1.getId());
        assertThat(stocks1).isEqualTo(stocks2);
        stocks2.setId(2L);
        assertThat(stocks1).isNotEqualTo(stocks2);
        stocks1.setId(null);
        assertThat(stocks1).isNotEqualTo(stocks2);
    }
}
