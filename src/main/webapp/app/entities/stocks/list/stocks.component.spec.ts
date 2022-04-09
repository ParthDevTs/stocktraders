import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { StocksService } from '../service/stocks.service';

import { StocksComponent } from './stocks.component';

describe('Stocks Management Component', () => {
  let comp: StocksComponent;
  let fixture: ComponentFixture<StocksComponent>;
  let service: StocksService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [StocksComponent],
    })
      .overrideTemplate(StocksComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StocksComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(StocksService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.stocks?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
