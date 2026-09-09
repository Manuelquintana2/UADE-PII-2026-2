/**
 * TDA Pila de ENTEROS, implementada con arreglo, con el TOPE AL INICIO
 * (siempre en el indice 0 del arreglo).
 *
 * Desventaja: apilar y desapilar son O(n), porque cada vez
 * hay que correr TODOS los demas elementos una posicion
 * (para abrir o cerrar el hueco en el indice 0).
 *
 * Cumple exactamente el mismo TDA que PilaTopeFinal (mismas
 * operaciones y mismo comportamiento LIFO), solo cambia el
 * costo de la implementacion.
 */
public class PilaTopeInicio {

    private final int[] datos;
    private int cantidad; // cantidad de elementos ocupados
    private int capacidad; // capacidad maxima de la pila

    public PilaTopeInicio(int capacidad) {
        this.datos = new int[capacidad];
        this.cantidad = 0;
        this.capacidad = capacidad;
    }

    /** post: x queda en el tope de la pila */
    public void apilar(int x) {
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

    /**
     * pre:  la pila no esta vacia
     * post: se elimina el elemento del tope y se devuelve
     */
    public int desapilar() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        int elemento = datos[0];
        // corre todos los elementos una posicion hacia la izquierda
        // para cerrar el hueco que deja el indice 0
        for (int i = 0; i < cantidad - 1; i++) {
            datos[i] = datos[i + 1]; // O(n)
        }
        cantidad--;
        return elemento;
    }

    /**
     * pre:  la pila no esta vacia
     * post: devuelve el elemento del tope sin eliminarlo
     */
    public int tope() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        return datos[0]; // O(1): consultar el tope no requiere correr nada
    }

    /** post: devuelve true si la pila no tiene elementos */
    public boolean esVacia() {
        return cantidad == 0;
    }

    /** post: devuelve true si la pila alcanzo su capacidad maxima */
    public boolean esLlena() {
        return cantidad == capacidad;
    }

    // ---- demo ----
    public static void main(String[] args) {
        PilaTopeInicio pila = new PilaTopeInicio(5);
        System.out.println("esVacia(): " + pila.esVacia()); // true

        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);

        System.out.println("tope(): " + pila.tope());            // 3
        System.out.println("desapilar(): " + pila.desapilar());  // 3
        System.out.println("tope() ahora: " + pila.tope());      // 2
        System.out.println("esVacia(): " + pila.esVacia());      // false

        // comparacion de eficiencia
        int n = 50_000;
        long t0 = System.nanoTime();
        PilaTopeInicio grande = new PilaTopeInicio(n);
        for (int i = 0; i < n; i++) {
            grande.apilar(i);
        }
        long t1 = System.nanoTime();
        System.out.printf("Apilar %d elementos (tope al inicio): %d ms%n",
                n, (t1 - t0) / 1_000_000);
    }
}
