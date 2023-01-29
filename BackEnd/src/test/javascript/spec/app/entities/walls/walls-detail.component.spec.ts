import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { WallsDetailComponent } from 'app/entities/walls/walls-detail.component';
import { Walls } from 'app/shared/model/walls.model';

describe('Component Tests', () => {
  describe('Walls Management Detail Component', () => {
    let comp: WallsDetailComponent;
    let fixture: ComponentFixture<WallsDetailComponent>;
    const route = ({ data: of({ walls: new Walls(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [WallsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WallsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WallsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load walls on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.walls).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
