import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';
import { AdminGuard } from './core/guards/admin.guard';
import { EmployeeListComponent } from './features/employees/employee-list/employee-list.component';
import { EmployeeFormComponent } from './features/admin/employee-form/employee-form.component';
import { RegisterComponent } from './features/auth/register/register.component';


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: 'employees',
    component: EmployeeListComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'employees/new',
    component: EmployeeFormComponent,
    canActivate: [AuthGuard, AdminGuard]
  },
  {
    path: 'employees/edit/:id',
    component: EmployeeFormComponent,
    canActivate: [AuthGuard, AdminGuard]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
