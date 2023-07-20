import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { SignatureService } from '../../services/signature.service';
import { signaturePayload, submitSignaturePayload } from 'src/app/models/event.module';
import { MessageUtil } from 'src/app/helpers/messages';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-verify-signature',
  templateUrl: './verify-signature.component.html',
  styleUrls: ['./verify-signature.component.scss']
})
export class VerifySignatureComponent implements OnInit{
  image: any;
  loading!: boolean;
  payload!:signaturePayload
  submitPayload!:submitSignaturePayload
  recordData: any;
  errormsg: string = '';
  signatureForm!:FormGroup
  actionType!: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) private dialogData:any,
    private signatureService:SignatureService,
    private fb:FormBuilder,
    private sanitize: DomSanitizer,
    public dialogref: MatDialogRef<VerifySignatureComponent>
  ){

  }




  ngOnInit(){
    this.validateSignatureRecord()
    this.signatureForm = this.fb.group({
      'signature': ['', Validators.required],
      'discrepancy': [''],
    })
  }

  close(){
    this.dialogref.close()
  }

  verify(type:string){
    this.actionType = type
    this.submitSignatureRecord()
  }

  validateSignatureRecord() {
    this.payload = {
      "eventAccountNumber": this.dialogData?.record?.debitAccountNumber,
      "eventSourcePid": this.dialogData?.record?.pid
    }
    this.loading = true;
    this.signatureService.validateSignature(this.payload).subscribe({
      next: (res:any) => {
        this.loading = false;
        if (res['message'] == MessageUtil.RESPONSE_SUCCESS) {
          this.recordData = res['data'];
          this.image = this.sanitize.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${this.recordData?.signImage}`)
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

  submitSignatureRecord() {
    this.errormsg = ''
    this.submitPayload = {
      actionStatus: this.actionType,
      amountInMur: this.dialogData?.form?.equivAmount,
      comments: this.dialogData?.form?.comments,
      debitAccountCcy: this.dialogData?.form?.accountCcy,
      debitAccountNumber: this.dialogData?.form?.accountNumber,
      discrepancyReason: this.signatureForm.get('discrepancy')?.value ? this.signatureForm.get('discrepancy')?.value : "N/A",
      paymentDetails1: this.dialogData?.form?.payment1,
      paymentDetails2: this.dialogData?.form?.payment2,
      paymentDetails3: this.dialogData?.form?.payment3,
      transactionAmount: this.dialogData?.form?.amount,
      transactionCurrency: this.dialogData?.form?.currency,
      verified: this.signatureForm.get('signature')?.value
    }

    this.loading = true;
    this.signatureService.submitSignature(this.dialogData?.record?.pid, this.submitPayload).subscribe({
      next: (res:any) => {
        this.loading = false;
        if (res['message'] == MessageUtil.RESPONSE_SUCCESS) {
          this.dialogref.close('Success')
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


}
