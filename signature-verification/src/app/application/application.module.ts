import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NavigationComponent } from './navigation/navigation.component';
import { ReportsComponent } from './reports/reports.component';
import { SignatureVerificationComponent } from './signature-verification/signature-verification.component';
import { SharedModule } from '../shared/shared.module';
import { ViewSignaturesComponent } from './signature-verification/view-signatures/view-signatures.component';
import { OpenSignaturesComponent } from './signature-verification/open-signatures/open-signatures.component';
import { VerifySignatureComponent } from './signature-verification/open-signatures/verify-signature/verify-signature.component';
import {AppModule} from "../app.module";
import {NavigatorComponent} from "./components/navigator/navigator.component";

const route:Routes = [
  {
    path: '',
    component: NavigationComponent,
    children: [
      {
        path: 'reports',
        component: ReportsComponent
      },
      {
        path: 'history',
        component: SignatureVerificationComponent
      },
      {
        path: 'history/open',
        component: OpenSignaturesComponent
      },
      {
        path: 'history/view',
        component: ViewSignaturesComponent
      }
    ]
  }
]

@NgModule({
  declarations: [
    NavigatorComponent,
    NavigationComponent,
    SignatureVerificationComponent,
    ReportsComponent,
    ViewSignaturesComponent,
    OpenSignaturesComponent,
    VerifySignatureComponent,

  ],
    imports: [
        CommonModule,
        RouterModule.forChild(route),
        SharedModule

    ]
})

export class ApplicationModule { }
