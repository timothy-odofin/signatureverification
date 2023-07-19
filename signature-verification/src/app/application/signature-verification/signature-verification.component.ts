import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { SignatureService } from './services/signature.service';
import { Constants, MessageUtil } from 'src/app/helpers/messages';
import { EventModel } from 'src/app/models/event.module';
import { Router } from '@angular/router';
import { UtilService } from 'src/app/services/utilities';

@Component({
  selector: 'app-signature-verification',
  templateUrl: './signature-verification.component.html',
  styleUrls: ['./signature-verification.component.scss']
})

export class SignatureVerificationComponent implements OnInit {
  errormsg: any;
  dataSource: any;
  pageValue!: MatPaginator;
  totalLength: any;

  constructor(
    private router: Router,
    private util: UtilService,
    private signatureService:SignatureService){
  }

  dataList: EventModel[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = ['select','date', 'priority', 'source','ref', 'dcpref','account', 'ccy', 'amount'];
  selection = new SelectionModel<any>(true, []);

  searchKey: string = '';
  loading!: boolean;

  ngOnInit(){
    this.getCardList()
    this.util.removeFromStore(Constants.OPENDATA)
  }

  getCardList() {
    this.loading = true;
    let page:number = this.pageValue ? this.pageValue.pageIndex : Constants.PAGE;
    let pageSize:number =  this.pageValue ? this.pageValue.pageSize : Constants.PAGE_SIZE;

    this.signatureService.getEventSource(page, pageSize).subscribe({
      next: (res:any) => {
        this.loading = false;
        if (res['message'] == MessageUtil.RESPONSE_SUCCESS) {
          this.dataList = res['data'];
          this.totalLength = res["meta"].totalElement;

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

    this.selection.selected.forEach( (record:any, index) => {
      record.index = index
    })
    // Save selected records to state management
    this.util.saveToStore(Constants.OPENDATA, this.selection.selected)


    let data = this.util.getFromStore(Constants.OPENDATA)

    this.router.navigateByUrl(`/app/history/open?pid=${data[0]['pid']}`)
    console.log(this.selection.selected, 'selections');
  }

  viewRecords(){

    this.selection.selected.forEach( (record:any, index) => {
      record.index = index
    })
    // Save selected records to state management
    this.util.saveToStore(Constants.OPENDATA, this.selection.selected)


    let data = this.util.getFromStore(Constants.OPENDATA)

    this.router.navigateByUrl(`/app/history/view?pid=${data[0]['pid']}`)
  }


  checkPage(event: MatPaginator) {
    this.pageValue = event;
    this.getCardList();
  }


  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource?.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource?.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;
  }

  applyFilter(){
    this.dataSource.filter = this.searchKey.trim().toLowerCase()
  }

}
