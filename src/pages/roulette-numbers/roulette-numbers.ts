import { Component, ViewChild } from '@angular/core';
import {NavController,NavParams} from 'ionic-angular';
import { RouletteDirection } from '../roulette-direction/roulette-direction';
/**
 * Generated class for the RouletteNumbers page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-roulette-numbers',
  templateUrl: 'roulette-numbers.html',
})
export class RouletteNumbers {
  @ViewChild('canvas') canvas : any;

  options = ["1", "2", "3", "4", "5"];
  
  startAngle = 0;
  arc = Math.PI / (this.options.length / 2);
  spinTimeout = null;
  
  spinArcStart = 10;
  spinTime = 0;
  spinTimeTotal = 0;
  spinAngleStart = 0;
  
   
  ctx:CanvasRenderingContext2D;

  buttons:any = [];
  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.buttonsCreation();

  }

  ionViewDidLoad(){
    if(this.navParams.get('buttons2'))
      this.buttons = this.navParams.get('buttons2');
    this.drawRouletteWheel()
  }

  buttonsCreation(){
    let index = parseInt(this.navParams.get('buttons'));
    if(index){
      for(let i = 0; i < index; i++){
        if(i == 0)
          this.buttons.push({name:'Jugador '+ (i+1), show: true});
        else
          this.buttons.push({name:'Jugador '+ (i+1), show: false}); 
      }
    }
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
    this.canvas = <HTMLCanvasElement>document.getElementById('canvas');
    this.canvas.width = (window.screen.width - 32);
    if (this.canvas.getContext) {
      let outsideRadius = (window.screen.width/2.2);
      let textRadius = (window.screen.width/3.5);
      let insideRadius = 30;
      
      this.ctx = this.canvas.getContext("2d");
      this.ctx.clearRect(0,0,500,500);
      
      //this.ctx.strokeStyle = "#828282";
      //this.ctx.lineWidth = 7;
  
      this.ctx.font = 'bold 30px sans-serif, Arial';
  
      for(var i = 0; i < this.options.length; i++) {
        var angle = this.startAngle + i * this.arc;
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
        this.ctx.translate((window.screen.width/2.2) + Math.cos(angle + this.arc / 2) * textRadius, 
                      200 + Math.sin(angle + this.arc / 2) * textRadius);
        //this.ctx.rotate(angle + this.arc / 2 + Math.PI / 2);
        var text = this.options[i];
        this.ctx.fillText(text, -this.ctx.measureText(text).width / 2, 0);
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
    let text = this.options[index]
    // this.ctx.fillText(text, 190 - this.ctx.measureText(text).width / 2, 250 + 10);
    this.ctx.restore();
    
    setTimeout(()=>{
      this.navCtrl.push(RouletteDirection, {number: text, buttons: this.buttons});
    }, 30)
  }
  
  easeOut(t, b, c, d) {
    let ts = (t/=d)*t;
    let tc = ts*t;
    return b+c*(tc + -3*ts + 3*t);
  }

}


