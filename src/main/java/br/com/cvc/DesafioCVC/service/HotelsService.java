package br.com.cvc.DesafioCVC.service;

import java.util.List;

import br.com.cvc.DesafioCVC.model.Hotel;
import br.com.cvc.DesafioCVC.model.dto.HotelDTO;

public interface HotelsService {
	public HotelDTO[] getHotelDetail(int idHotel);
	public HotelDTO[] getHotels(int idCidade);
	public List<Hotel> setTotalPrice(List<Hotel> hotels,
			String checkIn, 
			String checkOut, 
			Integer qtdPax, 
			Integer qtdChd);
	
}
