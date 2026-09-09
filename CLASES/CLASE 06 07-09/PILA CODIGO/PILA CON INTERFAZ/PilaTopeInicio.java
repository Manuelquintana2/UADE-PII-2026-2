/**
 * Implementacion de Pila con arreglo, tope ubicado en la
 * PRIMERA POSICION (indice 0) del arreglo.
 *
 * Desventaja: apilar y desapilar son O(n), porque cada vez
 * que se agrega o quita el elemento del tope hay que correr
 * TODOS los demas elementos una posicion (para abrir o cerrar
 * el hueco en el indice 0).
 *
 * Se incluye solo con fines didacticos, para comparar contra
 * PilaTopeFinal: mismo TDA (misma interfaz Pila<T>), pero con
 * un costo muy distinto.
 */
public class PilaTopeInicio<T> implements Pila<T> {

    private final Object[] datos;
    private int cantidad; // cantidad de elementos ocupados

    public PilaTopeInicio(int capacidad) {
        this.datos = new Object[capacidad];
        this.cantidad = 0;
    }

    @Override
    public void apilar(T x) {
        if (esLlena()) {
            throw new IllegalStateException("La pila esta llena");
        }
        // corre todos los elementos una posicion hacia la derecha
        // para dejar libre el indice 0
        for (int i = cantidad; i > 0; i--) {
            datos[i] = datos[i - 1]; // O(n)
        }
        datos[0] = x;
        cantidad++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T desapilar() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        T elemento = (T) datos[0];
        // corre todos los elementos una posicion hacia la izquierda
        // para cerrar el hueco que deja el indice 0
        for (int i = 0; i < cantidad - 1; i++) {
            datos[i] = datos[i + 1]; // O(n)
        }
        datos[cantidad - 1] = null;
        cantidad--;
        return elemento;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T tope() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        return (T) datos[0]; // O(1): consultar el tope no requiere correr nada
    }

    @Override
    public boolean esVacia() {
        return cantidad == 0;
    }

    @Override
    public boolean esLlena() {
        return cantidad == datos.length;
    }
}
