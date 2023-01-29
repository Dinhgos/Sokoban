import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWalls } from 'app/shared/model/walls.model';

type EntityResponseType = HttpResponse<IWalls>;
type EntityArrayResponseType = HttpResponse<IWalls[]>;

@Injectable({ providedIn: 'root' })
export class WallsService {
  public resourceUrl = SERVER_API_URL + 'api/walls';

  constructor(protected http: HttpClient) {}

  create(walls: IWalls): Observable<EntityResponseType> {
    return this.http.post<IWalls>(this.resourceUrl, walls, { observe: 'response' });
  }

  update(walls: IWalls): Observable<EntityResponseType> {
    return this.http.put<IWalls>(this.resourceUrl, walls, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWalls>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWalls[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
