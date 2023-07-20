export interface EventModel{
  businessKey: string,
  application: string,
  transactionCurrency: string,
  transactionAmount: number,
  amountInMur: number,
  debitAccountNumber: string,
  accountShortName: string,
  debitAccountCcy: string,
  paymentDetails1: string,
  paymentDetails2: string,
  paymentDetails3: string,
  paymentDetails4: string,
  verified: string,
  discrepancyReason: string,
  createdBy: string,
  updatedBy: string,
  priority: string,
  sourceBU: string,
  documentCaptureReference: string,
  status:string,
  createdOn: string,
  updatedOn: string,
  pid: string,
  signBase64Pdf?:string
}


export interface signaturePayload{
  eventAccountNumber: string,
  eventSourcePid: string
}

export interface submitSignaturePayload{
  actionStatus: string,
  amountInMur: number,
  comments: string,
  debitAccountCcy: string,
  debitAccountNumber: string,
  discrepancyReason: string,
  paymentDetails1: string,
  paymentDetails2: string,
  paymentDetails3: string,
  paymentDetails4?: string,
  transactionAmount: number,
  transactionCurrency: string,
  verified: string
}

