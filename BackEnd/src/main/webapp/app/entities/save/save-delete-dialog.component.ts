import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISave } from 'app/shared/model/save.model';
import { SaveService } from './save.service';

@Component({
  templateUrl: './save-delete-dialog.component.html',
})
export class SaveDeleteDialogComponent {
  save?: ISave;

  constructor(protected saveService: SaveService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.saveService.delete(id).subscribe(() => {
      this.eventManager.broadcast('saveListModification');
      this.activeModal.close();
    });
  }
}
