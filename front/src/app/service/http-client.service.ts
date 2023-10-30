import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export interface Person {
  name: string,
  surname: string,
  password: string,
  email: string,
  birthday: Date
}

export interface Response<T> {
  code: number,
  payload: T,
}

@Injectable({
  providedIn: 'root'
})

export class HttpClientService {
  url:string = 'http://crud-back:8080/person'
  //url: string = 'http://localhost:8080/person'

  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })

  constructor(private httpClient: HttpClient) { }

  createPerson(person: Person) {
    return this.httpClient.post<Response<Person>>(`${this.url}`, person, { headers: this.headers })
  }

  getPerson(email: string) {
    return this.httpClient.get<Response<Person>>(`${this.url}/byemail/${email}`, { headers: this.headers })
  }

  getAll() {
    return this.httpClient.get<Response<Person[]>>(`${this.url}/all`, { headers: this.headers })
  }

  deletePerson(email: string) {
    return this.httpClient.delete<Response<Person>>(`${this.url}/byemail/${email}`, { headers: this.headers })
  }

  updatePerson(email: string, person: Person) {
    return this.httpClient.put<Response<Person>>(`${this.url}/email/${email}`, person, { headers: this.headers })
  }

}
