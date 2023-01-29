import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMap } from 'app/shared/model/map.model';
import { MapService } from './map.service';
import { MapDeleteDialogComponent } from './map-delete-dialog.component';

@Component({
  selector: 'jhi-map',
  templateUrl: './map.component.html',
})
export class MapComponent implements OnInit, OnDestroy {
  maps?: IMap[];
  eventSubscriber?: Subscription;

  constructor(protected mapService: MapService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.mapService.query().subscribe((res: HttpResponse<IMap[]>) => (this.maps = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMaps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMap): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMaps(): void {
    this.eventSubscriber = this.eventManager.subscribe('mapListModification', () => this.loadAll());
  }

  delete(map: IMap): void {
    const modalRef = this.modalService.open(MapDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.map = map;
  }
}
