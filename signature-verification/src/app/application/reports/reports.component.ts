import { SelectionModel } from '@angular/cdk/collections';
import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { MessageUtil } from 'src/app/helpers/messages';
import { EventModel } from 'src/app/models/event.module';
import { SignatureService } from '../signature-verification/services/signature.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent {
  errormsg: any;

  constructor(
    private router: Router,
    private signatureService:SignatureService){
  }

  dataList: EventModel[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = ['label', 'frequency'];
  selection = new SelectionModel<any>(true, []);
  searchKey: string = '';
  dataSource: any;
  loading!: boolean;

  ngOnInit(){
    this.getReportData()
  }

  getReportData() {
    this.loading = true;
    this.signatureService.getReportData().subscribe({
      next: (res:any) => {
        this.loading = false;
        if (res['message'] == MessageUtil.RESPONSE_SUCCESS) {
          this.dataList = res['data'];
          this.dataSource = new MatTableDataSource(this.dataList);
          this.dataSource.sort = this.sort;
          // this.dataSource.paginator = this.paginator;
        }
        else {
          this.errormsg = res['data']
        }
      },
      error: (err) => {
        this.loading = false;
        this.errormsg = MessageUtil.SERVER_ERROR
      },
    });
  }


  goto(){
    this.router.navigateByUrl('/app/history/open?pid=64c6b9c3-6f37-418f-90aa-9610cc529ad6')

  }

  applyFilter(){
    this.dataSource.filter = this.searchKey.trim().toLowerCase()
  }

}
