import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBoxes } from 'app/shared/model/boxes.model';

@Component({
  selector: 'jhi-boxes-detail',
  templateUrl: './boxes-detail.component.html',
})
export class BoxesDetailComponent implements OnInit {
  boxes: IBoxes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boxes }) => (this.boxes = boxes));
  }

  previousState(): void {
    window.history.back();
  }
}
