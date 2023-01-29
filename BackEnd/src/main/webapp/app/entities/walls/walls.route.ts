import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWalls, Walls } from 'app/shared/model/walls.model';
import { WallsService } from './walls.service';
import { WallsComponent } from './walls.component';
import { WallsDetailComponent } from './walls-detail.component';
import { WallsUpdateComponent } from './walls-update.component';

@Injectable({ providedIn: 'root' })
export class WallsResolve implements Resolve<IWalls> {
  constructor(private service: WallsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWalls> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((walls: HttpResponse<Walls>) => {
          if (walls.body) {
            return of(walls.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Walls());
  }
}

export const wallsRoute: Routes = [
  {
    path: '',
    component: WallsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Walls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WallsDetailComponent,
    resolve: {
      walls: WallsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Walls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WallsUpdateComponent,
    resolve: {
      walls: WallsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Walls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WallsUpdateComponent,
    resolve: {
      walls: WallsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Walls',
    },
    canActivate: [UserRouteAccessService],
  },
];
