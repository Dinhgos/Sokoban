import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SaveDetailComponent } from 'app/entities/save/save-detail.component';
import { Save } from 'app/shared/model/save.model';

describe('Component Tests', () => {
  describe('Save Management Detail Component', () => {
    let comp: SaveDetailComponent;
    let fixture: ComponentFixture<SaveDetailComponent>;
    const route = ({ data: of({ save: new Save(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [SaveDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SaveDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SaveDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load save on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.save).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
