import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './screens/login/login.component';
import { ItemComponent } from './screens/item/item.component';
import { UserCartComponent } from './screens/user-cart/user-cart.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'item', component: ItemComponent },
  {path:'usercart',component:UserCartComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
