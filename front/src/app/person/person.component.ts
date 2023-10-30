import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { HttpClientService, Person } from '../service/http-client.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})


export class PersonComponent implements OnInit {
  public dataSource = new MatTableDataSource<Person>();
  public displayedColumns = ['name', 'surname', 'email', 'delete'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private httpClientService: HttpClientService
  ) {
  }

  ngOnInit(): void {
    this.loadPersons()
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  loadPersons() {
    this.httpClientService.getAll().subscribe(
      response => {
        console.log("Resposne:", response);
        this.dataSource.data = response.payload as Person[];
      },
    );
  }

  public doFilter = (event: Event) => {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLocaleLowerCase();
  }

  public deleteRecord(person: Person) {
    if (confirm("Are you sure, that you want to delete, the suer with email: " + person.email)) {
      this.httpClientService.deletePerson(person.email).subscribe(result => {
        if (result.code == 200) {
          console.log("Delete OK: ", result)
        } else {
          console.log("Delete WARN: ", result.payload)
        }

        this.loadPersons();
      }, error => {
        console.log("Delete ERR", error);
      });
    }
  }

}

