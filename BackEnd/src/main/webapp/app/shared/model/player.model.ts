import { IScore } from 'app/shared/model/score.model';
import { ISave } from 'app/shared/model/save.model';

export interface IPlayer {
  id?: number;
  name?: string;
  password?: string;
  level?: number;
  scores?: IScore[];
  saves?: ISave[];
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public name?: string,
    public password?: string,
    public level?: number,
    public scores?: IScore[],
    public saves?: ISave[]
  ) {}
}
