import { FilterUserNamePipe } from './filter-user-name.pipe';

describe('FilterUserNamePipe', () => {
  it('create an instance', () => {
    const pipe = new FilterUserNamePipe();
    expect(pipe).toBeTruthy();
  });
});
