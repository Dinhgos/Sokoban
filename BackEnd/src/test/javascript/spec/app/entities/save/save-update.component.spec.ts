import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SaveUpdateComponent } from 'app/entities/save/save-update.component';
import { SaveService } from 'app/entities/save/save.service';
import { Save } from 'app/shared/model/save.model';

describe('Component Tests', () => {
  describe('Save Management Update Component', () => {
    let comp: SaveUpdateComponent;
    let fixture: ComponentFixture<SaveUpdateComponent>;
    let service: SaveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [SaveUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SaveUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SaveUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SaveService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Save(123);
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
        const entity = new Save();
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
