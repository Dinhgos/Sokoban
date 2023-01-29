import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISave } from 'app/shared/model/save.model';

type EntityResponseType = HttpResponse<ISave>;
type EntityArrayResponseType = HttpResponse<ISave[]>;

@Injectable({ providedIn: 'root' })
export class SaveService {
  public resourceUrl = SERVER_API_URL + 'api/saves';

  constructor(protected http: HttpClient) {}

  create(save: ISave): Observable<EntityResponseType> {
    return this.http.post<ISave>(this.resourceUrl, save, { observe: 'response' });
  }

  update(save: ISave): Observable<EntityResponseType> {
    return this.http.put<ISave>(this.resourceUrl, save, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISave>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISave[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
