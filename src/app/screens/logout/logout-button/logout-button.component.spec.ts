import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { LogoutButtonComponent } from './logout-button.component';

describe('LogoutButtonComponent', () => {
  let component: LogoutButtonComponent;
  let fixture: ComponentFixture<LogoutButtonComponent>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [LogoutButtonComponent],
      providers: [{ provide: Router, useValue: routerSpy }]
    }).compileComponents();

    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogoutButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should logout and navigate to login page', () => {
    component.logout();
    expect(localStorage.getItem('currentUser')).toBeNull();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
});
