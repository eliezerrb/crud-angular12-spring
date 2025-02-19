import { CoursesService } from './../services/courses.service';
import { Component, OnInit } from '@angular/core';
import { Course } from '../model/course';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit {
  // por padrão tem que tipar, usar o any somente se não souber o tipo, ex: course: any[] = [];
  courses$: Observable<Course[]>;
  displayedColumns = ['name', 'category', 'actions'];

  // coursesService: CoursesService;

  constructor(
    private coursesService: CoursesService,
    public dialog: MatDialog,
    private router: Router,
    // ActivatedRoute rota que temos agora
    private route: ActivatedRoute
  ) {
    // iniciar a variável ou no construtor ou na declaração
    // this.courses = []

    // fazendo dessa forma quando não faz a injeção de dependencia no parametro do construtor
    // this.coursesService = new CoursesService();
    this.courses$ = this.coursesService.list().pipe(
      catchError((error) => {
        this.onError('Erro ao carregar cursos.');
        return of([]);
      })
    );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    });
  }

  onAdd() {
    console.log('onAdd');
    // Pego a rota que eu estou e agrego o / new
    this.router.navigate(['new'], {relativeTo: this.route});
  }

  ngOnInit(): void {}
}
