import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { User } from 'src/app/model/user/user';
import { UserLoginService } from './userlogin.service';

describe('UserLoginService', () => {
  let service: UserLoginService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserLoginService]
    });
    service = TestBed.inject(UserLoginService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in a user', () => {
    const user: User = { userId: 1, username: 'zidan@gmail.com', password: '' };
    const loggedInUser: User = { userId: 1, username: 'zidan@gmail.com', password: '' };
  
    service.login(user).subscribe((res: User) => {
      expect(res).toEqual(loggedInUser);
      expect(service.currentUser).toEqual(loggedInUser);
    });
  
    const req = httpMock.expectOne(service.loginUrl); // Issue might be here
    expect(req.request.method).toBe('POST');
    req.flush(loggedInUser);
  });
  

  it('should set and get the current user', () => {
    const user: User = { userId: 2, username: 'zidan@gmail.com', password: 'Zid@1234' };

    service.setCurrentUser(user);
    const currentUser = service.getCurrentUser();
    expect(currentUser).toEqual(user);

    const userId = service.getUserId();
    expect(userId).toBe(2);

    const username = service.getUsername();
    expect(username).toBe('zidan@gmail.com');
  });
});
