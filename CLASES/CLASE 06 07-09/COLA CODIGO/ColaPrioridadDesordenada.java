/**
 * Cola con prioridad de ENTEROS, implementada con arreglo ESTATICO
 * SIN ningun orden particular.
 *
 * No hay invariante sobre como quedan acomodados los elementos en
 * datos[0 .. cantidad-1]. Eso es lo que hace rapido a encolar() y
 * lento a desencolar()/obtenerPrimero().
 *
 * Costo: encolar() es O(1) (agrega al final, sin acomodar nada),
 * desencolar()/obtenerPrimero() son O(n) (hay que recorrer todo
 * para encontrar el de mayor prioridad).
 */
public class ColaPrioridadDesordenada {

    private final int[] datos; // sin ningun orden particular
    private int cantidad;
    private int capacidad;

    public ColaPrioridadDesordenada(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new int[capacidad];
        this.cantidad = 0;
    }

    /** post: x queda incorporado, en cualquier posicion libre */
    public void encolar(int x) {
        if (esLlena()) {
            throw new IllegalStateException("Cola llena");
        }
        datos[cantidad] = x; // O(1) -- no hay que acomodar nada
        cantidad++;
    }

    // busca el indice del elemento de mayor prioridad -- O(n)
    private int indiceDelMaximo() {
        int idxMax = 0;
        for (int i = 1; i < cantidad; i++) {
            if (datos[i] > datos[idxMax]) {
                idxMax = i;
            }
        }
        return idxMax;
    }

    /**
     * pre:  la cola no esta vacia
     * post: se elimina el elemento de mayor prioridad y se devuelve
     */
    public int desencolar() {
        if (esVacia()) {
            throw new IllegalStateException("Cola vacia");
        }
        int idx = indiceDelMaximo();       // O(n) -- hay que buscarlo
        int maximo = datos[idx];
        datos[idx] = datos[cantidad - 1];  // lo reemplazo por el ultimo
        cantidad--;                        // evita correr todo el resto
        return maximo;
    }

    /**
     * pre:  la cola no esta vacia
     * post: devuelve el elemento de mayor prioridad sin eliminarlo
     */
    public int obtenerPrimero() {
        if (esVacia()) {
            throw new IllegalStateException("Cola vacia");
        }
        return datos[indiceDelMaximo()]; // O(n)
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
        ColaPrioridadDesordenada cp = new ColaPrioridadDesordenada(6);
        System.out.println("esVacia(): " + cp.esVacia()); // true

        int[] valores = { 30, 10, 50, 20, 40 };
        for (int v : valores) {
            cp.encolar(v);
        }

        System.out.println("obtenerPrimero(): " + cp.obtenerPrimero()); // 50
        System.out.println("desencolar(): " + cp.desencolar());        // 50
        System.out.println("desencolar(): " + cp.desencolar());        // 40
        System.out.println("obtenerPrimero() ahora: " + cp.obtenerPrimero()); // 30

        // encolar() es O(1) sin importar cuantos elementos ya haya
        int n = 20_000;
        ColaPrioridadDesordenada grande = new ColaPrioridadDesordenada(n);
        java.util.Random rnd = new java.util.Random(42);
        long t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            grande.encolar(rnd.nextInt());
        }
        long t1 = System.nanoTime();
        System.out.printf("Encolar %d elementos (desordenada): %d ms%n", n, (t1 - t0) / 1_000_000);
    }
}
