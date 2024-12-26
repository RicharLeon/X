import { Component, OnInit } from '@angular/core';
import { ReservationModel } from '../../models/reservation-model';
import { ReservationService } from '../../services/reservation.service';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { FormularioReservasComponent } from '../formulario-reservas/formulario-reservas.component';
import { HttpClientModule, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';


@Component({
  selector: 'app-pagina-principal',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './pagina-principal.component.html',
  styleUrl: './pagina-principal.component.css'
})
export class PaginaPrincipalComponent implements OnInit {
  reservas: ReservationModel[] = [];

  constructor(private reservaService: ReservationService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.reservaService.obtenerReservas().subscribe(data => {
      this.reservas = data;
    });
  }

  abrirFormulario(reserva?: ReservationModel): void {
    const dialogRef = this.dialog.open(FormularioReservasComponent, {
      width: '400px',
      data: reserva ? { ...reserva } : {}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.ngOnInit();
        // Recarga las reservas 
      }
    });
  }
  cancelarReserva(id: number): void {
    if (confirm("¿Estás seguro de que deseas cancelar esta reserva?")) {
      this.reservaService.cancelarReserva(id).subscribe(() => {
        alert('Reserva cancelada exitosamente');
        this.ngOnInit();

        // Recarga las reservas para reflejar los cambios 
      }, error => {
        alert('Error al cancelar la reserva');

      });
    }

  }
}