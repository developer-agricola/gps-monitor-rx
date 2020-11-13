import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GpsMapFindComponent } from './gps-map-find.component';

describe('GpsMapFindComponent', () => {
  let component: GpsMapFindComponent;
  let fixture: ComponentFixture<GpsMapFindComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GpsMapFindComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GpsMapFindComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
