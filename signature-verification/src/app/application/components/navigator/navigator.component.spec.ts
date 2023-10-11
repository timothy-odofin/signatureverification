import { TestBed, ComponentFixture, tick, fakeAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NavigatorComponent } from './navigator.component';
import { UtilService } from '../../../services/utilities';
import { Constants } from '../../../helpers/messages';
import { Router } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

describe('NavigatorComponent', () => {
  let component: NavigatorComponent;
  let fixture: ComponentFixture<NavigatorComponent>;
  let utilService: UtilService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavigatorComponent],
      imports: [RouterTestingModule,HttpClientTestingModule,MatIconModule,MatFormFieldModule,MatSelectModule],
      providers: [UtilService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigatorComponent);
    component = fixture.componentInstance;
    utilService = TestBed.inject(UtilService);
    router = TestBed.inject(Router);
    utilService.saveToStore(Constants.OPENDATA, [{ pid: '1' }, { pid: '2' }, { pid: '3' }])
    component.redirectUrl="navigateByUrl"
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should set currentIndex and call validateButtons when item is found', () => {
      const mockItems = utilService.getFromStore(Constants.OPENDATA)
      component.pId='1'
      const utilServiceSpy = spyOn(utilService, 'getFromStore').and.returnValue(mockItems);
      const validateButtonsSpy = spyOn(component, 'validateButtons');
      component.ngOnInit();
      expect(utilServiceSpy).toHaveBeenCalledWith(Constants.OPENDATA);
      expect(component.currentIndex).toBe(0);
      expect(validateButtonsSpy).toHaveBeenCalled();
    });

    it('should log an error if item is not found', () => {
      spyOn(console, 'error'); // Spy on console.error
      const utilServiceSpy = spyOn(utilService, 'getFromStore').and.returnValue([]);
      component.ngOnInit();
      expect(utilServiceSpy).toHaveBeenCalledWith(Constants.OPENDATA);
      expect(console.error).toHaveBeenCalledWith(`Item with pid ${component.pId} not found in items array.`);
    });
  });

  describe('handleRemoveItem', () => {
    it('should navigate when there is zero item', () => {
      const utilServiceSpy = spyOn(utilService, 'getFromStore').and.returnValue([{ pid: '1' }]);
      const navigateByUrlSpy = spyOn(router, 'navigateByUrl');
      component.handleRemoveItem();
      expect(utilServiceSpy).toHaveBeenCalledWith(Constants.OPENDATA);
      expect(navigateByUrlSpy).toHaveBeenCalledWith(component.redirectUrl);
    });

    it('should remove item, update items and navigate', fakeAsync(() => {
      component.items=utilService.getFromStore(Constants.OPENDATA);
      component.currentIndex = 0;
      const utilServiceGetFromStoreSpy = spyOn(utilService, 'getFromStore').and.returnValue(component.items);
      const utilServiceSaveToStoreSpy = spyOn(utilService, 'saveToStore');
      component.handleRemoveItem();
      tick();

      expect(utilServiceGetFromStoreSpy).toHaveBeenCalledWith(Constants.OPENDATA);
      expect(utilServiceSaveToStoreSpy).toHaveBeenCalledWith(Constants.OPENDATA, component.items);
      expect(component.currentIndex).toBe(0);
    }));
  });
  describe('validateButtons', () => {
    it('should set showPrevious and showNext correctly when items are defined and non-empty', () => {
      component.items = utilService.getFromStore(Constants.OPENDATA);
      component.currentIndex = 0;
      component.validateButtons();
      expect(component.showPrevious).toBeFalsy();
      expect(component.showNext).toBeTruthy();
    });

    it('should not set showPrevious and showNext when items are empty', () => {
      component.items = [];
      component.currentIndex = 0;
      component.validateButtons();
      expect(component.showPrevious).toBeFalsy();
      expect(component.showNext).toBeFalsy();
    });

    it('should not set showPrevious and showNext when items are undefined', () => {
      component.items = undefined;
      component.currentIndex = 0;
      component.validateButtons();
      expect(component.showPrevious).toBeFalsy();
      expect(component.showNext).toBeFalsy();
    });
  });


  describe('updateDisplay', () => {
    it('should update display correctly', () => {
      component.currentIndex = 0;
      component.items = utilService.getFromStore(Constants.OPENDATA);

      component.updateDisplay();

      expect(component.display).toBe('1 of 3');
    });
  });

  describe('nextItem', () => {
    it('should increment currentIndex and emit nextItemEvent', () => {
      const spyEmit = spyOn(component.nextItemEvent, 'emit');
      component.currentIndex = 0;
      component.items = utilService.getFromStore(Constants.OPENDATA);

      component.nextItem();

      expect(component.currentIndex).toBe(1);
      expect(spyEmit).toHaveBeenCalledWith('2');
    });
  });

  describe('previousItem', () => {
    it('should decrement currentIndex and emit previousItemEvent', () => {
      const spyEmit = spyOn(component.previousItemEvent, 'emit');
      component.currentIndex = 1;
      component.items = utilService.getFromStore(Constants.OPENDATA);

      component.previousItem();

      expect(component.currentIndex).toBe(0);
      expect(spyEmit).toHaveBeenCalledWith('1');
    });
  });

});
