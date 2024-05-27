import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'shopping-cart';

  constructor(
    private router: Router,
    private cdref: ChangeDetectorRef) { };

  ngOnInit(): void {
    this.cdref.detectChanges()
    this.router.navigate(['usercart'])
  }

}

