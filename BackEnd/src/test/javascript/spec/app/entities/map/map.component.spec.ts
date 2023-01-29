import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { MapComponent } from 'app/entities/map/map.component';
import { MapService } from 'app/entities/map/map.service';
import { Map } from 'app/shared/model/map.model';

describe('Component Tests', () => {
  describe('Map Management Component', () => {
    let comp: MapComponent;
    let fixture: ComponentFixture<MapComponent>;
    let service: MapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [MapComponent],
      })
        .overrideTemplate(MapComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MapComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MapService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Map(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.maps && comp.maps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
