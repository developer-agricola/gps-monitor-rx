import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GpsMapListComponent } from './gps-map-list.component';

describe('GpsMapListComponent', () => {
  let component: GpsMapListComponent;
  let fixture: ComponentFixture<GpsMapListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GpsMapListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GpsMapListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
