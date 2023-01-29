import { IMap } from 'app/shared/model/map.model';

export interface IGoalPosition {
  id?: number;
  positionX?: number;
  positionY?: number;
  positionZ?: number;
  map?: IMap;
}

export class GoalPosition implements IGoalPosition {
  constructor(public id?: number, public positionX?: number, public positionY?: number, public positionZ?: number, public map?: IMap) {}
}
