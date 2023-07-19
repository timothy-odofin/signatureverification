import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NavigationComponent } from './navigation/navigation.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SettingsComponent } from './settings/settings.component';
import { UserManagementComponent } from './user-management/user-management.component';
import { ReportsComponent } from './reports/reports.component';
import { SignatureVerificationComponent } from './signature-verification/signature-verification.component';
import { SharedModule } from '../shared/shared.module';
import { ViewSignaturesComponent } from './signature-verification/view-signatures/view-signatures.component';
import { OpenSignaturesComponent } from './signature-verification/open-signatures/open-signatures.component';
import { VerifySignatureComponent } from './signature-verification/open-signatures/verify-signature/verify-signature.component';

const route:Routes = [
  {
    path: '',
    component: NavigationComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent
      },
      {
        path: 'reports',
        component: ReportsComponent
      },
      {
        path: 'settings',
        component: SettingsComponent
      },
      {
        path: 'user-management',
        component: UserManagementComponent
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
    NavigationComponent,
    DashboardComponent,
    SettingsComponent,
    UserManagementComponent,
    SignatureVerificationComponent,
    ReportsComponent,
    ViewSignaturesComponent,
    OpenSignaturesComponent,
    VerifySignatureComponent

  ],
  imports: [
    CommonModule,
    RouterModule.forChild(route),
    SharedModule

  ]
})

export class ApplicationModule { }
