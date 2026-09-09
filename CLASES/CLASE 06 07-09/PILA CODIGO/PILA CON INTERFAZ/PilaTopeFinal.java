/**
 * Implementacion de Pila con arreglo, tope ubicado en la
 * ULTIMA POSICION OCUPADA del arreglo.
 *
 * Ventaja: apilar y desapilar son O(1), porque solo se toca
 * la posicion 'tope' y se mueve un indice. No hay que correr
 * ningun elemento.
 */
public class PilaTopeFinal<T> implements Pila<T> {

    private final Object[] datos;
    private int tope; // indice de la ultima posicion ocupada; -1 si esta vacia

    public PilaTopeFinal(int capacidad) {
        this.datos = new Object[capacidad];
        this.tope = -1;
    }

    @Override
    public void apilar(T x) {
        if (esLlena()) {
            throw new IllegalStateException("La pila esta llena");
        }
        tope++;
        datos[tope] = x; // O(1)
    }

    @Override
    @SuppressWarnings("unchecked")
    public T desapilar() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        T elemento = (T) datos[tope];
        datos[tope] = null; // evita retener la referencia
        tope--;
        return elemento; // O(1)
    }

    @Override
    @SuppressWarnings("unchecked")
    public T tope() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        return (T) datos[tope]; // O(1)
    }

    @Override
    public boolean esVacia() {
        return tope == -1;
    }

    @Override
    public boolean esLlena() {
        return tope == datos.length - 1;
    }
}
