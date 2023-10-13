import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {UtilService} from "../../../services/utilities";
import {Constants} from "../../../helpers/messages";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigator',
  templateUrl: './navigator.component.html',
  styleUrls: ['./navigator.component.scss']
})
export class NavigatorComponent implements OnInit, OnChanges {
  @Input() redirectUrl!: string;
  @Input() pId!: string;
  @Input() currentAction!: string
  @Input() displayTitle!: string
  @Input() mode!:string
  @Output() nextItemEvent = new EventEmitter<string>();
  @Output() previousItemEvent = new EventEmitter<string>();
  showPrevious = false;
  showNext = false;
  items:any;
  currentIndex = 0;
  display = "";
  constructor(private util: UtilService, private router: Router,) {
  }

  ngOnInit(): void {
    this.items=this.util.getFromStore(Constants.OPENDATA);
    const item = this.items.find((val: any) => val.pid === this.pId);
    if (item) {
      this.currentIndex = this.items.indexOf(item);
      this.validateButtons();
    } else {
      console.error(`Item with pid ${this.pId} not found in items array.`);
    }
  }
  onItemSelect(event:any){
  let value:number=Number(event["target"]["value"]);
  this.currentIndex=value
    this.validateButtons();
    this.nextItemEvent.emit(this.items[value]['pid']);
  }
  ngOnChanges(changes: SimpleChanges) {
    if(changes['currentAction'] !==undefined){
    const{previousValue, currentValue}= changes['currentAction'];
    if(currentValue !== undefined && previousValue !=currentValue){
      this.handleRemoveItem()
    }
    }
  }

  handleRemoveItem() {
    this.items =this.util.getFromStore(Constants.OPENDATA)
    if(this.items.length===1){
      this.router.navigateByUrl(this.redirectUrl)
    }else{
      this.items.splice(this.currentIndex, 1)
      this.util.saveToStore(Constants.OPENDATA, this.items)
      this.items =this.util.getFromStore(Constants.OPENDATA)
      this.currentIndex = -1
      this.nextItem()
    }
  }

  validateButtons() {
    if (this.items && this.items.length > 0) {
    this.showPrevious = this.currentIndex !== 0;
    this.showNext = this.currentIndex !== this.items.length - 1;
    this.updateDisplay()
    }
  }

  updateDisplay() {
    let displayIndex: number;
    if (this.currentIndex === 0) {
      displayIndex = 1;
    } else if (this.currentIndex >= this.items.length) {
      displayIndex = this.items.length - 1;
    } else {
      displayIndex = this.currentIndex + 1;
    }
    this.display = displayIndex + " of " + this.items.length
  }

  nextItem() {
    this.currentIndex = this.currentIndex + 1;
    this.validateButtons();
    this.nextItemEvent.emit(this.items[this.currentIndex]['pid']);
  }

  previousItem() {
    this.currentIndex = this.currentIndex - 1;
    this.validateButtons();
    this.previousItemEvent.emit(this.items[this.currentIndex]['pid']);
  }
}

