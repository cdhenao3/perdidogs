import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { Home } from '../pages/home/home';
import { RouletteNumbers } from '../pages/roulette-numbers/roulette-numbers';
import { RouletteDirection } from '../pages/roulette-direction/roulette-direction';

@NgModule({
  declarations: [
    MyApp,
    Home,
    RouletteNumbers,
    RouletteDirection  
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    Home,
    RouletteNumbers,
    RouletteDirection
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
