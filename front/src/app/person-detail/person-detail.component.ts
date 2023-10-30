import { Component, OnInit } from '@angular/core';
import { NgForm, UntypedFormBuilder, UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { HttpClientService, Person, Response } from '../service/http-client.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-person-detail',
  templateUrl: './person-detail.component.html',
  styleUrls: ['./person-detail.component.css']
})

export class PersonDetailComponent implements OnInit {
  hideRequiredControl = new UntypedFormControl(false);
  floatLabelControl = new UntypedFormControl('auto');
  myControl = new UntypedFormControl();

  isAddMode: boolean;
  selEmail: string;

  fromPerson: Person

  stateForm: UntypedFormGroup = this.fb.group({
    stateGroup: ''
  });

  personForm: UntypedFormGroup = this.fb.group({
    name: ['', [Validators.required]],
    surname: ['', [Validators.required]],
    password: ['', [Validators.required]],
    email: ['', [Validators.required]],
    birthday: [new Date(), [Validators.required]]
  });

  constructor(
    private _snackBar: MatSnackBar,
    private service: HttpClientService,
    private activatedRoute: ActivatedRoute,
    private fb: UntypedFormBuilder,
    private router: Router) {
    this.createPersonForm();
  }

  ngOnInit(): void {
    this.selEmail = this.activatedRoute.snapshot.params['email'];

    this.isAddMode = this.router.url.includes('add');
    if (!this.isAddMode)
      this.initForm(this.selEmail);

  }

  onSubmit(form: NgForm) {
    console.log('Your form data : ', form.value);
  }

  createPersonForm() {
    this.personForm = this.personForm;
  }

  initForm(email: string): void {
    this.service.getPerson(email).subscribe(result => {
      console.log("Loading details of person: ", result)
      this.fillForm(result.payload);
    }, error => {
      console.log("Error while getting the person ");
    });
  }

  private fillForm(person: Person): void {
    this.personForm = this.fb.group({
      name: [person.name, [Validators.required]],
      surname: [person.surname, [Validators.required]],
      password: [person.password, [Validators.required]],
      email: [person.email, [Validators.required]],
      birthday: [person.birthday, [Validators.required]]
    });
  }

  isValidInput(fieldName: string | number): boolean {
    return this.personForm.controls[fieldName].invalid &&
      (this.personForm.controls[fieldName].dirty || this.personForm.controls[fieldName].touched);
  }

  ngAfterViewInit(): void { }

  save(): void {

    if (this.personForm.dirty) {
      let aux = new Date(this.personForm.value.birthday)
      aux.setHours(2) // To follow ISO 8601 (ESP +2) 
      this.fromPerson = {
        name: this.personForm.value.name,
        surname: this.personForm.value.surname,
        email: this.personForm.value.email,
        password: this.personForm.value.password,
        birthday: aux
      };
    }

    console.log("person: ", this.fromPerson);
    if (this.isAddMode) {

      this.service.createPerson(this.fromPerson).subscribe(result => {
        console.log("Create OK: ", result)
        this.openSnackBar("Person Created Successfully", "Status: " + result.code)
      }, error => {
        console.log("Create WARN: ", error)
        this.openSnackBar(error.error.payload, "Status: " + error.error.code)
      });

    } else {
      this.service.updatePerson(this.selEmail, this.fromPerson).subscribe(result => {
        console.log("Update OK: ", result)
        this.openSnackBar("Person Updated Successfully", "Status: " + result.code)
      }, error => {
        console.log("Update WARN: ", error)
        this.openSnackBar(error.error.payload, "Status: " + error.error.code)
      });
    }
  }

  resetForm(): void {
    if (this.isAddMode) {
      this.personForm.reset();

    } else {
      this.personForm.reset({ email: this.selEmail });
    }
  }

  public backToList(): void {
    this.router.navigate(['/person/']);
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000, // Optional: specify how long the snackbar should be visible
    });
  }
}
