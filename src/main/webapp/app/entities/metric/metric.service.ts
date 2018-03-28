import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Metric } from './metric.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Metric>;

@Injectable()
export class MetricService {

    private resourceUrl =  SERVER_API_URL + 'api/metrics';

    constructor(private http: HttpClient) { }

    create(metric: Metric): Observable<EntityResponseType> {
        const copy = this.convert(metric);
        return this.http.post<Metric>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(metric: Metric): Observable<EntityResponseType> {
        const copy = this.convert(metric);
        return this.http.put<Metric>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Metric>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Metric[]>> {
        const options = createRequestOption(req);
        return this.http.get<Metric[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Metric[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Metric = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Metric[]>): HttpResponse<Metric[]> {
        const jsonResponse: Metric[] = res.body;
        const body: Metric[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Metric.
     */
    private convertItemFromServer(metric: Metric): Metric {
        const copy: Metric = Object.assign({}, metric);
        return copy;
    }

    /**
     * Convert a Metric to a JSON which can be sent to the server.
     */
    private convert(metric: Metric): Metric {
        const copy: Metric = Object.assign({}, metric);
        return copy;
    }
}
