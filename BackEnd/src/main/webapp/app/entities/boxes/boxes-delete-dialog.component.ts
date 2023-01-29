import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBoxes } from 'app/shared/model/boxes.model';
import { BoxesService } from './boxes.service';

@Component({
  templateUrl: './boxes-delete-dialog.component.html',
})
export class BoxesDeleteDialogComponent {
  boxes?: IBoxes;

  constructor(protected boxesService: BoxesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boxesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('boxesListModification');
      this.activeModal.close();
    });
  }
}
