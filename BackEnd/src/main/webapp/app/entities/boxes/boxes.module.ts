import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared/shared.module';
import { BoxesComponent } from './boxes.component';
import { BoxesDetailComponent } from './boxes-detail.component';
import { BoxesUpdateComponent } from './boxes-update.component';
import { BoxesDeleteDialogComponent } from './boxes-delete-dialog.component';
import { boxesRoute } from './boxes.route';

@NgModule({
  imports: [MyAppSharedModule, RouterModule.forChild(boxesRoute)],
  declarations: [BoxesComponent, BoxesDetailComponent, BoxesUpdateComponent, BoxesDeleteDialogComponent],
  entryComponents: [BoxesDeleteDialogComponent],
})
export class MyAppBoxesModule {}
