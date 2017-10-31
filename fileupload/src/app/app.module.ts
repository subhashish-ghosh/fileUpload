import {Component, NgModule} from '@angular/core'
import {BrowserModule} from '@angular/platform-browser'
import { HttpModule, Http, Response, Headers,RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
declare var myExtObject: any;
declare var webGlObject: any;

@Component({
  selector: 'my-app',
  template: `
    <div>
	  <input type="file" (change)="CallFileChange1($event)" placeholder="Upload file" accept=".txt">
    </div>
  `,
})
export class App {
  name:string;
  constructor(private http:Http) {
    this.name = 'Angular2'
    webGlObject.init();
  }
  
  CallFunction1() {
    myExtObject.func1();
  }
  
  CallFunction2() {
    myExtObject.func2();
  }
  CallFileChange1(event) {
	  //alert('heelo'+event.target.files);
	  let fileList: FileList = event.target.files;
	  //alert('fileList::'+fileList);
	  //alert('fileList.length:::'+fileList.length);
	  let file: File = fileList[0];
      let formData:FormData = new FormData();
	  formData.append('file', file, file.name);
	  formData.append('name','');
      let headers = new Headers();
	  //alert('headers:::'+headers);
	  /** No need to include Content-Type in Angular 4 */
        //headers.append('Content-Type', 'multipart/form-data');
        //headers.append('Accept', 'application/json');
        let options = new RequestOptions({ headers: headers });
        this.http.post(`http://localhost:8080/SpringFileUpload/uploadFile`, formData, options)
            .map(res => res.json())
            .catch(error => Observable.throw(error))
            .subscribe(
                data => console.log('success'),
                error => console.log(error)
            )
	  //myExtObject.fileChange1();
  }
}

@NgModule({
  imports: [ BrowserModule,HttpModule],
  declarations: [ App ],
  bootstrap: [ App ]
})
export class AppModule {}