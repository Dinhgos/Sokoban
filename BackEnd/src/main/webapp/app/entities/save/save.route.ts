import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISave, Save } from 'app/shared/model/save.model';
import { SaveService } from './save.service';
import { SaveComponent } from './save.component';
import { SaveDetailComponent } from './save-detail.component';
import { SaveUpdateComponent } from './save-update.component';

@Injectable({ providedIn: 'root' })
export class SaveResolve implements Resolve<ISave> {
  constructor(private service: SaveService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISave> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((save: HttpResponse<Save>) => {
          if (save.body) {
            return of(save.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Save());
  }
}

export const saveRoute: Routes = [
  {
    path: '',
    component: SaveComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Saves',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SaveDetailComponent,
    resolve: {
      save: SaveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Saves',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SaveUpdateComponent,
    resolve: {
      save: SaveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Saves',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SaveUpdateComponent,
    resolve: {
      save: SaveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Saves',
    },
    canActivate: [UserRouteAccessService],
  },
];
