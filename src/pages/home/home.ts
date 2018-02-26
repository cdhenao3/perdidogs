import { Component } from '@angular/core';
import {NavController, NavParams} from 'ionic-angular';
import {RouletteNumbers} from '../roulette-numbers/roulette-numbers';


/**
 * Generated class for the Home page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class Home {

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  rouletteNumbers(index:string){
    this.navCtrl.push(RouletteNumbers,{buttons:index})
  }

}
