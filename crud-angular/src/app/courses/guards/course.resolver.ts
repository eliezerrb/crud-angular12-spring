import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Course } from '../model/course';
import { CoursesService } from '../services/courses.service';

@Injectable({
  providedIn: 'root'
})

// id do route.params['id'] é o id que vem da rota o mesmo do courses-routing.module.ts

export class CourseResolver implements Resolve<Course> {

  constructor(private service: CoursesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Course> {
    if(route.params && route.params['id']) {
      return this.service.loadById(route.params['id']);
    }

    // se não tiver id retorna um objeto Curso porque o novo passa por aqui também
    return of({
      _id: '',
      name: '',
      category: ''});
  }
}
