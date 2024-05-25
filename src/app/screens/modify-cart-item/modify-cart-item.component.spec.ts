import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyCartItemComponent } from './modify-cart-item.component';

describe('ModifyCartItemComponent', () => {
  let component: ModifyCartItemComponent;
  let fixture: ComponentFixture<ModifyCartItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyCartItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyCartItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
