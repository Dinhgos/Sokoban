import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared/shared.module';
import { MapComponent } from './map.component';
import { MapDetailComponent } from './map-detail.component';
import { MapUpdateComponent } from './map-update.component';
import { MapDeleteDialogComponent } from './map-delete-dialog.component';
import { mapRoute } from './map.route';

@NgModule({
  imports: [MyAppSharedModule, RouterModule.forChild(mapRoute)],
  declarations: [MapComponent, MapDetailComponent, MapUpdateComponent, MapDeleteDialogComponent],
  entryComponents: [MapDeleteDialogComponent],
})
export class MyAppMapModule {}
