import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReservationService } from '../../services/reservation.service';
import { ReservationModel } from '../../models/reservation-model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-formulario-reservas',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    HttpClientModule
  ],
  templateUrl: './formulario-reservas.component.html',
  styleUrl: './formulario-reservas.component.css'
})
export class FormularioReservasComponent {
  reservaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private reservaService: ReservationService,
    public dialogRef: MatDialogRef<FormularioReservasComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: ReservationModel
  ) {
    this.reservaForm = this.fb.group({
      idSpace: [data.idSpace || '',
      Validators.required],
      starTime: [
        data.starTime || '',
        Validators.required
      ],
      endTime: [data.endTime || '', Validators.required],
      idUser: [data.idUser || '', Validators.required]
    });
  }
  onSubmit(): void {
    if (this.reservaForm.valid) {
      const reserva: ReservationModel = this.reservaForm.value;
      if (this.data.id) {
        reserva.id = this.data.id;
        this.reservaService.editarReserva(reserva)
          .subscribe(response => {
            alert('Reserva actualizada exitosamente');
            this.dialogRef.close(true);
          },
            error => {
              alert('Error al actualizar la reserva');
            });
      }
      else {
        this.reservaService.crearReserva(reserva)
          .subscribe(response => {
            alert('Reserva creada exitosamente');
            this.dialogRef.close(true);
          }, error => {
            alert('Error al crear la reserva');
          });
      }
    }
  }

  onCancel(): void { this.dialogRef.close(); }
}