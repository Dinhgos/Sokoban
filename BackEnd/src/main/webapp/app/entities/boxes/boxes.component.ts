import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBoxes } from 'app/shared/model/boxes.model';
import { BoxesService } from './boxes.service';
import { BoxesDeleteDialogComponent } from './boxes-delete-dialog.component';

@Component({
  selector: 'jhi-boxes',
  templateUrl: './boxes.component.html',
})
export class BoxesComponent implements OnInit, OnDestroy {
  boxes?: IBoxes[];
  eventSubscriber?: Subscription;

  constructor(protected boxesService: BoxesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.boxesService.query().subscribe((res: HttpResponse<IBoxes[]>) => (this.boxes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBoxes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBoxes): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBoxes(): void {
    this.eventSubscriber = this.eventManager.subscribe('boxesListModification', () => this.loadAll());
  }

  delete(boxes: IBoxes): void {
    const modalRef = this.modalService.open(BoxesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.boxes = boxes;
  }
}
