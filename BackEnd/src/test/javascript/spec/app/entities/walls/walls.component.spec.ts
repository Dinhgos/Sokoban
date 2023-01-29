import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { WallsComponent } from 'app/entities/walls/walls.component';
import { WallsService } from 'app/entities/walls/walls.service';
import { Walls } from 'app/shared/model/walls.model';

describe('Component Tests', () => {
  describe('Walls Management Component', () => {
    let comp: WallsComponent;
    let fixture: ComponentFixture<WallsComponent>;
    let service: WallsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [WallsComponent],
      })
        .overrideTemplate(WallsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WallsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WallsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Walls(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.walls && comp.walls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
