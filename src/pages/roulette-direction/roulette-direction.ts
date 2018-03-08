import { Component, ViewChild } from '@angular/core';
import {NavController,NavParams,ModalController } from 'ionic-angular';
import { Home } from '../home/home';
/**
 * Generated class for the RouletteDirection page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
@Component({
  selector: 'page-roulette-direction',
  templateUrl: 'roulette-direction.html',
})
export class RouletteDirection {

  @ViewChild('canvas2') canvas2 : any;
  options = ["1", "2", "3", "4"];
  
  startAngle = 0;
  arc = Math.PI / (this.options.length / 2);
  spinTimeout = null;
  
  spinArcStart = 10;
  spinTime = 0;
  spinTimeTotal = 0;
  spinAngleStart = 0;

  number:string = ''
  direction:any
  buttons:any = []
  showIndicator:boolean = false;
  
  ctx:CanvasRenderingContext2D;

  constructor(public navCtrl: NavController,
              public navParams: NavParams, 
              public modalCtrl : ModalController) {
    this.number = this.navParams.get('number');
    this.buttons = this.navParams.get('buttons');
  }

  ionViewWillEnter(){
    this.drawRouletteWheel()
  }

  nextPlayer(){
    for(let btn = 1; btn < this.buttons.length; btn++){
      if(this.buttons[btn-1].show){
        this.buttons[btn-1].show = false
        this.buttons[btn].show = true
        break;
      }else if(this.buttons[this.buttons.length-1].show){
        this.buttons[this.buttons.length-1].show = false;
        this.buttons[0].show = true;
        break;
      }
    }
    this.navCtrl.pop();
  }

  fin(){
    this.navCtrl.popToRoot();
  }

  resultView(number:string, direction:any){
    this.showIndicator = true;
  }

  byte2Hex(n) {
    let nybHexString = "0123456789ABCDEF";
    return String(nybHexString.substr((n >> 4) & 0x0F,1)) + nybHexString.substr(n & 0x0F,1);
  }
  
  RGB2Color(r,g,b) {
    return '#' + this.byte2Hex(r) + this.byte2Hex(g) + this.byte2Hex(b);
  }
  
  getColor(item, maxitem) {
    let phase = 0;
    let center = 128;
    let width = 127;
    let frequency = Math.PI*2/maxitem;
    
    let red   = Math.sin(frequency*item+2+phase) * width + center;
    let green = Math.sin(frequency*item+0+phase) * width + center;
    let blue  = Math.sin(frequency*item+4+phase) * width + center;
    
    return this.RGB2Color(red,green,blue);
  }
  
  drawRouletteWheel() {
    this.canvas2 = <HTMLCanvasElement>document.getElementById('canvas2');
    this.canvas2.width = (window.screen.width - 32);
    if (this.canvas2.getContext) {
      let outsideRadius = (window.screen.width/2.2);
      let textRadius = (window.screen.width/3.5);
      let insideRadius = 30;
  
      this.ctx = this.canvas2.getContext("2d");
      this.ctx.clearRect(0,0,500,500);
  
      //this.ctx.strokeStyle = "#828282";
      //this.ctx.lineWidth = 7;
  
      this.ctx.font = 'bold 30px sans-serif, Arial';
  
      for(let i = 0; i < this.options.length; i++) {
        let img:any;
        img = document.getElementById("img"+[i]);
        let angle = this.startAngle + i * this.arc;
        //this.ctx.fillStyle = colors[i];
        this.ctx.fillStyle = this.getColor(i, this.options.length);
        
        this.ctx.beginPath();
        this.ctx.arc(((window.screen.width/2)-16), 200, outsideRadius, angle, angle + this.arc, false);
        this.ctx.arc(((window.screen.width/2)-16), 200, insideRadius, angle + this.arc, angle, true);
        //this.ctx.stroke();
        this.ctx.fill();
        this.ctx.save();
        this.ctx.shadowOffsetX = -1;
        this.ctx.shadowOffsetY = -1;
        this.ctx.shadowBlur    = 0;
        //this.ctx.shadowColor   = "rgb(220,220,220)";
        this.ctx.fillStyle = "#474747";
        this.ctx.translate((window.screen.width/2.4) + Math.cos(angle + this.arc / 2) * textRadius, 
                      200 + Math.sin(angle + this.arc / 2) * textRadius);
        //this.ctx.rotate(angle + this.arc / 2 + Math.PI / 2);
        let text = this.options[i];
        // let text:any = this.ctx.drawImage(img, 10, 10);
        // this.ctx.fillText(text, -this.ctx.measureText(text).width / 2, 0);
        this.ctx.drawImage(img, -25,-30)
        this.ctx.restore();
      } 
  
      //Arrow
      this.ctx.fillStyle = "black";
      this.ctx.beginPath();
      this.ctx.moveTo(((window.screen.width / 2) - 11-16), 200 - (outsideRadius + 12));
      this.ctx.lineTo(((window.screen.width / 2) + 11-16), 200 - (outsideRadius + 12));
      this.ctx.lineTo(((window.screen.width / 2) + 0-16), 200 - (outsideRadius - 15));
      this.ctx.lineTo(((window.screen.width / 2) - 11-16), 200 - (outsideRadius + 12));
      this.ctx.fill();
    }
  }
  
  spin() {
    this.spinAngleStart = Math.random() * 10 + 10;
    this.spinTime = 0;
    this.spinTimeTotal = Math.random() * 3 + 4 * 1000;
    this.rotateWheel();
  }
  
  rotateWheel() {
    this.spinTime += 30;
    if(this.spinTime >= this.spinTimeTotal) {
      this.stopRotateWheel();
      return;
    }
    let spinAngle = this.spinAngleStart - this.easeOut(this.spinTime, 0, this.spinAngleStart, this.spinTimeTotal);
    this.startAngle += (spinAngle * Math.PI / 180);
    this.drawRouletteWheel();
    this.spinTimeout = setTimeout(()=>
    this.rotateWheel(), 30);
  }
  
  stopRotateWheel() {
    clearTimeout(this.spinTimeout);
    let degrees = this.startAngle * 180 / Math.PI + 90;
    let arcd = this.arc * 180 / Math.PI;
    let index = Math.floor((360 - degrees % 360) / arcd);
    this.ctx.save();
    this.ctx.font = 'bold 50px Helvetica, Arial';
    this.direction = this.options[index]
    // this.ctx.fillText(text, 190 - this.ctx.measureText(text).width / 2, 250 + 10);
    this.ctx.restore();
    setTimeout(()=>{
      this.resultView(this.number, this.direction);
    },100)
  }
  
  easeOut(t, b, c, d) {
    let ts = (t/=d)*t;
    let tc = ts*t;
    return b+c*(tc + -3*ts + 3*t);
  }

}
