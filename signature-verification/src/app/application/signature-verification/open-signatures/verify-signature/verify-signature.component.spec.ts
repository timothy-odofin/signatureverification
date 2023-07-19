import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifySignatureComponent } from './verify-signature.component';

describe('VerifySignatureComponent', () => {
  let component: VerifySignatureComponent;
  let fixture: ComponentFixture<VerifySignatureComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifySignatureComponent]
    });
    fixture = TestBed.createComponent(VerifySignatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
