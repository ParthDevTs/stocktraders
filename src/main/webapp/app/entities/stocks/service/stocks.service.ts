import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStocks, getStocksIdentifier } from '../stocks.model';

export type EntityResponseType = HttpResponse<IStocks>;
export type EntityArrayResponseType = HttpResponse<IStocks[]>;

@Injectable({ providedIn: 'root' })
export class StocksService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stocks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stocks: IStocks): Observable<EntityResponseType> {
    return this.http.post<IStocks>(this.resourceUrl, stocks, { observe: 'response' });
  }

  update(stocks: IStocks): Observable<EntityResponseType> {
    return this.http.put<IStocks>(`${this.resourceUrl}/${getStocksIdentifier(stocks) as number}`, stocks, { observe: 'response' });
  }

  partialUpdate(stocks: IStocks): Observable<EntityResponseType> {
    return this.http.patch<IStocks>(`${this.resourceUrl}/${getStocksIdentifier(stocks) as number}`, stocks, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStocks>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStocks[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStocksToCollectionIfMissing(stocksCollection: IStocks[], ...stocksToCheck: (IStocks | null | undefined)[]): IStocks[] {
    const stocks: IStocks[] = stocksToCheck.filter(isPresent);
    if (stocks.length > 0) {
      const stocksCollectionIdentifiers = stocksCollection.map(stocksItem => getStocksIdentifier(stocksItem)!);
      const stocksToAdd = stocks.filter(stocksItem => {
        const stocksIdentifier = getStocksIdentifier(stocksItem);
        if (stocksIdentifier == null || stocksCollectionIdentifiers.includes(stocksIdentifier)) {
          return false;
        }
        stocksCollectionIdentifiers.push(stocksIdentifier);
        return true;
      });
      return [...stocksToAdd, ...stocksCollection];
    }
    return stocksCollection;
  }
}
