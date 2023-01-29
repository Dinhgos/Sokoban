import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoalPosition } from 'app/shared/model/goal-position.model';
import { GoalPositionService } from './goal-position.service';

@Component({
  templateUrl: './goal-position-delete-dialog.component.html',
})
export class GoalPositionDeleteDialogComponent {
  goalPosition?: IGoalPosition;

  constructor(
    protected goalPositionService: GoalPositionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goalPositionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('goalPositionListModification');
      this.activeModal.close();
    });
  }
}
