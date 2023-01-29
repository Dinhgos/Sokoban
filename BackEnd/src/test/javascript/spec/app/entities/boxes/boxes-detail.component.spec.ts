import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { BoxesDetailComponent } from 'app/entities/boxes/boxes-detail.component';
import { Boxes } from 'app/shared/model/boxes.model';

describe('Component Tests', () => {
  describe('Boxes Management Detail Component', () => {
    let comp: BoxesDetailComponent;
    let fixture: ComponentFixture<BoxesDetailComponent>;
    const route = ({ data: of({ boxes: new Boxes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [BoxesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BoxesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BoxesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load boxes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.boxes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
