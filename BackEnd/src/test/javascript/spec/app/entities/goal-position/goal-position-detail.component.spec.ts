import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { GoalPositionDetailComponent } from 'app/entities/goal-position/goal-position-detail.component';
import { GoalPosition } from 'app/shared/model/goal-position.model';

describe('Component Tests', () => {
  describe('GoalPosition Management Detail Component', () => {
    let comp: GoalPositionDetailComponent;
    let fixture: ComponentFixture<GoalPositionDetailComponent>;
    const route = ({ data: of({ goalPosition: new GoalPosition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [GoalPositionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GoalPositionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoalPositionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goalPosition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goalPosition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
