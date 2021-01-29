import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDisciplina } from 'app/shared/model/disciplina.model';

type EntityResponseType = HttpResponse<IDisciplina>;
type EntityArrayResponseType = HttpResponse<IDisciplina[]>;

@Injectable({ providedIn: 'root' })
export class DisciplinaService {
  public resourceUrl = SERVER_API_URL + 'api/disciplinas';

  constructor(protected http: HttpClient) {}

  create(disciplina: IDisciplina): Observable<EntityResponseType> {
    return this.http.post<IDisciplina>(this.resourceUrl, disciplina, { observe: 'response' });
  }

  update(disciplina: IDisciplina): Observable<EntityResponseType> {
    return this.http.put<IDisciplina>(this.resourceUrl, disciplina, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDisciplina>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDisciplina[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
