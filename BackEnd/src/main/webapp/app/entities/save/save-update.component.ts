import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISave, Save } from 'app/shared/model/save.model';
import { SaveService } from './save.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player/player.service';
import { IMap } from 'app/shared/model/map.model';
import { MapService } from 'app/entities/map/map.service';

type SelectableEntity = IPlayer | IMap;

@Component({
  selector: 'jhi-save-update',
  templateUrl: './save-update.component.html',
})
export class SaveUpdateComponent implements OnInit {
  isSaving = false;
  players: IPlayer[] = [];
  maps: IMap[] = [];

  editForm = this.fb.group({
    id: [],
    moves: [],
    time: [],
    playerPositionX: [],
    playerPositionY: [],
    playerPositionZ: [],
    player: [],
    map: [],
  });

  constructor(
    protected saveService: SaveService,
    protected playerService: PlayerService,
    protected mapService: MapService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ save }) => {
      this.updateForm(save);

      this.playerService.query().subscribe((res: HttpResponse<IPlayer[]>) => (this.players = res.body || []));

      this.mapService.query().subscribe((res: HttpResponse<IMap[]>) => (this.maps = res.body || []));
    });
  }

  updateForm(save: ISave): void {
    this.editForm.patchValue({
      id: save.id,
      moves: save.moves,
      time: save.time,
      playerPositionX: save.playerPositionX,
      playerPositionY: save.playerPositionY,
      playerPositionZ: save.playerPositionZ,
      player: save.player,
      map: save.map,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const save = this.createFromForm();
    if (save.id !== undefined) {
      this.subscribeToSaveResponse(this.saveService.update(save));
    } else {
      this.subscribeToSaveResponse(this.saveService.create(save));
    }
  }

  private createFromForm(): ISave {
    return {
      ...new Save(),
      id: this.editForm.get(['id'])!.value,
      moves: this.editForm.get(['moves'])!.value,
      time: this.editForm.get(['time'])!.value,
      playerPositionX: this.editForm.get(['playerPositionX'])!.value,
      playerPositionY: this.editForm.get(['playerPositionY'])!.value,
      playerPositionZ: this.editForm.get(['playerPositionZ'])!.value,
      player: this.editForm.get(['player'])!.value,
      map: this.editForm.get(['map'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISave>>): void {
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
