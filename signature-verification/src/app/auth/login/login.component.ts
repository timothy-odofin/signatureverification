import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';
import { UtilService } from 'src/app/services/utilities';
import { Constants, MessageUtil } from 'src/app/helpers/messages';
import { AppService } from 'src/app/app.service';
import { Login } from 'src/app/models/auth.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  form: FormGroup;
  errormsg: string = '';
  loading!: boolean;
  payload!:Login

  constructor(
      private fb:FormBuilder,
      private auth: AuthService,
      private router:Router,
      private util:UtilService,
      private appService:AppService
      ) {

      this.form = fb.group({
          username: ['', [Validators.required]],
          password: ['', [Validators.required]]
      });

  }

  login() {
    const val = this.form.value
    this.loading = true

    this.auth.login(val)
    .subscribe({
      next: (res:any) => {
        if(res['message'] != 'Fail'){
          this.util.saveToStore(Constants.LOGIN_USER, res['data'])
          this.appService.setLoginStatus(true)
          this.router.navigateByUrl('/app/history')
          this.loading = false
        }
        else{
          this.loading = false
          this.errormsg = res['data']
        }
      },
      error : () => {
        this.loading = false
        this.errormsg = MessageUtil.SERVER_ERROR
      }
    })
  }
}
