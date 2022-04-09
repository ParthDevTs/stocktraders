import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStocks } from '../stocks.model';
import { StocksService } from '../service/stocks.service';
import { StocksDeleteDialogComponent } from '../delete/stocks-delete-dialog.component';

@Component({
  selector: 'jhi-stocks',
  templateUrl: './stocks.component.html',
})
export class StocksComponent implements OnInit {
  stocks?: IStocks[];
  isLoading = false;

  constructor(protected stocksService: StocksService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.stocksService.query().subscribe({
      next: (res: HttpResponse<IStocks[]>) => {
        this.isLoading = false;
        this.stocks = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IStocks): number {
    return item.id!;
  }

  delete(stocks: IStocks): void {
    const modalRef = this.modalService.open(StocksDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stocks = stocks;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
