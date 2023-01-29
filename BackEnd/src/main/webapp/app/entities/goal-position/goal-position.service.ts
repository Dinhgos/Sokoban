import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoalPosition } from 'app/shared/model/goal-position.model';

type EntityResponseType = HttpResponse<IGoalPosition>;
type EntityArrayResponseType = HttpResponse<IGoalPosition[]>;

@Injectable({ providedIn: 'root' })
export class GoalPositionService {
  public resourceUrl = SERVER_API_URL + 'api/goal-positions';

  constructor(protected http: HttpClient) {}

  create(goalPosition: IGoalPosition): Observable<EntityResponseType> {
    return this.http.post<IGoalPosition>(this.resourceUrl, goalPosition, { observe: 'response' });
  }

  update(goalPosition: IGoalPosition): Observable<EntityResponseType> {
    return this.http.put<IGoalPosition>(this.resourceUrl, goalPosition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoalPosition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoalPosition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
