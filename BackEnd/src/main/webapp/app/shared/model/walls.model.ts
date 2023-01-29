import { IMap } from 'app/shared/model/map.model';

export interface IWalls {
  id?: number;
  positionX?: number;
  positionY?: number;
  positionZ?: number;
  map?: IMap;
}

export class Walls implements IWalls {
  constructor(public id?: number, public positionX?: number, public positionY?: number, public positionZ?: number, public map?: IMap) {}
}
