package br.com.cvc.DesafioCVC.service;

import br.com.cvc.DesafioCVC.model.dto.HotelDTO;

public interface HotelsService {
	
	public HotelDTO[] getHotels(int idCidade);

}
