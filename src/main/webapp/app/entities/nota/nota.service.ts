import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INota } from 'app/shared/model/nota.model';

type EntityResponseType = HttpResponse<INota>;
type EntityArrayResponseType = HttpResponse<INota[]>;

@Injectable({ providedIn: 'root' })
export class NotaService {
  public resourceUrl = SERVER_API_URL + 'api/notas';

  constructor(protected http: HttpClient) {}

  create(nota: INota): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nota);
    return this.http
      .post<INota>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nota: INota): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nota);
    return this.http
      .put<INota>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INota>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INota[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(nota: INota): INota {
    const copy: INota = Object.assign({}, nota, {
      data: nota.data && nota.data.isValid() ? nota.data.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nota: INota) => {
        nota.data = nota.data ? moment(nota.data) : undefined;
      });
    }
    return res;
  }
}
