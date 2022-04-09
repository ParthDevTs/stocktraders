import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStocks, Stocks } from '../stocks.model';
import { StocksService } from '../service/stocks.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { Market } from 'app/entities/enumerations/market.model';

@Component({
  selector: 'jhi-stocks-update',
  templateUrl: './stocks-update.component.html',
})
export class StocksUpdateComponent implements OnInit {
  isSaving = false;
  marketValues = Object.keys(Market);

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    stockname: [null, [Validators.required]],
    market: [null, [Validators.required]],
    highPrice: [null, [Validators.required]],
    lowPrice: [null, [Validators.required]],
    buyPrice: [null, [Validators.required]],
    user: [],
  });

  constructor(
    protected stocksService: StocksService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stocks }) => {
      this.updateForm(stocks);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stocks = this.createFromForm();
    if (stocks.id !== undefined) {
      this.subscribeToSaveResponse(this.stocksService.update(stocks));
    } else {
      this.subscribeToSaveResponse(this.stocksService.create(stocks));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStocks>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(stocks: IStocks): void {
    this.editForm.patchValue({
      id: stocks.id,
      stockname: stocks.stockname,
      market: stocks.market,
      highPrice: stocks.highPrice,
      lowPrice: stocks.lowPrice,
      buyPrice: stocks.buyPrice,
      user: stocks.user,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, stocks.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IStocks {
    return {
      ...new Stocks(),
      id: this.editForm.get(['id'])!.value,
      stockname: this.editForm.get(['stockname'])!.value,
      market: this.editForm.get(['market'])!.value,
      highPrice: this.editForm.get(['highPrice'])!.value,
      lowPrice: this.editForm.get(['lowPrice'])!.value,
      buyPrice: this.editForm.get(['buyPrice'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
