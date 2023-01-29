import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared/shared.module';
import { WallsComponent } from './walls.component';
import { WallsDetailComponent } from './walls-detail.component';
import { WallsUpdateComponent } from './walls-update.component';
import { WallsDeleteDialogComponent } from './walls-delete-dialog.component';
import { wallsRoute } from './walls.route';

@NgModule({
  imports: [MyAppSharedModule, RouterModule.forChild(wallsRoute)],
  declarations: [WallsComponent, WallsDetailComponent, WallsUpdateComponent, WallsDeleteDialogComponent],
  entryComponents: [WallsDeleteDialogComponent],
})
export class MyAppWallsModule {}
