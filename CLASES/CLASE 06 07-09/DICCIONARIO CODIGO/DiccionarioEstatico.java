/**
 * Diccionario de ENTEROS (clave -> valor), implementado con arreglo
 * ESTATICO SIN ningun orden particular entre las claves.
 *
 * Cada elemento del arreglo guarda un par (clave, valor). Las claves
 * no tienen que mantener ningun criterio de ordenamiento, asi que
 * elementos[0 .. cantidad-1] simplemente van quedando en el orden en
 * que se agregaron.
 *
 * Igual que en Conjunto desordenado, al eliminar una clave no hace
 * falta correr el resto del arreglo: alcanza con reemplazar la
 * posicion eliminada por el ultimo elemento y decrementar cantidad.
 *
 * Costo: agregar(), eliminar() y recuperar() son O(n) -- el costo
 * real esta en encontrar la clave (claveAIndice()), no en tocar el
 * arreglo. esVacio()/esLleno() son O(1). claves() es O(n).
 */
public class DiccionarioEstatico {

    class Elemento {
        int clave;
        int valor;
    }

    private Elemento[] elementos;
    private int cantidad;
    private int capacidad;

    public DiccionarioEstatico(int capacidad) {
        this.capacidad = capacidad;
        this.elementos = new Elemento[capacidad];
        this.cantidad = 0;
    }

    // busca la posicion de clave recorriendo todo el arreglo -- O(n)
    private int claveAIndice(int clave) {
        int i = cantidad - 1;
        while (i >= 0 && elementos[i].clave != clave) {
            i--;
        }
        return i;
    }

    /**
     * post: valor queda asociado a clave; si clave ya existia,
     *       se sobreescribe el valor (no se duplica la clave)
     */
    public void agregar(int clave, int valor) {
        int pos = claveAIndice(clave);
        if (pos == -1) {
            if (esLleno()) {
                throw new RuntimeException("Diccionario lleno");
            }
            pos = cantidad;
            elementos[pos] = new Elemento();
            elementos[pos].clave = clave;
            cantidad++;
        }
        elementos[pos].valor = valor; // actualizo valor
    }

    /**
     * post: se elimina clave y su valor asociado; si clave no
     *       existia, el diccionario no cambia
     */
    public void eliminar(int clave) {
        int pos = claveAIndice(clave);
        if (pos != -1) {
            elementos[pos] = elementos[cantidad - 1]; // swap con el ultimo
            cantidad--;
        }
    }

    /**
     * pre:  clave pertenece al diccionario
     * post: devuelve el valor asociado a clave
     */
    public int recuperar(int clave) {
        int pos = claveAIndice(clave);
        return elementos[pos].valor;
    }

    /**
     * post: devuelve el conjunto de todas las claves del diccionario
     *       (aca, un arreglo, ya que esta version es toda de enteros)
     */
    public int[] claves() {
        int[] c = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            c[i] = elementos[i].clave;
        }
        return c;
    }

    /** post: devuelve true si el diccionario no tiene claves */
    public boolean esVacio() {
        return cantidad == 0;
    }

    /** post: devuelve true si el diccionario alcanzo su capacidad maxima */
    public boolean esLleno() {
        return cantidad == capacidad;
    }

    // ---- demo ----
    public static void main(String[] args) {
        DiccionarioEstatico d = new DiccionarioEstatico(6);
        System.out.println("esVacio(): " + d.esVacio()); // true

        d.agregar(1, 100);
        d.agregar(2, 200);
        d.agregar(3, 300);
        System.out.println("recuperar(2): " + d.recuperar(2)); // 200

        d.agregar(2, 999); // clave repetida -> sobreescribe, no duplica
        System.out.println("recuperar(2) tras sobreescribir: " + d.recuperar(2)); // 999
        System.out.println("cantidad de claves: " + d.claves().length); // 3

        d.eliminar(1);
        System.out.println("esVacio() tras eliminar 1 clave (quedan 2): " + d.esVacio()); // false
        System.out.print("claves restantes: ");
        for (int c : d.claves()) {
            System.out.print(c + " ");
        }
        System.out.println();

        // agregar()/eliminar()/recuperar() son O(n): a mas claves,
        // mas caro encontrar una (claveAIndice recorre todo).
        int n = 50_000;
        DiccionarioEstatico grande = new DiccionarioEstatico(n);
        long t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            grande.agregar(i, i * 10);
        }
        long t1 = System.nanoTime();
        System.out.printf("Agregar %d claves: %d ms%n", n, (t1 - t0) / 1_000_000);

        t0 = System.nanoTime();
        int v = grande.recuperar(n - 1); // peor caso: la ultima en quedar
        t1 = System.nanoTime();
        System.out.printf("recuperar() en diccionario de %d claves: %d (%d ns)%n", n, v, (t1 - t0));
    }
}
