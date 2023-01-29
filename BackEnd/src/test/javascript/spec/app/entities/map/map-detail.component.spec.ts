import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { MapDetailComponent } from 'app/entities/map/map-detail.component';
import { Map } from 'app/shared/model/map.model';

describe('Component Tests', () => {
  describe('Map Management Detail Component', () => {
    let comp: MapDetailComponent;
    let fixture: ComponentFixture<MapDetailComponent>;
    const route = ({ data: of({ map: new Map(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [MapDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MapDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MapDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load map on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.map).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
