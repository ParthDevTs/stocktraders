import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StocksDetailComponent } from './stocks-detail.component';

describe('Stocks Management Detail Component', () => {
  let comp: StocksDetailComponent;
  let fixture: ComponentFixture<StocksDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StocksDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ stocks: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StocksDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StocksDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load stocks on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.stocks).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
