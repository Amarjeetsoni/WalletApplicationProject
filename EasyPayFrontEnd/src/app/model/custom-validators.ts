import { PatternValidator, ValidationErrors, ValidatorFn, AbstractControl } from '@angular/forms';


export class CustomValidators {
  /**
   * patternValidator is a custome validator which helps to validate input data on the basis of developer choise.
   * @param regex first parameter which is of RegExp type takes the regular expression on basis of that it validate.
   * @param error error message that it will returns.
   */
  static patternValidator(regex: RegExp, error: ValidationErrors): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      if (!control.value) {
        // if control is empty return no error
        return null;
      }

      // test the value of the control against the regexp supplied
      const valid = regex.test(control.value);

      // if true, return no error (no error), else return error passed in the second parameter
      return valid ? null : error;
    };
  }
  static patternValidatorForNumber(regex: RegExp, error: ValidationErrors): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      if (!control.value) {
        // if control is empty return no error
        return null;
      }

      // test the value of the control against the regexp supplied
      const valid = regex.test(control.value);

      // if true, return error, else return no error passed in the second parameter
      return valid ? error : null;
    };
  }
}
