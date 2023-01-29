import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoalPosition } from 'app/shared/model/goal-position.model';
import { GoalPositionService } from './goal-position.service';
import { GoalPositionDeleteDialogComponent } from './goal-position-delete-dialog.component';

@Component({
  selector: 'jhi-goal-position',
  templateUrl: './goal-position.component.html',
})
export class GoalPositionComponent implements OnInit, OnDestroy {
  goalPositions?: IGoalPosition[];
  eventSubscriber?: Subscription;

  constructor(
    protected goalPositionService: GoalPositionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.goalPositionService.query().subscribe((res: HttpResponse<IGoalPosition[]>) => (this.goalPositions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGoalPositions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGoalPosition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGoalPositions(): void {
    this.eventSubscriber = this.eventManager.subscribe('goalPositionListModification', () => this.loadAll());
  }

  delete(goalPosition: IGoalPosition): void {
    const modalRef = this.modalService.open(GoalPositionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.goalPosition = goalPosition;
  }
}
