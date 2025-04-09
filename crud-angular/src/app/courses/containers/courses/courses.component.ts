import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { CoursesService } from '../../services/courses.service';
import { Course } from './../../model/course';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { CoursePage } from '../../model/course-page';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { CoursesListComponent } from '../../components/courses-list/courses-list.component';
import { NgIf, AsyncPipe } from '@angular/common';
import { MatToolbar } from '@angular/material/toolbar';
import { MatCard } from '@angular/material/card';

@Component({
    selector: 'app-courses',
    templateUrl: './courses.component.html',
    styleUrls: ['./courses.component.scss'],
    standalone: true,
    imports: [
        MatCard,
        MatToolbar,
        NgIf,
        CoursesListComponent,
        MatPaginator,
        MatProgressSpinner,
        AsyncPipe,
    ],
})
export class CoursesComponent implements OnInit {
  // por padrão tem que tipar, usar o any somente se não souber o tipo, ex: course: any[] = [];
  courses$: Observable<CoursePage> | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  pageIndex = 0;
  pageSize = 10;

  // coursesService: CoursesService;

  constructor(
    private coursesService: CoursesService,
    public dialog: MatDialog,
    private router: Router,
    // ActivatedRoute rota que temos agora
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    // iniciar a variável ou no construtor ou na declaração
    // this.courses = []

    // fazendo dessa forma quando não faz a injeção de dependencia no parametro do construtor
    // this.coursesService = new CoursesService();
    this.refresh();
  }

  refresh(pageEvent: PageEvent = {length : 0, pageIndex: 0, pageSize: 10}) {
    this.courses$ = this.coursesService.list(pageEvent.pageIndex, pageEvent.pageSize)
    .pipe(
      tap(() => {
        this.pageIndex = pageEvent.pageIndex;
        this.pageSize = pageEvent.pageSize;
      }),
      catchError((error) => {
        this.onError('Erro ao carregar cursos.');
        return of({courses: [], totalElements: 0, totalPages: 0});
      })
    );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg,
    });
  }

  ngOnInit(): void {}

  onAdd() {
    console.log('onAdd');
    // Pego a rota que eu estou e agrego o / new
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  onEdit(course: Course) {
    this.router.navigate(['edit', course._id], { relativeTo: this.route });
  }

  onRemove(course: Course) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '320px',
      data: 'Deseja realmente remover o curso?',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.coursesService.remove(course._id).subscribe(
          () => {
            this.refresh();
            this.snackBar.open('Curso removido com sucesso!', 'X', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
          },
          () => this.onError('Erro ao remover curso.')
        );
      }
    });
  }
}
