import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStocks } from '../stocks.model';
import { StocksService } from '../service/stocks.service';

@Component({
  templateUrl: './stocks-delete-dialog.component.html',
})
export class StocksDeleteDialogComponent {
  stocks?: IStocks;

  constructor(protected stocksService: StocksService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stocksService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
