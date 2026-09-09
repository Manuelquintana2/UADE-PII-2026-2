/**
 * TDA Pila (genérico).
 *
 * Esta interfaz define el CONTRATO del TDA: qué operaciones existen y
 * qué garantizan (pre/post condiciones), sin decir nada sobre cómo se
 * implementan internamente. Cualquier clase que la implemente -con
 * arreglo, con lista enlazada, con el tope al inicio o al final- es una
 * pila válida mientras respete este contrato.
 */
public interface Pila<T> {

    /**
     * apilar(x)
     * post: x queda en el tope de la pila
     */
    void apilar(T x);

    /**
     * desapilar()
     * pre:  la pila no esta vacia
     * post: se elimina el elemento del tope y se devuelve
     */
    T desapilar();

    /**
     * tope()
     * pre:  la pila no esta vacia
     * post: devuelve el elemento del tope sin eliminarlo
     */
    T tope();

    /**
     * esVacia()
     * post: devuelve true si la pila no tiene elementos
     */
    boolean esVacia();

    /**
     * esLlena()
     * post: devuelve true si la pila alcanzo su capacidad maxima
     */
    boolean esLlena();
}
