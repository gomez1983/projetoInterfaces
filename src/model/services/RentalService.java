package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerDay;
	private Double pricePerHour;
	
	private TaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		super();
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}

	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double hours = (double)(t2 - t1) / 1000 / 60 / 60; //O Java pega a horas em milissegundos. Pra converter essa diferen�a de milissegundos para segundos, divide por 1000. Pra pegar essa diferen�a em segundos e converter pra minutos, divide por 60 (o mesmo de minutos pra horas).
		
		double basicPayment;
		if(hours <= 12.0 ) {
			basicPayment = Math.ceil(hours) * pricePerHour; //A fun��o Math.ceil(x) retorna o menor n�mero inteiro maior ou igual a "x".
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);
	
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}
