import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGoalPosition, GoalPosition } from 'app/shared/model/goal-position.model';
import { GoalPositionService } from './goal-position.service';
import { IMap } from 'app/shared/model/map.model';
import { MapService } from 'app/entities/map/map.service';

@Component({
  selector: 'jhi-goal-position-update',
  templateUrl: './goal-position-update.component.html',
})
export class GoalPositionUpdateComponent implements OnInit {
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
    protected goalPositionService: GoalPositionService,
    protected mapService: MapService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goalPosition }) => {
      this.updateForm(goalPosition);

      this.mapService.query().subscribe((res: HttpResponse<IMap[]>) => (this.maps = res.body || []));
    });
  }

  updateForm(goalPosition: IGoalPosition): void {
    this.editForm.patchValue({
      id: goalPosition.id,
      positionX: goalPosition.positionX,
      positionY: goalPosition.positionY,
      positionZ: goalPosition.positionZ,
      map: goalPosition.map,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goalPosition = this.createFromForm();
    if (goalPosition.id !== undefined) {
      this.subscribeToSaveResponse(this.goalPositionService.update(goalPosition));
    } else {
      this.subscribeToSaveResponse(this.goalPositionService.create(goalPosition));
    }
  }

  private createFromForm(): IGoalPosition {
    return {
      ...new GoalPosition(),
      id: this.editForm.get(['id'])!.value,
      positionX: this.editForm.get(['positionX'])!.value,
      positionY: this.editForm.get(['positionY'])!.value,
      positionZ: this.editForm.get(['positionZ'])!.value,
      map: this.editForm.get(['map'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoalPosition>>): void {
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
