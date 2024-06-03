import { User } from './user';

describe('User', () => {

  it('should create an instance', () => {
    const user = new User(1, 'testUser', 'password123');
    expect(user).toBeTruthy();
  });

  it('should correctly assign properties', () => {
    const userId = 1;
    const username = 'testUser';
    const password = 'password123';
    
    const user = new User(userId, username, password);

    expect(user.userId).toBe(userId);
    expect(user.username).toBe(username);
    expect(user.password).toBe(password);
  });

});
