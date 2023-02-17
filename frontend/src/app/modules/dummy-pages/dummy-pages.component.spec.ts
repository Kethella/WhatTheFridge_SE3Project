import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DummyPagesComponent } from './dummy-pages.component';

describe('DummyPagesComponent', () => {
  let component: DummyPagesComponent;
  let fixture: ComponentFixture<DummyPagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DummyPagesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DummyPagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
