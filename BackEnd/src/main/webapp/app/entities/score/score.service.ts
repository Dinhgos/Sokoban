import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IScore } from 'app/shared/model/score.model';

type EntityResponseType = HttpResponse<IScore>;
type EntityArrayResponseType = HttpResponse<IScore[]>;

@Injectable({ providedIn: 'root' })
export class ScoreService {
  public resourceUrl = SERVER_API_URL + 'api/scores';

  constructor(protected http: HttpClient) {}

  create(score: IScore): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(score);
    return this.http
      .post<IScore>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(score: IScore): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(score);
    return this.http
      .put<IScore>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IScore>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IScore[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(score: IScore): IScore {
    const copy: IScore = Object.assign({}, score, {
      date: score.date && score.date.isValid() ? score.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((score: IScore) => {
        score.date = score.date ? moment(score.date) : undefined;
      });
    }
    return res;
  }
}
