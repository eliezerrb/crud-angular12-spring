import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Course } from '../model/course';

import { delay, first, tap } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  // readonly previnir que eu faça modificações nesse valor
  // caminho do endpoint
  private readonly API = '/assets/courses.json'

  constructor(private httpClient: HttpClient) { }

  list() {
    // pipe pode manipular o retorno do get de maneira reativaa antes do retorno final
    return this.httpClient.get<Course[]>(this.API)
    .pipe(
      // pegar a primeira resposta e fechar a conexão, isso porque não é um websocket
      first(),
      delay(5000),
      // quando eu receber a lista de cursos eu faço alguma coisa, nessa caso mostra o log
      tap(courses => console.log(courses))
    );
}

}
