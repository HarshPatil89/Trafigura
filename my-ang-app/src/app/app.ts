import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { url } from 'inspector';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet,CommonModule, ReactiveFormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'my-ang-app';
  private http = inject(HttpClient);
  public message: string = '';
   positions : any;
  showModal = false;
  transactionForm: FormGroup;

    constructor(private fb: FormBuilder) {
    this.transactionForm = this.fb.group({
      tradeId: ['', Validators.required],
      securityCode: ['REL', Validators.required],
      quantity: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      buySell: ['Buy', Validators.required]
    });
  }

  ngOnInit() {
      this.http.get<any[]>('http://localhost:8080/positions').subscribe(data => {
      this.positions = data
        .filter(item => item.securityCode !== 'string')
        .map(item => ({
          name: item.securityCode,
          value: item.netQuantity > 0 ? `+${item.netQuantity}` : `${item.netQuantity}`
        }))
        .sort((a, b) => {
          const order = ['REL', 'ITC', 'INF'];
          return order.indexOf(a.name) - order.indexOf(b.name);
        });
    });
  }
  openModal() {
    this.showModal = true;
    this.transactionForm.reset({
      Operation: 'Insert',
      Side: 'Buy'
    });
  }

  closeModal() {
    this.showModal = false;
  }

  submitForm() {
    
  }

  onAction(type: 'Insert' | 'Update' | 'Cancel') {
  this.transactionForm.patchValue({ actionType: type });
  var urlVar = 'http://localhost:8080/addTransaction';
  if (this.transactionForm.valid) {
      const formData = this.transactionForm.value;
      fetch('http://localhost:8080/addTransaction', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      })
        .then(response => response.json())
        .then(data => {
          alert('Transaction submitted successfully!');
          this.closeModal();
        })
        .catch(error => {
          alert('Failed to submit transaction.');
        });
    } else {
      this.transactionForm.markAllAsTouched();
    }
    this.showModal = false;
  }
}
