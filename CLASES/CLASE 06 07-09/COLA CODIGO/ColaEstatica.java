/**
 * TDA Cola de ENTEROS, implementada con arreglo ESTATICO (no circular).
 *
 * Sigue la especificacion del TDA Cola vista en la Clase 2:
 *   crear() -> Cola
 *   encolar(c: Cola, x: elemento) -> Cola
 *   desencolar(c: Cola) -> Cola
 *   primero(c: Cola) -> elemento
 *   esVacia(c: Cola) -> boolean
 *   esLlena(c: Cola) -> boolean
 *
 * Atributos: un array fijo, "frente" (indice del primer elemento) y
 * "cantidad" (cuantos elementos hay actualmente). encolar() siempre
 * escribe en frente + cantidad; desencolar() siempre lee/avanza frente.
 * frente NUNCA vuelve a 0 automaticamente, por eso esLlena() se define
 * como frente + cantidad == capacidad y no como cantidad == capacidad:
 * el espacio liberado al principio del array queda "perdido" hasta que
 * se implemente una version circular.
 */
public class ColaEstatica {

    private final int[] datos;
    private int frente;   // indice del primer elemento
    private int cantidad; // cuantos elementos hay
    private int capacidad;

    public ColaEstatica(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new int[capacidad];
        this.frente = 0;
        this.cantidad = 0;
    }

    /** post: x queda al final de la cola */
    public void encolar(int x) {
        if (esLlena()) {
            throw new IllegalStateException("Cola llena");
        }
        datos[frente + cantidad] = x; // O(1)
        cantidad++;
    }

    /**
     * pre:  la cola no esta vacia
     * post: se elimina el elemento del frente y se devuelve
     */
    public int desencolar() {
        if (esVacia()) {
            throw new IllegalStateException("Cola vacia");
        }
        int x = datos[frente];
        frente++;   // O(1) — no se corre nada
        cantidad--;
        return x;
    }

    /**
     * pre:  la cola no esta vacia
     * post: devuelve el elemento del frente sin eliminarlo
     */
    public int obtenerPrimero() {
        if (esVacia()) {
            throw new IllegalStateException("Cola vacia");
        }
        return datos[frente]; // O(1)
    }

    /** post: devuelve true si la cola no tiene elementos */
    public boolean esVacia() {
        return cantidad == 0;
    }

    /** post: devuelve true si la cola alcanzo su capacidad maxima */
    public boolean esLlena() {
        return frente + cantidad == capacidad;
    }

    // ---- demo ----
    public static void main(String[] args) {
        ColaEstatica cola = new ColaEstatica(5);
        System.out.println("esVacia(): " + cola.esVacia()); // true

        cola.encolar(10);
        cola.encolar(20);
        cola.encolar(30);

        System.out.println("obtenerPrimero(): " + cola.obtenerPrimero()); // 10
        System.out.println("desencolar(): " + cola.desencolar());        // 10
        System.out.println("obtenerPrimero() ahora: " + cola.obtenerPrimero()); // 20
        System.out.println("esVacia(): " + cola.esVacia()); // false

        // encolar y desencolar son O(1): no se corre ningun elemento,
        // a diferencia de una pila con tope al inicio.
        int n = 50_000;
        ColaEstatica grande = new ColaEstatica(n);
        long t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            grande.encolar(i);
        }
        long t1 = System.nanoTime();
        System.out.printf("Encolar %d elementos: %d ms%n", n, (t1 - t0) / 1_000_000);

        // esLlena() con este diseño no-circular: si desencolamos todo
        // y volvemos a encolar, frente ya avanzo y "sobra" lugar al
        // principio que no se puede reutilizar.
        ColaEstatica chica = new ColaEstatica(3);
        chica.encolar(1);
        chica.encolar(2);
        chica.encolar(3);
        chica.desencolar();
        chica.desencolar();
        chica.desencolar();
        System.out.println("chica.esVacia(): " + chica.esVacia()); // true
        System.out.println("chica.esLlena(): " + chica.esLlena()); // true (!) -- frente=3, cantidad=0, capacidad=3
    }
}
