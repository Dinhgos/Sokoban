import { ISave } from 'app/shared/model/save.model';
import { IMap } from 'app/shared/model/map.model';

export interface IBoxes {
  id?: number;
  positionX?: number;
  positionY?: number;
  positionZ?: number;
  save?: ISave;
  map?: IMap;
}

export class Boxes implements IBoxes {
  constructor(
    public id?: number,
    public positionX?: number,
    public positionY?: number,
    public positionZ?: number,
    public save?: ISave,
    public map?: IMap
  ) {}
}
