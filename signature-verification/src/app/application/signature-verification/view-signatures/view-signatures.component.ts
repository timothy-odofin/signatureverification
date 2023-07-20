import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/login/services/auth.service';
import { Constants, MessageUtil } from 'src/app/helpers/messages';
import { UtilService } from 'src/app/services/utilities';
import { SignatureService } from '../services/signature.service';

@Component({
  selector: 'app-view-signatures',
  templateUrl: './view-signatures.component.html',
  styleUrls: ['./view-signatures.component.scss']
})
export class ViewSignaturesComponent {
  dataForm!: FormGroup;
  errormsg: string = '';
  loading!: boolean;
  payload!:any
  currencyList: any;
  pId!: string;
  recordData: any;
  image: any;
  showPrevious!: boolean;
  showNext!: boolean;
  selectedSignatures = this.util.getFromStore(Constants.OPENDATA)

  currentSignature: any;
  currentIndex: number = 0;

  constructor(
    private fb:FormBuilder,
    private auth: AuthService,
    private router:Router,
    private route:ActivatedRoute,
    private util:UtilService,
    private sanitize: DomSanitizer,
    private signatureService:SignatureService,
    private dialog: MatDialog
    ){

    this.route.queryParams.subscribe(params => {
      this.pId = params['pid']
    })
  }

  ngOnInit(): void {
    this.getSignatureRecord()
    this.initiateForm()
    this.currentSignature = this.selectedSignatures.find((val:any) => {
      return val.pid == this.pId
    })

    this.currentIndex = this.selectedSignatures.indexOf(this.currentSignature)
    this.validateButtons()

  }


  initiateForm(){
    this.dataForm = this.fb.group({
      'comments': [{value:'', disabled:true}],
      'dcpRef': [{value:'', disabled:true}],
      'accountNumber': [{value:'', disabled:true}],
      'accountName': [{value:'', disabled:true}],
      'accountCcy': [{value:'', disabled:true}],
      'currency': [{value:'', disabled:true}],
      'amount': [{value:'', disabled:true}],
      'equivAmount': [{value:'', disabled:true}],
      'payment1': [{value:'', disabled:true}],
      'payment2': [{value:'', disabled:true}],
      'payment3': [{value:'', disabled:true}],
    })
  }
  setFormValues(){
    let form = this.dataForm

    form.get('comments')?.setValue(this.recordData?.comments);
    form.get('dcpRef')?.setValue(this.recordData?.documentCaptureReference);
    form.get('accountNumber')?.setValue(this.recordData?.debitAccountNumber);
    form.get('accountName')?.setValue(this.recordData?.accountShortName);
    form.get('accountCcy')?.setValue(this.recordData?.debitAccountCcy);
    form.get('currency')?.setValue(this.recordData?.transactionCurrency);
    form.get('amount')?.setValue(this.recordData?.transactionAmount);
    form.get('equivAmount')?.setValue(this.recordData?.amountInMur);
    form.get('payment1')?.setValue(this.recordData?.paymentDetails1);
    form.get('payment2')?.setValue(this.recordData?.paymentDetails2);
    form.get('payment3')?.setValue(this.recordData?.paymentDetails3);
  }

  getSignatureRecord() {
    this.loading = true;
    this.signatureService.getEventSourceById(this.pId).subscribe({
      next: (res:any) => {
        this.loading = false;
        if (res['message'] == MessageUtil.RESPONSE_SUCCESS) {
          this.recordData = res['data'];
          this.setFormValues()
          this.image = this.sanitize.bypassSecurityTrustResourceUrl(`data:application/pdf;base64, ${this.recordData?.signBase64Pdf}`)
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


  validateButtons(){
    this.currentIndex == 0 ? this.showPrevious = false : this.showPrevious = true
    this.currentIndex == this.selectedSignatures.length - 1 ? this.showNext = false : this.showNext = true
  }

  nextSignature(){
    this.currentIndex = this.currentIndex + 1
    this.router.navigateByUrl(`/app/history/view?pid=${this.selectedSignatures[this.currentIndex]['pid']}`)
    this.pId = this.selectedSignatures[this.currentIndex]['pid']
    this.validateButtons()
    this.getSignatureRecord()
  }


  previousSignature(){
    this.currentIndex = this.currentIndex - 1

    this.router.navigateByUrl(`/app/history/view?pid=${this.selectedSignatures[this.currentIndex]['pid']}`)
    this.pId = this.selectedSignatures[this.currentIndex]['pid']
    this.validateButtons()
    this.getSignatureRecord()
  }
}
