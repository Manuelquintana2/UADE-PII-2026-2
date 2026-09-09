/**
 * Demuestra que PilaTopeFinal y PilaTopeInicio son
 * dos implementaciones distintas del MISMO TDA (Pila<T>):
 * se usan de forma identica a traves de la interfaz,
 * pero tienen un costo muy distinto en apilar/desapilar.
 */
public class Main {

    public static void main(String[] args) {

        // --- 1) Uso funcional basico: mismo codigo, dos implementaciones ---
        System.out.println("=== Uso basico ===");
        probarPila(new PilaTopeFinal<Integer>(5), "PilaTopeFinal");
        probarPila(new PilaTopeInicio<Integer>(5), "PilaTopeInicio");

        // --- 2) Comparacion de eficiencia con muchos elementos ---
        System.out.println("\n=== Comparacion de tiempos (apilar N elementos) ===");
        int n = 50_000;

        long t0 = System.nanoTime();
        Pila<Integer> pilaFinal = new PilaTopeFinal<>(n);
        for (int i = 0; i < n; i++) {
            pilaFinal.apilar(i);
        }
        long t1 = System.nanoTime();
        System.out.printf("PilaTopeFinal  (tope al final):  %d ms%n",
                (t1 - t0) / 1_000_000);

        long t2 = System.nanoTime();
        Pila<Integer> pilaInicio = new PilaTopeInicio<>(n);
        for (int i = 0; i < n; i++) {
            pilaInicio.apilar(i);
        }
        long t3 = System.nanoTime();
        System.out.printf("PilaTopeInicio (tope al inicio): %d ms%n",
                (t3 - t2) / 1_000_000);

        System.out.println(
            "\nAmbas cumplen exactamente el mismo TDA (interfaz Pila<T>).\n" +
            "La diferencia de tiempo es pura implementacion: correr\n" +
            "elementos en cada apilar/desapilar (O(n)) vs. solo mover\n" +
            "un indice (O(1))."
        );
    }

    // Trabaja sobre la interfaz Pila<T>, no sobre la implementacion
    // concreta: esto es justamente lo que significa "mismo TDA".
    private static void probarPila(Pila<Integer> pila, String nombre) {
        System.out.println("-- " + nombre + " --");
        System.out.println("esVacia(): " + pila.esVacia()); // true

        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);

        System.out.println("tope(): " + pila.tope());       // 3
        System.out.println("desapilar(): " + pila.desapilar()); // 3
        System.out.println("tope() ahora: " + pila.tope());     // 2
        System.out.println("esVacia(): " + pila.esVacia());     // false
        System.out.println();
    }
}
