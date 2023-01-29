import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWalls } from 'app/shared/model/walls.model';
import { WallsService } from './walls.service';
import { WallsDeleteDialogComponent } from './walls-delete-dialog.component';

@Component({
  selector: 'jhi-walls',
  templateUrl: './walls.component.html',
})
export class WallsComponent implements OnInit, OnDestroy {
  walls?: IWalls[];
  eventSubscriber?: Subscription;

  constructor(protected wallsService: WallsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.wallsService.query().subscribe((res: HttpResponse<IWalls[]>) => (this.walls = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWalls();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWalls): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWalls(): void {
    this.eventSubscriber = this.eventManager.subscribe('wallsListModification', () => this.loadAll());
  }

  delete(walls: IWalls): void {
    const modalRef = this.modalService.open(WallsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.walls = walls;
  }
}
