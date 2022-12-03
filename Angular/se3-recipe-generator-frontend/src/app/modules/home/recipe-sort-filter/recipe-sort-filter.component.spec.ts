import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeSortFilterComponent } from './recipe-sort-filter.component';

describe('RecipeSortFilterComponent', () => {
  let component: RecipeSortFilterComponent;
  let fixture: ComponentFixture<RecipeSortFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeSortFilterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipeSortFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
