import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AppService } from '../app.service';
import { UtilService } from '../services/utilities';
import { Constants } from '../helpers/messages';

export const authGuard: CanActivateFn = (route, state) => {


  const router =inject(Router)
  const utilService = inject(UtilService)



    if(utilService.getFromStore(Constants.LOGIN_USER)){
      return true
    }
    else{
      router.navigateByUrl('/');
      return false
    }
}
