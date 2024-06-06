import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserLoginService } from './userlogin.service';
import { User } from 'src/app/model/user/user';
 
describe('UserLoginService', () => {
  let service: UserLoginService;
  let httpTestingController: HttpTestingController;
 
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserLoginService]
    });
    service = TestBed.inject(UserLoginService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });
 
  afterEach(() => {
    httpTestingController.verify();
  });
 
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
 
  it('should log in a user', () => {
    const mockUser: User = { userId: 1, username: 'testuser', password: 'password123' };
 
    service.login(mockUser).subscribe(user => {
      expect(user).toEqual(mockUser);
    });
 
const req = httpTestingController.expectOne('http://localhost:9081/api/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);
  });
});