# AngularCliRest

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.6.4.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `-prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).

### SetupMyProject 

Please go to `app.component.ts` and change the _URL_ that is been using application context backend

If you are using JAX-RS option, you need to use the application context name that you choosed in your java web project for JAX-RS:

If you setted java project name with **explameApi**, change your _http.get_  like below: 
```javascript

this.http.get("http://localhost:8080/exampleApi/products")

``` 

If you are using SPRING BOOT option, you should remove context name in your _http.get_.


```javascript

this.http.get("http://localhost:8080/products")

``` 
