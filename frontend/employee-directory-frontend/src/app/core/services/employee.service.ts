import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) { }

  getEmployees(page: number, size: number) {
    return this.http.get(`${environment.apiUrl}/employees/page?page=${page}&size=${size}`);
  }
  createEmployee(data: any) {
    return this.http.post(`${environment.apiUrl}/employees/new`, data);
  }

  updateEmployee(id: string, data: any) {
    return this.http.put(`${environment.apiUrl}/employees/edit/${id}`, data);
  }

  getEmployeeById(id: string) {
    return this.http.get(`${environment.apiUrl}/employees/getById/${id}`);
  }
  deleteEmployee(id: string) {

    return this.http.delete(
      `${environment.apiUrl}/employees/delete/${id}`,
      { responseType: 'text' }
    );

  }
}
