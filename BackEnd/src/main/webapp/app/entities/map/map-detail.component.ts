import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMap } from 'app/shared/model/map.model';

@Component({
  selector: 'jhi-map-detail',
  templateUrl: './map-detail.component.html',
})
export class MapDetailComponent implements OnInit {
  map: IMap | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ map }) => (this.map = map));
  }

  previousState(): void {
    window.history.back();
  }
}
