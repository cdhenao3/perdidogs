import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { RouletteDirection } from './roulette-direction';

@NgModule({
  declarations: [
    RouletteDirection,
  ],
  imports: [
    IonicPageModule.forChild(RouletteDirection),
  ],
})
export class RouletteDirectionModule {}
