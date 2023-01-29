import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared/shared.module';
import { GoalPositionComponent } from './goal-position.component';
import { GoalPositionDetailComponent } from './goal-position-detail.component';
import { GoalPositionUpdateComponent } from './goal-position-update.component';
import { GoalPositionDeleteDialogComponent } from './goal-position-delete-dialog.component';
import { goalPositionRoute } from './goal-position.route';

@NgModule({
  imports: [MyAppSharedModule, RouterModule.forChild(goalPositionRoute)],
  declarations: [GoalPositionComponent, GoalPositionDetailComponent, GoalPositionUpdateComponent, GoalPositionDeleteDialogComponent],
  entryComponents: [GoalPositionDeleteDialogComponent],
})
export class MyAppGoalPositionModule {}
