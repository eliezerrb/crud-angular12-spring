import { Component, Input, OnInit } from '@angular/core';
import { Course } from '../model/course';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.scss']
})
export class CoursesListComponent implements OnInit {

  @Input() courses: Course[] = [];
  // readonly como se fosse um objeto final
  readonly displayedColumns = ['name', 'category', 'actions'];

  constructor(
    private router: Router,
    // ActivatedRoute rota que temos agora
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  onAdd() {
    console.log('onAdd');
    // Pego a rota que eu estou e agrego o / new
    this.router.navigate(['new'], {relativeTo: this.route});
  }


}
