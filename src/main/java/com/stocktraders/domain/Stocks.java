package com.stocktraders.domain;

import com.stocktraders.domain.enumeration.Market;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Stocks.
 */
@Entity
@Table(name = "stocks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Stocks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "stockname", nullable = false)
    private String stockname;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "market", nullable = false)
    private Market market;

    @NotNull
    @Column(name = "high_price", nullable = false)
    private Float highPrice;

    @NotNull
    @Column(name = "low_price", nullable = false)
    private Float lowPrice;

    @NotNull
    @Column(name = "buy_price", nullable = false)
    private Float buyPrice;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Stocks id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockname() {
        return this.stockname;
    }

    public Stocks stockname(String stockname) {
        this.setStockname(stockname);
        return this;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public Market getMarket() {
        return this.market;
    }

    public Stocks market(Market market) {
        this.setMarket(market);
        return this;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Float getHighPrice() {
        return this.highPrice;
    }

    public Stocks highPrice(Float highPrice) {
        this.setHighPrice(highPrice);
        return this;
    }

    public void setHighPrice(Float highPrice) {
        this.highPrice = highPrice;
    }

    public Float getLowPrice() {
        return this.lowPrice;
    }

    public Stocks lowPrice(Float lowPrice) {
        this.setLowPrice(lowPrice);
        return this;
    }

    public void setLowPrice(Float lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Float getBuyPrice() {
        return this.buyPrice;
    }

    public Stocks buyPrice(Float buyPrice) {
        this.setBuyPrice(buyPrice);
        return this;
    }

    public void setBuyPrice(Float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stocks user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stocks)) {
            return false;
        }
        return id != null && id.equals(((Stocks) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stocks{" +
            "id=" + getId() +
            ", stockname='" + getStockname() + "'" +
            ", market='" + getMarket() + "'" +
            ", highPrice=" + getHighPrice() +
            ", lowPrice=" + getLowPrice() +
            ", buyPrice=" + getBuyPrice() +
            "}";
    }
}
