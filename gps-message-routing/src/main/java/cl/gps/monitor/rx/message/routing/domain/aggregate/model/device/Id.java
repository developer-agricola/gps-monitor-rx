package cl.gps.monitor.rx.message.routing.domain.aggregate.model.device;

import lombok.Data;

@Data
public class Id {
	
	private long difusionValue;
	private long equipoValue;
	
	public Id(long difusionValue) {
		assertNotEmpty(difusionValue);
		//
		this.difusionValue = difusionValue;
	}

	public Id(long difusionValue, long equipoValue) {
		assertNotEmpty(difusionValue);
		assertNotEmpty(equipoValue);
		//
		this.difusionValue = difusionValue;
		this.equipoValue = equipoValue;
	}

	private void assertNotEmpty(long id) {
		if(id <= 0) {
			throw new IllegalArgumentException("El identificador debe ser un correlativo");
		}
	}
}
