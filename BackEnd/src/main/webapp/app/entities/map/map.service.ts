import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMap } from 'app/shared/model/map.model';

type EntityResponseType = HttpResponse<IMap>;
type EntityArrayResponseType = HttpResponse<IMap[]>;

@Injectable({ providedIn: 'root' })
export class MapService {
  public resourceUrl = SERVER_API_URL + 'api/maps';

  constructor(protected http: HttpClient) {}

  create(map: IMap): Observable<EntityResponseType> {
    return this.http.post<IMap>(this.resourceUrl, map, { observe: 'response' });
  }

  update(map: IMap): Observable<EntityResponseType> {
    return this.http.put<IMap>(this.resourceUrl, map, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMap>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMap[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
