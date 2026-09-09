/**
 * Cola con prioridad de ENTEROS, implementada con arreglo ESTATICO
 * que se mantiene siempre ORDENADO ascendentemente por prioridad.
 *
 * Invariante: datos[0 .. cantidad-1] esta ordenado de menor a mayor,
 * por eso el elemento de mayor prioridad vive siempre en la misma
 * posicion: datos[cantidad - 1].
 *
 * Costo: encolar() es O(n) (busca el lugar e inserta corriendo),
 * desencolar()/obtenerPrimero() son O(1) (el maximo ya esta ubicado).
 */
public class ColaPrioridadOrdenada {

    private final int[] datos; // ordenado ascendente
    private int cantidad;      // cuantos elementos hay
    private int capacidad;

    public ColaPrioridadOrdenada(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new int[capacidad];
        this.cantidad = 0;
    }

    /** post: x queda incorporado, respetando el orden ascendente */
    public void encolar(int x) {
        if (esLlena()) {
            throw new IllegalStateException("Cola llena");
        }
        int i = cantidad - 1;
        while (i >= 0 && datos[i] > x) {
            datos[i + 1] = datos[i]; // corre los mayores a la derecha
            i--;
        }
        datos[i + 1] = x;
        cantidad++; // O(n) en el peor caso
    }

    /**
     * pre:  la cola no esta vacia
     * post: se elimina el elemento de mayor prioridad y se devuelve
     */
    public int desencolar() {
        if (esVacia()) {
            throw new IllegalStateException("Cola vacia");
        }
        cantidad--;
        return datos[cantidad]; // O(1) -- el mayor siempre esta al final
    }

    /**
     * pre:  la cola no esta vacia
     * post: devuelve el elemento de mayor prioridad sin eliminarlo
     */
    public int obtenerPrimero() {
        if (esVacia()) {
            throw new IllegalStateException("Cola vacia");
        }
        return datos[cantidad - 1]; // O(1)
    }

    /** post: devuelve true si la cola no tiene elementos */
    public boolean esVacia() {
        return cantidad == 0;
    }

    /** post: devuelve true si la cola alcanzo su capacidad maxima */
    public boolean esLlena() {
        return cantidad == capacidad;
    }

    // ---- demo ----
    public static void main(String[] args) {
        ColaPrioridadOrdenada cp = new ColaPrioridadOrdenada(6);
        System.out.println("esVacia(): " + cp.esVacia()); // true

        int[] valores = { 30, 10, 50, 20, 40 };
        for (int v : valores) {
            cp.encolar(v);
        }

        System.out.println("obtenerPrimero(): " + cp.obtenerPrimero()); // 50
        System.out.println("desencolar(): " + cp.desencolar());        // 50
        System.out.println("desencolar(): " + cp.desencolar());        // 40
        System.out.println("obtenerPrimero() ahora: " + cp.obtenerPrimero()); // 30

        // encolar() es O(n): a mas elementos, mas caro insertar
        int n = 20_000;
        ColaPrioridadOrdenada grande = new ColaPrioridadOrdenada(n);
        java.util.Random rnd = new java.util.Random(42);
        long t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            grande.encolar(rnd.nextInt());
        }
        long t1 = System.nanoTime();
        System.out.printf("Encolar %d elementos (ordenada): %d ms%n", n, (t1 - t0) / 1_000_000);
    }
}
