import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IScore, Score } from 'app/shared/model/score.model';
import { ScoreService } from './score.service';
import { IMap } from 'app/shared/model/map.model';
import { MapService } from 'app/entities/map/map.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player/player.service';

type SelectableEntity = IMap | IPlayer;

@Component({
  selector: 'jhi-score-update',
  templateUrl: './score-update.component.html',
})
export class ScoreUpdateComponent implements OnInit {
  isSaving = false;
  maps: IMap[] = [];
  players: IPlayer[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    date: [],
    map: [],
    player: [],
  });

  constructor(
    protected scoreService: ScoreService,
    protected mapService: MapService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ score }) => {
      if (!score.id) {
        const today = moment().startOf('day');
        score.date = today;
      }

      this.updateForm(score);

      this.mapService.query().subscribe((res: HttpResponse<IMap[]>) => (this.maps = res.body || []));

      this.playerService.query().subscribe((res: HttpResponse<IPlayer[]>) => (this.players = res.body || []));
    });
  }

  updateForm(score: IScore): void {
    this.editForm.patchValue({
      id: score.id,
      value: score.value,
      date: score.date ? score.date.format(DATE_TIME_FORMAT) : null,
      map: score.map,
      player: score.player,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const score = this.createFromForm();
    if (score.id !== undefined) {
      this.subscribeToSaveResponse(this.scoreService.update(score));
    } else {
      this.subscribeToSaveResponse(this.scoreService.create(score));
    }
  }

  private createFromForm(): IScore {
    return {
      ...new Score(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      map: this.editForm.get(['map'])!.value,
      player: this.editForm.get(['player'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScore>>): void {
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
