import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StocksComponent } from './list/stocks.component';
import { StocksDetailComponent } from './detail/stocks-detail.component';
import { StocksUpdateComponent } from './update/stocks-update.component';
import { StocksDeleteDialogComponent } from './delete/stocks-delete-dialog.component';
import { StocksRoutingModule } from './route/stocks-routing.module';

@NgModule({
  imports: [SharedModule, StocksRoutingModule],
  declarations: [StocksComponent, StocksDetailComponent, StocksUpdateComponent, StocksDeleteDialogComponent],
  entryComponents: [StocksDeleteDialogComponent],
})
export class StocksModule {}
