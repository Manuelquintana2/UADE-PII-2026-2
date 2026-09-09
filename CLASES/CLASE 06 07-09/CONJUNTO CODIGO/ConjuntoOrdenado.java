/**
 * Conjunto de ENTEROS, implementado con arreglo ESTATICO que se
 * mantiene siempre ORDENADO ascendentemente y SIN elementos repetidos.
 *
 * Invariante: datos[0 .. cantidad-1] esta ordenado de menor a mayor
 * y no tiene repetidos. Ese orden es lo que permite usar busqueda
 * binaria en pertenece().
 *
 * Costo: pertenece() es O(log n) (busqueda binaria). agregar() y
 * sacar() siguen siendo O(n): encontrar la posicion es rapido, pero
 * insertar o borrar en el medio de un array ordenado obliga a correr
 * el resto de los elementos.
 */
public class ConjuntoOrdenado {

    private int[] datos; // ordenado ascendente, sin repetidos
    private int cantidad;
    private int capacidad;

    public ConjuntoOrdenado(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new int[capacidad];
        this.cantidad = 0;
    }

    // busca el indice de x mediante busqueda binaria -- O(log n)
    private int indiceDe(int x) {
        int izq = 0, der = cantidad - 1;
        while (izq <= der) {
            int medio = (izq + der) / 2;
            if (datos[medio] == x) {
                return medio;
            } else if (datos[medio] < x) {
                izq = medio + 1;
            } else {
                der = medio - 1;
            }
        }
        return -1;
    }

    /** post: devuelve true si x pertenece al conjunto */
    public boolean pertenece(int x) {
        return indiceDe(x) != -1;
    }

    /**
     * pre:  x no pertenece al conjunto
     * post: x queda incorporado, respetando el orden ascendente
     */
    public void agregar(int x) {
        if (esLleno()) {
            throw new RuntimeException("Conjunto lleno");
        }
        if (pertenece(x)) {
            throw new IllegalArgumentException("x ya esta");
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
     * pre:  x pertenece al conjunto
     * post: x queda eliminado, se mantiene el orden
     */
    public void sacar(int x) {
        int idx = indiceDe(x);
        if (idx == -1) {
            throw new IllegalArgumentException("x no esta");
        }
        for (int i = idx; i < cantidad - 1; i++) {
            datos[i] = datos[i + 1]; // corre los siguientes a la izquierda
        }
        cantidad--; // O(n)
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
        ConjuntoOrdenado c = new ConjuntoOrdenado(6);
        System.out.println("esVacio(): " + c.esVacio()); // true

        int[] valores = { 30, 10, 50, 20, 40 };
        for (int v : valores) {
            c.agregar(v);
        }

        System.out.println("pertenece(20): " + c.pertenece(20)); // true
        System.out.println("pertenece(99): " + c.pertenece(99)); // false

        c.sacar(30);
        System.out.println("pertenece(30) tras sacar: " + c.pertenece(30)); // false

        // pertenece() es O(log n): la cantidad de comparaciones crece
        // muy despacio aunque el conjunto sea enorme.
        int n = 50_000;
        ConjuntoOrdenado grande = new ConjuntoOrdenado(n);
        for (int i = 0; i < n; i++) {
            grande.agregar(i); // ya vienen ordenados: peor caso de agregar()
        }
        long t0 = System.nanoTime();
        boolean encontrado = grande.pertenece(n - 1);
        long t1 = System.nanoTime();
        System.out.printf("pertenece() en conjunto de %d elementos: %s (%d ns)%n",
                n, encontrado, (t1 - t0));
    }
}
