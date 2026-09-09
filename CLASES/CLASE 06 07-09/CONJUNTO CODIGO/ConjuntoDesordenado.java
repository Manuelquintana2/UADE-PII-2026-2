/**
 * Conjunto de ENTEROS, implementado con arreglo ESTATICO SIN ningun
 * orden particular (aunque nunca tiene repetidos).
 *
 * Sin invariante de orden: datos[0 .. cantidad-1] no tiene ningun
 * orden particular. Sin orden no hay forma de "saltear" mitades:
 * pertenece() tiene que mirar todo en el peor caso.
 *
 * Costo: pertenece() es O(n) (recorrido lineal). agregar() es O(n)
 * por el chequeo de duplicados (pertenece()), no por el append, que
 * es O(1). sacar() aprovecha que el orden no importa: una vez
 * encontrado el indice, reemplaza por el ultimo elemento -- O(1).
 * Lo unico caro en toda la clase es encontrar el indice.
 */
public class ConjuntoDesordenado {

    private int[] datos; // sin orden, sin repetidos
    private int cantidad;
    private int capacidad;

    public ConjuntoDesordenado(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new int[capacidad];
        this.cantidad = 0;
    }

    // busca el indice de x recorriendo todo el array -- O(n)
    private int indiceDe(int x) {
        for (int i = 0; i < cantidad; i++) {
            if (datos[i] == x) return i;
        }
        return -1;
    }

    /** post: devuelve true si x pertenece al conjunto */
    public boolean pertenece(int x) {
        return indiceDe(x) != -1;
    }

    /**
     * pre:  x no pertenece al conjunto
     * post: x queda incorporado, en cualquier posicion libre
     */
    public void agregar(int x) {
        if (esLleno()) {
            throw new RuntimeException("Conjunto lleno");
        }
        if (pertenece(x)) {
            throw new IllegalArgumentException("x ya esta");
        }
        datos[cantidad] = x;
        cantidad++;
    }

    /**
     * pre:  x pertenece al conjunto
     * post: x queda eliminado
     */
    public void sacar(int x) {
        int idx = indiceDe(x);
        if (idx == -1) {
            throw new IllegalArgumentException("x no esta");
        }
        datos[idx] = datos[cantidad - 1]; // lo reemplazo por el ultimo
        cantidad--; // evita correr todo el resto
    }

    /** post: devuelve true si el conjunto no tiene elementos */
    public boolean esVacio() {
        return cantidad == 0;
    }

    /** post: devuelve true si el conjunto alcanzo su capacidad maxima */
    public boolean esLleno() {
        return cantidad == capacidad;
    }

    // ---- demo ----
    public static void main(String[] args) {
        ConjuntoDesordenado c = new ConjuntoDesordenado(6);
        System.out.println("esVacio(): " + c.esVacio()); // true

        int[] valores = { 30, 10, 50, 20, 40 };
        for (int v : valores) {
            c.agregar(v);
        }

        System.out.println("pertenece(20): " + c.pertenece(20)); // true
        System.out.println("pertenece(99): " + c.pertenece(99)); // false

        c.sacar(30);
        System.out.println("pertenece(30) tras sacar: " + c.pertenece(30)); // false

        // agregar() es O(1) en el append, pero O(n) por el chequeo de
        // duplicados via pertenece() -- a diferencia del ordenado, no
        // hay costo de corrimiento.
        int n = 50_000;
        ConjuntoDesordenado grande = new ConjuntoDesordenado(n);
        long t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            grande.agregar(i);
        }
        long t1 = System.nanoTime();
        System.out.printf("Agregar %d elementos (desordenado): %d ms%n", n, (t1 - t0) / 1_000_000);
    }
}
