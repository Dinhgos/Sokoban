import { Moment } from 'moment';
import { IMap } from 'app/shared/model/map.model';
import { IPlayer } from 'app/shared/model/player.model';

export interface IScore {
  id?: number;
  value?: number;
  date?: Moment;
  map?: IMap;
  player?: IPlayer;
}

export class Score implements IScore {
  constructor(public id?: number, public value?: number, public date?: Moment, public map?: IMap, public player?: IPlayer) {}
}
