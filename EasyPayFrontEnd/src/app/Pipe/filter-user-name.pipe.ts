import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'search'
})
export class FilterUserNamePipe implements PipeTransform {

  /**
   * transform method use to filter the list on the basis of given text.
   * @param usersList  first parameter of transform method and of array of string type.
   * @param searchText1 second parameter of transform method of string type which filter FirstName.
   * @param searchText2 third parameter of transform method of string type which filter mobile_no.
   * @param searchText3 fourth parameter of transform method of string type which filter user_name.
   * transform method return filtered list.
   */
  transform(usersList: any, searchText1: any, searchText2: any, searchText3: any): any {
    // let newList: any;
    if (searchText1) {
      usersList = usersList.filter(user => user.firstName.toLowerCase().startsWith(searchText1.toLowerCase()));
    }
    else {
      usersList = usersList;
    }
    if (searchText2) {
      usersList = usersList.filter(user => user.mobile_no.startsWith(searchText2));
    }
    else {
      usersList = usersList;
    }
    if (searchText3) {
      usersList = usersList.filter(user => user.user_name.toLowerCase().startsWith(searchText3.toLowerCase()));
    }
    else {
      usersList = usersList;
    }
    return usersList;
  }

}
