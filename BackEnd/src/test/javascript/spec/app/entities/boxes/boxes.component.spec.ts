import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { BoxesComponent } from 'app/entities/boxes/boxes.component';
import { BoxesService } from 'app/entities/boxes/boxes.service';
import { Boxes } from 'app/shared/model/boxes.model';

describe('Component Tests', () => {
  describe('Boxes Management Component', () => {
    let comp: BoxesComponent;
    let fixture: ComponentFixture<BoxesComponent>;
    let service: BoxesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [BoxesComponent],
      })
        .overrideTemplate(BoxesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoxesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoxesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Boxes(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.boxes && comp.boxes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
