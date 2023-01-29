import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { GoalPositionUpdateComponent } from 'app/entities/goal-position/goal-position-update.component';
import { GoalPositionService } from 'app/entities/goal-position/goal-position.service';
import { GoalPosition } from 'app/shared/model/goal-position.model';

describe('Component Tests', () => {
  describe('GoalPosition Management Update Component', () => {
    let comp: GoalPositionUpdateComponent;
    let fixture: ComponentFixture<GoalPositionUpdateComponent>;
    let service: GoalPositionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [GoalPositionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GoalPositionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoalPositionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoalPositionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoalPosition(123);
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
        const entity = new GoalPosition();
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
