import { TestBed } from '@angular/core/testing';

import { UserCartService } from './user-cart.service';

describe('UserCartService', () => {
  let service: UserCartService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserCartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
