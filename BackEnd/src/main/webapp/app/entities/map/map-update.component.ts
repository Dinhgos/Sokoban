import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMap, Map } from 'app/shared/model/map.model';
import { MapService } from './map.service';

@Component({
  selector: 'jhi-map-update',
  templateUrl: './map-update.component.html',
})
export class MapUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    playerPositionX: [],
    playerPositionY: [],
    playerPositionZ: [],
  });

  constructor(protected mapService: MapService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ map }) => {
      this.updateForm(map);
    });
  }

  updateForm(map: IMap): void {
    this.editForm.patchValue({
      id: map.id,
      playerPositionX: map.playerPositionX,
      playerPositionY: map.playerPositionY,
      playerPositionZ: map.playerPositionZ,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const map = this.createFromForm();
    if (map.id !== undefined) {
      this.subscribeToSaveResponse(this.mapService.update(map));
    } else {
      this.subscribeToSaveResponse(this.mapService.create(map));
    }
  }

  private createFromForm(): IMap {
    return {
      ...new Map(),
      id: this.editForm.get(['id'])!.value,
      playerPositionX: this.editForm.get(['playerPositionX'])!.value,
      playerPositionY: this.editForm.get(['playerPositionY'])!.value,
      playerPositionZ: this.editForm.get(['playerPositionZ'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMap>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
