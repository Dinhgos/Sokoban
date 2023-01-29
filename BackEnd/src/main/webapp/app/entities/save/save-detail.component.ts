import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISave } from 'app/shared/model/save.model';

@Component({
  selector: 'jhi-save-detail',
  templateUrl: './save-detail.component.html',
})
export class SaveDetailComponent implements OnInit {
  save: ISave | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ save }) => (this.save = save));
  }

  previousState(): void {
    window.history.back();
  }
}
