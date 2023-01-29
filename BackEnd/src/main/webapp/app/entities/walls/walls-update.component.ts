import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWalls, Walls } from 'app/shared/model/walls.model';
import { WallsService } from './walls.service';
import { IMap } from 'app/shared/model/map.model';
import { MapService } from 'app/entities/map/map.service';

@Component({
  selector: 'jhi-walls-update',
  templateUrl: './walls-update.component.html',
})
export class WallsUpdateComponent implements OnInit {
  isSaving = false;
  maps: IMap[] = [];

  editForm = this.fb.group({
    id: [],
    positionX: [],
    positionY: [],
    positionZ: [],
    map: [],
  });

  constructor(
    protected wallsService: WallsService,
    protected mapService: MapService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ walls }) => {
      this.updateForm(walls);

      this.mapService.query().subscribe((res: HttpResponse<IMap[]>) => (this.maps = res.body || []));
    });
  }

  updateForm(walls: IWalls): void {
    this.editForm.patchValue({
      id: walls.id,
      positionX: walls.positionX,
      positionY: walls.positionY,
      positionZ: walls.positionZ,
      map: walls.map,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const walls = this.createFromForm();
    if (walls.id !== undefined) {
      this.subscribeToSaveResponse(this.wallsService.update(walls));
    } else {
      this.subscribeToSaveResponse(this.wallsService.create(walls));
    }
  }

  private createFromForm(): IWalls {
    return {
      ...new Walls(),
      id: this.editForm.get(['id'])!.value,
      positionX: this.editForm.get(['positionX'])!.value,
      positionY: this.editForm.get(['positionY'])!.value,
      positionZ: this.editForm.get(['positionZ'])!.value,
      map: this.editForm.get(['map'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWalls>>): void {
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

  trackById(index: number, item: IMap): any {
    return item.id;
  }
}
