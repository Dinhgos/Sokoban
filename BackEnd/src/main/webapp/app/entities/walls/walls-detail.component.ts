import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWalls } from 'app/shared/model/walls.model';

@Component({
  selector: 'jhi-walls-detail',
  templateUrl: './walls-detail.component.html',
})
export class WallsDetailComponent implements OnInit {
  walls: IWalls | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ walls }) => (this.walls = walls));
  }

  previousState(): void {
    window.history.back();
  }
}
