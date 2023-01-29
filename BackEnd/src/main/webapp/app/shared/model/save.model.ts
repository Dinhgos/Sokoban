import { IBoxes } from 'app/shared/model/boxes.model';
import { IPlayer } from 'app/shared/model/player.model';
import { IMap } from 'app/shared/model/map.model';

export interface ISave {
  id?: number;
  moves?: number;
  time?: number;
  playerPositionX?: number;
  playerPositionY?: number;
  playerPositionZ?: number;
  boxes?: IBoxes[];
  player?: IPlayer;
  map?: IMap;
}

export class Save implements ISave {
  constructor(
    public id?: number,
    public moves?: number,
    public time?: number,
    public playerPositionX?: number,
    public playerPositionY?: number,
    public playerPositionZ?: number,
    public boxes?: IBoxes[],
    public player?: IPlayer,
    public map?: IMap
  ) {}
}
