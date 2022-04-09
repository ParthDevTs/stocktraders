import { IUser } from 'app/entities/user/user.model';
import { Market } from 'app/entities/enumerations/market.model';

export interface IStocks {
  id?: number;
  stockname?: string;
  market?: Market;
  highPrice?: number;
  lowPrice?: number;
  buyPrice?: number;
  user?: IUser | null;
}

export class Stocks implements IStocks {
  constructor(
    public id?: number,
    public stockname?: string,
    public market?: Market,
    public highPrice?: number,
    public lowPrice?: number,
    public buyPrice?: number,
    public user?: IUser | null
  ) {}
}

export function getStocksIdentifier(stocks: IStocks): number | undefined {
  return stocks.id;
}
