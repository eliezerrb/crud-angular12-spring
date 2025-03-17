import { Lesson } from './../../model/lesson';
import { Course } from './../../model/course';
import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  Form,
  FormGroup,
  NonNullableFormBuilder,
  UntypedFormArray,
  Validators,
} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { CoursesService } from '../../services/courses.service';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss'],
})
export class CourseFormComponent implements OnInit {
  // ! permite que a variavel posso ser inicializada em outro momento ngOnInit ou no construtor
  form!: FormGroup;

  /*   form = this.formBuilder.group({
    _id: [''],
    name: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    category: ['', Validators.required],
  }); */

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute
  ) {
    //this.form
  }

  ngOnInit(): void {
    // o snapshot é uma foto da rota, ele pega o valor do curso que foi passado no resolver, ou seja o .data pode ser qualquer coisa que foi passado no resolver
    const course = this.route.snapshot.data['course'];

    // caso o course seja vazio o resolver já está passando vazio
    this.form = this.formBuilder.group({
      _id: [course._id],
      name: [
        course.name,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100),
        ],
      ],
      category: [course.category, Validators.required],
      lessons: this.formBuilder.array(
        this.retrieveLessons(course),
        Validators.required
      ),
    });
    console.log(this.form);
    console.log(this.form.value);
  }

  private retrieveLessons(course: Course) {
    const lessons = [];
    if (course?.lessons) {
      course.lessons.forEach((lesson) => {
        lessons.push(this.createLesson(lesson));
      });
    } else {
      lessons.push(this.createLesson());
    }
    return lessons;
  }

  // caso o valor da Lesson seja vazio, inicializa com um objeto vazio {id: '', name: '', youtubeUrl: ''}
  private createLesson(lesson: Lesson = { id: '', name: '', youtubeUrl: '' }) {
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100),
      ]],
      youtubeUrl: [lesson.youtubeUrl, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(11),
      ]],
    });
  }

  getLessonsFormArray() {
    return (<UntypedFormArray>this.form.get('lessons'))?.controls;
  }

  AddNewLesson() {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.push(this.createLesson());
  }

  removeLesson(index: number) {
    const lesson = this.form.get('lessons') as UntypedFormArray;
    lesson.removeAt(index);
  }

  onSubmit() {
    if (this.form.valid) {
      // o retorno do post é um observable, por isso tem que fazer o subscribe que é se inscrever
      this.service.save(this.form.value).subscribe((result) => this.onSuccess(), (error) => this.onError());
    } else {
      alert('Formulario inválido');
    }
  }

  onCancel() {
    this.location.back();
  }

  private onSuccess() {
    this.snackBar.open('Curso salvo com sucesso!', '', { duration: 5000 });
    this.onCancel();
  }

  private onError() {
    this.snackBar.open('Erro ao salvar o curso', '', { duration: 5000 });
  }

  getErrorMessage(fieldName: string) {
    const field = this.form.get(fieldName);

    if (field?.hasError('required')) {
      return 'Campo obrigatório';
    }

    if (field?.hasError('minlength')) {
      // minlenght é um objeto que tem requiredLength, que é o tamanho minimo que o campo deve ter retormando o valor do requiredLength ou o valor 5
      const requiredLength: number = field.errors
        ? field.errors['minlength']['requiredLength']
        : 5;
      return `Tamanho mínimo precisa ser de ${requiredLength} caracteres`;
    }

    if (field?.hasError('maxlength')) {
      const requiredLength: number = field.errors
        ? field.errors['maxlength']['requiredLength']
        : 200;
      return `Tamanho máximo execedido de ${requiredLength} caracteres`;
    }

    return 'Campo inválido';
  }

  isFormArrayRequired() {
    const lesson = this.form.get('lessons') as UntypedFormArray;
    // lesson.touched verifica se o campo foi tocado
    return !lesson.valid && lesson.hasError('required') && lesson.touched;
  }

}
