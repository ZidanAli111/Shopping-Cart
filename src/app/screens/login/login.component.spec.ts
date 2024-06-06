import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { of, throwError } from 'rxjs';
import { User } from 'src/app/model/user/user';
import { UserLoginService } from 'src/app/service/userLogin/userlogin.service';
import { LoginComponent } from './login.component';
 
class MockUserLoginService {
  currentUser: User = { userId: 1, username: 'testuser', password: 'testpass' };
 
  login(user: User) {
    return of(this.currentUser);
  }
 
  setCurrentUser(user: User): void {
    this.currentUser = user;
  }
 
  getCurrentUser(): User | null {
    return this.currentUser;
  }
 
  getUserId(): number | null {
    return this.currentUser?.userId ?? null;
  }
 
  getUsername(): string | null {
    return this.currentUser?.username ?? null;
  }
}
 
describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let mockUserLoginService: MockUserLoginService;
  let mockRouter: jasmine.SpyObj<Router>;
 
  beforeEach(async () => {
    mockUserLoginService = new MockUserLoginService();
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
 
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [FormsModule], // Import FormsModule
      providers: [
        { provide: UserLoginService, useValue: mockUserLoginService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();
  });
 
  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
 
  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
 
  it('should login successfully', () => {
    spyOn(mockUserLoginService, 'login').and.callThrough();
    component.user.username = 'testuser';
    component.user.password = 'testpass';
    component.userLogin();
    expect(mockUserLoginService.login).toHaveBeenCalledWith(component.user);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/item']);
  });
 
  it('should handle login error', () => {
    spyOn(mockUserLoginService, 'login').and.returnValue(throwError({ status: 401 }));
    component.user.username = 'testuser';
    component.user.password = 'testpass';
    component.userLogin();
    expect(component.loading).toBeFalse();
    expect(component.errorMessage).toBe('Login error occurred!!!');
  });
 
  it('should show error if username or password is missing', () => {
    component.user.username = '';
    component.user.password = 'testpass';
    component.userLogin();
    expect(component.loading).toBeFalse();
    expect(component.errorMessage).toBe('Username and Password are required');
  });
 
  it('should toggle password visibility', () => {
    component.passwordVisible = false;
    component.togglePasswordVisibility();
    expect(component.passwordVisible).toBeTrue();
    component.togglePasswordVisibility();
    expect(component.passwordVisible).toBeFalse();
  });
});
