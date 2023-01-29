import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { MapUpdateComponent } from 'app/entities/map/map-update.component';
import { MapService } from 'app/entities/map/map.service';
import { Map } from 'app/shared/model/map.model';

describe('Component Tests', () => {
  describe('Map Management Update Component', () => {
    let comp: MapUpdateComponent;
    let fixture: ComponentFixture<MapUpdateComponent>;
    let service: MapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [MapUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MapUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MapUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MapService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Map(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Map();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
