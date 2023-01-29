import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared/shared.module';
import { SaveComponent } from './save.component';
import { SaveDetailComponent } from './save-detail.component';
import { SaveUpdateComponent } from './save-update.component';
import { SaveDeleteDialogComponent } from './save-delete-dialog.component';
import { saveRoute } from './save.route';

@NgModule({
  imports: [MyAppSharedModule, RouterModule.forChild(saveRoute)],
  declarations: [SaveComponent, SaveDetailComponent, SaveUpdateComponent, SaveDeleteDialogComponent],
  entryComponents: [SaveDeleteDialogComponent],
})
export class MyAppSaveModule {}
