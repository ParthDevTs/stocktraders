import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StocksComponent } from '../list/stocks.component';
import { StocksDetailComponent } from '../detail/stocks-detail.component';
import { StocksUpdateComponent } from '../update/stocks-update.component';
import { StocksRoutingResolveService } from './stocks-routing-resolve.service';

const stocksRoute: Routes = [
  {
    path: '',
    component: StocksComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StocksDetailComponent,
    resolve: {
      stocks: StocksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StocksUpdateComponent,
    resolve: {
      stocks: StocksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StocksUpdateComponent,
    resolve: {
      stocks: StocksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(stocksRoute)],
  exports: [RouterModule],
})
export class StocksRoutingModule {}
