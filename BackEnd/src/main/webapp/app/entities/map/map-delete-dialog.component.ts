import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMap } from 'app/shared/model/map.model';
import { MapService } from './map.service';

@Component({
  templateUrl: './map-delete-dialog.component.html',
})
export class MapDeleteDialogComponent {
  map?: IMap;

  constructor(protected mapService: MapService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mapService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mapListModification');
      this.activeModal.close();
    });
  }
}
