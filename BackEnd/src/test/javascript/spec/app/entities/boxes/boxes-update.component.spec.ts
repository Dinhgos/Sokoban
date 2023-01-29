import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { BoxesUpdateComponent } from 'app/entities/boxes/boxes-update.component';
import { BoxesService } from 'app/entities/boxes/boxes.service';
import { Boxes } from 'app/shared/model/boxes.model';

describe('Component Tests', () => {
  describe('Boxes Management Update Component', () => {
    let comp: BoxesUpdateComponent;
    let fixture: ComponentFixture<BoxesUpdateComponent>;
    let service: BoxesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [BoxesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BoxesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoxesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoxesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Boxes(123);
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
        const entity = new Boxes();
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
