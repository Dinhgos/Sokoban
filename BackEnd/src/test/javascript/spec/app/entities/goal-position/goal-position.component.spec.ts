import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { GoalPositionComponent } from 'app/entities/goal-position/goal-position.component';
import { GoalPositionService } from 'app/entities/goal-position/goal-position.service';
import { GoalPosition } from 'app/shared/model/goal-position.model';

describe('Component Tests', () => {
  describe('GoalPosition Management Component', () => {
    let comp: GoalPositionComponent;
    let fixture: ComponentFixture<GoalPositionComponent>;
    let service: GoalPositionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [GoalPositionComponent],
      })
        .overrideTemplate(GoalPositionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoalPositionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoalPositionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GoalPosition(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.goalPositions && comp.goalPositions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
