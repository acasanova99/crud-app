import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PersonComponent } from './person/person.component';
import { PersonDetailComponent } from './person-detail/person-detail.component';

const routes: Routes = [
  { path: "person", component: PersonComponent },
  { path: "person/detail/add", component: PersonDetailComponent },
  { path: "person/detail/edit/:email", component: PersonDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
