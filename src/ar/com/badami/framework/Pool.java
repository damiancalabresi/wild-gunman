package ar.com.badami.framework;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {

	public interface PoolObjectFactory<T> {
		public T createObject();
	}

	private final List<T> freeObjects;
	private final PoolObjectFactory<T> factory;
	private final int maxSize;

	// Se le pasa el factory al construir el pool
	public Pool(PoolObjectFactory<T> factory, int maxSize) {
		this.factory = factory;
		this.maxSize = maxSize;
		this.freeObjects = new ArrayList<T>(maxSize);
	}

	public T newObject() {
		T object = null;
		// Saca un objeto del final de la lista y lo devuelve. Si está vacía usa
		// el factory recibido en el constructor para crear un nuevo objeto.
		if (freeObjects.isEmpty())
			object = factory.createObject();
		else
			object = freeObjects.remove(freeObjects.size() - 1);
		return object;
	}

	public void free(T object) {
		// Agrega el objeto al pool si tiene lugar
		if (freeObjects.size() < maxSize)
			freeObjects.add(object);
	}
}
