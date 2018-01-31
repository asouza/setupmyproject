import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {  
  results;
  constructor(private http: HttpClient){}
  
  ngOnInit() {
    this.http.get("http://localhost:8080/REPLACE_WITH_CONTEXT_APPLICATION/products").subscribe(data =>{
      this.results = data
    });
  }
}
