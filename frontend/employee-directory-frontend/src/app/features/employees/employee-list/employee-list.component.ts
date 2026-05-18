import {
  Component,
  OnInit,
  ViewChild,
  AfterViewInit
} from '@angular/core';

import { Router } from '@angular/router';

import { EmployeeService } from 'src/app/core/services/employee.service';

import { MatPaginator } from '@angular/material/paginator';

import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent
  implements OnInit, AfterViewInit {

  employees: any[] = [];

  role: string = '';

  get displayedColumns(): string[] {

    if (this.isAdmin) {
      return [
        'sno',
        'firstName',
        'lastName',
        'email',
        'department',
        'joinedOn',
        'mobileNo',
        'status',
        'actions'
      ];
    }

    return [
      'sno',
      'firstName',
      'lastName',
      'email',
      'department',
      'joinedOn',
      'mobileNo',
      'status'
    ];
  }

  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.role = localStorage.getItem('role') || '';


  }

  ngAfterViewInit(): void {

  this.paginator.pageSize = 5;

  this.loadEmployees();

  this.paginator.page.subscribe(() => {

    this.loadEmployees();

  });

}

loadEmployees(): void {

  this.employeeService
    .getEmployees(
      this.paginator.pageIndex,
      this.paginator.pageSize
    )
    .subscribe({

      next: (data: any) => {

        this.dataSource.data = data.content;

        this.paginator.length = data.totalElements;

      },

      error: (err) => {

        console.error(err);

      }

    });

}  get isAdmin(): boolean {

    return localStorage.getItem('role') === 'ADMIN';

  }

  /* SEARCH */
  applyFilter(event: Event): void {

    const filterValue =
      (event.target as HTMLInputElement).value;

    this.dataSource.filter =
      filterValue.trim().toLowerCase();

  }

  createEmployee(): void {

    this.router.navigate(['/employees/new']);

  }

  editEmployee(emp: any): void {

    this.router.navigate(['/employees/edit', emp.id]);

  }

  deleteEmployee(id: string): void {

    if (confirm('Are you sure you want to delete this employee?')) {

      this.employeeService.deleteEmployee(id)
        .subscribe({

          next: () => {

            this.dataSource.data =
              this.dataSource.data.filter(
                emp => emp.id !== id
              );

            alert('Employee deleted successfully');

          },

          error: (err) => {

            console.error(err);

            alert('Delete failed');

          }

        });

    }

  }
  logout(): void {

    if (confirm('Are you sure you want to logout?')) {

      localStorage.clear();
      this.router.navigate(['/login']);
    }
  }

}