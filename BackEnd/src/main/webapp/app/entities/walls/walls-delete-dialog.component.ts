import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWalls } from 'app/shared/model/walls.model';
import { WallsService } from './walls.service';

@Component({
  templateUrl: './walls-delete-dialog.component.html',
})
export class WallsDeleteDialogComponent {
  walls?: IWalls;

  constructor(protected wallsService: WallsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wallsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('wallsListModification');
      this.activeModal.close();
    });
  }
}
