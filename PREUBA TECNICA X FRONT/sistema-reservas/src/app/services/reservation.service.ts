import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservationModel } from '../models/reservation-model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private apiUrl = 'http://localhost:8083/api/reservation/v1';
  // URL de tu API 
  constructor(private http: HttpClient) { } 
  
  obtenerReservas(): Observable<ReservationModel[]> { 
    return this.http.get<ReservationModel[]>(this.apiUrl); 
  } 
  
  crearReserva(reserva: ReservationModel): Observable<ReservationModel> { 
    return this.http.post<ReservationModel>(this.apiUrl, reserva); 
  } 

  editarReserva(reserva: ReservationModel): Observable<ReservationModel> { 
    return this.http.put<ReservationModel>(`${this.apiUrl}/${reserva.id}`, reserva);
  }
  
  cancelarReserva(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}