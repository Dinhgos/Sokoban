import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBoxes, Boxes } from 'app/shared/model/boxes.model';
import { BoxesService } from './boxes.service';
import { BoxesComponent } from './boxes.component';
import { BoxesDetailComponent } from './boxes-detail.component';
import { BoxesUpdateComponent } from './boxes-update.component';

@Injectable({ providedIn: 'root' })
export class BoxesResolve implements Resolve<IBoxes> {
  constructor(private service: BoxesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBoxes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((boxes: HttpResponse<Boxes>) => {
          if (boxes.body) {
            return of(boxes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Boxes());
  }
}

export const boxesRoute: Routes = [
  {
    path: '',
    component: BoxesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Boxes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BoxesDetailComponent,
    resolve: {
      boxes: BoxesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Boxes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BoxesUpdateComponent,
    resolve: {
      boxes: BoxesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Boxes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BoxesUpdateComponent,
    resolve: {
      boxes: BoxesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Boxes',
    },
    canActivate: [UserRouteAccessService],
  },
];
