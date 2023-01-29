import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { WallsUpdateComponent } from 'app/entities/walls/walls-update.component';
import { WallsService } from 'app/entities/walls/walls.service';
import { Walls } from 'app/shared/model/walls.model';

describe('Component Tests', () => {
  describe('Walls Management Update Component', () => {
    let comp: WallsUpdateComponent;
    let fixture: ComponentFixture<WallsUpdateComponent>;
    let service: WallsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [WallsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WallsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WallsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WallsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Walls(123);
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
        const entity = new Walls();
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
