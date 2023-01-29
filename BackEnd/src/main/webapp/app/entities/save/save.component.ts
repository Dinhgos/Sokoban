import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISave } from 'app/shared/model/save.model';
import { SaveService } from './save.service';
import { SaveDeleteDialogComponent } from './save-delete-dialog.component';

@Component({
  selector: 'jhi-save',
  templateUrl: './save.component.html',
})
export class SaveComponent implements OnInit, OnDestroy {
  saves?: ISave[];
  eventSubscriber?: Subscription;

  constructor(protected saveService: SaveService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.saveService.query().subscribe((res: HttpResponse<ISave[]>) => (this.saves = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSaves();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISave): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSaves(): void {
    this.eventSubscriber = this.eventManager.subscribe('saveListModification', () => this.loadAll());
  }

  delete(save: ISave): void {
    const modalRef = this.modalService.open(SaveDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.save = save;
  }
}
