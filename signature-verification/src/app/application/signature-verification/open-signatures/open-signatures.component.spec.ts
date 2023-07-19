import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenSignaturesComponent } from './open-signatures.component';

describe('OpenSignaturesComponent', () => {
  let component: OpenSignaturesComponent;
  let fixture: ComponentFixture<OpenSignaturesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OpenSignaturesComponent]
    });
    fixture = TestBed.createComponent(OpenSignaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
