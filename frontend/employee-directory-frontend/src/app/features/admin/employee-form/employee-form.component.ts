import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../../../core/services/employee.service';

@Component({
  selector: 'app-employee-form',
  templateUrl: './employee-form.component.html'
})
export class EmployeeFormComponent implements OnInit {

  employeeForm!: FormGroup;
  employeeId: string | null = null;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {

    this.employeeForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      department: ['', Validators.required],
      designation: ['', Validators.required],
      status: ['ACTIVE', Validators.required],
      mobileNo: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      joinedOn: ['', Validators.required],
      address: ['', Validators.required],

    });

    this.employeeId = this.route.snapshot.paramMap.get('id');

    if (this.employeeId) {
      this.isEditMode = true;
      this.loadEmployee(this.employeeId);
    }
  }

  loadEmployee(id: string) {
    this.employeeService.getEmployeeById(id).subscribe((data: any) => {
      if (!this.employeeForm) return;

      this.employeeForm.patchValue({
        firstName: data.firstName,
        lastName: data.lastName,
        email: data.email,
        department: data.department,
        designation: data.designation,
        status: data.status,
        mobileNo: data.mobileNo,
        address: data.address,
        joinedOn: data.joinedOn ? data.joinedOn.split('T')[0] : null
      });
    });
  }

  onSubmit() {
    if (this.employeeForm.invalid) return;

    if (this.isEditMode) {
      this.employeeService.updateEmployee(this.employeeId!, this.employeeForm.value)
        .subscribe(() => {
          this.router.navigate(['/employees']);
        });
    } else {
      this.employeeService.createEmployee(this.employeeForm.value)
        .subscribe(() => {
          this.router.navigate(['/employees']);
        });
    }
  }
}