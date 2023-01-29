import { IScore } from 'app/shared/model/score.model';
import { ISave } from 'app/shared/model/save.model';
import { IGoalPosition } from 'app/shared/model/goal-position.model';
import { IWalls } from 'app/shared/model/walls.model';
import { IBoxes } from 'app/shared/model/boxes.model';

export interface IMap {
  id?: number;
  playerPositionX?: number;
  playerPositionY?: number;
  playerPositionZ?: number;
  scores?: IScore[];
  saves?: ISave[];
  goalPositions?: IGoalPosition[];
  walls?: IWalls[];
  boxes?: IBoxes[];
}

export class Map implements IMap {
  constructor(
    public id?: number,
    public playerPositionX?: number,
    public playerPositionY?: number,
    public playerPositionZ?: number,
    public scores?: IScore[],
    public saves?: ISave[],
    public goalPositions?: IGoalPosition[],
    public walls?: IWalls[],
    public boxes?: IBoxes[]
  ) {}
}
