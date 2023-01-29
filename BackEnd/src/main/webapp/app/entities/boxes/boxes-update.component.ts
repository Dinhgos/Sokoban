import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBoxes, Boxes } from 'app/shared/model/boxes.model';
import { BoxesService } from './boxes.service';
import { ISave } from 'app/shared/model/save.model';
import { SaveService } from 'app/entities/save/save.service';
import { IMap } from 'app/shared/model/map.model';
import { MapService } from 'app/entities/map/map.service';

type SelectableEntity = ISave | IMap;

@Component({
  selector: 'jhi-boxes-update',
  templateUrl: './boxes-update.component.html',
})
export class BoxesUpdateComponent implements OnInit {
  isSaving = false;
  saves: ISave[] = [];
  maps: IMap[] = [];

  editForm = this.fb.group({
    id: [],
    positionX: [],
    positionY: [],
    positionZ: [],
    save: [],
    map: [],
  });

  constructor(
    protected boxesService: BoxesService,
    protected saveService: SaveService,
    protected mapService: MapService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boxes }) => {
      this.updateForm(boxes);

      this.saveService.query().subscribe((res: HttpResponse<ISave[]>) => (this.saves = res.body || []));

      this.mapService.query().subscribe((res: HttpResponse<IMap[]>) => (this.maps = res.body || []));
    });
  }

  updateForm(boxes: IBoxes): void {
    this.editForm.patchValue({
      id: boxes.id,
      positionX: boxes.positionX,
      positionY: boxes.positionY,
      positionZ: boxes.positionZ,
      save: boxes.save,
      map: boxes.map,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const boxes = this.createFromForm();
    if (boxes.id !== undefined) {
      this.subscribeToSaveResponse(this.boxesService.update(boxes));
    } else {
      this.subscribeToSaveResponse(this.boxesService.create(boxes));
    }
  }

  private createFromForm(): IBoxes {
    return {
      ...new Boxes(),
      id: this.editForm.get(['id'])!.value,
      positionX: this.editForm.get(['positionX'])!.value,
      positionY: this.editForm.get(['positionY'])!.value,
      positionZ: this.editForm.get(['positionZ'])!.value,
      save: this.editForm.get(['save'])!.value,
      map: this.editForm.get(['map'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoxes>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
