import { HttpClient, HttpEvent, HttpParams, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private _baseUrl = "http://localhost:8085/media";


  constructor(private http: HttpClient) { }


  uploadFile(file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();
    data.append('file', file);
    const newRequest = new HttpRequest('POST', `${this._baseUrl}/upload`, data, {
    reportProgress: true,
    responseType: 'text'
    });
    return this.http.request(newRequest);
  }

  deleteFile(recipeImageGetUrl: string) {
    let fileId = recipeImageGetUrl.slice(37)
    this.http.delete(`${this._baseUrl}/delete/` + fileId)
  }

  updateFile(recipeImageGetUrl: string, file: File) {
    let fileToUpdateId = recipeImageGetUrl.slice(37)

    const data: FormData = new FormData();
    data.append('file', file);
    const newRequest = new HttpRequest('POST', `${this._baseUrl}/delete/${fileToUpdateId}`, data, {
    reportProgress: true,
    responseType: 'text'
    });
    return this.http.request(newRequest);
  }

}
