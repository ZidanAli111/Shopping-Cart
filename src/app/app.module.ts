import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './screens/login/login.component';
import { ItemComponent } from './screens/item/item.component';
import { FormsModule } from '@angular/forms';
import { UserCartComponent } from './screens/user-cart/user-cart.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ItemComponent,
    UserCartComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule ,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
