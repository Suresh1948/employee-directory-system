import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { environment } from 'src/environments/environment';

declare const google: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements AfterViewInit {

  email: string = '';
  password: string = '';
  private isLoggingIn = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }


  login() {

    this.authService
      .login(this.email, this.password)
      .subscribe((res: any) => {

        localStorage.setItem(
          'token',
          res.token
        );

        localStorage.setItem(
          'role',
          res.role
        );

        this.router.navigate(['/employees']);
      });

  }
  handleGoogleLogin(response: any) {

    this.isLoggingIn = false;

    const idToken = response.credential;

    this.authService.googleLogin(idToken).subscribe((res: any) => {
      localStorage.setItem('token', res.token);
      localStorage.setItem('role', res.role);

      this.router.navigate(['/employees']);
    });
  }

  ngAfterViewInit(): void {

    google.accounts.id.initialize({
      client_id: environment.googleClientId,
      callback: (response: any) => this.handleGoogleLogin(response),
      use_fedcm_for_prompt: true
    });

    google.accounts.id.renderButton(
      document.getElementById("googleBtn"),
      {
        theme: "outline",
        size: "medium",
        type: "standard"
      }
    );
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }

}