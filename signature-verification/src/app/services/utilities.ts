import { Injectable } from '@angular/core';
import { Constants} from '../helpers/messages';

@Injectable({
  providedIn: 'root'
})
export class UtilService {


  public getUserId() {
    let data = this.getFromStore(Constants.LOGIN_USER);
    if (data) {
      let userid = data.pid
      return userid
    }
  }

  public getUserFullname() {
    let data = this.getFromStore(Constants.LOGIN_USER);
    if(data){
      let username = data.firstName + '' + data.lastName
      return username
    }
    return
  }

  public getUserBusinessUnit() {
    let usern = this.getFromStore(Constants.LOGIN_USER);
    if(usern){
      let business = usern.businessUnit
      return business
    }
    return
  }

  public saveToStore(key: string, data: any) {
    localStorage.setItem(key, JSON.stringify(data));
  }

  public removeFromStore(key: string) {
    localStorage.removeItem(key)
  }

  public getFromStore(key: string) {
    let store = localStorage.getItem(key);

    if (store) {
      return JSON.parse(store)
    }
  }
}
