import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGoalPosition, GoalPosition } from 'app/shared/model/goal-position.model';
import { GoalPositionService } from './goal-position.service';
import { GoalPositionComponent } from './goal-position.component';
import { GoalPositionDetailComponent } from './goal-position-detail.component';
import { GoalPositionUpdateComponent } from './goal-position-update.component';

@Injectable({ providedIn: 'root' })
export class GoalPositionResolve implements Resolve<IGoalPosition> {
  constructor(private service: GoalPositionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoalPosition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((goalPosition: HttpResponse<GoalPosition>) => {
          if (goalPosition.body) {
            return of(goalPosition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoalPosition());
  }
}

export const goalPositionRoute: Routes = [
  {
    path: '',
    component: GoalPositionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GoalPositions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoalPositionDetailComponent,
    resolve: {
      goalPosition: GoalPositionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GoalPositions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoalPositionUpdateComponent,
    resolve: {
      goalPosition: GoalPositionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GoalPositions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoalPositionUpdateComponent,
    resolve: {
      goalPosition: GoalPositionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GoalPositions',
    },
    canActivate: [UserRouteAccessService],
  },
];
