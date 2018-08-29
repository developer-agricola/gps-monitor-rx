package cl.gps.monitor.rx.message.routing.domain.aggregate.model.device;

import lombok.Data;

@Data
public class Geo {

    private double latitud;
    private double longitud;

	public Geo(double latitud,  double longitud) {
		assertNotEmpty(latitud);
		assertNotEmpty(longitud);

		this.latitud = latitud;
		this.longitud = longitud;
	}

	private void assertNotEmpty(double coordinates) {
		if(coordinates == 0) {
			throw new IllegalArgumentException("No es una coordenanda valida");
		}
	}
}
     
              
