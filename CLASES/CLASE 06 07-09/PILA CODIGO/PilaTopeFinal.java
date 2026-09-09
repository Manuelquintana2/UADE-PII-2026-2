/**
 * TDA Pila de ENTEROS, implementada con arreglo, con el TOPE AL FINAL
 * (en la ultima posicion ocupada del arreglo).
 *
 * Ventaja: apilar y desapilar son O(1), porque solo se mueve
 * un indice y se lee/escribe esa posicion. No hace falta correr
 * ningun otro elemento.
 */
public class PilaTopeFinal {

    private final int[] datos;
    private int tope; // indice de la ultima posicion ocupada; -1 si esta vacia
    private int capacidad; // capacidad maxima de la pila

    public PilaTopeFinal(int capacidad) {
        this.datos = new int[capacidad];
        this.tope = -1;
        this.capacidad = capacidad;
    }

    /** post: x queda en el tope de la pila */
    public void apilar(int x) {
        if (esLlena()) {
            throw new IllegalStateException("La pila esta llena");
        }
        tope++;
        datos[tope] = x; // O(1)
    }

    /**
     * pre:  la pila no esta vacia
     * post: se elimina el elemento del tope y se devuelve
     */
    public int desapilar() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        int elemento = datos[tope];
        tope--;
        return elemento; // O(1)
    }

    /**
     * pre:  la pila no esta vacia
     * post: devuelve el elemento del tope sin eliminarlo
     */
    public int tope() {
        if (esVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        return datos[tope]; // O(1)
    }

    /** post: devuelve true si la pila no tiene elementos */
    public boolean esVacia() {
        return tope == -1;
    }

    /** post: devuelve true si la pila alcanzo su capacidad maxima */
    public boolean esLlena() {
        return tope == capacidad - 1;
    }

    // ---- demo ----
    public static void main(String[] args) {
        PilaTopeFinal pila = new PilaTopeFinal(5);
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
        PilaTopeFinal grande = new PilaTopeFinal(n);
        for (int i = 0; i < n; i++) {
            grande.apilar(i);
        }
        long t1 = System.nanoTime();
        System.out.printf("Apilar %d elementos (tope al final): %d ms%n",
                n, (t1 - t0) / 1_000_000);
    }
}
