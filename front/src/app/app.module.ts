import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PersonComponent } from './person/person.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PersonDetailComponent } from './person-detail/person-detail.component';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@NgModule({
  declarations: [
    AppComponent,
    PersonComponent,
    PersonDetailComponent
  ],
  imports: [
    HttpClientModule,
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    MatTableModule,
    MatFormFieldModule,
    MatIconModule,
    MatPaginatorModule,
    BrowserAnimationsModule,
    MatCardModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTooltipModule,
    MatInputModule,
    MatSnackBarModule
  ],
  providers: [
    MatDatepickerModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
