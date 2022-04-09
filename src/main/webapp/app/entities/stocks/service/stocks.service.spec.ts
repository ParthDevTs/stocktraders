import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Market } from 'app/entities/enumerations/market.model';
import { IStocks, Stocks } from '../stocks.model';

import { StocksService } from './stocks.service';

describe('Stocks Service', () => {
  let service: StocksService;
  let httpMock: HttpTestingController;
  let elemDefault: IStocks;
  let expectedResult: IStocks | IStocks[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StocksService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      stockname: 'AAAAAAA',
      market: Market.NSE,
      highPrice: 0,
      lowPrice: 0,
      buyPrice: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Stocks', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Stocks()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Stocks', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stockname: 'BBBBBB',
          market: 'BBBBBB',
          highPrice: 1,
          lowPrice: 1,
          buyPrice: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Stocks', () => {
      const patchObject = Object.assign(
        {
          buyPrice: 1,
        },
        new Stocks()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Stocks', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stockname: 'BBBBBB',
          market: 'BBBBBB',
          highPrice: 1,
          lowPrice: 1,
          buyPrice: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Stocks', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStocksToCollectionIfMissing', () => {
      it('should add a Stocks to an empty array', () => {
        const stocks: IStocks = { id: 123 };
        expectedResult = service.addStocksToCollectionIfMissing([], stocks);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stocks);
      });

      it('should not add a Stocks to an array that contains it', () => {
        const stocks: IStocks = { id: 123 };
        const stocksCollection: IStocks[] = [
          {
            ...stocks,
          },
          { id: 456 },
        ];
        expectedResult = service.addStocksToCollectionIfMissing(stocksCollection, stocks);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Stocks to an array that doesn't contain it", () => {
        const stocks: IStocks = { id: 123 };
        const stocksCollection: IStocks[] = [{ id: 456 }];
        expectedResult = service.addStocksToCollectionIfMissing(stocksCollection, stocks);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stocks);
      });

      it('should add only unique Stocks to an array', () => {
        const stocksArray: IStocks[] = [{ id: 123 }, { id: 456 }, { id: 82234 }];
        const stocksCollection: IStocks[] = [{ id: 123 }];
        expectedResult = service.addStocksToCollectionIfMissing(stocksCollection, ...stocksArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const stocks: IStocks = { id: 123 };
        const stocks2: IStocks = { id: 456 };
        expectedResult = service.addStocksToCollectionIfMissing([], stocks, stocks2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stocks);
        expect(expectedResult).toContain(stocks2);
      });

      it('should accept null and undefined values', () => {
        const stocks: IStocks = { id: 123 };
        expectedResult = service.addStocksToCollectionIfMissing([], null, stocks, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stocks);
      });

      it('should return initial array if no Stocks is added', () => {
        const stocksCollection: IStocks[] = [{ id: 123 }];
        expectedResult = service.addStocksToCollectionIfMissing(stocksCollection, undefined, null);
        expect(expectedResult).toEqual(stocksCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
