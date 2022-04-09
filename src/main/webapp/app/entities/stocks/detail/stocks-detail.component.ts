import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStocks } from '../stocks.model';

@Component({
  selector: 'jhi-stocks-detail',
  templateUrl: './stocks-detail.component.html',
})
export class StocksDetailComponent implements OnInit {
  stocks: IStocks | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stocks }) => {
      this.stocks = stocks;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
