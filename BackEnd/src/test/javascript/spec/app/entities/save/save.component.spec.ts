import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { SaveComponent } from 'app/entities/save/save.component';
import { SaveService } from 'app/entities/save/save.service';
import { Save } from 'app/shared/model/save.model';

describe('Component Tests', () => {
  describe('Save Management Component', () => {
    let comp: SaveComponent;
    let fixture: ComponentFixture<SaveComponent>;
    let service: SaveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [SaveComponent],
      })
        .overrideTemplate(SaveComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SaveComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SaveService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Save(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.saves && comp.saves[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
