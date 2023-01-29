import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBoxes } from 'app/shared/model/boxes.model';

type EntityResponseType = HttpResponse<IBoxes>;
type EntityArrayResponseType = HttpResponse<IBoxes[]>;

@Injectable({ providedIn: 'root' })
export class BoxesService {
  public resourceUrl = SERVER_API_URL + 'api/boxes';

  constructor(protected http: HttpClient) {}

  create(boxes: IBoxes): Observable<EntityResponseType> {
    return this.http.post<IBoxes>(this.resourceUrl, boxes, { observe: 'response' });
  }

  update(boxes: IBoxes): Observable<EntityResponseType> {
    return this.http.put<IBoxes>(this.resourceUrl, boxes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBoxes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBoxes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
